package com.pizza73.webapp.base.exception;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ExceptionController.java TODO comment me
 * 
 * @author chris 20-Sep-06
 * 
 * @Copyright Flying Pizza 73
 */
@Controller
public class ExceptionController
{
   private static Logger log = Logger.getLogger(ExceptionController.class);
   protected static final String page = "exception";

   @RequestMapping("/exception.html")
   public String exception()
   {
      log.debug("exception.html requested");
      return page;
   }
}
