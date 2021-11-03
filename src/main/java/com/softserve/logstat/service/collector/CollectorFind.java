package com.softserve.logstat.service.collector;

import com.softserve.logstat.model.Log;
import com.softserve.logstat.model.report.Report;
import com.softserve.logstat.model.report.ReportFind;
import com.softserve.logstat.model.Command;
import static java.util.stream.Collectors.toMap;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Roman Grabovetskyi mock class
 */
public class CollectorFind extends CollectorTop {

	@Override
	public Report collect(Stream<Log> logs, Command command) {
		List<Log> logslist = new ArrayList<>(logs.collect(Collectors.toList()));
		var wrapper = new Object() {
			int index = 0;
		};
		
		var result = command.getToWrite()
				.stream()
				.map(type -> logslist.stream()
						.filter(command.getToFind())
				        .map(log -> getFieldByParam(type, log).toString())
				        .distinct()
				        .collect(Collectors.toList()))
				.collect(toMap(
			            line -> command.getToWrite().get(wrapper.index++).toString(), 
						line -> line));

		return new ReportFind(result);
	}
}
