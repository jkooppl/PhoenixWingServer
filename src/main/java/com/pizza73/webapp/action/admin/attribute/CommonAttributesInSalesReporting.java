package com.pizza73.webapp.action.admin.attribute;

import java.text.ParseException;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.pizza73.webapp.util.DateParser;

@Configurable
public class CommonAttributesInSalesReporting {
	@ModelAttribute("SELECTED_DATE")
	public Calendar populateSelectedDate(HttpServletRequest request) throws ParseException{
	    return DateParser.parseTrimedDate(request.getParameter("date"));
	}
}
