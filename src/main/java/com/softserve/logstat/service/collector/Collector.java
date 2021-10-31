/**
 * Copyright 2021
 *
 * All rights reserved.
 *
 * Created on Oct 29, 2021 2:05:17 PM
 */
package com.softserve.logstat.service.collector;

import java.util.Map;
import java.util.stream.Stream;

import com.softserve.logstat.model.Log;
import com.softserve.logstat.model.report.Report;
import com.softserve.logstat.service.Command;

/**
 * @author <paste here your name>
 *
 */
public interface Collector {
    Report collect(Stream<Log> logs, Command comand);




}
