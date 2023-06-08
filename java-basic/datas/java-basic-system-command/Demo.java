
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.Charset;

import java.net.URL;
import java.net.URLConnection;
import java.net.HttpURLConnection;

import java.util.Properties;

public class Demo {

  public static void main(String[] args){
    //getJVMParams();
    String defaultCharset = getJVMDefaultCharset();
    System.out.println(defaultCharset);
    run();
  }

  public static void run(){
    String url = "https://baidu.com";
    System.getProperties().put("http.keepAlive", "true");
    System.getProperties().put("sun.net.http.errorstream.enableBuffering", "true");
    System.getProperties().put("sun.net.http.errorstream.timeout", "300");
    System.getProperties().put("sun.net.http.errorstream.bufferSize", "4096");
    DownloaderSettings.init();
    SimpleURLConnection simpleConn = new SimpleURLConnection();
    Downloader.simpleConnection = simpleConn;
    Downloader.task = new Task();
    Downloader.download(url, 0);
    //HttpURLConnection conn = simpleConn.getConnection(url);
    getJVMParams();
  }

  public static void ping(){
    String input = "ping baidu.com";
    String ret = exeCmd(input);
    System.out.println(ret);
  }

  public static String exeCmd(String cmd) {  
    Runtime runtime = Runtime.getRuntime();  
    Process proc = null;  
    String retStr = "";  
    InputStreamReader insReader = null;  
    char[] tmpBuffer = new char[1024];  
    int nRet = 0;  
    try {  
      proc = runtime.exec(cmd);  
      insReader = new InputStreamReader(proc.getInputStream(), Charset.forName("GB2312"));  
      while ((nRet = insReader.read(tmpBuffer, 0, 1024)) != -1) {  
        retStr += new String(tmpBuffer, 0, nRet);  
      }  
      insReader.close();  
      //retStr = HTMLEncode(retStr);  
    } catch (Exception e) {  
      retStr = "<font color=\"red\">bad command \"" + cmd + "\"</font>";  
    } finally {  
      return retStr;  
    }  
  }  

  public static String HTMLEncode(String str) {  
    str = str.replaceAll(" ", "&nbsp;");  
    str = str.replaceAll("<", "&lt;");  
    str = str.replaceAll(">", "&gt;");  
    str = str.replaceAll("\r\n", "<br>");  
    return str;  
  }

  public static String getJVMDefaultCharset(){
    return Charset.defaultCharset().displayName();
  }

  // Get JVM Properties
  public static void getJVMParams() {
    try {
      Properties properties=System.getProperties();
      PrintWriter out = null;
      out = new PrintWriter(new File("a.txt"));
      properties.list(out);
      out.flush();
    } catch(FileNotFoundException e) {
      e.printStackTrace();
    }
  }

}
