/**
 * 
 */
package com.pizza73.webapp.base.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chris
 *
 */
public class SuccessMessage implements Serializable
{

   // FIELDS
   private static final long serialVersionUID = -5477954481497804696L;
   
   private String header = "";
   private String message = "";
   private List<Object> list = new ArrayList<Object>();
   
   public SuccessMessage(){}
   
   public SuccessMessage(String header)
   {
      this.header = header;
   }
   
   public String getHeader()
   {
      return header;
   }
   public void setHeader(String header)
   {
      this.header = header;
   }
   public String getMessage()
   {
      return message;
   }
   public void setMessage(String message)
   {
      this.message = message;
   }
   public List<Object> getList()
   {
      return list;
   }
   public void setList(List<Object> list)
   {
      this.list = list;
   }
}
