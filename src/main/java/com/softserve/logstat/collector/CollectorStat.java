package com.softserve.logstat.collector;

import com.softserve.logstat.model.Log;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectorStat {
    private Integer startValue = 1;
    private Stream<String> objectLogs;
    private Stream<Log> logStream;
    private Map<String, Integer> res;

    public Map<String, Integer> collect(Stream<Log> logs,
                                        boolean isUrl,
                                        boolean isIp,
                                        boolean isHttpVersion,
                                        boolean isHttpMethod,
                                        boolean isAnswerCode,
                                        boolean isSize,
                                        boolean isdateTime,
                                        int size,
                                        LocalDateTime time) throws Exception {

        logStream = logs.filter((log) -> {
            if (isSize) {
                return log.getAnswerSize() <= size;
            } else {
                return true;
            }
        }).filter((log) -> {
            if (isdateTime) {
                //log.getDateTime() <= time
                return true;
            } else {
                return true;
            }
        });

        if (isUrl) {
            objectLogs = logStream.map(Log::getRequest);
        } else if (isIp) {
            objectLogs = logStream.map(Log::getIp);
        } else if (isHttpVersion) {
            objectLogs = logStream.map(Log::getHttpVersion);
        } else if (isHttpMethod) {
            objectLogs = logStream.map(log -> String.valueOf(log.getMethod()));
        } else if (isAnswerCode) {
            objectLogs = logStream.map(log -> String.valueOf(log.getAnswerCode()));
        } else {
            throw new Exception();
        }

        res = objectLogs.collect(Collectors.toMap(Function.identity(), (value) -> 1, Integer::sum));
        return res;
    }

}
