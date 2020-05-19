package com.mkyong.core.utils;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtils {

	private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);
	
	public static void main(String[] args) {

		logger.debug("[MAIN] Current Date : {}", getLocalCurrentDate());
		System.out.println(getLocalCurrentDate());
		
	}

	private static String getLocalCurrentDate() {
	
		LocalDate date = new LocalDate();
		return date.toString();
		
	}

}
