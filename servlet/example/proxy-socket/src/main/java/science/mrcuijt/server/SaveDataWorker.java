package science.mrcuijt.server;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SaveDataWorker implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(SaveDataWorker.class);

	/**
	 * The Carriage Return ASCII character value.
	 */
	public static final byte CR = 0x0D;

	/**
	 * The Line Feed ASCII character value.
	 */
	public static final byte LF = 0x0A;

	/**
	 * The dash (-) ASCII character value.
	 */
	public static final byte DASH = 0x2D;

	/**
	 * A byte sequence that marks the end of <code>header-part</code>
	 * (<code>CRLFCRLF</code>).
	 */
	protected static final byte[] HEADER_SEPARATOR = { CR, LF, CR, LF };

	/**
	 * A byte sequence that that follows a delimiter that will be followed by an
	 * encapsulation (<code>CRLF</code>).
	 */
	protected static final byte[] FIELD_SEPARATOR = { CR, LF };

	/**
	 * The maximum length of <code>header-part</code> that will be processed (10
	 * kilobytes = 10240 bytes.).
	 */
	public static final int HEADER_PART_SIZE_MAX = 10240;

	/**
	 * The default length of the buffer used for processing a request.
	 */
	protected static final int DEFAULT_BUFSIZE = 4096;

	// 请求部首的 headMap
	Map<String, String> headMap = new HashMap<String, String>();

	// 请求部首的集合
	ArrayList<String> headPart = new ArrayList<>();

	// 从 Content-Type 中读取到内容类型
	String contentType = null;

	// 从 Content-Type 中读取到的 boundary
	String boundary = null;

	// 从 Content-Length 中读取到的长度
	int contentLength = 0;

	private Socket socket = null;

	public SaveDataWorker(Socket socket) {
		this.socket = socket;
	}

	public void run() {

		logger.info("-------------------------------------------------");
		logger.info("客户端接入成功！");
		// 获得服务器的信息
		logger.info("ip:" + socket.getInetAddress());
		// 服务器开放的端口是服务器随机指定的
		logger.info("客户端端口:" + socket.getLocalPort());// 端口
		logger.info("-------------------------------------------------");

		try {

			logger.info("处理请求报文");

			InputStream is = socket.getInputStream();

			BufferedReader br = new BufferedReader(new InputStreamReader(is, "iso-8859-1"));

			// 处理请求报文主体实体
			StringBuffer stringBuffer = new StringBuffer();

			OutputStream os = new FileOutputStream(new File(System.currentTimeMillis() + ".head"));

			byte[] buffer = new byte[DEFAULT_BUFSIZE];

			int pos = 0;

			byte[] arrayBuffer = new byte[DEFAULT_BUFSIZE]; // 暂与读取到的缓存数组大小保持一致

			int writeLength = 0;

			int readLength = is.read(buffer);

			for (;;) {

				if (pos >= readLength)
					break;

				byte byteItem = buffer[pos];

				// TODO 需要先判断后续操作是否存在数组越界
				if (byteItem == CR && buffer[pos + 1] == LF && buffer[pos + 2] == CR && buffer[pos + 3] == LF) { // 向下读取两行读到连续的两个
																													// \r\n\r\n
																													// 后，HTTP
																													// 首部读取完毕

					if (writeLength > 0) {

						byte[] temp = new byte[writeLength];

						System.arraycopy(arrayBuffer, 0, temp, 0, writeLength);

						os.write(temp);

						headPart.add(new String(temp));

						// TODO 思考是否要将 arrayBuffer 也置空

						writeLength = 0;

					}

					for (String head : headPart) {
						String[] arryHead = head.split(": ");
						if (arryHead.length == 1) {
							headMap.put("protocol", arryHead[0]);
						} else if (arryHead.length == 2) {
							headMap.put(arryHead[0], arryHead[1]);
						}
					}

					if (headMap.get("protocol").startsWith("POST")) {

						try {

							contentLength = Integer.parseInt(headMap.get("Content-Length"));

							byte[] bContentType = headMap.get("Content-Type").getBytes();

							// 获取 ContentType & boundary
							for (int i = 0; i < bContentType.length; i++) {

								if (bContentType[i] == (byte) ';') {
									byte[] temp = new byte[i];
									System.arraycopy(bContentType, 0, temp, 0, temp.length);
									contentType = new String(temp);
//								System.out.println(contentType);
									continue;
								}

								if (bContentType[i] == (byte) '=') {
									byte[] temp = new byte[bContentType.length - i - 1];
									System.arraycopy(bContentType, i + 1, temp, 0, temp.length);
									boundary = new String(temp);
//								System.out.println(boundary);
									break;
								}
							}

						} catch (NumberFormatException e) {
							e.printStackTrace();
						}

						os.write(HEADER_SEPARATOR);

						pos += 4;
					}

					break; // buffer[pos] == \n | buffer[pos + 1] == \n | buffer[pos + 2] == - | buffer[pos
							// + 3] == -

				} else if (byteItem == CR && buffer[pos + 1] == LF) {

					// 本行读取结束

					if (writeLength > 0) {

						byte[] temp = new byte[writeLength];

						System.arraycopy(arrayBuffer, 0, temp, 0, writeLength);

						os.write(temp);

						headPart.add(new String(temp));

						// TODO 思考是否要将 arrayBuffer 也置空

						writeLength = 0;

					}

					os.write(FIELD_SEPARATOR);

					pos += 2; // buffer[pos + 1] == \n 下次循环跳过 \n

					continue;

				}

				// 存入缓存数组中
				arrayBuffer[writeLength] = byteItem;

				writeLength++;

				pos++;

			} // -- headRead loop end

			// Content-Length Body 体的长度

			if (contentLength != 0) {

				// 对齐一下 buffer

				int useLength = 0; // 使用 useLength 代替 buffer

				if (pos > 0) { // 如果有多个 field item 项就需要判断多次
					// 重新对其 buffer
					System.arraycopy(buffer, pos, buffer, 0, buffer.length - pos);
					// 获取 buffer 剩余可用长度
					useLength = buffer.length - pos;
					// 重置 buffer 偏移量
					pos = 0;
				}

				// 判断内容长度是否大于 buffer 剩下可用长度
				if (contentLength > useLength) {
					// 将剩下数据写入到文件中
					os.write(buffer, pos, useLength);
					// 重新计算剩余的内容长度
					contentLength -= useLength;

					int totalRead = 0;

					int i = 0;
//					while (br.ready() && (i = br.read()) != -1) {
					while ((i = br.read()) != -1) {
//						stringBuffer.append((char) i);
						buffer[pos] = (byte) i;
						pos++;
						if (pos == DEFAULT_BUFSIZE) {
							os.write(buffer);
							pos = 0;
						}
						totalRead++;
						if (totalRead == contentLength) {
							break;
						}
					}

					if (pos > 0) {
						os.write(buffer, 0, pos);
					}

				} else {
					// 将内容长度完全写入
					os.write(buffer, pos, contentLength);
				}

			}

//			System.out.println(stringBuffer);

			os.flush();
			os.close();

			// 响应
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			InputStream fis = Class.forName("science.mrcuijt.jaxws.client.WeatherClient")
					.getResourceAsStream("/soapheader.txt");
			byte[] buf = new byte[fis.available()];
			fis.read(buf, 0, buf.length);
			fis.close();

			// 写入 HTTP Header
			baos.write(buf);

			fis = Class.forName("science.mrcuijt.jaxws.client.WeatherClient").getResourceAsStream("/soapmesssage.txt");
			String contentLength = String.format("Content-Length: %d", fis.available());
			buf = new byte[fis.available()];
			fis.read(buf, 0, buf.length);
			fis.close();

			baos.write(FIELD_SEPARATOR);
			// 添加 HTTP Content-Length 头
			baos.write(contentLength.getBytes());
			baos.write(HEADER_SEPARATOR);

			// 添加响应正文
			baos.write(buf);
			logger.info(new String(baos.toByteArray()));
			socket.getOutputStream().write(baos.toByteArray());
//			socket.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
