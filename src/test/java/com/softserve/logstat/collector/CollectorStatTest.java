package com.softserve.logstat.collector;

import com.softserve.logstat.model.HTTPMethod;
import com.softserve.logstat.model.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class CollectorStatTest {
    private ArrayList<Log> logs = new ArrayList<>();
    private CollectorStat collectorStat;
    private Log copyLog1;
    private Log log1;
    private Log log2;
    private Log log3;
    private Log log4;
    private Log log5;
    private HashMap<String, Integer> statisticManual;
    private HashMap statisticAuto;

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
        copyLog1.setAnswerCode((short) 200);
        copyLog1.setHttpVersion("1.1");
        copyLog1.setMethod(HTTPMethod.GET);
        copyLog1.setAnswerSize(300);
        log1 = new Log();
        log1.setRequest("http:/1");
        log1.setIp("1");
        log1.setAnswerCode((short) 200);
        log1.setHttpVersion("1.1");
        log1.setMethod(HTTPMethod.GET);
        log1.setAnswerSize(300);
        log2 = new Log();
        log2.setRequest("http:/2");
        log2.setIp("2");
        log2.setAnswerCode((short) 200);
        log2.setHttpVersion("1.1");
        log2.setMethod(HTTPMethod.GET);
        log2.setAnswerSize(400);
        log3 = new Log();
        log3.setRequest("http:/3");
        log3.setIp("3");
        log3.setAnswerCode((short) 200);
        log3.setHttpVersion("1.1");
        log3.setMethod(HTTPMethod.POST);
        log3.setAnswerSize(500);
        log4 = new Log();
        log4.setRequest("http:/4");
        log4.setIp("4");
        log4.setAnswerCode((short) 404);
        log4.setHttpVersion("2.0");
        log4.setMethod(HTTPMethod.POST);
        log4.setAnswerSize(400);
        log5 = new Log();
        log5.setRequest("http:/5");
        log5.setIp("5");
        log5.setAnswerCode((short) 500);
        log5.setHttpVersion("2.0");
        log5.setMethod(HTTPMethod.PUT);
        log5.setAnswerSize(700);
    }

    @Test
    @DisplayName("Find -url statistic(one log is copy).")
    void collect1() throws Exception {
        statisticAuto = (HashMap) collectorStat.collect(logs.stream(),
                true,
                false,
                false,
                false,
                false,
                false,
                false, 0, LocalDateTime.now());
        statisticManual = new HashMap<>();
        statisticManual.put(copyLog1.getRequest(), 2);
        statisticManual.put(log2.getRequest(), 1);
        statisticManual.put(log3.getRequest(), 1);
        statisticManual.put(log4.getRequest(), 1);
        statisticManual.put(log5.getRequest(), 1);
        assertEquals(statisticManual, statisticAuto);
    }

    @Test
    @DisplayName("Find -ip statistic(one log is copy).")
    void collect2() throws Exception {
        statisticAuto = (HashMap) collectorStat.collect(logs.stream(),
                false,
                true,
                false,
                false,
                false,
                false,
                false, 0, LocalDateTime.now());
        statisticManual = new HashMap<>();
        statisticManual.put(log1.getIp(), 2);
        statisticManual.put(log2.getIp(), 1);
        statisticManual.put(log3.getIp(), 1);
        statisticManual.put(log4.getIp(), 1);
        statisticManual.put(log5.getIp(), 1);
        assertEquals(statisticManual, statisticAuto);
    }

    @Test
    @DisplayName("Find -httpVersion statistic(log1,log2,log3,copyLog1 -> 1.1, log4,log5 -> 2.0).")
    void collect3() throws Exception {
        statisticAuto = (HashMap) collectorStat.collect(logs.stream(),
                false,
                false,
                true,
                false,
                false,
                false,
                false, 0, LocalDateTime.now());
        statisticManual = new HashMap<>();
        statisticManual.put(String.valueOf(log1.getHttpVersion()), 4);
        statisticManual.put(String.valueOf(log5.getHttpVersion()), 2);
        assertEquals(statisticManual, statisticAuto);
    }

    @Test
    @DisplayName("Find -codeResponse statistic(log1,log2,log3,copyLog1 -> 200, log4 -> 404, log5 -> 500).")
    void collect4() throws Exception {
        statisticAuto = (HashMap) collectorStat.collect(logs.stream(),
                false,
                false,
                false,
                false,
                true,
                false,
                false, 0, LocalDateTime.now());
        statisticManual = new HashMap<>();
        statisticManual.put(String.valueOf(log1.getAnswerCode()), 4);
        statisticManual.put(String.valueOf(log4.getAnswerCode()), 1);
        statisticManual.put(String.valueOf(log5.getAnswerCode()), 1);
        assertEquals(statisticManual, statisticAuto);
    }
    @Test
    @DisplayName("Find -httpMethod statistic(log1,log2,copyLog1 -> GET, log3,log4-> POST log5 -> PUT).")
    void collect5() throws Exception {
        statisticAuto = (HashMap) collectorStat.collect(logs.stream(),
                false,
                false,
                false,
                true,
                false,
                false,
                false, 0, LocalDateTime.now());
        statisticManual = new HashMap<>();
        statisticManual.put(String.valueOf(log1.getMethod()), 3);
        statisticManual.put(String.valueOf(log3.getMethod()), 2);
        statisticManual.put(String.valueOf(log5.getMethod()), 1);
        assertEquals(statisticManual, statisticAuto);
    }
    @Test
    @DisplayName("Find -url statistic limiting by size(one log is copy, size=500).The log5 will not in result")
    void collect6() throws Exception {
        statisticAuto = (HashMap) collectorStat.collect(logs.stream(),
                true,
                false,
                false,
                false,
                false,
                true,
                false, 500, LocalDateTime.now());
        statisticManual = new HashMap<>();
        statisticManual.put(copyLog1.getRequest(), 2);
        statisticManual.put(log2.getRequest(), 1);
        statisticManual.put(log3.getRequest(), 1);
        statisticManual.put(log4.getRequest(), 1);
        assertEquals(statisticManual, statisticAuto);
    }
}