/**
 * Copyright 2021
 *
 * All rights reserved.
 *
 * Created on Oct 29, 2021 2:42:51 PM
 */
package com.softserve.logstat.service;

import com.softserve.logstat.model.Command;
import com.softserve.logstat.model.report.Report;
import com.softserve.logstat.service.collector.Collector;
import com.softserve.logstat.service.collector.CollectorFactory;

/**
 * @author Max Kutsepalov
 *
 */
public class Controller {
    private LogFileReader reader;
    
    public void execute(Command command) {
	reader = new LogFileReader(command.getInputFilePath());
	Collector collector = CollectorFactory.choose(command.getName());
	Report report = collector.collect(reader.readAll(), command);
	ReportWriter writer = new ReportWriter();
	if(command.getOutputFilePath() != null) {
	    writer.setFilePath(command.getOutputFilePath());
	}
	writer.write(report);
    }
}
