package com.softserve.logstat.service.collector;

import com.softserve.logstat.model.Command;
import com.softserve.logstat.model.Log;
import com.softserve.logstat.model.report.Report;
import com.softserve.logstat.model.report.ReportStat;
import com.softserve.logstat.service.parser.ParamType;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The class implements a simple logic for collecting statistics witch based on the main arguments.
 * The class has private field objectLogs, that's field represents stream which was filtered by main arguments.
 * The class has private field logStream, that's field represents stream which was filtered by additional arguments.
 * The class has private field res, that's field represents collection with result statistic.
 *
 * @author Dmitri Chistiuk
 */
public class CollectorStat implements Collector {
    private Stream<String> objectLogs;
    private Stream<Log> logStream;
    private Map<String, Integer> res;

    /**
     * @param logs    represents stream from instances of class Log. Instances are logs from input file.
     * @param command represents instance of class Command witch contains parsed cmd arguments.
     * @return interface instance Report witch responsible for writing result statistic correctly.
     * @throws IllegalArgumentException when as main argument gets unexpected parameter.
     */
    public Report collect(Stream<Log> logs, Command command) throws IllegalArgumentException {
        filterByAdditionalParam(logs, command);
        collectionByMainParam(command);
        res = objectLogs.collect(Collectors.toMap(Function.identity(), value -> 1, Integer::sum));
        ReportStat reporterStat = new ReportStat();
        reporterStat.setStatRes(res);
        return reporterStat;
    }

    /**
     * The method implements a logic for filtering logStream by main arguments such as IP,CR,URL,HTTPVersion
     *
     * @param command represents instance of class Command witch contains parsed cmd arguments.
     */
    private void collectionByMainParam(Command command) {
        if (command.getToWrite().size() > 1) {
            throw new IllegalArgumentException("There are a lot of arguments, but expected only 1");
        }
        for (ParamType paramType : command.getToWrite()) {
            switch (paramType){
                case URL -> objectLogs = logStream.map(Log::getRequest);
                case IP ->  objectLogs = logStream.map(Log::getIp);
                case HTTPVERSION -> objectLogs = logStream.map(Log::getHttpVersion);
                case METHOD -> objectLogs = logStream.map(log -> String.valueOf(log.getMethod()));
                case SC -> objectLogs = logStream.map(log -> String.valueOf(log.getResponseCode()));
                default -> throw new IllegalArgumentException("Failed to collect statistic using" + paramType);
            }
        }
    }

    /**
     * The method implements a logic for filtering logs by additional arguments such as -time or -size.
     *
     * @param logs    represents stream from instances of class Log. Instances are logs from input file.
     * @param command represents instance of class Command witch contains parsed cmd arguments.
     */
    private void filterByAdditionalParam(Stream<Log> logs, Command command) {
        if (!command.getFilters().isEmpty()) {
            logStream = logs.filter(command.getFilters().stream().reduce(log -> true, Predicate::and));
        } else {
            logStream = logs;
        }
    }
}
