package com.softserve.logstat.service.collector;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CollectorFactoryTest {



    @Test
    void choose1() {
        Collector collector1=CollectorFactory.choose("top");

        assertEquals(collector1.getClass(),CollectorTop.class);
    }
    @Test
    void choose2() {

        Collector collector1=CollectorFactory.choose("stat");

        assertEquals(collector1.getClass(),CollectorStat.class);
    }
    @Test
    void choose3() {
        Collector collector1=CollectorFactory.choose("find");

        assertEquals(collector1.getClass(),CollectorFind.class);
    }
    @Test
    void choose4() {
        Collector collector1=CollectorFactory.choose("-top");

        assertEquals(collector1.getClass(),CollectorTop.class);
    }
    @Test
    void choose5() {

        Collector collector1=CollectorFactory.choose("-stat");

        assertEquals(collector1.getClass(),CollectorStat.class);
    }
    @Test
    void choose6() {
        Collector collector1=CollectorFactory.choose("-find");
        assertEquals(collector1.getClass(),CollectorFind.class);
    }
    @Test
    void choose7()throws IllegalArgumentException {

        assertThrows(IllegalArgumentException.class, () -> { CollectorFactory.choose("tre"); });

    }
}