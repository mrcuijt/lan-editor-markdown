
// 死锁的实现
class A {
    public void get(){
        System.out.println("A说:我开始启动了，B，给我你的资源");
    }
    public void say(){
        System.out.println("A获得资源");
    }
}
 
class B {
    public void get(){
        System.out.println("B说:我开始启动了，A，给我你的资源");
    }
    public void say(){
        System.out.println("B获得资源");
    }
}
 
class MyThread implements Runnable {
    public static A a = new A();
    public static B b = new B();
    public boolean flag = false;
    public void run(){
        if(flag){
            synchronized(a){
                a.get();
                try{ Thread.sleep(500); }catch(InterruptedException e){}
                synchronized(b){     // 此同步代码块在另一同步代码块里
                    a.say();
                }
            }
        } else {
            synchronized(b){
                b.get();
                try{ Thread.sleep(500); }catch(InterruptedException e){}
                synchronized(a){     // 此同步代码块在另一同步代码块里
                    b.say();
                }
            }
        }
    }
}
 
public class Demo24 {
    public static void main(String args[]){
        MyThread mt1 = new MyThread();
        MyThread mt2 = new MyThread();
        mt1.flag=true;
        mt2.flag=false;
        Thread th1 = new Thread(mt1);
        Thread th2 = new Thread(mt2);
        th1.start();
        th2.start();
    }
}
