package com.softserve.logstat.service.collector;


import com.softserve.logstat.model.Log;
import com.softserve.logstat.model.report.Report;
import com.softserve.logstat.model.Command;

import java.util.stream.Stream;

/**
 * @author Roman Grabovetskyi
 *  mock class
 */
public class CollectorFind implements Collector {


    @Override
    public Report collect(Stream<Log> logs, Command comand) {
        return null;
    }
}