package com.softserve.logstat.model.report;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Dmitriy Veretelnikov
 * @see Report
 */
public class ReportStat implements Report {
    private Map<String, Integer> statRes;

    public Map<String, Integer> getStatRes() {
        return statRes;
    }

    public void setRes(Map<String, Integer> statRes) {
        this.statRes = statRes;
    }

    @Override
    public List<String> getAsList() {
        List<String> listStatResult = new ArrayList<>(statRes.size() + 1);
        int sizeKey = getSizeOfTheBiggestKey(statRes);
        int sizeValue = getSizeOfTheBiggestValue(statRes);
        if (statRes.size() != 0) {
            listStatResult.add(String.format("| %-" + sizeKey + "s | %-" + sizeValue + "s |", "Parameter", "Count"));
            Iterator<Map.Entry<String, Integer>> iterator = statRes.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Integer> entry = iterator.next();
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
        Iterator<Map.Entry<String, Integer>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();
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
        Iterator<Map.Entry<String, Integer>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();
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
