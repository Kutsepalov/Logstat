package com.softserve.logstat.service.collector;
/**
 * @author Roman Grabovetskyi
 *
 */
public class CollectorFactory {

    public static Collector choose(String str){
        return switch (str) {
                case "top", "-top" ->  new CollectorTop();

                case "stat", "-stat" -> new CollectorStat();

                case "find", "-find" ->new  CollectorFind();

     default -> {
                    System.out.println("Wrong flag entered");
                    throw  new IllegalArgumentException();
                }
            };
    }
}
