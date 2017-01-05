package com.pizza73.callCenter.report;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;

import org.apache.commons.lang.time.DateUtils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Component;

/**
 * 
 * @author chris
 * 
 */
@Component
public class WeeklyACDExcelReport
{
   private static final short DATE_ID_POS = 0;
   private static final short AGT_ID_POS = 1;
   private static final short CALLS_ANSWD_POS = 2;
   private static final String CALLS_ANSWD_COL = "C";
   private static final short MANNED_TIME_POS = 3;
   private static final String MANNED_TIME_COL = "D";
   private static final short TOTAL_DCP_POS = 4;
   private static final String TOTAL_DCP_COL = "E";
   private static final short TOTAL_HDCP_POS = 5;
   private static final String TOTAL_HDCP_COL = "F";
   private static final short TOTAL_PCP_POS = 6;
   private static final String TOTAL_PCP_COL = "G";
   private static final short TOTAL_WAIT_POS = 7;
   private static final String TOTAL_WAIT_COL = "H";
   private static final short DN_INC_POS = 8;
   private static final String DN_INC_COL = "I";
   private static final short INC_TIME_POS = 9;
   private static final String INC_TIME_COL = "J";
   private static final short DN_OUT_POS = 10;
   private static final String DN_OUT_COL = "K";
   private static final short OUT_TIME_POS = 11;
   private static final String OUT_TIME_COL = "L";
   private static final short NUM_XFER_IDN_POS = 12;
   private static final String NUM_XFER_IDN_COL = "M";
   private static final short NUM_XFER_ACD_POS = 13;
   private static final String NUM_XFER_ACD_COL = "N";
   private static final short POS_ID_POS = 14;
   private static final short SUMMARY_LABEL=15;
   private static final short SUMMARY_DIGIT=16;

   private static String FORMAT_STYLE = "0.00%";
   private static HSSFCellStyle style;
   
   private static final String[] datePatterns={"yyyyMMdd"};
   private static final SimpleDateFormat excelDateFormat=new SimpleDateFormat("yyyyMMdd");
   
   public WeeklyACDExcelReport()
   {}

   public HSSFWorkbook createExcelReport(Map<String, Map<String, List<ACDReport>>> weekly) throws ParseException
   {
      HSSFWorkbook wb = new HSSFWorkbook();
      
      style = wb.createCellStyle();
      style.setDataFormat(HSSFDataFormat.getBuiltinFormat(FORMAT_STYLE));
      HSSFFont font = wb.createFont();
      font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
      style.setFont(font);
      
      Set<String> keySet = weekly.keySet();
      Iterator<String> iter = keySet.iterator();
      String key = null;
      while (iter.hasNext())
      {
         key = iter.next();
         buildReport(wb.createSheet(key), weekly.get(key), key);
      }
      
      return wb;
   }

   @SuppressWarnings("unchecked")
   public static void parseSheet(HSSFSheet sheet,
         Map<String, Map<String, List<ACDReport>>> report, String date)
   {  
      HSSFRow row = null;
      HSSFCell cell = null;
      
      Iterator rowIter = sheet.rowIterator();
      
      Map<String, List<ACDReport>> map = null;
      while(rowIter.hasNext())
      {
         short cellnum = 0;
         row = (HSSFRow)rowIter.next();
         String sValue = null;
         if(row.getFirstCellNum() == row.getLastCellNum())
         {
            cell = row.getCell(cellnum);
            sValue = cell.getStringCellValue();
            if(!report.containsKey(sValue))
            {
               map = new TreeMap<String, List<ACDReport>>();
               report.put(sValue, map);
            }
            else
            {
               map = report.get(sValue);
            }
         }
         else
         {
            cellnum = 0;
            cell = row.getCell(cellnum);
            int count = 1;
            boolean headerRow = false;
            if(cell.getCellType() != HSSFCell.CELL_TYPE_BLANK)
            {
               ACDReport acd = new ACDReport();
               Iterator cellIter = row.cellIterator();
               while(cellIter.hasNext())
               {
                  cell = (HSSFCell)cellIter.next();
                  String cellValue = null;
                  if(cell.getCellType() == HSSFCell.CELL_TYPE_STRING)
                  {
                     cellValue = cell.getStringCellValue();
                     if(cellValue.equals(ACDReport.AGT_ID))
                     {
                        headerRow = true;
                        break;
                     }
                     else
                     {
                        acd.populateForExcel(cellValue, count);
                     }
                  }
                  else
                  {
                     int value = 
                        BigDecimal.valueOf(cell.getNumericCellValue()).intValue();
                     cellValue = value +""; 
                     acd.populateForExcel(cellValue, count);
                  }
                  count++;
               }
               if(!headerRow)
               {
                  acd.setDate(date);
                  List<ACDReport> acds = null;
                  if(map.containsKey(acd.getAgentId()))
                  {
                     acds = map.get(acd.getAgentId());
                     acds.add(acd);
                  }
                  else
                  {
                     acds = new ArrayList<ACDReport>();
                     acds.add(acd);
                     map.put(acd.getAgentId(), acds);
                  }
               }
               else
               {
                  headerRow = false;
               }
            }
         }
            
      }
   }

