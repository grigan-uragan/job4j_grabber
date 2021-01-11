package ru.grigan.grabber;

import org.junit.Test;

import static org.junit.Assert.*;

public class HelloWorldTest {

    @Test
    public void shouldSayHello() {
        HelloWorld helloWorld = new HelloWorld("Vasya");
        assertEquals("Hello Vasya", helloWorld.sayHello());
    }

}