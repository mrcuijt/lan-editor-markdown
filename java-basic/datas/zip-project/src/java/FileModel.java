package science.mrcuijt.webdoc.entity;

public class FileModel {

  public static final String FILE = "1";
  public static final String DIRECTORY = "2";

  private String fileName;
  private String fileType;
  private String filePath;

  public String getFileName(){ return fileName; }
  public void setFileName(String fileName){ this.fileName = fileName; }

  public String getFileType(){ return fileType; }
  public void setFileType(String fileType){ this.fileType = fileType; }

  public String getFilePath(){ return filePath; }
  public void setFilePath(String filePath){ this.filePath = filePath; }

}
