package com.chy;

import org.junit.Test;

import java.nio.channels.Selector;

/**
 * @author chenhaoyu
 * @Created 2018-03-01 18:01
 */
public class TempTest {

    public static void main(String[] args) {
        B a = new B(5);
        System.out.println(a.getDesc());
    }

}

class B extends A {
    public B() {
        System.out.println("B's constructor(no param) end");
    }

    public B(int number) {
        this.number = number;
        this.desc = this.number+"--B";
        System.out.println("B's constructor(has param) end");
    }

    @Override
    public String getDesc() {
        return desc;
    }
}

class A {
    protected String desc;
    protected int number;

    public A() {
        System.out.println("A's constructor end");
    }

    public String getDesc() {
        return desc;
    }
}