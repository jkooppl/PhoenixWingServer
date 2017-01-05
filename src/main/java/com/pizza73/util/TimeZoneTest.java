package com.pizza73.util;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TimeZoneTest
{

   /**
    * @param args
    */
   public static void main(String[] args)
   {
      Calendar now = Calendar.getInstance();
//      TimeZone tzMt = TimeZone.getTimeZone("America/Edmonton");
      TimeZone tzMt = TimeZone.getTimeZone("MST7MDT");
      
      TimeZone tz = TimeZone.getTimeZone("Canada/Saskatchewan");
      Calendar skClose = Calendar.getInstance();
      skClose.setTimeZone(tz);

      
      Calendar close = Calendar.getInstance();
      close.set(Calendar.YEAR, now.get(Calendar.YEAR));
      close.set(Calendar.MONTH, now.get(Calendar.MONTH));
      close.set(Calendar.DAY_OF_MONTH, 14);
      close.set(Calendar.HOUR_OF_DAY, 11);
      close.set(Calendar.MINUTE, 55);
      close.set(Calendar.SECOND, 0);
      close.set(Calendar.MILLISECOND, 0);
//      System.out.println("OPEN: " +  open.get(Calendar.YEAR) + "-" + open.get(Calendar.MONTH) + "-" + open.get(Calendar.DAY_OF_WEEK) + " " + open.get(Calendar.HOUR_OF_DAY) + ":" + open.get(Calendar.MINUTE));
      System.out.println("CLOSE: " +  close.get(Calendar.YEAR) + "-" + close.get(Calendar.MONTH) + "-" + close.get(Calendar.DAY_OF_WEEK) + " " + close.get(Calendar.HOUR_OF_DAY) + ":" +close.get(Calendar.MINUTE));
      System.out.println("SK_CLOSE: " +  skClose.get(Calendar.YEAR) + "-" + skClose.get(Calendar.MONTH) + "-" + skClose.get(Calendar.DAY_OF_WEEK) + " " + skClose.get(Calendar.HOUR_OF_DAY) + ":" +skClose.get(Calendar.MINUTE));
      System.out.println("NOW: " +  now.get(Calendar.YEAR) + "-" + now.get(Calendar.MONTH) + "-" + now.get(Calendar.DAY_OF_WEEK) + " " + now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.MINUTE));           
      
//      System.out.println("OPEN: " +  open.getTimeInMillis());
      System.out.println("CLOSE: " +  close.getTimeInMillis());
      System.out.println("SK_CLOSE: " +  skClose.getTimeInMillis());
      System.out.println("NOW: " +  now.getTimeInMillis());
      Date date = new Date(now.getTimeInMillis());
      if(tzMt.inDaylightTime(date))
      {
         System.out.println("in daylight time");
      }
      if(!tzMt.inDaylightTime(now.getTime()))
      {
         System.out.println("NOT in daylight time");
      }
      
      if(close.after(now))
      {
         System.out.println("after");
      }
      if(close.before(now))
      {
         System.out.println("before");
      }
      if(close.equals(now))
      {
         System.out.println("equal");
      }
   }

}
