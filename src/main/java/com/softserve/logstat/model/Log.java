/**
 * Copyright 2021
 *
 * All rights reserved.
 *
 * Created on Oct 20, 2021 3:21:09 PM
 */
package com.softserve.logstat.model;

/**
 * @author Max Kutsepalov
 *
 */
public class Log {
    private String ip;
    private String dateTime;
    private HTTPMethod method;
    private String request;
    private short answerCode;
    private int answerSize;
    private String httpVersion;
    private String referer;
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
    public String getDateTime() {
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
     * @return the referer
     */
    public String getReferer() {
        return referer;
    }
    /**
     * @return the answerCode
     */
    public short getAnswerCode() {
        return answerCode;
    }
    /**
     * @return the userAgent
     */
    public String getUserAgent() {
        return userAgent;
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
    public void setDateTime(String dateTime) {
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
     * @param referer the referer to set
     */
    public void setReferer(String referer) {
        this.referer = referer;
    }
    /**
     * @param answerCode the answerCode to set
     */
    public void setAnswerCode(short answerCode) {
        this.answerCode = answerCode;
    }
    /**
     * @param ipInfo the ipInfo to set
     */
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
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
	builder.append(", Answer code = ");
	builder.append(answerCode);
	builder.append(", Answer size = ");
	builder.append(answerSize);
	builder.append(", Http version = ");
	builder.append(httpVersion);
	builder.append(", Referer = ");
	builder.append(referer);
	builder.append(", User agent = ");
	builder.append(userAgent);
	builder.append("]");
	return builder.toString();
    }
    /**
     * @return the answerSize
     */
    public int getAnswerSize() {
        return answerSize;
    }
    /**
     * @param answerSize the answerSize to set
     */
    public void setAnswerSize(int answerSize) {
        this.answerSize = answerSize;
    }
}
