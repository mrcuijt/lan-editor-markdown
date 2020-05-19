package server;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.xml.messaging.saaj.util.ByteOutputStream;

public class SaveDataWorker3 implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(SaveDataWorker3.class);

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

	static // 请求部首的 headMap
	Map<String, String> headMap = new HashMap<String, String>();

	// 请求部首的集合
	static ArrayList<String> headPart = new ArrayList<>();

	// 从 Content-Type 中读取到内容类型
	String contentType = null;

	// 从 Content-Type 中读取到的 boundary
	String boundary = null;

	// 从 Content-Length 中读取到的长度
	static int contentLength = 0;

	private Socket socket = null;

	public SaveDataWorker3(Socket socket) {
		this.socket = socket;
	}

	/**
	 *  // 读取到 \r\n\r\n 
	 *	// 读取 Content-Length 得到继续读取数据的长度
	 *	// 继续向下读取，
	 *	
	 *	// 读取 HTTP 请求 首部
	 *	// 1. 读取到 \r\n\r\n 为结束
	 *	//   1) 判断本次读取的数据缓存中是否存在 \r\n\r\n
	 *	//   2) i > 4 时反向判断, 当前是 \n -1 \r -2 \n -3 \r , [预防数组越界, 忽略], 直接反向判断
	 *	//   3) 取到 \r\n\r\n 后直接将其写入 ByteArrayOutputStream 然后逐行读取，处理 Content-Type
	 *	//   4) 不存在 Content-Type 将缓冲数组剩余内容写入后，再次进行读取写入，设置超时读取时间，超时结束。
	 */
	@SuppressWarnings("resource")
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

			boolean header = false;

			ByteArrayOutputStream proxyBaos = new ByteArrayOutputStream();
			OutputStream proxyOs = new FileOutputStream((System.currentTimeMillis() + "-proxy.head"));
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			// 判断数组是否存在分界符 \r\n\r\n
			for (; pos < readLength; pos++) {
				if (buffer[pos] == LF && buffer[pos - 1] == CR && buffer[pos - 2] == LF && buffer[pos - 3] == CR) {
					pos++;
					baos.write(buffer, 0, pos);
					header = true;
					break;
				}
			}

			if (header) {
				// 处理 Content-Type
				InputStream inputStream = new ByteArrayInputStream(baos.toByteArray());
				Reader reader = new InputStreamReader(inputStream);
				BufferedReader bufReader = new BufferedReader(reader);
				String line = null;
				while ((line = bufReader.readLine()) != null && !line.equals("")) {
					if (line.startsWith("Host")) {
						proxyBaos.write("Host: www.webxml.com.cn".getBytes());
					} else if (line.startsWith("Content-Type")) {
						proxyBaos.write("Content-Type: text/xml; charset=utf-8".getBytes());
					} else if (line.startsWith("Accept-Encoding")) {
						proxyBaos.write("Demo: identity;q=0.5".getBytes());
//						proxyBaos.write("Accept-Encoding: identity;q=0.5".getBytes());
					} else {
						proxyBaos.write(line.getBytes());
					}
					proxyBaos.write(FIELD_SEPARATOR);
					logger.info(line);
					String[] arryHead = line.split(": ");
					if (arryHead.length == 1) {
						headMap.put("protocol", arryHead[0]);
					} else if (arryHead.length == 2) {
						headMap.put(arryHead[0], arryHead[1]);
					}
					headPart.add(new String(line));
				}
				proxyBaos.write(FIELD_SEPARATOR);

				if (headMap.get("protocol").startsWith("POST")
						&& headMap.get("Content-Type").startsWith("multipart/form-data")) {
					logger.info(headMap.get("Content-Type"));
				}

				contentLength = Integer.parseInt(headMap.get("Content-Length"));
			} else {
				throw new RuntimeException(
						String.format("process HTTP header error,unread all header readerLength: %d", readLength));
			}

			if (contentLength > 0) {
				// 重新对其 buffer
				System.arraycopy(buffer, pos, buffer, 0, buffer.length - pos);
				readLength -= pos;
				if (contentLength > readLength) {
					baos.write(buffer, 0, readLength);
					proxyBaos.write(buffer, 0, readLength);
					int length = 0;
					int read = readLength;
					while ((length = is.read(buffer, 0, buffer.length)) != -1) {
						baos.write(buffer, 0, length);
						proxyBaos.write(buffer, 0, length);
						read += length;
						if (contentLength <= read) {
							break;
						}
					}
				} else {
					baos.write(buffer, 0, readLength);
					proxyBaos.write(buffer, 0, readLength);
				}
			}

			os.write(baos.toByteArray());
			os.flush();
			os.close();
			proxyOs.write(proxyBaos.toByteArray());
			proxyOs.flush();
			proxyOs.close();
			
			proxyRequest(proxyBaos);
 			
