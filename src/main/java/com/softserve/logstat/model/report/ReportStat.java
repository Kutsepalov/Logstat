/**
 * Copyright 2021
 * 
 * All rights reserved.
 * 
 * Created on Oct 29, 2021 3:32:54 PM
 */
package com.softserve.logstat.model.report;

import java.util.*;

/**
 * @author Dmitriy Veretelnikov
 */
public class ReportStat implements Report {
    private Map<String, Integer> statRes;

    public Map<String, Integer> getStatRes() {
        return statRes;
    }

    public void setStatRes(Map<String, Integer> statRes) {
        this.statRes = statRes;
    }

    @Override
    public List<String> getAsList() {
        List<String> listStatResult = new ArrayList<>(statRes.size() + 1);
        int sizeKey = getSizeOfTheBiggestKey(statRes);
        int sizeValue = getSizeOfTheBiggestValue(statRes);
        if (statRes.size() != 0) {
            listStatResult.add(String.format("| %-" + sizeKey + "s | %-" + sizeValue + "s |", "Parameter", "Count"));
            for (Map.Entry<String, Integer> entry : statRes.entrySet()) {
                String key = entry.getKey();
                Integer value = statRes.getOrDefault(key, 0);
                listStatResult.add(String.format("| %-" + sizeKey + "s | %-" + sizeValue + "d |", key, value));
            }
        } else {
            listStatResult.add("Nothing was found");
        }
        return listStatResult;
    }

    private static int getSizeOfTheBiggestKey(Map<String, Integer> map) {
        int biggestKey = 0;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getKey().length() > biggestKey) {
                biggestKey = entry.getKey().length();
            }
        }
        if (biggestKey < 9) {
            biggestKey = 9;
        }
        return biggestKey;
    }


    private static int getSizeOfTheBiggestValue(Map<String, Integer> map) {
        int biggestValue = 0;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue().toString().length() > biggestValue) {
                biggestValue = entry.getValue().toString().length();
            }
        }
        if (biggestValue < 5) {
            biggestValue = 5;
        }
        return biggestValue;
    }
}
