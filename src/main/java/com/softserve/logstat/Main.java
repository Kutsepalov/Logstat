package com.softserve.logstat;

import com.softserve.logstat.collector.CollectorStat;
import com.softserve.logstat.collector.Command;
import com.softserve.logstat.collector.ParamType;
import com.softserve.logstat.model.HTTPMethod;
import com.softserve.logstat.model.Log;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        LocalDateTime date = LocalDateTime.of(2019, Month.MAY, 15, 12, 15, 00);
        System.out.println(date);

    }
}
