package com.softserve.logstat.service.collector;


import com.softserve.logstat.model.Command;
import com.softserve.logstat.model.Log;
import com.softserve.logstat.model.report.Report;
import java.util.stream.Stream;

/**
 * @author Max Silchenk
 *
 */
public class CollectorTop implements Collector {



	@Override
	public Report collect(Stream<Log> logs,Command command) {
		var wrapper=new Object(){ long place=1;};
		
		var result=logs.
				filter(command.getFilters().isEmpty()?
					log -> true:
					command.getFilters().	
					stream().
					reduce(cond -> true, Predicate::and)).
				map(x -> getFieldByParam(command.getType(), x).toString()).
				collect(groupingBy(log -> log ,counting())).
				entrySet().
				stream().
				sorted((x,y) -> y.getValue().compareTo(x.getValue())).
				limit(command.getLimit()).
				peek(log -> setPosition(log, wrapper.place++)).
				collect(Collectors.toMap(Entry::getKey, entry -> entry.getValue().intValue()));
		
		return new ReportTop(result);
		        
	}

	protected Entry<String,Long> setPosition(Entry<String, Long> val, long position) {
		val.setValue(position);
		return val;
	}

	protected Object getFieldByParam(ParamType type, Log log) {
		Object val = null;
		val=switch (type) {
		case URL -> log.getRequest();
		case IP ->log.getIp();
		case SIZE -> log.getResponseSize();
		case SC ->  log.getResponseCode();
		case DATETIME -> log.getDateTime();
		case AGENT -> log.getUserAgent();
		case HTTPVERSION -> log.getHttpVersion();
		case METHOD -> log.getMethod();
		case REFFERER -> log.getReferrer();
		default -> null;
		};
		return val;
	}

}
