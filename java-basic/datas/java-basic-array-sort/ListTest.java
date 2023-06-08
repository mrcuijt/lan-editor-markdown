
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class ListTest{

  public static void main(String[] args){
    run();
  }

  public static void run(){
    String[] ary1 = {"2","4","6","8"};
    String[] ary2 = {"1","3","5","7"};
    System.out.println(Arrays.asList(ary1).getClass());
    List<String> list = new ArrayList<String>();
    list.addAll(Arrays.asList(ary1));
    list.addAll(Arrays.asList(ary2));
    System.out.println(list);
  }
}
