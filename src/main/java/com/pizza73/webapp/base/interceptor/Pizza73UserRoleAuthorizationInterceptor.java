package com.pizza73.webapp.base.interceptor;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @author chris
 * 
 */
public class Pizza73UserRoleAuthorizationInterceptor extends
   HandlerInterceptorAdapter
{
   private static Logger log = Logger.getLogger(Pizza73UserRoleAuthorizationInterceptor.class);
   private String[] authorizedRoles;
   private String[] authorizedURLs;


   /**
    * Set the roles that this interceptor should treat as authorized.
    * @param authorizedRoles array of role names
    */
   public final void setAuthorizedRoles(String[] authorizedRoles) {
      this.authorizedRoles = authorizedRoles;
   }

   public final void setAuthorizedURLs(String[] authorizedURLs) {
      this.authorizedURLs = authorizedURLs;
   }

   @Override
   public final boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
         throws ServletException, IOException {
      boolean checkRole = false;
      
      if(this.authorizedURLs != null)
      {
         log.debug("authorized URLs length: " + authorizedURLs.length);
         for (int j = 0; j < this.authorizedURLs.length; j++) 
         {
            String authorizedURL = authorizedURLs[j];
            log.debug("authorized URL: " + authorizedURL);
            if(request.getRequestURI().equals(authorizedURL))
            {
               checkRole = true;   
               break;
            }
         }
      }
      if(!checkRole)
      {
         return true;
      }
      
      if (this.authorizedRoles != null) {
         for (int i = 0; i < this.authorizedRoles.length; i++) {
            if (request.isUserInRole(this.authorizedRoles[i])) {
               return true;
            }
         }
      }
      handleNotAuthorized(request, response, handler);
      return false;
   }

   /**
    * Handle a request that is not authorized according to this interceptor.
    * Default implementation sends HTTP status code 403 ("forbidden").
    * <p>This method can be overridden to write a custom message, forward or
    * redirect to some error page or login page, or throw a ServletException.
    * @param request current HTTP request
    * @param response current HTTP response
    * @param handler chosen handler to execute, for type and/or instance evaluation
    * @throws javax.servlet.ServletException if there is an internal error
    * @throws java.io.IOException in case of an I/O error when writing the response
    */
   protected void handleNotAuthorized(HttpServletRequest request, HttpServletResponse response, Object handler)
         throws ServletException, IOException {

      response.sendError(HttpServletResponse.SC_FORBIDDEN);
   }

}
