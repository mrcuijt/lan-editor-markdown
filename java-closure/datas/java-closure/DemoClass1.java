public class DemoClass1 {

  private int level = 0;

  private int length = 0;

  public ILog logger(final int l) throws Exception {

    return new ILog() {
      {
        level = l;
        //实例初始化，不能重载 
        if(level != 1)
          throw new Exception("日志等级不正确！");
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