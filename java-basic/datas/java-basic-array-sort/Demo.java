import java.util.Arrays;
import java.util.ArrayList;

public class Demo {

  public static void main(String[] args){
    Integer[] array = {47,55,56,48,49,54,57,52,53,59,58,50,51};
    int index = search(array);
    System.out.println(String.format("%d %d", index, array[index]));
    Integer[] max = sort1(array);
    System.out.println(new ArrayList<Integer>(Arrays.asList(max)));
  }

  public static int search(Integer[] array){
    // 设数组第一个元素的值为最大
    int max = 0;
    for(int i = 1; i < array.length; i++){
      if(array[i] > array[max]){
        max = i;
      }
    }
    return max;
  }

  public static Integer[] sort1(Integer[] array){
    // 当前元素每次循环从数组末尾开始比较
    // 如果当前元素大于数组末尾元素，则与之交换
    // 需要循环几次完成任务？
    // 第一次循环得到数组末尾的最大值
    // 第二次循环与数组末尾 -1 位置元素进行比较，直至得到第二大值。
    // array.length - 1 次
    // 
    // 什么是退出条件？每个元素遍历完成后做为退出条件
    int max = array.length - 1;// 获取数组末尾元素下标
    // 从数组首位开始向末尾进行比较
    for(int i = 0; i < max - 1; i++){
      // 如果首位元素大于末尾元素，则将它们进行交换
      if(array[i] > array[max]){
        
        // 交换数值
        int temp = array[i]; // max value to temp
        array[i] = array[max]; // min value to current element
        array[max] = temp; // max value to max element
      }
    }

    for(int j = 2; j < array.length - 1; j++){
      max = array.length - j;// 获取数组末尾元素下标
      // 从数组收位开始向末尾进行比较
      for(int i = 0; i < max; i++){
        // 如果首位元素大于末尾元素，则将它们进行交换
        if(array[i] > array[max]){
          
          // 交换数值
          int temp = array[i]; // max value to temp
          array[i] = array[max]; // min value to current element
          array[max] = temp; // max value to max element
        }
      }
    }
    return array;
  }

  public static Integer[] sort(Integer[] array){
    // 将数组中值比较大的元素与后面的元素进行位置交换
    // 退出条件是什么？
    // 如何保证最后交换了的顺序？
    // 流程分析
    // [10,2,3,4,5,6,7,8,9]
    // [0] = 10
    // 逐个与后面的元素交换
    // 第一次循环结束后得到最大值并存储在了末尾
    // 第二次循环结束后得到第二大值并存储在了末尾前一位
    // 如此循环需要几次？数组长度 - 1 次
    int max = 0;
    // 找最大的与后面的交换
    for(int i = 1; i < array.length; i++){
      if(array[i] > array[max]){
        max = i;
      }
    }
    if(max != array.length - 1 && array[array.length - 1] < array[max]){
      int temp = array[array.length - 1];
      array[array.length - 1] = array[max];
      array[max] = temp;
      max = array.length - 1;
    }
    return array;
  }
}