package com.tdj_sj_webandroid.base;

import java.util.ArrayList;
import java.util.List;

public class TextJava {
    public static void main(String[] args) {

        List<String> list=new ArrayList<>();
        List<String> list1=new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("e");
        list.add("f");

        list1.add("a");
        list1.add("b");
        list1.add("c");
        list1.add("d");
        list1.add("e");
        list1.add("g");

        list1.removeAll(list);
        System.out.println(list1);
        System.out.println(list);
    }
}
