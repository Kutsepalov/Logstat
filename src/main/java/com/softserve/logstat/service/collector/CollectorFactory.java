package com.softserve.logstat.service.collector;
/**
 * @author <Roman Grabovetskyi>
 *
 */
public class CollectorFactory {
    public static Collector choose(String str){
        try {
            if (str == "top") return new CollectorTop();
            else if (str == "stat") return new CollectorStat();
            else if (str == "find") return new CollectorFind();
            else {throw new IllegalArgumentException();}
        }catch (IllegalArgumentException e){
            System.out.println("Wrong flag entered");
        }
        return null;
    }
}
