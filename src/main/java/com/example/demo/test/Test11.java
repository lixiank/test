package com.example.demo.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Test11 {
    public static void main(String[] args) throws Exception {
//        ArrayList<Integer> list = new ArrayList<>();
//        list.add(1);
//        list.add(2);
//        list.add(3);
//        list.add(4);
//        list.add(5);
//        Iterator<Integer> iterator = list.iterator();
//        while (iterator.hasNext()){
//            Integer next = iterator.next();
//            if(next == 4){
//                list.remove(next);
//            }
//        }
//        System.out.println(list);
        HashMap<String,Integer> map = new HashMap<>();
        map.put("a",1);
        map.put("a",1);
        map.put("c",1);
        System.out.println(map);
    }
}


 class Father {
    public Father(){
        System.out.println("父类构造器");
    }

}

 class Son extends Father {
    public Son(){
        System.out.println("子类构造器");
    }
 }
