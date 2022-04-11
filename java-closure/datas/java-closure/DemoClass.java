public class DemoClass {
    private int length = 0;

    private class InnerClass implements ILog {
        @Override
        public void write(String message) {
            DemoClass.this.length = message.length();
            System.out.println("DemoClass.InnerClass:" + length);
        }
    }

    public ILog logger() {
        return new InnerClass();
    }

	public interface ILog {
		public void write(String message);
	}

    public static void main(String[] args){
        DemoClass demoClass = new DemoClass();
        demoClass.logger().write("abc");

        //new
        DemoClass dc = new DemoClass();
        ILog ic = dc.logger();
        ic.write("abcde");
		//dc.logger().write("abcde");
    }
}