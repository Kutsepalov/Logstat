package com.softserve.logstat.parser;

import com.softserve.logstat.exceptions.NoInputFileException;
import com.softserve.logstat.model.HTTPMethod;
import com.softserve.logstat.model.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArgParser {

    public Command chooseCollectorType(String[] args, Command command) {

        if (Objects.equals(args[0], "top")) {
            setCollector(args, "top", command);
            setPredicatesFilter(args, command);

        } else if (Objects.equals(args[0], "stat")) {
            setCollector(args, "stat", command);
            setPredicatesFilter(args, command);

        } else if (Objects.equals(args[0], "find")) {
            setCollector(args, "find", command);
            setPredicatesFilter(args, command);
            setParamTypes(args, command, findParamKey(args));
        }
        return command;
    }

    public Command parseStart(String[] args) throws NoInputFileException {
        Command command = new Command();
        chooseCollectorType(args, command);

        if (countTxtFiles(args) == 2) {
            setOutputAndInput(args, command, 2);
        } else if (countTxtFiles(args) == 1) {
            setOutputAndInput(args, command, 1);
        } else {
            throw new NoInputFileException("There is no input file");
        }
        return command;
    }

    private void setOutputAndInput(String[] args, Command command, int filesCount) {
        int count = 0;
        for (int i = 0; i < args.length; i++) {
            if (count == 2) {
                break;
            }
            if (isTxt(args[i]) && count != 1) {
                command.setInputFile(args[i]);
                if (filesCount == 1) {
                    break;
                }
                count++;
            } else if (isTxt(args[i]) && count == 1) {
                command.setOutputFile(args[i]);
                count++;
            }
        }
    }

    private int findParamKey(String[] args) {
        int res = 0;
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("params")) {
                res = i;
                break;
            }
        }
        return res;
    }

    private boolean isTxt(String string) {
        Matcher m = Pattern.compile(".txt").matcher(string);

        if (m.find()) {
            return true;
        }
        return false;
    }

    private int countTxtFiles(String[] args) {
        String argString = String.join(" ", args);
        int count = 0;
        Matcher m = Pattern.compile(".txt")
                .matcher(argString);


        for (String s : args) {
            if (m.find()) {
                count++;
            }
        }

        return count;
    }

    public void setCollector(String[] args, String type, Command command) {
        command.setCollectorType(type);
        if (type.equals("find")) {
            setPredicateToFind(args[1], args[2], command);
        }
        if (!isNumeric(args[1])) {
            setParamToCommand(args[1], command);

        } else {
            command.setLimit(Integer.parseInt(args[1]));
            setParamToCommand(args[2], command);
        }
    }

    private void setParamTypes(String[] args, Command command, int startPoint) {
        for (int i = startPoint; i < args.length; i++) {
            switch (args[i]) {
                case "-ip":
                    command.addToWrite(ParamType.IP);
                    break;
                case "-url":
                    command.addToWrite(ParamType.URL);
                    break;
                case "-httpm":
                    command.addToWrite(ParamType.METHOD);
                    break;
                case "-httpv":
                    command.addToWrite(ParamType.HTTPVERSION);
                    break;
                case "-sc":
                    command.addToWrite(ParamType.SC);
                    break;
                case "-date":
                    command.addToWrite(ParamType.DATETIME);
                    break;
                case "-agent":
                    command.addToWrite(ParamType.AGENT);
                    break;
                case "-refferer":
                    command.addToWrite(ParamType.REFFERER);
                    break;
                case "-size":
                    command.addToWrite(ParamType.SIZE);
                    break;
            }
        }
    }

    private void setPredicateToFind(String filter, String value, Command command) {
        Predicate<Log> predicate = null;
        switch (filter) {
            case "-ip":
                predicate = log -> (log.getIp() == value);
                break;
            case "-size":
                predicate = log -> (log.getIp() == value);
                break;
            case "-sc":
                predicate = log -> (log.getIp() == value);
                break;
            case "-httpmethod":
                predicate = log -> (log.getIp() == value);
                break;
            case "-httpversion":
                predicate = log -> (log.getIp() == value);
                break;
            case "-agent":
                predicate = log -> (log.getIp() == value);
                break;
            case "-referrer":
                predicate = log -> (log.getIp() == value);
                break;
            case "-time":
                predicate = log -> (log.getIp() == value);
                break;
            default:
                predicate = null;
        }

        command.setToFind(predicate);
    }

    public void setParamToCommand(String string, Command command) {
        switch (string) {
            case "-ip":
                command.addToWrite(ParamType.IP);
                break;

            case "-url":
                command.addToWrite(ParamType.URL);
                break;

            case "-httpv":
                command.addToWrite(ParamType.HTTPVERSION);
                break;

            case "-httpm":
                command.addToWrite(ParamType.METHOD);
                break;

            case "-sc":
                command.addToWrite(ParamType.SC);
                break;
        }
    }


    public boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);

        } catch (NumberFormatException exception) {
            return false;
        }
        return true;
    }

    public void setPredicatesFilter(String[] arguments, Command command) {
        List<Predicate<Log>> predicates = new ArrayList<>();
        addPredicate(arguments, predicates, "ip");
        addPredicate(arguments, predicates, "size");
        addPredicate(arguments, predicates, "sc");
        addPredicate(arguments, predicates, "httpmethod");
        addPredicate(arguments, predicates, "httpversion");
        addPredicate(arguments, predicates, "agent");
        addPredicate(arguments, predicates, "referrer");
        addPredicate(arguments, predicates, "time");

        command.setFilters(predicates);
    }

    protected Predicate<Log> getFilter(String field, String operator, String value) {
        Predicate<Log> operation = null;
        switch (operator) {
            case "eq":
                operation = log -> getFieldValue(field, log).compareTo(parseTo(value, field)) == 0;
                break;
            case "more":
                operation = log -> getFieldValue(field, log).compareTo(parseTo(value, field)) >= 0;
                break;
            case "less":
                operation = log -> getFieldValue(field, log).compareTo(parseTo(value, field)) < 0;
                break;
            case "not":
                operation = log -> getFieldValue(field, log).compareTo(parseTo(value, field)) != 0;
                break;
            default:
                operation = null;

        }
        return operation;
    }

    protected Comparable<?> getFieldValue(String fieldName, Log log) {
        Comparable<?> field = null;
        switch (fieldName) {
            case "url":
                field = log.getRequest();
                break;
            case "ip":
                field = log.getIp();
                break;
            case "size":
                field = log.getResponseSize();
                break;
            case "sc":
                field = log.getResponseCode();
                break;
            case "httpmethod":
                field = log.getMethod();
                break;
            case "httpversion":
                field = log.getHttpVersion();
                break;
            case "agent":
                field = log.getUserAgent();
                break;
            case "referrer":
                field = log.getReferrer();
                break;
            case "time":
                field = log.getDateTime();
                break;
            default:
                field = null;
        }
        return field;
    }

    protected <T> T parseTo(String value, String fieldName) {
        Object val = null;
        switch (fieldName) {
            case "size":
                val = Integer.valueOf(value);
                break;
            case "httpmethod":
                val = HTTPMethod.valueOf(value);
                break;
            case "sc":
                val = Short.valueOf(value);
                break;
            case "time":
                val = LocalDateTime.parse(value);
                System.out.println(value.getClass());
                break;
            default:
                val = null;
        }

        return (T) val;
    }

    public boolean isDate(String date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);

        try {
            dateFormat.parse(date);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    public void addPredicate(String[] arguments, List<Predicate<Log>> predicates, String filter) {
        String argString = String.join(" ", arguments);
        int predicatesCount = predicatesCount(arguments, argString, filter);
        for (int i = 0; i < arguments.length; i++) {
            if (predicatesCount == 0) {
                break;
            }
            if (predicatesCount > 0) {
                if (!arguments[i + 1].equals("between")) {
                    if ((arguments[i].equals("-" + filter)) && isNumeric(arguments[i + 2]) || isDate(arguments[i + 2])) {
                        var predicateValue = arguments[i + 2];
                        setPredicateByOperation(arguments[i + 1], predicates, filter, predicateValue);
                        predicatesCount--;

                    }
                } else {
                    addBetweenTime(arguments[i + 2], arguments[i + 3], predicates);
                    predicatesCount -= 2;
                }

            }
        }
    }


    public void setPredicateByOperation(String operation, List<Predicate<Log>> predicates, String filter, String predicateValue) {
        if (operation == "eq") {
            predicates.add(getFilter(filter, "eq", String.valueOf(predicateValue)));
        } else if (operation == "not") {
            predicates.add(getFilter(filter, "not", String.valueOf(predicateValue)));
        } else if (operation == "more") {
            predicates.add(getFilter(filter, "more", String.valueOf(predicateValue)));
        } else if (operation == "less") {
            predicates.add(getFilter(filter, "less", String.valueOf(predicateValue)));
        }
    }

    public void addBetweenTime(String time1, String time2, List<Predicate<Log>> predicates) {

        predicates.add(getFilter("time", "more", time1));
        predicates.add(getFilter("time", "less", time2));
    }

    public int predicatesCount(String[] arguments, String argString, String filter) {
        int count = 0;
        Matcher m = Pattern.compile("(" + filter + ")(\\s)(not|eq|more|less)")
                .matcher(argString);


        for (String s : arguments) {
            if (m.find()) {
                count++;
            }
        }
        if (count == 0 || filter == "time") {
            m = Pattern.compile("(" + filter + ")(\\s)(between)").matcher(argString);
            if (m.find()) {
                count += 2;
            }
        }

        return count;
    }
}