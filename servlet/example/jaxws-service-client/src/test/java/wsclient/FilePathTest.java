/**
 * 
 */
package wsclient;

import java.io.InputStream;


/**
 * @author Administrator
 *
 */
public class FilePathTest {

	public static void main(String[] args) {
		String path = "jar:file:/F:/test_env/apache-tomcat-8.0.53/temp/axis2-tmp-5754819559332852182.tmp/axis28707515879829161794DICOMService.aar!/science/mrcuijt/axis2/service/";
		System.out.println(path.replaceAll("jar:file:/", ""));
	}
	
	public static void test() {
		String str = FilePathTest.class.getResource("").toString();
		System.out.println(str);
		
		str = FilePathTest.class.getResource("/").toString();
		System.out.println(str);
		
		//https://blog.csdn.net/fengsheng5210/article/details/80856392
		// 读取当前 class 下的资源文件
		FilePathTest.class.getClassLoader().getResourceAsStream("");
	}

}
