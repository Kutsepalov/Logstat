/**
 * Copyright 2021
 * <p>
 * All rights reserved.
 * <p>
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
 * @author <Dmitriy Veretelnikov>
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

    public static void main(String[] args) throws IOException {
        ReportStat rep = new ReportStat();
        Map<String, Integer> map = new LinkedHashMap<>();
//        map.put("83.149.9.216", 10);
//        map.put("24.236.252.67", 32);
//        map.put("50.16.19.13", 4);
//        map.put("110.136.166.128", 154);
//        map.put("110.12.166.128", 154);
        rep.setStatRes(map);
        ReportWriter rw = new ReportWriter("output.txt");
        rw.write(rep);
    }


}
