package science.mrcuijt.webdoc.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.nio.charset.Charset;

import java.util.Arrays;

import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipUtilTest {

  public static void main(String[] args){
    run();
  }

  public static void run(){
    testZipInputStream();
  }

  public static void testZipInputStream(){
    String path = "D:/workspace/docworkspace/javadoc/css3.0.zip";
    //String entry = "index.html";
    ZipInputStream in = null;
    String defaultCharset = "ISO-8859-1";
    try { 
      in = new ZipInputStream(new FileInputStream(path), Charset.forName("ISO-8859-1"));
      ZipEntry entry = null;
      while(null != (entry = in.getNextEntry())){
        String entryName = entry.getName();
        byte[] temps = entryName.getBytes("ISO-8859-1");
        System.out.println(Arrays.toString(temps));
        String charset = ZipUtil.getCharset(new ByteArrayInputStream(temps));
        if(charset != null){
          System.out.println("charset: " + charset + ", entry :" + new String(entry.getName().getBytes("ISO-8859-1"), charset));
        } else {
          System.out.println("default charset: " + defaultCharset + ", entry :" + new String(entry.getName().getBytes("ISO-8859-1"), defaultCharset));
        }
      }
    } catch(IOException e){
      e.printStackTrace();
    }
  }

}