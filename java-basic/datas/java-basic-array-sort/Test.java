import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class Test{
  public static void main(String[] args){
    //run();
    //test();
    test2();
    test3();
  }

  public static void run(){
    Integer[] array = {22,3,32,21,213,34,33,65,37,65,67,7,8,898,55,43,65,56,78,98};
    List<Integer> list = Arrays.asList(array);
    System.out.println(list);
    Arrays.sort(array);
    list = Arrays.asList(array);
    System.out.println(list);
    //Collections.sort(list);
  }

  public static void test(){
    String[] array = {"22","3","32","21","213","34","33","6","78","98"};
    List<String> strings = Arrays.asList(array);
    int ret = compare(array[0], array[1]);
    System.out.println(ret);
  }

  public static void test2(){
    String[] array = {"a","b","c","d","e","f","g"
        ,"h","i","j","k","i","m","n"
        ,"o","p","q","r","s","t","u"
        ,"v","w","x","y","z"};

    String[] array1 = {"1","2","3","4","5","6"
        ,"7","8","9","10","11","12","13","14"
        ,"15","16","17","18","19","20","21","22"
        ,"30","40","50","60","70","80","90","100"};
    List<String> arrays = new ArrayList<String>();
    arrays.addAll(Arrays.asList(array));
    //arrays.addAll(new ArrayList<String>(Arrays.asList(array1)));
    arrays.addAll(Arrays.asList(array1));
    System.out.println(arrays);

    Collections.sort(arrays);
    System.out.println(arrays);
  }

  public static void test3(){

    String[] array = {"a","b","c","d","e","f","g"
        ,"h","i","j","k","i","m","n"
        ,"o","p","q","r","s","t","u"
        ,"v","w","x","y","z"};

    String[] array1 = {"1","2","3","4","5","6"
        ,"7","8","9","10","11","12","13","14"
        ,"15","16","17","18","19","20","21","22"
        ,"30","40","50","60","70","80","90","100"};

    List<String> list = new ArrayList<String>();
    list.addAll(Arrays.asList(array));
    list.addAll(Arrays.asList(array1));

    Collections.sort(list, new Comparator<String>(){
      @Override
      public int compare(String str1, String str2){
        if(str1.length() > str2.length()){
          return 1;
        } else if(str1.length() < str2.length()){
          return -1;
        } else {
          return str1.compareToIgnoreCase(str2);
        }
      }
    });
    System.out.println(list);
  }

  public static int compare(String str1, String str2){
    return str1.compareTo(str2);
  }

}
