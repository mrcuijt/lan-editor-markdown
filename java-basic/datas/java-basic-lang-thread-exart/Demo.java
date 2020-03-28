public class Demo {

  public synchronized static void demo1(){
    System.out.println("demo1");
  }

  public synchronized static void demo2(){
    System.out.println("demo2");
  }

  public synchronized static void demo3(){
    demo1();
    demo2();
  }

  public static void main(String[] args){
    Demo.demo1();
    Demo.demo2();
    Demo.demo3();
  }

}
