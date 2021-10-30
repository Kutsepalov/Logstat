/**
 * Copyright 2021
 *
 * All rights reserved.
 *
 * Created on Oct 20, 2021 3:21:09 PM
 */
package com.softserve.logstat.model;

import java.time.LocalDateTime;

/**
 * @author Max Kutsepalov
 *
 */
public class Log {
    private String ip;
    private LocalDateTime dateTime;
    private HTTPMethod method;
    private String request;
    private short responseCode;
    private int responseSize;
    private String httpVersion;
    private String referrer;
    private String userAgent;
    
    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }
    /**
     * @return the dateTime
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }
    /**
     * @return the method
     */
    public HTTPMethod getMethod() {
        return method;
    }
    /**
     * @return the request
     */
    public String getRequest() {
        return request;
    }
    /**
     * @return the httpVersion
     */
    public String getHttpVersion() {
        return httpVersion;
    }
    /**
     * @return the referrer
     */
    public String getReferrer() {
        return referrer;
    }
    /**
     * @return the responseCode
     */
    public short getResponseCode() {
        return responseCode;
    }
    /**
     * @return the userAgent
     */
    public String getUserAgent() {
        return userAgent;
    }
    /**
     * @return the responseSize
     */
    public int getResponseSize() {
	return responseSize;
    }
    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }
    /**
     * @param dateTime the dateTime to set
     */
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
    /**
     * @param method the method to set
     */
    public void setMethod(HTTPMethod method) {
        this.method = method;
    }
    /**
     * @param request the request to set
     */
    public void setRequest(String request) {
        this.request = request;
    }
    /**
     * @param httpVersion the httpVersion to set
     */
    public void setHttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
    }
    /**
     * @param referrer the referrer to set
     */
    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }
    /**
     * @param responseCode the responseCode to set
     */
    public void setResponseCode(short responseCode) {
        this.responseCode = responseCode;
    }
    /**
     * @param ipInfo the ipInfo to set
     */
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
    /**
     * @param responseSize the responseSize to set
     */
    public void setResponseSize(int responseSize) {
	this.responseSize = responseSize;
    }
    
    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("Log [ip = ");
	builder.append(ip);
	builder.append(", Date time = ");
	builder.append(dateTime);
	builder.append(", Method = ");
	builder.append(method);
	builder.append(", Request = ");
	builder.append(request);
	builder.append(", Response code = ");
	builder.append(responseCode);
	builder.append(", Response size = ");
	builder.append(responseSize);
	builder.append(", Http version = ");
	builder.append(httpVersion);
	builder.append(", Referrer = ");
	builder.append(referrer);
	builder.append(", User agent = ");
	builder.append(userAgent);
	builder.append("]");
	return builder.toString();
    }
}
