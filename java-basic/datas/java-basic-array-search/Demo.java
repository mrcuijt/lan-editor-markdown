public class Demo{
  public static void main(String[] args){
    int[] array = new int[]{47,48,49,50,51,52,53,54,55,56,57,58,59};
    int k = 66;
    int res = search(k, array);
    System.out.println(String.format("serach:%d; search2:%d;", res, search2(k, array)));

    array = new int[]{0,1};
    res = search(k, array);
    System.out.println(String.format("serach:%d; search2:%d;", res, search2(k, array)));
  }
  public static int search(int k, int[] a){
    int idx = -1;
    int l = 0;
    int r = a.length -1;
    for(int i = 0; i < a.length; i++){
      int m = (l + r) / 2;
      if(a[m] < k){
        l = m + 1;
      } else if(a[m] > k){
        r = m - 1;
      } else if(a[m] == k){
        idx = m;
        break;
      }
    }
    return idx;
  }

  public static int search2(int k, int[] a){
    int ret = -1;
    int l = 0;
    int r = a.length - 1;
    while(l != r){
      int m = (l + r) / 2;
      if(a[m] == k){
        ret = m;
        break;
      } else if(a[m] > k){
        r = m - 1;
      } else {
        l = m + 1;
      }
    }

    if(a[l] == k){
      ret = l;
    }
    
    return ret;
  }
}