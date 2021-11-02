package com.softserve.logstat;

import com.softserve.logstat.model.Command;
import com.softserve.logstat.model.HTTPMethod;
import com.softserve.logstat.model.Log;
import com.softserve.logstat.model.ParamType;
import com.softserve.logstat.service.collector.CollectorStat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс-заглушка для тестов.
 * @author Dmirti Chistiuk
 */
public class ArgParser {

    public Command chooseCollectorType(String[] args) {
        Command command = new Command();

        if (Objects.equals(args[0], "-top")) {
            setCollector(args, "top", command);
            setPredicatesFilter(args, command);

        } else if (Objects.equals(args[0], "-stat")) {
            setCollector(args, "stat", command);
            setPredicatesFilter(args, command);

        } else if (Objects.equals(args[0], "-find")) {
            setCollector(args, "find", command);
            setPredicatesFilter(args, command);
        }
        System.out.println(command.toString());
        return command;
    }

    public void setCollector(String[] args, String type, Command command) {
        command.setCollectorType(type);

        if (!isNumeric(args[1])) {
            setParamToCommand(args[1], command);

        } else {
            command.setLimit(Integer.parseInt(args[1]));
            setParamToCommand(args[2], command);

        }

    }

    public void setParamToCommand(String string, Command command) {
        switch (string) {

            case "ip":
                command.setParamType(ParamType.IP);
                break;

            case "url":
                command.setParamType(ParamType.URL);
                break;

            case "httpv":
                command.setParamType(ParamType.HTTPVERSION);
                break;

            case "httpm":
                command.setParamType(ParamType.HTTPMETHOD);
                break;

            case "rc":
                command.setParamType(ParamType.RC);
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
        addPredicate(arguments, predicates, "url");
        addPredicate(arguments, predicates, "ip");
        addPredicate(arguments, predicates, "size");
        addPredicate(arguments, predicates, "sc");
        addPredicate(arguments, predicates, "httpmethod");
        addPredicate(arguments, predicates, "httpversion");
        addPredicate(arguments, predicates, "agent");
        addPredicate(arguments, predicates, "refferer");
        addPredicate(arguments, predicates, "time");

        command.setPredicates(predicates);
    }

    protected Predicate<Log> getFilter(String field, String operator, String value) {
        Predicate<Log> operation = null;
        switch (operator) {
            case "eq":
                operation = log -> getFieldValue(field, log).compareTo(parseTo(value, field)) == 0;
                break;
            case "more":
                operation = log -> getFieldValue(field, log).compareTo(parseTo(value, field)) > 0;
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
            case "refferer":
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

    @SuppressWarnings("unchecked")
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
                break;
            default:
                val = null;
        }

        return (T) val;
    }

    public void addPredicate(String[] arguments, List<Predicate<Log>> predicates, String filter) {
        String argString = String.join(" ", arguments);
        int predicatesCount = predicatesCount(arguments, argString, filter);
        for (int i = 0; i < arguments.length; i++) {
            if (predicatesCount == 0) {
                break;
            }
            if (predicatesCount > 0) {
                if ((arguments[i].equals("-" + filter)) && isNumeric(arguments[i + 2])) {
                    if(arguments[i+1].equals("between")){
                        addBetweenTime(arguments[i+2], arguments[i+3], predicates);
                        break;
                    }
                    int predicateValue = Integer.parseInt(arguments[i + 2]);
                    if (arguments[i + 1] == "eq") {
                        predicates.add(getFilter(filter, "eq", String.valueOf(predicateValue)));
                        predicatesCount--;
                    } else if (arguments[i + 1] == "not") {
                        predicates.add(getFilter(filter, "not", String.valueOf(predicateValue)));
                        predicatesCount--;
                    } else if (arguments[i + 1] == "less") {
                        predicates.add(getFilter(filter, "less", String.valueOf(predicateValue)));
                        predicatesCount--;
                    } else if (arguments[i + 1] == "more") {
                        predicates.add(getFilter(filter, "more", String.valueOf(predicateValue)));
                        predicatesCount--;
                    }

                }
            }
        }
    }

    public void addBetweenTime(String time1, String time2, List<Predicate<Log>> predicates){
        predicates.add(getFilter("time","more",time1));
        predicates.add(getFilter("time","less",time2));
    }

    public int predicatesCount(String[] arguments, String argString, String filter) {
        int count = 0;
        Matcher m = Pattern.compile("("+filter+")(\\s)(not|eq|more|less)")
                .matcher(argString);

        for (String s : arguments) {
            if (m.find()) {
                count++;
            }
        }
        if(count == 0 && filter == "time"){
            m = Pattern.compile("("+filter+")(\\s)(between)").matcher(argString);
            if(m.find()){
                count++;
            }
        }

        return count;
    }
}
