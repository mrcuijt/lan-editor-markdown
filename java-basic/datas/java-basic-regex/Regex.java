
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {

  public static void main(String[] args){
    run();
  }

  public static void run(){
    test2();
  }

  public static void test2(){
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

  public static void test1(){
    String title = "1.2:路3明非";
    title = "cc cc=3 , dd avg = 22ms (dd)";
    // number start , capture 1 or more
    String regex = "^[0-9&&[^: ]]{1,}";
    // regex = "[0-9&&[^: ]]{1,}";
    // regex = "avg[_]{1}=[_]{1}[0-9]{1,}";

    // delete capture numeric , split string
    regex = "[^2]{1,}";
    regex = "[^2]{1,}[^d]{1,}";

    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(title);
    while(matcher.find()){
      // get matocher string
      String capture = matcher.group();
      System.out.println(capture);
    }
    //} else {
    //  System.out.println("Not Matcher");
    //}
  }
}
