package com.pizza73.callCenter.report;

import java.io.Serializable;
import java.util.StringTokenizer;

public class ACDReport implements Serializable, Comparable<ACDReport>
{

   //FIELDS
   private static final long serialVersionUID = 5113665601031297301L;
   
   public static final String POS_ID = "POS_ID";
   public static final int POS_ID_POS = 1;
   public static final String CALLS_ANSWD = "CALLS_ANSWD";
   public static final int CALLS_ANSWD_POS = 2;
   public static final String TOTAL_DCP = "TOTAL_DCP";
   public static final int TOTAL_DCP_POS = 3;
   public static final String TOTAL_HDCP = "TOTAL_HDCP";
   public static final int TOTAL_HDCP_POS = 4;
   public static final String TOTAL_PCP = "TOTAL_PCP";
   public static final int TOTAL_PCP_POS = 5;
   public static final String TOTAL_WAIT = "TOTAL_WAIT";
   public static final int TOTAL_WAIT_POS = 6;
   public static final String DN_INC = "DN_INC";
   public static final int DN_INC_POS = 7;
   public static final String INC_TIME = "INC_TIME";
   public static final int INC_TIME_POS = 8;
   public static final String DN_OUT = "DN_OUT";
   public static final int DN_OUT_POS = 9;
   public static final String OUT_TIME = "OUT_TIME";
   public static final int OUT_TIME_POS = 10;
   public static final String NUM_XFER_IDN = "#-XFER_IDN";
   public static final int NUM_XFER_IDN_POS = 11;
   public static final String NUM_XFER_ACD = "#-XFER_ACD";
   public static final int NUM_XFER_ACD_POS = 12;   
   public static final String MANNED_TIME = "MANNED_TIME";
   public static final int MANNED_TIME_POS = 13;
   public static final String AGT_ID = "AGT_ID";
   public static final int AGT_ID_POS = 14;
   public static final String QP = "Q/P";
   public static final int QP_POS = 15;
   public static final String DATE = "DATE";
   
   private static final short DATE_POS_XLS = 0;
   private static final short AGT_ID_POS_XLS = 1;
   private static final short CALLS_ANSWD_POS_XLS = 2;
   private static final short MANNED_TIME_POS_XLS = 3;
   private static final short TOTAL_DCP_POS_XLS = 4;
   private static final short TOTAL_HDCP_POS_XLS = 5;
   private static final short TOTAL_PCP_POS_XLS = 6;
   private static final short TOTAL_WAIT_POS_XLS = 7;
   private static final short DN_INC_POS_XLS = 8;
   private static final short INC_TIME_POS_XLS = 9;
   private static final short DN_OUT_POS_XLS = 10;
   private static final short OUT_TIME_POS_XLS = 11;
   private static final short NUM_XFER_IDN_POS_XLS = 12;
   private static final short NUM_XFER_ACD_POS_XLS = 13;
   private static final short POS_ID_POS_XLS = 14;   
   
   private static final String TAB = "\t";
   
   private String positionId;
   private Integer callsAnswered;
   private Integer totalDCP;
   private Integer totalHDCP;
   private Integer totalPCP;
   private Integer totalWait;
   private Integer dnInc;
   private Integer incTime;
   private Integer dnOut;
   private Integer outTime;
   private Integer numberTransferredIDN;
   private Integer numberTransferredACD;
   private Integer mannedTime;
   private String agentId;
   private String qp;
   private String date;
   
   //CONSTRUCTOR
   public ACDReport(){}
   
