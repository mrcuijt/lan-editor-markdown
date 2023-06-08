package science.mrcuijt.webdoc.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import java.util.zip.ZipFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;

import java.util.logging.Logger;

import org.mozilla.intl.chardet.nsDetector;
import org.mozilla.intl.chardet.nsPSMDetector;
import org.mozilla.intl.chardet.HtmlCharsetDetector;
import org.mozilla.intl.chardet.nsICharsetDetectionObserver;

public class ZipUtil {

  private static final Logger logger = Logger.getLogger(ZipUtil.class.getName());

  public static Iterator<ZipEntry> iterator(ZipFile zip){
    Iterator<ZipEntry> iter = null;
    try {
      iter = (Iterator<ZipEntry>) zip.entries();
    } catch(Exception e){
      e.printStackTrace();
    }
    return iter;
  }

  public static Enumeration<ZipEntry> entries(ZipFile zip){
    Enumeration<ZipEntry> entries = null;
    try {
      entries = (Enumeration<ZipEntry>) zip.entries();
    } catch(Exception e){
      e.printStackTrace();
    }
    return entries;
  }

  public static List<String> entryList(String path){
    ZipFile zip = null;
    List<String> entries = new ArrayList<String>();
    try {
      zip = getZipFile(path);
      Iterator<ZipEntry> iter = iterator(zip);
      if(iter == null) return Collections.emptyList();
      while(iter.hasNext()){
        ZipEntry entry = iter.next();
        entries.add(entry.getName());
      }
    } catch (Exception e){
      e.printStackTrace();
    } finally {
      try {
        if(zip != null) zip.close();
      } catch (IOException e){
        e.printStackTrace();
      }
    }
    // TODO: getZipFile(path) while throw Exception.
    return (entries.size() == 0) ? Collections.emptyList() : entries;
  }

  public static InputStream getResource(ZipFile zip, String entry){
    InputStream in = new ByteArrayInputStream(new byte[]{});
    try {
      ZipEntry zipEntry = zip.getEntry(entry);
      in = zip.getInputStream(zipEntry);
    } catch(Exception e){
      e.printStackTrace();
    }
    return in;
  }

  public static boolean verify(String path){
    List<String> entries = entryList(path);
    return (!entries.isEmpty());
  }

  public static ZipFile getZipFile(String path) throws ZipException, IOException {
    return new ZipFile(new File(path));
  }

  public static String getCharset(InputStream in){

    String charset = "UTF-8";

    try {

      ZipUtil.DetectionObserver cdo = new ZipUtil.DetectionObserver();
      nsDetector det = new nsDetector(nsPSMDetector.CHINESE) ;
      det.Init(cdo); // 设置一个 Oberver

      boolean done = false; // 是否已经确定某种字符集
      boolean isAscii = true; // 假定当前的串是 ASCII 编码

      int len = -1;
      byte[] buf = new byte[1024];
      while( (len = in.read(buf, 0, buf.length)) != -1) {
        // 检查是不是全是 ASCII 字符，当有一个字符不是 ASC 编码时，则所有的数据即不是 ASCII 编码了。
        if (isAscii) isAscii = det.isAscii(buf, len);
        // 如果不是 ASCII 字符，则调用 DoIt 方法.
        if (!isAscii && !done) done = det.DoIt(buf,len, false); // 如果不是 ASCII，又还没确定编码集，则继续检测。
      }

      // 最后要调用此方法，此时，Notify 被调用。
      det.DataEnd();
      
      boolean found = HtmlCharsetDetector.found;

      if (isAscii) {
        System.out.println("CHARSET = ASCII");
        found = true ;
      }

      if (!found) { // 如果没找到，则找到最可能的那些字符集
        String prob[] = det.getProbableCharsets() ;
        for(int i = 0; i < prob.length; i++)
          System.out.println("Probable Charset = " + prob[i]);
      }

      charset = cdo.getCharset();

    } catch (Exception e){
      e.printStackTrace();
    } finally {
      try {
        if(in != null) in.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return charset;
  }

  public static class DetectionObserver implements nsICharsetDetectionObserver{
    private String charset = null;
    public String getCharset(){ return charset; }
    //public void setCharset(String charset){ this.charset = charset; }
    public void Notify(String charset) {
      HtmlCharsetDetector.found = true ;
      this.charset = charset;
      System.out.println("CHARSET = " + charset);
    }
  }

}
