package com.pizza73.callCenter.report;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ACDExcelReport
{
   private static final short POS_ID_POS = 13;
   private static final short CALLS_ANSWD_POS = 1;
   private static final short TOTAL_DCP_POS = 3;
   private static final short TOTAL_HDCP_POS = 4;
   private static final short TOTAL_PCP_POS = 5;
   private static final short TOTAL_WAIT_POS = 6;
   private static final short DN_INC_POS = 7;
   private static final short INC_TIME_POS = 8;
   private static final short DN_OUT_POS = 9;
   private static final short OUT_TIME_POS = 10;
   private static final short NUM_XFER_IDN_POS = 11;
   private static final short NUM_XFER_ACD_POS = 12;   
   private static final short MANNED_TIME_POS = 2;
   private static final short AGT_ID_POS = 0;
 
   public ACDExcelReport()
   {}

   public void createExcelReport(Map<String, Map<String, ACDReport>> reports,
      HSSFWorkbook wb, String date)
   {
      HSSFSheet sheet = wb.createSheet("ACD Report-" + date);
      buildDataList(sheet, reports);
   }

   private void buildDataList(HSSFSheet sheet, 
      Map<String, Map<String, ACDReport>> reports)
   {  
      int rowNum = 0;
      Set<String> key = reports.keySet();
      Iterator<String> reportIter = key.iterator();
      while(reportIter.hasNext())
      {
         String reportName = reportIter.next();
         HSSFRow row = sheet.createRow(rowNum++);  
         short cellNum = 0;
         HSSFCell cell = row.createCell(cellNum);
         cell.setCellValue(reportName);
         rowNum = buildReport(sheet, reports.get(reportName), rowNum);
         rowNum++;
      }
   }

   private int buildReport(HSSFSheet sheet, Map<String, ACDReport> map, int rowNum)
   {
      HSSFRow row = null;
      HSSFCell cell = null;
      short cellNum = 0;
      
      Set<String> keySet = map.keySet();
      Iterator<String> iter = keySet.iterator();
      System.out.println("Report Type size: " + map.size());
      
      //write header
      row = sheet.createRow(rowNum++);
      StringTokenizer headerToken = 
         new StringTokenizer(ACDReport.headerLine());
      cellNum = 0; 
      //header line
      while(headerToken.hasMoreTokens())
      {
         String headerCell = headerToken.nextToken();
         cell = row.createCell(cellNum);
         cell.setCellValue(headerCell);
         cellNum++;
      }
      
      while(iter.hasNext())
      {
         ACDReport reportLine = map.get(iter.next());
         row = sheet.createRow(rowNum++);
         cellNum = 0;
            
         if(reportLine.getAgentId() != null)
         {
            cell = row.createCell(AGT_ID_POS);
            cell.setCellValue(reportLine.getAgentId());
         }
         if(reportLine.getCallsAnswered() != null)
         {
            cell = row.createCell(CALLS_ANSWD_POS);
            cell.setCellValue(reportLine.getCallsAnswered());
         }
         if(reportLine.getMannedTime() != null)
         {
            cell = row.createCell(MANNED_TIME_POS);
            cell.setCellValue(reportLine.getMannedTime());
         }
         if(reportLine.getTotalDCP() != null)
         {
            cell = row.createCell(TOTAL_DCP_POS);
            cell.setCellValue(reportLine.getTotalDCP());
         }
         if(reportLine.getTotalHDCP() != null)
         {
            cell = row.createCell(TOTAL_HDCP_POS);
            cell.setCellValue(reportLine.getTotalHDCP());
         }
         if(reportLine.getTotalPCP() != null)
         {
            cell = row.createCell(TOTAL_PCP_POS);
            cell.setCellValue(reportLine.getTotalPCP());
         }
         if(reportLine.getTotalWait() != null)
         {
            cell = row.createCell(TOTAL_WAIT_POS);
            cell.setCellValue(reportLine.getTotalWait());
         }
         if(reportLine.getDnInc() != null)
         {
            cell = row.createCell(DN_INC_POS);
            cell.setCellValue(reportLine.getDnInc());
         }
         if(reportLine.getIncTime() != null)
         {
            cell = row.createCell(INC_TIME_POS);
            cell.setCellValue(reportLine.getIncTime());
         }
         if(reportLine.getDnOut() != null)
         {
            cell = row.createCell(DN_OUT_POS);
            cell.setCellValue(reportLine.getDnOut());
         }
         if(reportLine.getOutTime() != null)
         {
            cell = row.createCell(OUT_TIME_POS);
            cell.setCellValue(reportLine.getOutTime());
         }
         if(reportLine.getNumberTransferredIDN() != null)
         {
            cell = row.createCell(NUM_XFER_IDN_POS);
            cell.setCellValue(reportLine.getNumberTransferredIDN());
         }
         if(reportLine.getNumberTransferredACD() != null)
         {
            cell = row.createCell(NUM_XFER_ACD_POS);
            cell.setCellValue(reportLine.getNumberTransferredACD());
         }
         if(reportLine.getPositionId() != null)
         {
            cell = row.createCell(POS_ID_POS);
            cell.setCellValue(reportLine.getPositionId());
         }
      }
      rowNum++;
      return rowNum;  
   }
}
