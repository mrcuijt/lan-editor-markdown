
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Properties;
import java.util.Arrays;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class M {

  public static void main(String[] args){
    if(args == null || args.length == 0) return;
    run(args[0]);
  }

  public static void run(String address){
    demo(address);
  }

  public static void run1(){
    // String address = "baidu.com";
    // address = preProcessAddress(address);
    // String ret = ping(address);
    // System.out.println(ret);
    // test(ret);
    // test1();
    // demo();
  }

  public static void demo(String address){
    // String address = "baidu.com";
    address = preProcessAddress(address);
    String ret = ping(address);
    Integer[] lostAndAverage = lostAndAverage(ret);
    if(lostAndAverage == null) return;
    System.out.println(Arrays.asList(lostAndAverage));
  }

  public static String preProcessAddress(String address){
    if(address == null || address.trim().equals("")) return "NULL";
    address = address.replaceAll("http://","");
    address = address.replaceAll("https://","");
    address = address.replaceAll("/","");
    address = address.replaceAll("|", "");
    address = address.replaceAll("&", "");
    address = address.replaceAll("'", "");
    address = address.replaceAll("\"", "");
    address = address.replaceAll(" ", "");
    return address;
  }

  public static Integer[] lostAndAverage(String ret){
    if(ret == null || ret.trim().length() == 0) return null;
    Integer[] result = new Integer[2];
    String slost = "Lost = ";
    String saverage = "Average = ";
    String lost = null;
    String average = null;

    String regex = "Lost.{3}[\\d]{1,}";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(ret);
    if(matcher.find()){
      lost = matcher.group();
    }
    regex = "Average.{3}[\\d]{1,}";
    pattern = Pattern.compile(regex);
    matcher = pattern.matcher(ret);
    if(matcher.find()){
      average = matcher.group();
    }

    try {
      if(lost != null){
        result[0] = Integer.parseInt(lost.replace(slost,""));
      }

      if(average != null){
        result[1] = Integer.parseInt(average.replace(saverage,""));
      }
    } catch(Exception e){
      e.printStackTrace();
    }
    if(result[0] == null || result[1] == null) result = null;
    return result;
  }

  public static void test1(){
    String demo = ""
      + "Pinging baidu.com [39.156.69.79] with 32 bytes of data:\r\n"
      + "Reply from 39.156.69.79: bytes=32 time=15ms TTL=45\r\n"
      + "Reply from 39.156.69.79: bytes=32 time=12ms TTL=45\r\n"
      + "Reply from 39.156.69.79: bytes=32 time=13ms TTL=45\r\n"
      + "Reply from 39.156.69.79: bytes=32 time=14ms TTL=45\r\n\r\n"

      + "Ping statistics for 39.156.69.79:\r\n"
      + "    Packets: Sent = 4, Received = 4, Lost = 0 (0% loss),\r\n"
      + "Approximate round trip times in milli-seconds:\r\n"
      + "    Minimum = 12ms, Maximum = 15ms, Average = 13ms\r\n";

    String regex = "Lost.{3}[\\d]{1,}";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(demo);
    if(matcher.find()){
      String ret = matcher.group();
      System.out.println(ret);
    }
    regex = "Average.{3}[\\d]{1,}";
    pattern = Pattern.compile(regex);
    matcher = pattern.matcher(demo);
    if(matcher.find()){
      String ret = matcher.group();
      System.out.println(ret);
    }
  }

  public static void test(String ret){
    Pattern re = Pattern.compile("^PING\\b" // match ping
      + "[^(]*\\(([^)]*)\\)" // # capture IP
      + "\\s([^.]*)\\."      // # capture the bytes of data
      + ".*?^(\\d+\\sbytes)" // # capture bytes
      + ".*?icmp_seq=(\\d+)" // # capture icmp_seq
      + ".*?ttl=(\\d+)"      // # capture ttl
      + ".*?time=(.*?ms)"    // # capture time
      + ".*?(\\d+)\\spackets\\stransmitted"
      + ".*?(\\d+)\\sreceived"
      + ".*?(\\d+%)\\spacket\\sloss"
      + ".*?time\\s(\\d+ms)"
      + ".*?=\\s([^\\/]*)\\/([^\\/]*)\\/([^\\/]*)\\/(.*?)\\sms",
      Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
    Matcher m = re.matcher(ret);
    int mIdx = 0;
    while (m.find()){
      for( int groupIdx = 0; groupIdx < m.groupCount()+1; groupIdx++ ){
        System.out.println( "[" + mIdx + "][" + groupIdx + "] = " + m.group(groupIdx));
      }
      mIdx++;
    }
  }

  // Test
  public static String ping(String address){
    String cmd = String.format("chcp 65001 && ping %s", address);
    String retStr = "";
    InputStreamReader insReader = null;
    char[] tmpBuffer = new char[1024];
    try {
      int nRet = 0;
      ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", cmd);
      Process p = pb.start();
      p.waitFor();
      insReader = new InputStreamReader(p.getInputStream(), Charset.forName("UTF-8"));
      while ((nRet = insReader.read(tmpBuffer, 0, 1024)) != -1) {
        retStr += new String(tmpBuffer, 0, nRet);
      }
      insReader.close();
    } catch (Exception e) {
      retStr = "bad command " + cmd ;
    } finally {
      return retStr;
    }
  }

}
