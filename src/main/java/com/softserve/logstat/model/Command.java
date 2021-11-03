package com.softserve.logstat.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import com.softserve.logstat.model.Log;
import com.softserve.logstat.service.parser.ParamType;

public class Command {
    private List<Predicate<Log>> filters;
    private List<ParamType> toWrite = new ArrayList<>();
    private Predicate<Log> toFind;
    private int limit;
    private String collectorType;
    private String inputFile;
    private String outputFile;

	public Predicate<Log> getToFind() {
        return toFind;
    }

    public void setToFind(Predicate<Log> toFind) {
        this.toFind = toFind;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public List<Predicate<Log>> getFilters() {
        return filters;
    }

    public void setFilters(List<Predicate<Log>> filters) {
        this.filters = filters;
    }

    public List<ParamType> getToWrite() {
        return toWrite;
    }

    public void setToWrite(List<ParamType> toWrite) {
        this.toWrite = toWrite;
    }

    public String getInputFile() {
        return inputFile;
    }

    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    public String getOutputFile() {
        return outputFile;
    }

    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }

    public void addToWrite(ParamType paramType) {
        this.toWrite.add(paramType);
    }

    public String getCollectorType() {
        return collectorType;
    }

    public void setCollectorType(String collectorType) {
        this.collectorType = collectorType;
    }

    @Override
    public String toString() {
        return "Command{" +
                "filters=" + filters +
                ", toWrite=" + toWrite +
                ", toFind=" + toFind +
                ", limit=" + limit +
                ", collectorType='" + collectorType + '\'' +
                ", inputFile='" + inputFile + '\'' +
                ", outputFile='" + outputFile + '\'' +
                '}';
    }
}