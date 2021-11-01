package com.softserve.logstat.collector;

import java.util.Map;

public class ReportStat implements Report{
    Map <String,Integer> statRes;

    public Map<String, Integer> getStatRes() {
        return statRes;
    }

    public void setStatRes(Map<String, Integer> statRes) {
        this.statRes = statRes;
    }
}
