package science.mrcuijt.webdoc.util;

import org.mozilla.universalchardet.UniversalDetector;

import java.io.IOException;

import java.util.Arrays;

public class CharsetTest {

  public static void main(String[] args){
    run5();
  }

  public static void run5(){
    String gb2312 = "GB2312";
    String utf8 = "UTF-8";
    String iso_8859_1 = "ISO-8859-1";
    try {
        String str2 = "姜丽娜";
        System.out.println(concatBytes(str2.getBytes(iso_8859_1)));// 636363
        System.out.println(concatBytes(str2.getBytes(gb2312)));    // -67-86-64-10-60-56
        System.out.println(concatBytes(str2.getBytes(utf8)));      // -27-89-100-28-72-67-27-88-100
        String temp = new String(str2.getBytes(utf8), iso_8859_1);
        System.out.println(temp); // å§œä¸½å¨
        System.out.println(concatBytes(temp.getBytes(iso_8859_1)));// -27-89-100-28-72-67-27-88-100
        System.out.println(concatBytes(temp.getBytes(gb2312)));    // 63-95-206363636363-95-8963
        System.out.println(concatBytes(temp.getBytes(utf8)));      // -61-91-62-89-62-100-61-92-62-72-62-67-61-91-62-88-62-100
    } catch(IOException e){
      e.printStackTrace();
    }
  }

  public static String concatBytes(byte[] buf){
    return Arrays.toString(buf);
  }

  public static void run4(){
    try {
      String msg = "你好";
      System.out.println(msg);
      byte[] bgbk = msg.getBytes("GBK");
      byte[] butf8 = msg.getBytes("UTF-8");
      byte[] biso88591 = msg.getBytes("ISO-8859-1");
      System.out.println("ISO-8859-1" + Arrays.toString(biso88591));
      System.out.println("UTF8" + Arrays.toString(butf8));
      System.out.println("GBK" + Arrays.toString(bgbk));
    } catch(IOException e){
      e.printStackTrace();
    }
  }

  // error charset encoding string
  // original utf8 msg , use gbk read, will be fail.
  // 
  public static void run3(){
    try {
      byte[] buf = new byte[]{72, 83, 76, 32, 99, 111, 108, 111, 114, 115, 46, 104, 116, 109, 108};
      buf = new byte[]{-54, -12, -48, -44, 40, 112, 114, 111, 112, 101, 114, 116, 105, 101, 115, 41, 46, 104, 116, 109, 108};
      for(int i = 0; i < buf.length; i++){
        byte b1 = buf[i];
        if(b1 == 63)
          break;
        else if(b1 > 0)
          continue;
        else if(b1 < 0){
          System.out.println(new String(buf, "GBK"));
          break;
        }
      }
    } catch(IOException e){
      e.printStackTrace();
    }
  }

  public static void run2(){
    try {
      String msg = "你好";
      msg = new String(msg.getBytes("UTF-8"), "GBK");
      System.out.println("GBK" + Arrays.toString(msg.getBytes("GBK")));
      System.out.println("ISO-8859-1" + Arrays.toString(new String(msg.getBytes("UTF-8"), "ISO-8859-1").getBytes()));
      System.out.println(msg);
    } catch(IOException e){
      e.printStackTrace();
    }
  }

  public static void run1(){
    byte[] buf = new byte[]{72, 83, 76, 32, 99, 111, 108, 111, 114, 115, 46, 104, 116, 109, 108};
    buf = new byte[]{-54, -12, -48, -44, 40, 112, 114, 111, 112, 101, 114, 116, 105, 101, 115, 41, 46, 104, 116, 109, 108};
    buf = new byte[]{-54, -12, -48, -44};
    testCharset(buf);
  }

  public static void testCharset(byte[] buf){
    UniversalDetector detector = null;
    try {
      detector = new UniversalDetector(null);
      detector.handleData(buf, 0, 4);
      detector.dataEnd();
      String encoding = detector.getDetectedCharset();
      if (encoding != null) {
        System.out.println("Detected encoding = " + encoding);
        System.out.println(new String(buf, encoding));
      } else {
        System.out.println("No encoding detected.");
      }
    } catch(IOException e){
      e.printStackTrace();
    } finally {
      if(detector != null) detector.reset();
    }
  }

}