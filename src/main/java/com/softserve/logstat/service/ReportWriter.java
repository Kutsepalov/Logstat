/**
 * Copyright 2021
 * <p>
 * All rights reserved.
 * <p>
 * Created on Oct 29, 2021 3:21:03 PM
 */
package com.softserve.logstat.service;

import com.softserve.logstat.model.report.Report;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;



/**
 * @author <Dmitriy Veretelnikov>
 */
public class ReportWriter {
    private boolean firstLine = true;
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
        if (getFileOutput() != null) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(getFileOutput()))) {
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
            }
        } else {
            for (String line : report.getAsList()) {
                System.out.println(line);
            }
        }
    }
}
