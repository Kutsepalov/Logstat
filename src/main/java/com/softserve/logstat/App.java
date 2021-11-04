/**
 * Copyright 2021
 *
 * All rights reserved.
 *
 * Created on Oct 29, 2021 1:49:47 PM
 */
package com.softserve.logstat;

import com.softserve.logstat.model.Command;
import com.softserve.logstat.service.Controller;
import com.softserve.logstat.service.parser.ArgParser;
import com.softserve.logstat.service.parser.exceptions.NoInputFileException;


/**
 * @author <paste here your name>
 *
 */
public class App {

    /**
     * @param args is command arguments
     */
    public static void main(String[] args) {
	try {
	    ArgParser x = new ArgParser();
	    Controller controller = new Controller();
	    Command comm = x.parseStart(args);
	    controller.execute(comm);
	} catch (NoInputFileException e) {
	    System.out.println("Please, set a input file.");
	   
	} catch (Exception e) {
	    System.out.println("Something went wrong. \nError: \n" + e.getMessage());   
	}
    }
}
