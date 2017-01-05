package com.pizza73.webapp.util;

import java.text.ParseException;
import java.util.Calendar;

import org.apache.commons.lang.time.DateUtils;

public class DateParser {
   static public  Calendar parseTrimedDate(String date) throws ParseException{
	      String[] patterns={"yyyyMMdd"};
	      Calendar requestDate=Calendar.getInstance();
	      requestDate.setTime(DateUtils.parseDate(date, patterns));
	      requestDate=DateUtils.truncate(requestDate, Calendar.DATE);
	      return requestDate;
   }
}
