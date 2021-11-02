/**
 * Copyright 2021
 * 
 * All rights reserved.
 * 
 * Created on Oct 29, 2021 3:21:03 PM
 */
package com.softserve.logstat.service;

import com.softserve.logstat.model.report.Report;
import com.softserve.logstat.model.report.ReportStat;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * @author Dmitriy Veretelnikov
 */
public class ReportWriter {
    private static boolean firstLine = true;
    private String fileOutput;

    public String getFileOutput() {
        return fileOutput;
    }

    public void setFileOutput(String fileOutput) {
        this.fileOutput = fileOutput;
    }

    public ReportWriter() {
    }

    public ReportWriter(String fileOutput) {
        this.fileOutput = fileOutput;
    }

    public void write(Report report) throws IOException {
        BufferedWriter bw = null;
        if (getFileOutput() != null) {
            try {
                bw = new BufferedWriter(new FileWriter(getFileOutput()));
                for (String line : report.getAsList()) {
                    if (firstLine) {
                        bw.write(line);
                        firstLine = false;
                    } else {
                        bw.write("\n" + line);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                bw.close();
            }
        } else {
            for (String line : report.getAsList()) {
                System.out.println(line);
            }
        }
    }
}
