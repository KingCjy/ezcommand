package me.kingcjy.ezcommand.minecraft;

import org.junit.Test;

public class ReflectionTest {

    @Test
    public void subTypeTest() {
        System.out.println(Parent.class.isAssignableFrom(Child.class));
    }


    interface Interface {

    }
    class Parent implements Interface {

    }

    class Child extends Parent {

    }

    class Test2 {

    }
}