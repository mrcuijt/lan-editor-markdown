/**
 * 
 */
package science.mrcuijt.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Administrator
 *
 */
public class ThreadService {

	private static final Logger logger = LoggerFactory.getLogger(ThreadService.class);
	
	private static Integer port = null;
	private static String host = null;
	public static Map<String, Object> root = new HashMap<String, Object>();
	
	static {
		// 设置端口
		Properties prop = new Properties();
		try {
			System.out.println(Class.forName("science.mrcuijt.server.ThreadService").getResource(""));
			//prop.load(Class.forName("science.mrcuijt.server.ThreadService").getResourceAsStream("res.properties"));
			prop.load(new FileInputStream(new File("D:/workspace/docworkspace/lan-editor-markdown/servlet/example/proxy-socket/src/main/resources/res.properties")));
			port = Integer.parseInt(prop.getProperty("SERVER.PORT"));
			host = prop.getProperty("SERVER.HOST");
			root.put("port", port + "");
			root.put("host", host);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		Socket socket = null;

		try {

			serverSocket = new ServerSocket(port); // 创建服务器端的 Socket
			
			logger.info("服务器启动成功！");
			
			while (true) {
				
				// 等待返回客户端的信息
				socket = serverSocket.accept(); // 阻塞制的编程，在服务器启动后会一直等待客户端去连他
				SaveDataWorker4 worker = new SaveDataWorker4(socket);
				Thread thread = new Thread(worker);
				thread.start();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
