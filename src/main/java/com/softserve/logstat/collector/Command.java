package com.softserve.logstat.collector;

import com.softserve.logstat.model.Log;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class Command {
    private int limit;
    private ParamType paramType;
    private String collectorType;
    private List<Predicate<Log>> predicates;


    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public List<Predicate<Log>> getPredicates() {
        return predicates;
    }

    public void setPredicates(List<Predicate<Log>> predicates) {
        this.predicates = predicates;
    }

    public String getCollectorType() {
        return collectorType;
    }

    public void setCollectorType(String collectorType) {
        this.collectorType = collectorType;
    }

    public ParamType getParamType() {
        return paramType;
    }

    public void setParamType(ParamType paramType) {
        this.paramType = paramType;
    }

    @Override
    public String toString() {
        return "Command{" +
                "limit=" + limit +
                ", paramType=" + paramType +
                ", collectorType='" + collectorType + '\'' +
                ", predicates=" + predicates +
                '}';
    }
}
