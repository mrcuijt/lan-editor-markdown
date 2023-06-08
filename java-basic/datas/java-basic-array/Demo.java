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

    // ��λԪ�ؽ��бȽ�
    if(min > max){
      int temp = max;
      int tempIndex = maxIndex;

      max = min;
      maxIndex = minIndex;

      min = temp;
      minIndex = tempIndex;
    }

    // �����������飬ȡ�����ֵ����Сֵ
    // �ֱ����ֵ����Сֵ��������ĩβ�뿪ͷ
    for(int i = 1; i < array.length - 1; i ++){
      // �Ƚ����ֵ
      if(array[i] > max){
        max = array[i];
        maxIndex = i;
      }

      // �Ƚ���Сֵ
      if(array[i] < min){
        min = array[i];
        minIndex = i;
      }
    }

    // �������Ԫ��������ĩβ���н���
    if(maxIndex != array.length - 1){
      int temp = array[array.length - 1];
      array[array.length - 1] = max;
      array[maxIndex] = temp;
    }

    // ��С����Ԫ����������λ���н���
    if(minIndex != 0){
      int temp = array[0];
      array[0] = min;
      array[minIndex] = temp;
    }

  }

  // ��ȫ�������飬���ܱ���ȡ������Ԫ�ص�ֵ
  public static void sortMax(Integer[] array){
    int index = 0;
    int max = array[0];
    // ������ 1 �� array.length - 1 ֮�������Ԫ��
    for(int i = 1; i < array.length; i++){
      // ÿ��Ԫ���� max �Ƚϴ�С
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
    // ������ 1 �� array.length - 1 ֮�������Ԫ��
    for(int i = 1; i < array.length - 1; i++){
      // ÿ��Ԫ���� max �Ƚϴ�С
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
    // ����ѭ���Ĵ���
    for(int i = 0; i < array.length; i++){
      // ���ƴ�ӡ�����ݡ����ʵ����ݷ�Χ
      for(int j = 0; j < array.length - i; j++){
        System.out.print(String.format("%d, ", array[j]));
      }
      System.out.println();
    }
  }

  public static void bothRange(Integer[] array){
    // ����ѭ���Ĵ���
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
    // ����ѭ���Ĵ���
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