   private void buildReport(HSSFSheet sheet, Map<String, List<ACDReport>> map, String name) throws ParseException
   {
      HSSFRow row = null;
      int rownum = 0;
      HSSFCell cell = null;
      short cellnum = 0;
      
      row = sheet.createRow(rownum++);
      cell = row.createCell(cellnum);
      cell.setCellValue(name);
      //write header

      
      Set<String> keySet = map.keySet();
      Iterator<String> iter = keySet.iterator();
      System.out.println("Report Type size: " + map.size());
      while(iter.hasNext())
      { row = sheet.createRow(rownum++);
        StringTokenizer headerToken = 
           new StringTokenizer(ACDReport.headerLineWithDate());
        cellnum = 0; 
        //header line
        while(headerToken.hasMoreTokens())
        {
          String headerCell = headerToken.nextToken();
          cell = row.createCell(cellnum);
          cell.setCellValue(headerCell);
          cellnum++;
        }
         
         List<ACDReport> acds = map.get(iter.next());
         zeroFillMissingACDReports(acds);
         Iterator<ACDReport> acdIter = acds.iterator();
         ACDReport acd = null;
         int startRow = rownum + 1;
         while(acdIter.hasNext())
         {
            acd = acdIter.next();
            row = sheet.createRow(rownum++);
            cellnum = 1;
             
            if(acd.getDate() != null)
            {
               cell = row.createCell(DATE_ID_POS);
               cell.setCellValue(acd.getDate());
            }
            if(acd.getAgentId() != null)
            {
               cell = row.createCell(AGT_ID_POS);
               cell.setCellValue(acd.getAgentId());
            }
            if(acd.getCallsAnswered() != null)
            {
               cell = row.createCell(CALLS_ANSWD_POS);
               cell.setCellValue(acd.getCallsAnswered());
            }
            if(acd.getMannedTime() != null)
            {
               cell = row.createCell(MANNED_TIME_POS);
               cell.setCellValue(acd.getMannedTime());
            }
            if(acd.getTotalDCP() != null)
            {
               cell = row.createCell(TOTAL_DCP_POS);
               cell.setCellValue(acd.getTotalDCP());
            }
            if(acd.getTotalHDCP() != null)
            {
               cell = row.createCell(TOTAL_HDCP_POS);
               cell.setCellValue(acd.getTotalHDCP());
            }
            if(acd.getTotalPCP() != null)
            {
               cell = row.createCell(TOTAL_PCP_POS);
               cell.setCellValue(acd.getTotalPCP());
            }
            if(acd.getTotalWait() != null)
            {
               cell = row.createCell(TOTAL_WAIT_POS);
               cell.setCellValue(acd.getTotalWait());
            }
            if(acd.getDnInc() != null)
            {
               cell = row.createCell(DN_INC_POS);
               cell.setCellValue(acd.getDnInc());
            }
            if(acd.getIncTime() != null)
            {
               cell = row.createCell(INC_TIME_POS);
               cell.setCellValue(acd.getIncTime());
            }
            if(acd.getDnOut() != null)
            {
               cell = row.createCell(DN_OUT_POS);
               cell.setCellValue(acd.getDnOut());
            }
            if(acd.getOutTime() != null)
            {
               cell = row.createCell(OUT_TIME_POS);
               cell.setCellValue(acd.getOutTime());
            }
            if(acd.getNumberTransferredIDN() != null)
            {
               cell = row.createCell(NUM_XFER_IDN_POS);
               cell.setCellValue(acd.getNumberTransferredIDN());
            }
            if(acd.getNumberTransferredACD() != null)
            {
               cell = row.createCell(NUM_XFER_ACD_POS);
               cell.setCellValue(acd.getNumberTransferredACD());
            }
            if(acd.getPositionId() != null)
            {
               cell = row.createCell(POS_ID_POS);
               cell.setCellValue(acd.getPositionId());
            }
         }
         
         rownum = writeTotal(sheet, startRow, acds.size(), rownum);
         
         
         cellnum = 1;
         
         rownum++;
      }
      rownum++;
   }