   //METHODS
   public String getPositionId()
   {
      return positionId;
   }
   public void setPositionId(String positionId)
   {
      this.positionId = positionId;
   }
   public Integer getCallsAnswered()
   {
      return callsAnswered;
   }
   public void setCallsAnswered(Integer callsAnswered)
   {
      this.callsAnswered = callsAnswered;
   }
   public Integer getTotalDCP()
   {
      return totalDCP;
   }
   public void setTotalDCP(Integer totalDCP)
   {
      this.totalDCP = totalDCP;
   }
   public Integer getTotalHDCP()
   {
      return totalHDCP;
   }
   public void setTotalHDCP(Integer totalHDCP)
   {
      this.totalHDCP = totalHDCP;
   }
   public Integer getTotalPCP()
   {
      return totalPCP;
   }
   public void setTotalPCP(Integer totalPCP)
   {
      this.totalPCP = totalPCP;
   }
   public Integer getTotalWait()
   {
      return totalWait;
   }
   public void setTotalWait(Integer totalWait)
   {
      this.totalWait = totalWait;
   }
   public Integer getDnInc()
   {
      return dnInc;
   }
   public void setDnInc(Integer dnInc)
   {
      this.dnInc = dnInc;
   }
   public Integer getIncTime()
   {
      return incTime;
   }
   public void setIncTime(Integer incTime)
   {
      this.incTime = incTime;
   }
   public Integer getDnOut()
   {
      return dnOut;
   }
   public void setDnOut(Integer dnOut)
   {
      this.dnOut = dnOut;
   }
   public Integer getOutTime()
   {
      return outTime;
   }
   public void setOutTime(Integer outTime)
   {
      this.outTime = outTime;
   }
   public Integer getNumberTransferredIDN()
   {
      return numberTransferredIDN;
   }
   public void setNumberTransferredIDN(Integer numberTransferredIDN)
   {
      this.numberTransferredIDN = numberTransferredIDN;
   }
   public Integer getNumberTransferredACD()
   {
      return numberTransferredACD;
   }
   public void setNumberTransferredACD(Integer numberTransferredACD)
   {
      this.numberTransferredACD = numberTransferredACD;
   }
   public Integer getMannedTime()
   {
      return mannedTime;
   }
   public void setMannedTime(Integer mannedTime)
   {
      this.mannedTime = mannedTime;
   }
   public String getAgentId()
   {
      return agentId;
   }
   public void setAgentId(String agentId)
   {
      this.agentId = agentId;
   }
   public String getQp()
   {
      return qp;
   }
   public void setQp(String qp)
   {
      this.qp = qp;
   }
   
   public String getDate()
   {
      return this.date;
   }
   
   public void setDate(String date)
   {
      this.date = date;
   }
   
   public static final String headerLine()
   {
      StringBuffer line = new StringBuffer();
      line.append(AGT_ID).append(TAB).append(CALLS_ANSWD).append(TAB)
         .append(MANNED_TIME).append(TAB).append(TOTAL_DCP).append(TAB)
         .append(TOTAL_HDCP).append(TAB).append(TOTAL_PCP).append(TAB)
         .append(TOTAL_WAIT).append(TAB).append(DN_INC).append(TAB)
         .append(INC_TIME).append(TAB).append(DN_OUT).append(TAB)
         .append(OUT_TIME).append(TAB).append(NUM_XFER_IDN).append(TAB)
         .append(NUM_XFER_ACD).append(TAB).append(POS_ID);
      
      return line.toString();
   }
   
   public static final String headerLineWithDate()
   {
      StringBuffer line = new StringBuffer();
      line.append(DATE).append(TAB).append(AGT_ID).append(TAB)
         .append(CALLS_ANSWD).append(TAB).append(MANNED_TIME).append(TAB)
         .append(TOTAL_DCP).append(TAB).append(TOTAL_HDCP).append(TAB)
         .append(TOTAL_PCP).append(TAB).append(TOTAL_WAIT).append(TAB)
         .append(DN_INC).append(TAB).append(INC_TIME).append(TAB).append(DN_OUT)
         .append(TAB).append(OUT_TIME).append(TAB).append(NUM_XFER_IDN).append(TAB)
         .append(NUM_XFER_ACD).append(TAB).append(POS_ID);
      
      return line.toString();
   }
   
