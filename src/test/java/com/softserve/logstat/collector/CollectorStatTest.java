package com.softserve.logstat.collector;

import com.softserve.logstat.model.HTTPMethod;
import com.softserve.logstat.model.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class CollectorStatTest {
    private ArrayList<Log> logs = new ArrayList<>();
    private CollectorStat collectorStat;
    private ReportStat reporterStat;
    private Log copyLog1;
    private Log log1;
    private Log log2;
    private Log log3;
    private Log log4;
    private Log log5;
    private HashMap<String, Integer> statisticManual;

    @BeforeEach
    void start() {
        collectorStat = new CollectorStat();
        logInit();

        logs.add(log1);
        logs.add(log2);
        logs.add(log3);
        logs.add(log4);
        logs.add(log5);
        logs.add(copyLog1);

    }

    private void logInit() {
        copyLog1 = new Log();
        copyLog1.setRequest("http:/1");
        copyLog1.setIp("1");
        copyLog1.setResponseCode((short) 200);
        copyLog1.setHttpVersion("1.1");
        copyLog1.setMethod(HTTPMethod.GET);
        copyLog1.setResponseSize(300);
        copyLog1.setDateTime(LocalDateTime.of(2019, Month.MAY, 01, 12, 15, 00));
        log1 = new Log();
        log1.setRequest("http:/1");
        log1.setIp("1");
        log1.setResponseCode((short) 200);
        log1.setHttpVersion("1.1");
        log1.setMethod(HTTPMethod.GET);
        log1.setResponseSize(300);
        log1.setDateTime(LocalDateTime.of(2019, Month.MAY, 01, 12, 15, 00));
        log2 = new Log();
        log2.setRequest("http:/2");
        log2.setIp("2");
        log2.setResponseCode((short) 200);
        log2.setHttpVersion("1.1");
        log2.setMethod(HTTPMethod.GET);
        log2.setResponseSize(400);
        log2.setDateTime(LocalDateTime.of(2019, Month.MAY, 02, 12, 15, 00));
        log3 = new Log();
        log3.setRequest("http:/3");
        log3.setIp("3");
        log3.setResponseCode((short) 200);
        log3.setHttpVersion("1.1");
        log3.setMethod(HTTPMethod.POST);
        log3.setResponseSize(500);
        log3.setDateTime(LocalDateTime.of(2019, Month.MAY, 03, 12, 15, 00));
        log4 = new Log();
        log4.setRequest("http:/4");
        log4.setIp("4");
        log4.setResponseCode((short) 404);
        log4.setHttpVersion("2.0");
        log4.setMethod(HTTPMethod.POST);
        log4.setResponseSize(400);
        log4.setDateTime(LocalDateTime.of(2019, Month.MAY, 04, 12, 15, 00));
        log5 = new Log();
        log5.setRequest("http:/5");
        log5.setIp("5");
        log5.setResponseCode((short) 500);
        log5.setHttpVersion("2.0");
        log5.setMethod(HTTPMethod.PUT);
        log5.setResponseSize(700);
        log5.setDateTime(LocalDateTime.of(2019, Month.MAY, 05, 12, 15, 00));
    }

    @Test
    @DisplayName("Find -url statistic(one log is copy).")
    void collect1() throws IllegalArgumentException {
        String[] arguments = new String[]{
                "-stat",
                "url"
        };
        Command command = new ArgParser().chooseCollectorType(arguments);
        reporterStat = (ReportStat) collectorStat.collect(logs.stream(),command);
        statisticManual = new HashMap<>();
        statisticManual.put(copyLog1.getRequest(), 2);
        statisticManual.put(log2.getRequest(), 1);
        statisticManual.put(log3.getRequest(), 1);
        statisticManual.put(log4.getRequest(), 1);
        statisticManual.put(log5.getRequest(), 1);
        assertEquals(statisticManual, reporterStat.getStatRes());
    }

    @Test
    @DisplayName("Find -ip statistic(one log is copy).")
    void collect2() throws IllegalArgumentException {
        String[] arguments = new String[]{
                "-stat",
                "ip"
        };
        Command command = new ArgParser().chooseCollectorType(arguments);
        reporterStat = (ReportStat) collectorStat.collect(logs.stream(),command);
        statisticManual = new HashMap<>();
        statisticManual.put(log1.getIp(), 2);
        statisticManual.put(log2.getIp(), 1);
        statisticManual.put(log3.getIp(), 1);
        statisticManual.put(log4.getIp(), 1);
        statisticManual.put(log5.getIp(), 1);
        assertEquals(statisticManual, reporterStat.getStatRes());
    }

    @Test
    @DisplayName("Find -httpVersion statistic(log1,log2,log3,copyLog1 -> 1.1, log4,log5 -> 2.0).")
    void collect3() throws IllegalArgumentException {
        String[] arguments = new String[]{
                "-stat",
                "httpv"
        };
        Command command = new ArgParser().chooseCollectorType(arguments);
        reporterStat = (ReportStat) collectorStat.collect(logs.stream(),command);
        statisticManual = new HashMap<>();
        statisticManual.put(String.valueOf(log1.getHttpVersion()), 4);
        statisticManual.put(String.valueOf(log5.getHttpVersion()), 2);
        assertEquals(statisticManual, reporterStat.getStatRes());
    }

    @Test
    @DisplayName("Find -codeResponse statistic(log1,log2,log3,copyLog1 -> 200, log4 -> 404, log5 -> 500).")
    void collect4() throws IllegalArgumentException {
        String[] arguments = new String[]{
                "-stat",
                "rc"
        };
        Command command = new ArgParser().chooseCollectorType(arguments);
        reporterStat = (ReportStat) collectorStat.collect(logs.stream(),command);
        statisticManual = new HashMap<>();
        statisticManual.put(String.valueOf(log1.getResponseCode()), 4);
        statisticManual.put(String.valueOf(log4.getResponseCode()), 1);
        statisticManual.put(String.valueOf(log5.getResponseCode()), 1);
        assertEquals(statisticManual, reporterStat.getStatRes());
    }
    @Test
    @DisplayName("Find -httpMethod statistic(log1,log2,copyLog1 -> GET, log3,log4-> POST log5 -> PUT).")
    void collect5() throws IllegalArgumentException {
        String[] arguments = new String[]{
                "-stat",
                "httpm"
        };
        Command command = new ArgParser().chooseCollectorType(arguments);
        reporterStat = (ReportStat) collectorStat.collect(logs.stream(),command);
        statisticManual = new HashMap<>();
        statisticManual.put(String.valueOf(log1.getMethod()), 3);
        statisticManual.put(String.valueOf(log3.getMethod()), 2);
        statisticManual.put(String.valueOf(log5.getMethod()), 1);
        assertEquals(statisticManual, reporterStat.getStatRes());
    }
    @Test
    @DisplayName("Find -url statistic limiting by size with less(one log is copy).The log5,log3 will not be in result")
    void collect6() throws IllegalArgumentException {
        String[] arguments = new String[]{
                "-stat",
                "url",
                "where",
                "-size",
                "less",
                "500"
        };
        Command command = new ArgParser().chooseCollectorType(arguments);
        reporterStat = (ReportStat) collectorStat.collect(logs.stream(),command);
        statisticManual = new HashMap<>();
        statisticManual.put(copyLog1.getRequest(), 2);
        statisticManual.put(log2.getRequest(), 1);
        statisticManual.put(log4.getRequest(), 1);
        assertEquals(statisticManual, reporterStat.getStatRes());
    }
    @Test
    @DisplayName("Find -url statistic limiting by size with more(one log is copy).The log2,log1,log1Copy will not be in result")
    void collect7() throws IllegalArgumentException {
        String[] arguments = new String[]{
                "-stat",
                "url",
                "where",
                "-size",
                "more",
                "400"
        };
        Command command = new ArgParser().chooseCollectorType(arguments);
        reporterStat = (ReportStat) collectorStat.collect(logs.stream(),command);
        statisticManual = new HashMap<>();
        statisticManual.put(log2.getRequest(), 2);
        statisticManual.put(log3.getRequest(), 1);
        statisticManual.put(log5.getRequest(), 1);
        assertEquals(statisticManual, reporterStat.getStatRes());
    }
    @Test
    @DisplayName("Find -url statistic limiting by 2 different size(one log is copy).The log5,log2,log1,log1Copy will not be in result")
    void collect8() throws IllegalArgumentException {
        String[] arguments = new String[]{
                "-stat",
                "url",
                "where",
                "-size",
                "more",
                "400",
                "and",
                "less",
                "700"
        };
        Command command = new ArgParser().chooseCollectorType(arguments);
        reporterStat = (ReportStat) collectorStat.collect(logs.stream(),command);
        statisticManual = new HashMap<>();
        statisticManual.put(log2.getRequest(), 2);
        statisticManual.put(log3.getRequest(), 1);
        assertEquals(statisticManual, reporterStat.getStatRes());
    }
    @Test
    @DisplayName("Find -url statistic limiting by size with equals(one log is copy).The log5 will be in result")
    void collect9() throws IllegalArgumentException {
        String[] arguments = new String[]{
                "-stat",
                "url",
                "where",
                "-size",
                "eq",
                "700"
        };
        Command command = new ArgParser().chooseCollectorType(arguments);
        reporterStat = (ReportStat) collectorStat.collect(logs.stream(),command);
        statisticManual = new HashMap<>();
        statisticManual.put(log5.getRequest(), 1);
        assertEquals(statisticManual, reporterStat.getStatRes());
    }
    @Test
    @DisplayName("Find -url statistic limiting by size with not equals(one log is copy).The log5 will not be in result")
    void collect10() throws IllegalArgumentException {
        String[] arguments = new String[]{
                "-stat",
                "url",
                "where",
                "-size",
                "not",
                "700"
        };
        Command command = new ArgParser().chooseCollectorType(arguments);
        reporterStat = (ReportStat) collectorStat.collect(logs.stream(),command);
        statisticManual = new HashMap<>();
        statisticManual.put(log1.getRequest(), 2);
        statisticManual.put(log2.getRequest(), 1);
        statisticManual.put(log3.getRequest(), 1);
        statisticManual.put(log4.getRequest(), 1);
        assertEquals(statisticManual, reporterStat.getStatRes());
    }
    @Test
    @DisplayName("Find -url statistic limiting by time with not equals(one log is copy).The log5 will not be in result")
    void collect11() throws IllegalArgumentException {
        String[] arguments = new String[]{
                "-stat",
                "url",
                "where",
                "-time",
                "not",
                "22019-05-05T12:15"
        };
        Command command = new ArgParser().chooseCollectorType(arguments);
        reporterStat = (ReportStat) collectorStat.collect(logs.stream(),command);
        statisticManual = new HashMap<>();
        statisticManual.put(log1.getRequest(), 2);
        statisticManual.put(log2.getRequest(), 1);
        statisticManual.put(log3.getRequest(), 1);
        statisticManual.put(log4.getRequest(), 1);
        assertEquals(statisticManual, reporterStat.getStatRes());
    }
    @Test
    @DisplayName("Find -url statistic limiting by time with equals(one log is copy).The log5 will not be in result")
    void collect12() throws IllegalArgumentException {
        String[] arguments = new String[]{
                "-stat",
                "url",
                "where",
                "-time",
                "eq",
                "2019-05-05T12:15"
        };
        Command command = new ArgParser().chooseCollectorType(arguments);
        reporterStat = (ReportStat) collectorStat.collect(logs.stream(),command);
        statisticManual = new HashMap<>();
        statisticManual.put(log5.getRequest(), 1);
        assertEquals(statisticManual, reporterStat.getStatRes());
    }
    @Test
    @DisplayName("Find -url statistic limiting by time with less(one log is copy).The log5,log4 will not be in result")
    void collect13() throws IllegalArgumentException {
        String[] arguments = new String[]{
                "-stat",
                "url",
                "where",
                "-time",
                "less",
                "2019-05-04T12:15"
        };
        Command command = new ArgParser().chooseCollectorType(arguments);
        reporterStat = (ReportStat) collectorStat.collect(logs.stream(),command);
        statisticManual = new HashMap<>();
        statisticManual.put(log1.getRequest(), 2);
        statisticManual.put(log2.getRequest(), 1);
        statisticManual.put(log3.getRequest(), 1);
        assertEquals(statisticManual, reporterStat.getStatRes());
    }
    @Test
    @DisplayName("Find -url statistic limiting by time with more(one log is copy).The log5,log4 will be in result")
    void collect14() throws IllegalArgumentException {
        String[] arguments = new String[]{
                "-stat",
                "url",
                "where",
                "-time",
                "less",
                "2019-05-04T12:15"
        };
        Command command = new ArgParser().chooseCollectorType(arguments);
        reporterStat = (ReportStat) collectorStat.collect(logs.stream(),command);
        statisticManual = new HashMap<>();
        statisticManual.put(log5.getRequest(), 1);
        statisticManual.put(log4.getRequest(), 1);
        assertEquals(statisticManual, reporterStat.getStatRes());
    }
    @Test
    @DisplayName("Find -url statistic limiting by time with between(one log is copy).The log5,log4 will be in result")
    void collect15() throws IllegalArgumentException {
        String[] arguments = new String[]{
                "-stat",
                "url",
                "where",
                "-time",
                "between",
                "2019-05-04T12:15",
                "2019-05-05T12:15"
        };
        Command command = new ArgParser().chooseCollectorType(arguments);
        reporterStat = (ReportStat) collectorStat.collect(logs.stream(),command);
        statisticManual = new HashMap<>();
        statisticManual.put(log5.getRequest(), 1);
        statisticManual.put(log4.getRequest(), 1);
        assertEquals(statisticManual, reporterStat.getStatRes());
    }
}