   private int writeTotal(HSSFSheet sheet, int startRow, int sumRows, int rownum)
   {
      HSSFRow row = sheet.createRow(rownum++);
      HSSFCell cell = row.createCell(DATE_ID_POS);
      cell.setCellValue("TOTAL");
      int endRow = startRow + sumRows - 1;
      for (int i = 2; i < 14; i++)
      {
         
         if(i == CALLS_ANSWD_POS)
         {
            cell = row.createCell(CALLS_ANSWD_POS);
            cell.setCellFormula(
               "SUM(" + CALLS_ANSWD_COL + startRow + ":" 
               + CALLS_ANSWD_COL + endRow + ")");
         }
         if(i == MANNED_TIME_POS)
         {
            cell = row.createCell(MANNED_TIME_POS);
            cell.setCellFormula(
               "SUM(" + MANNED_TIME_COL + startRow + ":" 
               + MANNED_TIME_COL + endRow + ")");
         }
         if(i == TOTAL_DCP_POS)
         {
            cell = row.createCell(TOTAL_DCP_POS);
            cell.setCellFormula(
               "SUM(" + TOTAL_DCP_COL + startRow + ":" 
               + TOTAL_DCP_COL + endRow + ")");
         }
         if(i == TOTAL_HDCP_POS)
         {
            cell = row.createCell(TOTAL_HDCP_POS);
            cell.setCellFormula(
               "SUM(" + TOTAL_HDCP_COL + startRow + ":" 
               + TOTAL_HDCP_COL + endRow + ")");
         }
         if(i == TOTAL_PCP_POS)
         {
            cell = row.createCell(TOTAL_PCP_POS);
            cell.setCellFormula(
               "SUM(" + TOTAL_PCP_COL + startRow + ":" + TOTAL_PCP_COL + endRow + ")");
         }
         if(i == TOTAL_WAIT_POS)
         {
            cell = row.createCell(TOTAL_WAIT_POS);
            cell.setCellFormula(
               "SUM(" + TOTAL_WAIT_COL + startRow + ":" 
               + TOTAL_WAIT_COL + endRow + ")");
         }
         if(i == DN_INC_POS)
         {
            cell = row.createCell(DN_INC_POS);
            cell.setCellFormula(
               "SUM(" + DN_INC_COL + startRow + ":" + DN_INC_COL + endRow + ")");
         }
         if(i == INC_TIME_POS)
         {
            cell = row.createCell(INC_TIME_POS);
            cell.setCellFormula(
               "SUM(" + INC_TIME_COL + startRow + ":" + INC_TIME_COL + endRow + ")");
         }
         if(i == DN_OUT_POS)
         {
            cell = row.createCell(DN_OUT_POS);
            cell.setCellFormula(
               "SUM(" + DN_OUT_COL + startRow + ":" + DN_OUT_COL + endRow + ")");
         }
         if(i == OUT_TIME_POS)
         {
            cell = row.createCell(OUT_TIME_POS);
            cell.setCellFormula(
               "SUM(" + OUT_TIME_COL + startRow + ":" + OUT_TIME_COL + endRow + ")");
         }
         if(i == NUM_XFER_IDN_POS)
         {
            cell = row.createCell(NUM_XFER_IDN_POS);
            cell.setCellFormula(
               "SUM(" + NUM_XFER_IDN_COL + startRow + ":" 
               + NUM_XFER_IDN_COL + endRow + ")");
         }
         if(i == NUM_XFER_ACD_POS)
         {
            cell = row.createCell(NUM_XFER_ACD_POS);
            cell.setCellFormula(
               "SUM(" + NUM_XFER_ACD_COL + startRow + ":" 
               + NUM_XFER_ACD_COL + endRow + ")");
         }
      }
      
      endRow++;
      row =sheet.getRow(rownum-9);
     // row = sheet.createRow(rownum++);
      cell = row.createCell(SUMMARY_LABEL);
      cell.setCellValue("% NOT READY");
      cell = row.createCell(SUMMARY_DIGIT);
      cell.setCellFormula(TOTAL_PCP_COL + endRow + "/" + MANNED_TIME_COL + endRow);
      cell.setCellStyle(style);
      
      //row = sheet.createRow(rownum++);
      row =sheet.getRow(rownum-8);
      cell = row.createCell(SUMMARY_LABEL);
      cell.setCellValue("% XFRED OUT");
      cell = row.createCell(SUMMARY_DIGIT);
      cell.setCellFormula(NUM_XFER_ACD_COL + endRow + "/" + CALLS_ANSWD_COL + endRow);
      cell.setCellStyle(style);
      
      //row = sheet.createRow(rownum++);
      row =sheet.getRow(rownum-7);
      cell = row.createCell(SUMMARY_LABEL);
      cell.setCellValue("% OUTBOUND CALLS");
      cell = row.createCell(SUMMARY_DIGIT);
      cell.setCellFormula(OUT_TIME_COL + endRow + "/" + MANNED_TIME_COL + endRow);
      cell.setCellStyle(style);
      
      return rownum;
   }
   
