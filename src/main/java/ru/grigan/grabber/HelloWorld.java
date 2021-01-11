package ru.grigan.grabber;

public class HelloWorld {
    private String name;

    public HelloWorld(String name) {
        this.name = name;
    }

    public String sayHello() {
        return "Hello " + name;
    }
}
