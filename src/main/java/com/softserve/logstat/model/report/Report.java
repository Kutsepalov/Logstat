package com.softserve.logstat.model.report;


import java.util.List;

/**
 * @author Max Kutsepalov, Dmitriy Veretelnikov, Max Bohachov
 * @see ReportTop
 * @see ReportFind
 * @see ReportStat
 */
public interface Report {
    /**
     * @return list of rows should be printed based on input map
     */
    List<String> getAsList();
}
