
import java.io.File;
import java.net.URL;

public class Demo {

  public static void main(String[] args){
    Demo demo = new Demo();
    demo.run();
  }

  public void run(){
    demo1();
    demo2();
    demo3();
    demo4();
    demo5();
    demo6();
  }

  public void demo1(){
    System.out.println("demo1 ---------------");
    String path = this.getClass().getResource("/").getPath();
    System.out.println(path);
  }

  public void demo2(){
    System.out.println("demo2 ---------------");
    String path = this.getClass().getResource("").getPath();
    System.out.println(path);
  }

  public void demo3(){
    System.out.println("demo3 ---------------");
    File file = new File("");
    System.out.println(file.getAbsolutePath());
  }

  public void demo4(){
    System.out.println("demo4 ---------------");
    URL path = this.getClass().getClassLoader().getResource("");
    System.out.println(path.getPath());
  }

  public void demo5(){
    System.out.println("demo5 ---------------");
    String path = System.getProperty("user.dir");
    System.out.println(path);
  }
 
  public void demo6(){
    System.out.println("demo6 ---------------");
    String path = System.getProperty("java.class.path");
    System.out.println(path);
  }

}
