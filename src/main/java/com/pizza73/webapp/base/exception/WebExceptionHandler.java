package com.pizza73.webapp.base.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/**
 * WebExceptionHandler.java TODO comment me
 *
 * @author chris
 * 26-Jan-07
 *
 * @Copyright Flying Pizza 73 
 */
public class WebExceptionHandler implements HandlerExceptionResolver
{
   private static final Logger log = Logger.getLogger(WebExceptionHandler.class);
   
   /* (non-Javadoc)
    * @see org.springframework.web.servlet.HandlerExceptionResolver#resolveException(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
    */
   public ModelAndView resolveException(HttpServletRequest request, 
      HttpServletResponse response, Object handler, Exception ex)
   {
	   ModelAndView mav = 
         new ModelAndView(new RedirectView("dataAccessFailure.html"));
     
     if(ex instanceof DataAccessException)
     {
         log.error("Sending error to dataAccessException page: {}", ex);
     }
     else 
     {
         log.error("Sending error to generic exception page: {}", ex);
         mav.setView(new RedirectView("exception.html"));
     }
     
     return mav;
	}

}