   private void zeroFillMissingACDReports(List<ACDReport> acds) throws ParseException {
      ACDReport tempReport=acds.get(0);
      String agentId=tempReport.getAgentId();
      Date firstDay=DateUtils.parseDate(tempReport.getDate(),datePatterns);
      Date sunday=DateUtils.addDays(firstDay, 0-firstDay.getDay());
      Date tempDay;
      for(int i=0;i<7;i++){
         tempDay=DateUtils.addDays(sunday,i);
         checkAndAddACDReports(acds,tempDay,agentId);
      }
      
   }
   
   private void checkAndAddACDReports(List<ACDReport> acds, Date tempDay, String agentId) throws ParseException
   {  Date temp;
      int i=0;
      for(i=0;i<acds.size();i++){
         temp=DateUtils.parseDate(acds.get(i).getDate(),datePatterns);
         if(DateUtils.isSameDay(tempDay,temp))
            return;
         else if(temp.after(tempDay))
            break;            
   }
      ACDReport tempACDReport=new ACDReport();
      tempACDReport.setDate(WeeklyACDExcelReport.excelDateFormat.format(tempDay));
      tempACDReport.setAgentId(agentId);
      tempACDReport.setCallsAnswered(0);
      tempACDReport.setMannedTime(0);
      tempACDReport.setTotalDCP(0);
      tempACDReport.setTotalHDCP(0);
      tempACDReport.setTotalPCP(0);
      tempACDReport.setTotalWait(0);
      tempACDReport.setDnInc(0);
      tempACDReport.setIncTime(0);
      tempACDReport.setDnOut(0);
      tempACDReport.setOutTime(0);
      tempACDReport.setNumberTransferredIDN(0);
      tempACDReport.setNumberTransferredACD(0);
      tempACDReport.setPositionId("0");
      // i is the index with the first record after search date
      acds.add(i, tempACDReport);            
   }

}
