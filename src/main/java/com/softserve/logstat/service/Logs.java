/**
 * Copyright 2021
 *
 * All rights reserved.
 *
 * Created on Oct 20, 2021 5:21:02 PM
 */
package com.softserve.logstat.service;

import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.softserve.logstat.model.HTTPMethod;
import com.softserve.logstat.model.Log;

/**
 * @author Max Kutsepalov
 *
 */
public final class Logs {
    
    private Logs() {
	
    }
    
    public static Log parse(String log) {
	Log o = new Log();
	o.setIp(getIp(log));
	o.setDateTime(getDateTime(log));
	o.setMethod(HTTPMethod.valueOf(getHTTPMethod(log)));
	o.setRequest(getRequest(log));
	o.setAnswerCode(getAnswerCode(log));
	o.setAnswerSize(getAnswerSize(log));
	o.setHttpVersion(getHTTPVersion(log));
	o.setReferer(getReferer(log));
	o.setUserAgent(getUserAgent(log));
	return o;
    }
    
    public static String getReferer(String log) {
	return findByPattern(log, Pattern.compile("\\\"(-|http.+?)\\\" \\\"(.+)"),1);
    }
    
    public static String getUserAgent(String log) {
	return findByPattern(log, Pattern.compile("\\\"(-|http.+?)\\\" \\\"(.+)"),2);
    }
    
    public static short getAnswerCode(String log) {
	String value = findByPattern(log, Pattern.compile("\\s(\\d{3})\\s(\\d{1,}|-)\\s"), 1);
	return Short.parseShort(value.trim());
    }
    
    public static int getAnswerSize(String log) {
	String value = findByPattern(log, Pattern.compile("\\s(\\d{3})\\s(\\d{1,}|-)\\s"), 2);
	return Integer.parseInt(value.replace('-', '0'));
    }

    public static String getIp(String log) {
        return findByPattern(log, 
        	Pattern.compile("\\b(?:[0-9]{1,3}\\.){3}[0-9]{1,3}\\b"));
    }
    
    public static String getDateTime(String log) {
	return findByPattern(log, Pattern.compile("\\[(.*?)\\]"),1)
		.replace(" +0000", "");
    }
    
    public static String getHTTPMethod(String log) {
	StringJoiner pattern = new StringJoiner("|");
	for(HTTPMethod method : HTTPMethod.values()) {
	    pattern.add(method.name());   
	}
	return findByPattern(log, Pattern.compile("\\b" + pattern));
    }
    
    public static String getRequest(String log) {
	return findByPattern(log, 
		Pattern.compile(getHTTPMethod(log) 
			+ "\\s([^\\s]+)\\s\\bHTTP/\\d\\.\\d"),1);
    }
    
    public static String getHTTPVersion(String log) {
	return findByPattern(log, Pattern.compile("\\bHTTP/\\d\\.\\d"));
    }
    
    private static String findByPattern(String log, Pattern pattern) {
        return findByPattern(log, pattern, 0);
    }
    
    private static String findByPattern(String log, Pattern pattern, int group) {
        Matcher matcher = pattern.matcher(log);
        if(!matcher.find()) {            
            throw new IllegalArgumentException("String: (" + log + ") isn't correct");
        }
        return matcher.group(group);
    }
}
