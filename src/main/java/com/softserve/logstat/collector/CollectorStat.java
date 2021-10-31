package com.softserve.logstat.collector;

import com.softserve.logstat.model.Log;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectorStat {
    private Integer startValue = 1;
    private Stream<String> objectLogs;
    private Stream<Log> logStream;
    private Map<String, Integer> res;

    public Map<String, Integer> collect(Stream<Log> logs, Command command) throws Exception {
        if (!command.getPredicates().isEmpty()) {
            logStream = logs.filter(command.getPredicates().stream().reduce(log -> true, Predicate::and));
        } else {
            logStream = logs;
        }

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
            throw new Exception();
        }

        res = objectLogs.collect(Collectors.toMap(Function.identity(), (value) -> 1, Integer::sum));
        return res;
    }

}
