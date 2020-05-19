package jaqua.poc;

public class Main {
    public static void main(String[] args) {
        MessageFactory factory = new HelloWorldMessageFactory();
        System.out.println( factory.createMessage() );
    }
}
