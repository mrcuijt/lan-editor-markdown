import java.util.Scanner;

import wagu.Block;
import wagu.Board;
import wagu.Table;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class Demo {

  public static void main(String[] args){
    run();
    //tableDemo();
  }

  public static void run(){
    setStirng();
  }

  public static void setStirng(){
    String sample = "demo";
    String demo = "de" + "mo";
    String temp0 = "demo";
    String temp1 = new String("demo");
    String temp2 = new String("demo");
    String temp3 = new String(temp0);
    byte[] bytes = sample.getBytes();
    String temp4 = new String(bytes);
    StringBuffer strb = new StringBuffer();
    strb.append(sample);
    String temp5 = strb.toString();
    String temp6 = strb.toString();
    char[] chars = sample.toCharArray();
    String temp7 = new String(chars);
    String temp8 = new String(chars);
    String[] headers = new String[]{"Variable Name", "Value", "Memery Address"};
    List<List<String>> datas = new ArrayList<List<String>>();
    datas.add(Arrays.asList("sample", sample, String.valueOf(System.identityHashCode(sample))));
    datas.add(Arrays.asList("demo", demo, String.valueOf(System.identityHashCode(demo))));
    datas.add(Arrays.asList("temp0", temp0, String.valueOf(System.identityHashCode(temp0))));
    datas.add(Arrays.asList("temp1", temp1, String.valueOf(System.identityHashCode(temp1))));
    datas.add(Arrays.asList("temp2", temp2, String.valueOf(System.identityHashCode(temp2))));
    datas.add(Arrays.asList("temp3", temp3, String.valueOf(System.identityHashCode(temp3))));
    datas.add(Arrays.asList("temp4", temp4, String.valueOf(System.identityHashCode(temp4))));
    datas.add(Arrays.asList("temp5", temp5, String.valueOf(System.identityHashCode(temp5))));
    datas.add(Arrays.asList("temp6", temp6, String.valueOf(System.identityHashCode(temp6))));
    datas.add(Arrays.asList("temp7", temp5, String.valueOf(System.identityHashCode(temp7))));
    datas.add(Arrays.asList("temp8", temp5, String.valueOf(System.identityHashCode(temp8))));
    print(headers, datas);
  }

  public static void input(){
    Scanner in = new Scanner(System.in);
    String sample = "demo";
    System.out.println(String.format("创建 String 类型变量 sample=%s;", sample));
    System.out.println("请输入 demo 为临时变量赋值与 sample 进行比较内存地址：");
    String[] headers = new String[]{"Variable Name", "Value", "Memery Address"};
    List<List<String>> datas = new ArrayList<List<String>>();
    while(in.hasNext()){
      String temp = in.next();
      datas.add(Arrays.asList("sample", sample, String.valueOf(System.identityHashCode(sample))));
      datas.add(Arrays.asList("temp", temp, String.valueOf(System.identityHashCode(temp))));
      print(headers, datas);
      int size = datas.size();
      for(int i = size - 1; i >= 0; i--){
        datas.remove(i);
      }
    }
  }

  public static void print(String[] headers, List<List<String>> datas){
    List<String> headersList = Arrays.asList(headers);
    List<List<String>> rowsList = datas;

    Board board = new Board(55);
    Table table = new Table(board, 55, headersList, rowsList);
    table.setGridMode(Table.GRID_COLUMN);

    Integer[] colCenter = new Integer[headersList.size()];
    for(int i = 0; i < colCenter.length; i++){
      colCenter[i] = Block.DATA_CENTER;
    }
     List<Integer> colAlignList = Arrays.asList(colCenter);
    table.setColAlignsList(colAlignList);

    Block tableBlock = table.tableToBlocks();
    board.setInitialBlock(tableBlock);
    board.build();
    String tableString = board.getPreview();
    System.out.println(tableString);
  }

  public static void tableDemo(){
    List<String> headersList = Arrays.asList("NAME", "GENDER", "MARRIED", "AGE", "SALARY($)");
    List<List<String>> rowsList = Arrays.asList(
            Arrays.asList("Eddy", "Male", "No", "23", "1200.27"),
            Arrays.asList("Libby", "Male", "No", "17", "800.50"),
            Arrays.asList("Rea", "Female", "No", "30", "10000.00"),
            Arrays.asList("Deandre", "Female", "No", "19", "18000.50"),
            Arrays.asList("Alice", "Male", "Yes", "29", "580.40"),
            Arrays.asList("Alyse", "Female", "No", "26", "7000.89"),
            Arrays.asList("Venessa", "Female", "No", "22", "100700.50")
    );

    Board board = new Board(75);
    Table table = new Table(board, 75, headersList, rowsList);
    table.setGridMode(Table.GRID_COLUMN);
    //setting width and data-align of columns
    //List<Integer> colWidthsList = Arrays.asList(14, 14, 13, 14, 14);
    //List<Integer> colAlignList = Arrays.asList(Block.DATA_CENTER, Block.DATA_CENTER, Block.DATA_CENTER, Block.DATA_CENTER, Block.DATA_CENTER);

    //table.setColWidthsList(colWidthsList);
    //table.setColAlignsList(colAlignList);

    Integer[] colCenter = new Integer[headersList.size()];
    for(int i = 0; i < colCenter.length; i++){
      colCenter[i] = Block.DATA_CENTER;
    }
     List<Integer> colAlignList = Arrays.asList(colCenter);
    table.setColAlignsList(colAlignList);

    Block tableBlock = table.tableToBlocks();
    board.setInitialBlock(tableBlock);
    board.build();
    String tableString = board.getPreview();
    System.out.println(tableString);
  }
}