// 			int length = bis.read(buffer, 0, buffer.length);
// 			responseBaos.write(buffer, 0, length);
//			int length = 0;
//			while ((length = is.read(buffer, 0, buffer.length)) != -1) {
//				responseBaos.write(buffer, 0, length);
//			}
// 			int i = 0;
//			while ((i = is.read()) > 0) {
//				responseBaos.write(i);
//			}
// 			BufferedInputStream bis = new BufferedInputStream(is);
// 			BufferedReader r = new BufferedReader(new InputStreamReader(bis, Charset.forName("UTF-8")));
// 			String line = null;
// 			while((line = r.readLine()) != null) {
// 				responseBaos.write(line.getBytes());
// 				responseBaos.write(FIELD_SEPARATOR);
// 				if(line.equals("")) {
// 					responseBaos.write(FIELD_SEPARATOR);
// 				}
// 			}
//			responseOs.write(responseBaos.toByteArray());
//			responseOs.flush();
//			responseOs.close();
//			System.out.println(stringBuffer);

			// 响应
			sendResponse(socket.getOutputStream());
//			socket.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static void proxyRequest(ByteArrayOutputStream proxyBaos) {
		
		try {
			
			Socket socket = new Socket("www.webxml.com.cn", 80);
			socket.getOutputStream().write(proxyBaos.toByteArray());
			InputStream is = socket.getInputStream();
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			byte[] buffer = new byte[DEFAULT_BUFSIZE];

			int pos = 0;

			int readLength = is.read(buffer);

			boolean header = false;
			
			// 判断数组是否存在分界符 \r\n\r\n
			for (; pos < readLength; pos++) {
				if (buffer[pos] == LF && buffer[pos - 1] == CR && buffer[pos - 2] == LF && buffer[pos - 3] == CR) {
					pos++;
					baos.write(buffer, 0, pos);
					header = true;
					break;
				}
			}

			if (header) {
				// 处理 Content-Type
				InputStream inputStream = new ByteArrayInputStream(baos.toByteArray());
				Reader reader = new InputStreamReader(inputStream);
				BufferedReader bufReader = new BufferedReader(reader);
				String line = null;
				while ((line = bufReader.readLine()) != null && !line.equals("")) {
					logger.info(line);
					String[] arryHead = line.split(": ");
					if (arryHead.length == 1) {
						headMap.put("protocol", arryHead[0]);
					} else if (arryHead.length == 2) {
						headMap.put(arryHead[0], arryHead[1]);
					}
					headPart.add(new String(line));
				}

				contentLength = Integer.parseInt(headMap.get("Content-Length"));
			} else {
				throw new RuntimeException(
						String.format("process HTTP header error,unread all header readerLength: %d", readLength));
			}

			if (contentLength > 0) {
				// 重新对其 buffer
				System.arraycopy(buffer, pos, buffer, 0, buffer.length - pos);
				readLength -= pos;
				if (contentLength > readLength) {
					baos.write(buffer, 0, readLength);
					int length = 0;
					int read = readLength;
					while ((length = is.read(buffer, 0, buffer.length)) != -1) {
						baos.write(buffer, 0, length);
						read += length;
						if (contentLength <= read) {
							break;
						}
					}
				} else {
					baos.write(buffer, 0, readLength);
				}
			}
			OutputStream os = new FileOutputStream(new File((System.currentTimeMillis() + "-response.head")));
			os.write(baos.toByteArray());
			os.flush();
			os.close();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 响应
	 * 
	 * @param os
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	private static void sendResponse(OutputStream os) throws ClassNotFoundException, IOException {
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
//		logger.info(new String(baos.toByteArray()));
		os.write(baos.toByteArray());
	}
}
