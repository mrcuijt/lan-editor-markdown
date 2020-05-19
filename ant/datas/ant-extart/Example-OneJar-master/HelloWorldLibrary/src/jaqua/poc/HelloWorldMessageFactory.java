package jaqua.poc;

public class HelloWorldMessageFactory implements MessageFactory {

    public String createMessage() {
        return "Hello, world!";
    }
}
