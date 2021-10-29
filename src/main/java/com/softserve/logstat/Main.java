package com.softserve.logstat;

import com.softserve.logstat.collector.CollectorStat;
import com.softserve.logstat.model.HTTPMethod;
import com.softserve.logstat.model.Log;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {
    private List<Log> logs;
    private HashMap result;
    private CollectorStat collectorStat;
    private Log log1;
    private Log log2;
    private Log log3;
    private Log log4;
    private Log log5;

    public static void main(String[] args) throws Exception {
        List<Log> logs = new ArrayList<>();
        CollectorStat collectorStat = new CollectorStat();
        Log log1 = new Log();
        log1.setRequest("http:/1");
        log1.setIp("1");
        log1.setAnswerCode((short) 404);
        log1.setMethod(HTTPMethod.GET);
        Log copyLog1 = new Log();
        copyLog1.setRequest("http:/1");
        copyLog1.setIp("1");
        copyLog1.setAnswerCode((short) 404);
        copyLog1.setMethod(HTTPMethod.GET);
        Log log2 = new Log();
        log2.setRequest("http:/2");
        log2.setIp("2");
        log2.setMethod(HTTPMethod.POST);
        log2.setAnswerCode((short) 500);



        logs.add(log1);
        logs.add(log2);
        logs.add(log1);
        logs.add(copyLog1);

        HashMap result = (HashMap) collectorStat.collect(logs.stream(),
                false,
                false,
                false,
                false,
                true,
                false,
                false, 0, LocalDateTime.now());
        System.out.println(result);
    }
}
