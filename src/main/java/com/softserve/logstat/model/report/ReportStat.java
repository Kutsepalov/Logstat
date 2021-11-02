/**
 * Copyright 2021
 *
 * All rights reserved.
 *
 * Created on Oct 29, 2021 3:32:54 PM
 */
package com.softserve.logstat.model.report;

import java.util.Map;

/**
 * @author <paste here your name>
 *
 */
public class ReportStat implements Report {

    private Map<String,Integer> res;

    public Map<String, Integer> getRes() {
        return res;
    }

    public void setRes(Map<String, Integer> res) {
        this.res = res;
    }

    @Override
    public String getAsString() {
	// TODO Auto-generated method stub
	return null;
    }
    
}
