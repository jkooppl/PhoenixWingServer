package com.pizza73.webapp.base.exception;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * DataAccessExceptionController.java TODO comment me
 * 
 * @author chris 20-Sep-06
 * 
 * @Copyright Flying Pizza 73
 */
@Controller
public class DataAccessExceptionController
{   
   private static Logger log = Logger.getLogger(DataAccessExceptionController.class);
   protected static final String page = "dataAccessFailure";

   @RequestMapping("/dataAccessFailure.html")
   public String exception()
   {
      log.error("Data Access Exception");
      log.debug("dataAccessExceptionController.html requested");
      return page;
   }
}
