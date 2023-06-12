package science.mrcuijt.webdoc.util;

import java.io.ByteArrayOutputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.List;
import java.util.zip.ZipFile;

import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.http.MimeTypes;

import science.mrcuijt.webdoc.entity.FileModel;
import science.mrcuijt.webdoc.util.ZipUtil;

public class FileUtil {

  public static List<File> files(String path){
    File filePath = new File(path);
    if(!filePath.exists()) return new ArrayList<File>();
    List<File> files = new ArrayList<File>();
    files.addAll(Arrays.asList(filePath.listFiles()));
    return files;
  }

  public static List<FileModel> generateFileModels(List<File> files){
    List<FileModel> fileModels = new ArrayList<FileModel>();
    if(files == null || files.size() == 0) return fileModels;
    for(File file : files){
      fileModels.add(generateFileModel(file));
    }
    return fileModels;
  }

  public static FileModel generateFileModel(File file){
    FileModel fileModel = new FileModel();
    fileModel.setFileName(file.getName());
    fileModel.setFilePath(file.getAbsolutePath());
    if(file.isFile()){
      fileModel.setFileType(FileModel.FILE);
    } else {
      fileModel.setFileType(FileModel.DIRECTORY);
    }
    return fileModel;
  }

  public static void writeResource(HttpServletResponse response, InputStream in) throws IOException{
    try {
      Date bowerCache = DateUtil.bowerCache();
      response.addHeader("Expires", DateUtil.dateFormat(bowerCache, TimeZone.getTimeZone("GMT+8")));
      response.addHeader("Cache-Control", Long.toString(bowerCache.getTime() - Calendar.getInstance().getTime().getTime()));
      OutputStream out = response.getOutputStream();
      int len = -1;
      byte[] buf = new byte[2048];
      while( (len = in.read(buf)) != -1 ){
        out.write(buf, 0, len);
      }
    } finally {
      try {
        if(in != null) in.close();
      } catch (IOException e){
        e.printStackTrace();
      }
    }
  }

  public static void writeResource(HttpServletResponse response, String path, String entry) throws IOException {
    ZipFile zip = null;
    try {
      zip = ZipUtil.getZipFile(path);
      String mimeType = MimeTypes.getDefaultMimeByExtension(entry);
      String charset = ZipUtil.getCharset(ZipUtil.getResource(zip, entry));
      response.setContentType(String.format("%s; charset=%s", mimeType, charset));
      writeResource(response, ZipUtil.getResource(zip, entry));
    } finally {
      try {
        if(zip != null) zip.close();
      } catch (IOException e){
        e.printStackTrace();
      }
    }
  }

  public static void writeConfig(File file, String config){
    FileOutputStream fos = null;
    try{
        if(file.exists()) backup(file);
        fos = new FileOutputStream(file);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] bytes = config.getBytes("UTF-8");
        baos.write(bytes, 0, bytes.length);
        baos.writeTo(fos);
        baos.flush();
    }catch(Exception e){
      e.printStackTrace();
    }finally{
      try{
        if(fos != null) fos.close();
      }catch(Exception e){
        e.printStackTrace();
      }
    }
  }

  public static void backup(File file) {
    FileInputStream fis = null;
    FileOutputStream fos = null;
    try{
        fis = new FileInputStream(file);
        fos = new FileOutputStream(new File(file.getName() + ".bak"));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length = 0;
        while((length = fis.read(buffer, 0, buffer.length)) != -1){
          baos.write(buffer, 0, length);
        }
        fos.write(baos.toByteArray());
        fos.flush();
    }catch(Exception e){
      e.printStackTrace();
    }finally{
      try{
        if(fos != null) fos.close();
        if(fis != null) fis.close();
      }catch(Exception e){
        e.printStackTrace();
      }
    }
  }

  private static FileUtil instance;
  private static FileUtil getInstance(){
    if(instance == null)
      instance = new FileUtil();
    return instance;
  }

  public static File getResource(InputStream is){
    if(is == null) return null;
    File file = null;
    BufferedOutputStream bos = null;
    //String fileName = String.format("temp/%s.tmp", String.valueOf(System.currentTimeMillis()));
    String fileName = String.format("%s.tmp", String.valueOf(System.currentTimeMillis()));
    try {
      byte[] buffer = new byte[1024];
      int length = 0;
      file = new File("temp");
      if(!file.exists()){
        file.mkdirs();
      }
      fileName = String.format("%s/%s", file.getAbsolutePath(), fileName);
      file = new File(fileName);
      FileOutputStream fos = new FileOutputStream(file);
      bos = new BufferedOutputStream(fos);
      while( (length = is.read(buffer, 0, buffer.length)) != -1 ){
        bos.write(buffer, 0, length);
      }
      bos.flush();
    } catch(IOException e){
      e.printStackTrace();
    } catch(Exception e){
      e.printStackTrace();
    } finally {
      if(is != null){
        try { is.close(); } catch(Exception e){}
      }
      if(bos != null){
        try { bos.close(); } catch(Exception e){}
      }
    }
    return file;
  }

  public static InputStream getResource(String file){
    return FileUtil.getInstance().getClass().getClassLoader().getResourceAsStream(file);
  }
}
