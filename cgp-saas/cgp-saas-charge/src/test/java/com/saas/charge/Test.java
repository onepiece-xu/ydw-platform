package com.saas.charge;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author xulh
 * @description: TODO
 * @date 2020/10/2913:51
 */
public class Test {
    public static void main(String[] args) {
        LocalTime localTime = LocalTime.parse("07:00");
        LocalTime localTime1 = LocalTime.parse("23:00");
        System.out.println(Duration.between(localTime1,LocalTime.MAX).toMinutes());

        List<String> list1 = new ArrayList<>();
        list1.add("1");
        list1.add("2");
        list1.add("3");
        list1.add("4");
        list1.add("5");
        list1.add("6");
        Iterator<String> iterator = list1.iterator();
        while (iterator.hasNext()){
            String next = iterator.next();
            System.out.println(next);
            if (next.equals("3")){
                iterator.remove();
            }
        }
        System.out.println(list1);
    }
}
