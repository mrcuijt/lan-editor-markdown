public class LambdaDemo {

  public static void main(String[] args){
    LambdaDemo demo = new LambdaDemo();
    demo.run();
  }


  public void run(){
    StringConverter converter = new StringConverter();
    Deserializer des = converter::convertToInt;
    int a = des.deserialize("1");
    System.out.println(a);
  }

  public interface Deserializer {

    public int deserialize(String v1);
  }

  class StringConverter {

    public int convertToInt(String v1){

      return Integer.valueOf(v1);
    }

  }

}
