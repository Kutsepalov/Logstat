package com.softserve.logstat.service.collector;


import com.softserve.logstat.model.Log;

import java.util.Map;
import java.util.stream.Stream;

/**
 * @author <Roman Grabovetskyi>
 * mock class
 */
public class CollectorTop implements Collector {
    @Override
    public Map<Object, Object> collect(Stream<Log> logs) {
        return null;
    }
}
