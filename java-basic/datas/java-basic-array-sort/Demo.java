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
    // �������һ��Ԫ�ص�ֵΪ���
    int max = 0;
    for(int i = 1; i < array.length; i++){
      if(array[i] > array[max]){
        max = i;
      }
    }
    return max;
  }

  public static Integer[] sort1(Integer[] array){
    // ��ǰԪ��ÿ��ѭ��������ĩβ��ʼ�Ƚ�
    // �����ǰԪ�ش�������ĩβԪ�أ�����֮����
    // ��Ҫѭ�������������
    // ��һ��ѭ���õ�����ĩβ�����ֵ
    // �ڶ���ѭ��������ĩβ -1 λ��Ԫ�ؽ��бȽϣ�ֱ���õ��ڶ���ֵ��
    // array.length - 1 ��
    // 
    // ʲô���˳�������ÿ��Ԫ�ر�����ɺ���Ϊ�˳�����
    int max = array.length - 1;// ��ȡ����ĩβԪ���±�
    // ��������λ��ʼ��ĩβ���бȽ�
    for(int i = 0; i < max - 1; i++){
      // �����λԪ�ش���ĩβԪ�أ������ǽ��н���
      if(array[i] > array[max]){
        
        // ������ֵ
        int temp = array[i]; // max value to temp
        array[i] = array[max]; // min value to current element
        array[max] = temp; // max value to max element
      }
    }

    for(int j = 2; j < array.length - 1; j++){
      max = array.length - j;// ��ȡ����ĩβԪ���±�
      // ��������λ��ʼ��ĩβ���бȽ�
      for(int i = 0; i < max; i++){
        // �����λԪ�ش���ĩβԪ�أ������ǽ��н���
        if(array[i] > array[max]){
          
          // ������ֵ
          int temp = array[i]; // max value to temp
          array[i] = array[max]; // min value to current element
          array[max] = temp; // max value to max element
        }
      }
    }
    return array;
  }

  public static Integer[] sort(Integer[] array){
    // ��������ֵ�Ƚϴ��Ԫ��������Ԫ�ؽ���λ�ý���
    // �˳�������ʲô��
    // ��α�֤��󽻻��˵�˳��
    // ���̷���
    // [10,2,3,4,5,6,7,8,9]
    // [0] = 10
    // ���������Ԫ�ؽ���
    // ��һ��ѭ��������õ����ֵ���洢����ĩβ
    // �ڶ���ѭ��������õ��ڶ���ֵ���洢����ĩβǰһλ
    // ���ѭ����Ҫ���Σ����鳤�� - 1 ��
    int max = 0;
    // �����������Ľ���
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