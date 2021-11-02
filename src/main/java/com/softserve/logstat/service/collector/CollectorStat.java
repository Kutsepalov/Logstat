package com.softserve.logstat.service.collector;

import com.softserve.logstat.model.Command;
import com.softserve.logstat.model.ParamType;
import com.softserve.logstat.model.Log;
import com.softserve.logstat.model.report.Report;
import com.softserve.logstat.model.report.ReportStat;

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
 * @autor Dmitri Chistiuk
 */
public class CollectorStat implements Collector{
    private Stream<String> objectLogs;
    private Stream<Log> logStream;
    private Map<String, Integer> res;

    /**
     *
     * @param logs represents stream from instances of class Log. Instances are logs from input file.
     * @param command represents instance of class Command witch contains parsed cmd arguments.
     * @return interface instance Report witch responsible for writing result statistic correctly.
     * @throws IllegalArgumentException when as main argument gets unexpected parameter.
     */
    public Report collect(Stream<Log> logs, Command command) throws IllegalArgumentException {
        filterByAdditionalParam(logs, command);
        collectionByMainParam(command);
        res = objectLogs.collect(Collectors.toMap(Function.identity(), value -> 1, Integer::sum));
        ReportStat reporterStat = new ReportStat();
        reporterStat.setRes(res);
        return reporterStat;
    }

    /**
     * The method implements a logic for filtering logStream by main arguments such as IP,CR,URL,HTTPVersion
     * @param command represents instance of class Command witch contains parsed cmd arguments.
     */
    private void collectionByMainParam(Command command) {
        if (command.getParamType() == ParamType.URL) {
            objectLogs = logStream.map(Log::getRequest);
        } else if (command.getParamType() == ParamType.IP) {
            objectLogs = logStream.map(Log::getIp);
        } else if (command.getParamType() == ParamType.HTTPVERSION) {
            objectLogs = logStream.map(Log::getHttpVersion);
        } else if (command.getParamType() == ParamType.HTTPMETHOD) {
            objectLogs = logStream.map(log -> String.valueOf(log.getMethod()));
        } else if (command.getParamType() == ParamType.RC) {
            objectLogs = logStream.map(log -> String.valueOf(log.getResponseCode()));
        } else {
            throw new IllegalArgumentException("Failed to collect statistic using" + command.getParamType());
        }
    }

    /**
     * The method implements a logic for filtering logs by additional arguments such as -time or -size.
     * @param logs represents stream from instances of class Log. Instances are logs from input file.
     * @param command represents instance of class Command witch contains parsed cmd arguments.
     */
    private void filterByAdditionalParam(Stream<Log> logs, Command command) {
        if (!command.getPredicates().isEmpty()) {
            logStream = logs.filter(command.getPredicates().stream().reduce(log -> true, Predicate::and));
        } else {
            logStream = logs;
        }
    }
}
