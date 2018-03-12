package com.chy.demo;

import java.util.*;

/**
 * Created by chenhaoyu on 2018/2/27
 */
public class ListTest {

    /*
    public static void main(String[] args) {

        List<String> list = new ArrayList<>();
        list.add("abc");
        list.add("efg");
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String str = iterator.next();
            if ("abc".equals(str)) {
                iterator.remove();
            }
        }
        System.out.println(list);
    }*/

    /*
    public static void main(String[] args) {

        //final List<Integer> list = new ArrayList<>();
        final Vector<Integer> list = new Vector<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                Iterator<Integer> iterator = list.iterator();
                synchronized (list) {
                    while(iterator.hasNext()){
                        Integer integer = iterator.next();
                        System.out.println(integer);
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                Iterator<Integer> iterator = list.iterator();
                synchronized (list) {
                    while(iterator.hasNext()){
                        Integer integer = iterator.next();
                        if (integer==2) {
                            iterator.remove();
                            System.out.println(Thread.currentThread().getName());
                        }
                    }
                }
            }
        });
        t1.start();
        t2.start();
    }
    */

    public static void main(String[] args) {
        Properties properties = System.getProperties();
        Set<Map.Entry<Object, Object>> entries = properties.entrySet();
        for (Map.Entry<Object, Object> map : entries) {
            System.out.println(map.getKey()+":-->"+map.getValue());
        }
    }

}
