/**
 * Copyright 2021
 *
 * All rights reserved.
 *
 * Created on Oct 29, 2021 1:49:47 PM
 */
package com.softserve.logstat;

import com.softserve.logstat.model.Command;
import com.softserve.logstat.model.HTTPMethod;
import com.softserve.logstat.model.Log;
import com.softserve.logstat.model.report.ReportStat;
import com.softserve.logstat.service.collector.Collector;
import com.softserve.logstat.service.collector.CollectorStat;
import com.softserve.logstat.service.parser.ArgParser;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <paste here your name>
 *
 */
public class App {

    /**
     * @param args is command arguments
     */
    public static void main(String[] args) {
	// TODO Auto-generated method stub

        String[] arguments = new String[]{
                "stat",
                "-url",
                "where",
                "-time",
                "not",
                "2019-05-05T12:15:10"
        };
        CollectorStat collectorStat = new CollectorStat();
        Log log1 = new Log();
        log1.setRequest("http:/1");
        log1.setIp("1");
        log1.setResponseCode((short) 200);
        log1.setHttpVersion("1.1");
        log1.setMethod(HTTPMethod.GET);
        log1.setResponseSize(300);
        log1.setDateTime(LocalDateTime.of(2019, Month.MAY, 01, 12, 15, 10));
        Log log2 = new Log();
        log2.setRequest("http:/2");
        log2.setIp("2");
        log2.setResponseCode((short) 200);
        log2.setHttpVersion("1.1");
        log2.setMethod(HTTPMethod.GET);
        log2.setResponseSize(400);
        log2.setDateTime(LocalDateTime.of(2019, Month.MAY, 02, 12, 15, 10));
        Log log3 = new Log();
        log3.setRequest("http:/3");
        log3.setIp("3");
        log3.setResponseCode((short) 200);
        log3.setHttpVersion("1.1");
        log3.setMethod(HTTPMethod.POST);
        log3.setResponseSize(700);
        log3.setDateTime(LocalDateTime.of(2019, Month.MAY, 03, 12, 15, 10));
        List<Log> logs = new ArrayList<>();
        logs.add(log1);
        logs.add(log2);
        logs.add(log3);
        Command entryCommand = new Command();
        Command command = new ArgParser().chooseCollectorType(arguments,entryCommand);
        ReportStat reporterStat = (ReportStat) collectorStat.collect(logs.stream(),command);
        System.out.println(reporterStat.getStatRes());

    }
}
