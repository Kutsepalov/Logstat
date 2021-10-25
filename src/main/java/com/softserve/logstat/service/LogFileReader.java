/**
 * Copyright 2021
 *
 * All rights reserved.
 *
 * Created on Oct 20, 2021 3:17:43 PM
 */
package com.softserve.logstat.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import com.softserve.logstat.model.Log;

/**
 * @author Max Kutsepalov
 *
 */
public class LogFileReader {
    private String filePath;

    public LogFileReader(String filePath) {
	this.filePath = filePath;
    }
    
    /**
     * @param args
     * @throws IOException 
     */
    public List<Log> readAll () {
	List<Log> logs;
	try(BufferedReader br = Files.newBufferedReader(Paths.get(filePath))){
	    logs = br.lines().map(Logs::parse).collect(Collectors.toList());
	} catch (IOException e)  {
	    throw new IllegalArgumentException("Can't read the file: " + filePath);
	}
	return logs;
    }
}