   public void parseLine(String line)
   {
      StringTokenizer tokenizer = new StringTokenizer(line);
      if(tokenizer.countTokens() == 14 || tokenizer.countTokens() == 15)
      {
         this.positionId = tokenizer.nextToken();
         this.callsAnswered = Integer.valueOf(tokenizer.nextToken());
         this.totalDCP = Integer.valueOf(tokenizer.nextToken());
         this.totalHDCP = Integer.valueOf(tokenizer.nextToken());
         this.totalPCP = Integer.valueOf(tokenizer.nextToken());
         this.totalWait = Integer.valueOf(tokenizer.nextToken());
         this.dnInc = Integer.valueOf(tokenizer.nextToken());
         this.incTime = Integer.valueOf(tokenizer.nextToken());
         this.dnOut = Integer.valueOf(tokenizer.nextToken());
         this.outTime = Integer.valueOf(tokenizer.nextToken());
         this.numberTransferredIDN = Integer.valueOf(tokenizer.nextToken());
         this.numberTransferredACD = Integer.valueOf(tokenizer.nextToken());
         this.mannedTime = Integer.valueOf(tokenizer.nextToken());
         this.agentId = tokenizer.nextToken();
      }
      else if(tokenizer.countTokens() == 13)
      {
         this.positionId = tokenizer.nextToken();
         this.callsAnswered = Integer.valueOf(tokenizer.nextToken());
         this.totalDCP = Integer.valueOf(tokenizer.nextToken());
         this.totalHDCP = Integer.valueOf(tokenizer.nextToken());
         this.totalPCP = Integer.valueOf(tokenizer.nextToken());
         this.totalWait = Integer.valueOf(tokenizer.nextToken());
         this.dnInc = Integer.valueOf(tokenizer.nextToken());
         this.incTime = Integer.valueOf(tokenizer.nextToken());
         this.dnOut = Integer.valueOf(tokenizer.nextToken());
         this.outTime = Integer.valueOf(tokenizer.nextToken());
         this.numberTransferredIDN = Integer.valueOf(tokenizer.nextToken());
         this.numberTransferredACD = Integer.valueOf(tokenizer.nextToken());
         this.mannedTime = Integer.valueOf(tokenizer.nextToken());
         this.agentId = "";
      }
      else
      {
         throw new IllegalArgumentException(
            "Input does not have required number of tokens");
      }
   }
   
   public void addLineDetails(String line)
   {
      StringTokenizer tokenizer = new StringTokenizer(line);
      if(tokenizer.countTokens() == 14 || tokenizer.countTokens() == 15)
      {
         //get rid of position id
         tokenizer.nextToken();
         this.callsAnswered += Integer.valueOf(tokenizer.nextToken());
         this.totalDCP += Integer.valueOf(tokenizer.nextToken());
         this.totalHDCP += Integer.valueOf(tokenizer.nextToken());
         this.totalPCP += Integer.valueOf(tokenizer.nextToken());
         this.totalWait += Integer.valueOf(tokenizer.nextToken());
         this.dnInc += Integer.valueOf(tokenizer.nextToken());
         this.incTime += Integer.valueOf(tokenizer.nextToken());
         this.dnOut += Integer.valueOf(tokenizer.nextToken());
         this.outTime += Integer.valueOf(tokenizer.nextToken());
         this.numberTransferredIDN += Integer.valueOf(tokenizer.nextToken());
         this.numberTransferredACD += Integer.valueOf(tokenizer.nextToken());
         this.mannedTime += Integer.valueOf(tokenizer.nextToken());
         //not worried about agentId
         //this.agentId = tokenizer.nextToken();
      }
      else if(tokenizer.countTokens() == 13)
      {
         // get rid of position id
         tokenizer.nextToken();
         this.callsAnswered += Integer.valueOf(tokenizer.nextToken());
         this.totalDCP += Integer.valueOf(tokenizer.nextToken());
         this.totalHDCP += Integer.valueOf(tokenizer.nextToken());
         this.totalPCP += Integer.valueOf(tokenizer.nextToken());
         this.totalWait += Integer.valueOf(tokenizer.nextToken());
         this.dnInc += Integer.valueOf(tokenizer.nextToken());
         this.incTime += Integer.valueOf(tokenizer.nextToken());
         this.dnOut += Integer.valueOf(tokenizer.nextToken());
         this.outTime += Integer.valueOf(tokenizer.nextToken());
         this.numberTransferredIDN += Integer.valueOf(tokenizer.nextToken());
         this.numberTransferredACD += Integer.valueOf(tokenizer.nextToken());
         this.mannedTime += Integer.valueOf(tokenizer.nextToken());
      }
      else
      {
         throw new IllegalArgumentException(
            "Input does not have required number of tokens");
      }
   }
   
