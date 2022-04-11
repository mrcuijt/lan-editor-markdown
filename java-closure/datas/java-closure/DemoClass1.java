public class DemoClass1 {

  private int level = 0;

  private int length = 0;

  public ILog logger(final int l) throws Exception {

    return new ILog() {
      {
        level = l;
        //ʵ����ʼ������������ 
        if(level != 1)
          throw new Exception("��־�ȼ�����ȷ��");
      }
 
      @Override
      public void write(String message) {
        length = message.length();
        System.out.println("DemoClass5.AnonymousClass:" + length);
      }
    };
  }

  public interface ILog {
    public void write(String message);
  }

  class LoggerDemo implements ILog {
    public LoggerDemo(){}
	
	public LoggerDemo(int level){
	  DemoClass.this.level = level;
	}
 
	@Override
	public void write(String message) {
	  length = message.length();
	  System.out.println("DemoClass5.AnonymousClass:" + length);
	}
  }

}