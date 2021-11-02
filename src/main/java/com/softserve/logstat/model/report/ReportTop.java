package com.softserve.logstat.model.report;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Bohachov Maksym
 */
public class ReportTop implements Report {
    private final Map<String, Integer> info;
    private int length;

    /**
     * Creates instance of ReportTop
     * @param info - input map
     */
    public ReportTop(Map<String, Integer> info) {
        this.info = info;

        length = 0;

        if (!info.isEmpty()) {
            checkLength();
        }
    }

    /**
     * Computes length of column based on max length of keySet's elements
     */
    private void checkLength() {
        Iterator<String> iterator = info.keySet().iterator();

        length = iterator.next().length();

        while(iterator.hasNext()) {
            length = Math.max(iterator.next().length(), length);
        }
    }

    @Override
    public List<String> getAsList() {
        List<String> result = new ArrayList<>(info.size() + 1);

        if(length > 0) {
            Iterator<String> iterator = info.keySet().iterator();

            result.add(String.format("| %-" + length + "s | %-9s |", "Key", "Value"));

            while (iterator.hasNext()) {
                String current = iterator.next();

                result.add(String.format("| %-" + length + "s | %-9d |", current, info.get(current)));
            }
        } else {
            result.add("There is no information.");
        }

        return result;
    }
}