   public String writeLine()
   {
      StringBuffer line = new StringBuffer();
      line.append(this.agentId).append(TAB).append(this.callsAnswered).append(TAB)
         .append(this.mannedTime).append(TAB).append(this.totalDCP).append(TAB)
         .append(this.totalHDCP).append(TAB).append(this.totalPCP).append(TAB)
         .append(this.totalWait).append(TAB).append(this.dnInc).append(TAB)
         .append(this.incTime).append(TAB).append(this.dnOut).append(TAB)
         .append(this.outTime).append(TAB).append(this.numberTransferredIDN)
         .append(TAB).append(this.numberTransferredACD).append(TAB)
         .append(this.positionId); 
      
      return line.toString();
   }
   
   public void addToSelf(ACDReport line)
   {
      this.callsAnswered += line.getCallsAnswered();
      this.totalDCP += line.getTotalDCP();
      this.totalHDCP += line.getTotalHDCP();
      this.totalPCP += line.getTotalPCP();
      this.totalWait += line.getTotalWait();
      this.dnInc += line.getDnInc();
      this.incTime += line.getIncTime();
      this.dnOut += line.getDnOut();
      this.outTime += line.getOutTime();
      this.numberTransferredIDN += line.getNumberTransferredIDN();
      this.numberTransferredACD += line.getNumberTransferredACD();
      this.mannedTime += line.getMannedTime();
   }

   public int compareTo(ACDReport o)
   {
      if (this.getAgentId() != null && o.getAgentId() != null)
      {
         return this.getAgentId().compareTo(o.getAgentId());
      }
      
      return 0;
   }

   public void populateForExcel(String cellValue, int count)
   {
      if(count == DATE_POS_XLS)
      {
         this.date = cellValue;
      }
      if(count == AGT_ID_POS_XLS)
      {
         this.agentId = cellValue;
      }
      else if(count == POS_ID_POS_XLS)
      {
         this.positionId = cellValue;
      }
      else if(count == CALLS_ANSWD_POS_XLS)
      {
         this.callsAnswered = Integer.parseInt(cellValue);
      }
      else if(count == TOTAL_DCP_POS_XLS)
      {
         this.totalDCP = Integer.parseInt(cellValue);
      }
      else if(count == TOTAL_HDCP_POS_XLS)
      {
         this.totalHDCP = Integer.parseInt(cellValue);
      }
      else if(count == TOTAL_PCP_POS_XLS)
      {
         this.totalPCP = Integer.parseInt(cellValue);
      }
      else if(count == TOTAL_WAIT_POS_XLS)
      {
         this.totalWait = Integer.parseInt(cellValue);
      }
      else if(count == DN_INC_POS_XLS)
      {
         this.dnInc = Integer.parseInt(cellValue);
      }
      else if(count == INC_TIME_POS_XLS)
      {
         this.incTime = Integer.parseInt(cellValue);
      }
      else if(count == DN_OUT_POS_XLS)
      {
         this.dnOut = Integer.parseInt(cellValue);
      }
      else if(count == OUT_TIME_POS_XLS)
      {
         this.outTime = Integer.parseInt(cellValue);
      }
      else if(count == NUM_XFER_IDN_POS_XLS)
      {
         this.numberTransferredIDN = Integer.parseInt(cellValue);
      }
      else if(count == NUM_XFER_ACD_POS_XLS)
      {
         this.numberTransferredACD = Integer.parseInt(cellValue);
      }
      else if(count == MANNED_TIME_POS_XLS)
      {
         this.mannedTime = Integer.parseInt(cellValue);
      }
   }
}