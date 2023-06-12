public class Demo{
  public static void main(String[] args){
    Integer[] array = {57,47,55,56,48,49,54,52,53,59,58,50,51};
    test3(array);
  }

  public static void test(Integer[] array){
    loop(array);
    sortMax(array);
    loop(array);
    sortSecord(array);
    loop(array);
    lowRange(array);
  }

  public static void test2(Integer[] array){
    demo1(array);
    loop(array);
  }

  public static void test3(Integer[] array){
    lowRange(array);
    bothRange(array);
  }

  public static void demo1(Integer[] array){
    int max = array[array.length - 1];
    int maxIndex = array.length - 1;
    int min = array[0];
    int minIndex = 0;

    // 首位元素进行比较
    if(min > max){
      int temp = max;
      int tempIndex = maxIndex;

      max = min;
      maxIndex = minIndex;

      min = temp;
      minIndex = tempIndex;
    }

    // 遍历整个数组，取出最大值和最小值
    // 分别将最大值和最小值放在数组末尾与开头
    for(int i = 1; i < array.length - 1; i ++){
      // 比较最大值
      if(array[i] > max){
        max = array[i];
        maxIndex = i;
      }

      // 比较最小值
      if(array[i] < min){
        min = array[i];
        minIndex = i;
      }
    }

    // 最大数组元素与数组末尾进行交换
    if(maxIndex != array.length - 1){
      int temp = array[array.length - 1];
      array[array.length - 1] = max;
      array[maxIndex] = temp;
    }

    // 最小数组元素与数组首位进行交换
    if(minIndex != 0){
      int temp = array[0];
      array[0] = min;
      array[minIndex] = temp;
    }

  }

  // 完全遍历数组，才能保持取得最大的元素的值
  public static void sortMax(Integer[] array){
    int index = 0;
    int max = array[0];
    // 遍历从 1 到 array.length - 1 之间的数据元素
    for(int i = 1; i < array.length; i++){
      // 每个元素与 max 比较大小
      if(array[i] > max){
        index = i;
        max = array[i];
      }
    }
    if(index > 0){
      int temp = array[array.length - 1];
      array[array.length - 1] = max;
      array[index] = temp;
    }
  }

  public static void sortSecord(Integer[] array){
    int index = 0;
    int max = array[0];
    int secondIndex = array.length - 2;
    // 遍历从 1 到 array.length - 1 之间的数据元素
    for(int i = 1; i < array.length - 1; i++){
      // 每个元素与 max 比较大小
      if(array[i] > max){
        index = i;
        max = array[i];
      }
    }
    if(index > 0){
      int temp = array[secondIndex];
      array[secondIndex] = max;
      array[index] = temp;
    }
  }

  public static void loop(Integer[] array){
    for(int i = 0; i < array.length; i++){
      System.out.print(String.format("%d, ", array[i]));
    }
    System.out.println();
  }

  public static void lowRange(Integer[] array){
    // 控制循环的次数
    for(int i = 0; i < array.length; i++){
      // 控制打印的内容、访问的数据范围
      for(int j = 0; j < array.length - i; j++){
        System.out.print(String.format("%d, ", array[j]));
      }
      System.out.println();
    }
  }

  public static void bothRange(Integer[] array){
    // 控制循环的次数
    int loop = (array.length % 2) > 0 ? array.length / 2 + 1 : array.length / 2;
    for(int i = 0; i < loop; i++){
      String space = "";
      for(int k = 0; k < i; k ++){
        space += "    ";
      }
      for(int j = i; j < array.length - i; j++){
        if(!space.equals("")){
          System.out.print(space);
          space = "";
        }
        System.out.print(String.format("%d, ", array[j]));
      }
      System.out.println();
    }
  }

  public static void demo2(Integer[] array){
    // 控制循环的次数
    int loop = (array.length % 2) > 0 ? array.length / 2 + 1 : array.length / 2;
    for(int i = 0; i < loop; i++){
      String space = "";
      for(int k = 0; k < i; k ++){
        space += "    ";
      }

      for(int j = i; j < array.length - i; j++){
        if(!space.equals("")){
          System.out.print(space);
          space = "";
        }
        System.out.print(String.format("%d, ", array[j]));
      }
      System.out.println();
    }
  }

}