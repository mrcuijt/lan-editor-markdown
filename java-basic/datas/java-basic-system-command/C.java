
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Properties;

//import sun.io.CharToByteConverter;
//import sun.io.ByteToCharConverter;

public class C {

  public static void main(String[] args){
    //charset();
    String ping = run();
    System.out.println(ping);
  }

  public static void charset(){
    //System.out.println(ByteToCharConverter.getDefault().toString());
    //System.out.println(CharToByteConverter.getDefault().toString());
  }
  // Test
  public static String run(){
    String cmd = "chcp 65001 && ping baidu.com";
    String retStr = "";  
    InputStreamReader insReader = null;  
    char[] tmpBuffer = new char[1024];  
    try {
      int nRet = 0;  
      //ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "ping", cmd).inheritIO();
      //ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "ping", cmd);
      ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", cmd);
      Process p = pb.start();
      p.waitFor();
      insReader = new InputStreamReader(p.getInputStream(), Charset.forName("GB2312"));
      while ((nRet = insReader.read(tmpBuffer, 0, 1024)) != -1) {  
        retStr += new String(tmpBuffer, 0, nRet);  
      }
      insReader.close();
    } catch (Exception e) {  
      retStr = "<font color=\"red\">bad command \"" + cmd + "\"</font>";  
    } finally {  
      return retStr;  
    }  
  }

}
