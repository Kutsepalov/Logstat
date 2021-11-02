/**
 * Copyright 2021
 *
 * All rights reserved.
 *
 * Created on Oct 29, 2021 3:21:03 PM
 */
package com.softserve.logstat.service;

import com.softserve.logstat.model.report.Report;

/**
 * @author <paste here your name>
 *
 */
public class ReportWriter {
    private String filePath;
    
    public ReportWriter() {
	
    }
    
    public ReportWriter(String filePath) {
	this.filePath = filePath;
    }
    
    public void write(Report report) {
	
    }

    /**
     * @return the filePath
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * @param filePath the filePath to set
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

}
