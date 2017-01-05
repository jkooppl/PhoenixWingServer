package com.pizza73.reporting;


/**
 * ESPSalesReport - TODO comment me
 *
 * @author chris
 * Created on: 29-June-10
 * 
 * Copyright Pizza 73 2008
 */
public class ESPSalesReport
{
//   private static final Logger log = Logger.getLogger(ESPSalesReport.class);
//   private SalesManager salesManager;
//
//   private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
//   private static final String STYLE_1 = "style='background-color:#5ea9ff;color=black'";
//   private static final String SMTP = "mercury.nthodom1.ca";
//   private static final String FROM = "webadmin@pizza73.com";
//   private static final String SUBJECT = "Pizza 73 ESP Operator Report for ";
//   private static final String to = "chris@ctrlspace.ca";
   
//   protected ClassPathXmlApplicationContext ctx;
//
//   private static final double MILLI_SECONDS_IN_HOURS = 3600.0d;
//   private Calendar reportDate;
//   private Map<String, ESPSale> espSales = new TreeMap<String, ESPSale>();
//   
   public ESPSalesReport()
   {
   }
   
//   public void setUp()
//   {
//       String[] paths = {
//           "util-applicationContext-resources.xml", "applicationContext.xml" };
//       ctx = new ClassPathXmlApplicationContext(paths);
//       salesManager = (SalesManager)ctx.getBean("salesManager");
//   }
//
//   /**
//    * @param args
//    * @throws ParseException 
//    */
//   public static void main(String[] args) throws ParseException
//   {
//      String date = null;
//      if(args.length == 1)
//         date = args[0];
//      ESPSalesReport esp = new ESPSalesReport();
//      esp.setUp();
//      
//      if(null == esp.reportDate)
//      {
//         if(null != date)
//         {
//            Calendar c = Calendar.getInstance();
//            c.setTime(DATE_FORMAT.parse(date));
//            esp.reportDate = c;
//         }
//         else
//            esp.reportDate = esp.salesManager.businessDate();
//      }
//      List<Sales> sales = esp.salesManager.salesForEspOperatorsAndBusinessDate(esp.reportDate);
//   
//      Map<String, List<Sales>> operatorSales = splitSales(sales);
//      
//      for (String operator : operatorSales.keySet())
//      {
//         esp.espSales.put(operator, esp.parseSales(operator, operatorSales.get(operator)));
//      }
//      
//      String body = esp.toHtml();
//      esp.send(body, ESPSalesReport.SUBJECT + DATE_FORMAT.format(esp.reportDate.getTime()), "chris@ctrlspace.ca");
//   }
//
//   private ESPSale parseSales(String operator, List<Sales> salesList)
//   {
//      ESPSale espSale = parseESPSale(operator, salesList); 
//      
//      Object[] errors = this.salesManager.salesErrorsForEspOperator(operator, this.reportDate);
//      espSale.setErrors(((BigInteger)errors[0]).intValue());
//      espSale.setCostOfErrors((BigDecimal)errors[1]);
//      
//      return espSale;
//   }
//
//   private ESPSale parseESPSale(String operator, List<Sales> salesList)
//   {
//      ESPSale espSale = new ESPSale(operator);
//      espSale.setOrders(salesList.size());
//      
//      BigDecimal smallest = null;
//      BigDecimal largest = BigDecimal.ZERO;
//      Calendar cFirst = null;
//      Calendar cLast = null;
//      Calendar cSale = null;
//      BigDecimal combinedTotal = BigDecimal.ZERO;
//      
//      Sales sale = null;
//      Iterator<Sales> salesIterator = salesList.iterator();
//      while (salesIterator.hasNext())
//      {
//         sale = salesIterator.next();
//         
//         cSale = parseCalendar(sale.getOrderDate(), sale.getOrderHour(), sale.getOrderMinute());
//         if(null == cFirst)
//            cFirst = cSale;
//         if(null == cLast)
//            cLast = cSale;
//         
//         BigDecimal total = sale.getTotal();
//         char deliveryMethod = sale.getDeliveryMethod();
//         if(total.compareTo(BigDecimal.ZERO) == 1 && deliveryMethod != 'W')
//         {
//            if(sale.getTotal().compareTo(largest) == 1)
//               largest = sale.getTotal();
//            if(null == smallest)
//               smallest = largest;
//            if(sale.getTotal().compareTo(BigDecimal.ZERO) > 0 && sale.getTotal().compareTo(smallest) == -1)
//               smallest = sale.getTotal();
//            
//            if(cSale.compareTo(cLast) == 1)
//               cLast = cSale;
//            if(cSale.compareTo(cFirst) == -1)
//               cFirst = cSale;
//            
//            total = total.subtract(sale.getDeliveryCharge());
//            total = total.subtract(sale.getGst());
//            
//            combinedTotal = combinedTotal.add(total);
//         }
//      }
//
//      espSale.setNetSales(combinedTotal);      
//      espSale.setFirst(cFirst);            
//      espSale.setLast(cLast);            
//      espSale.setLargest(largest);
//      espSale.setSmallest(smallest);
//      
//      Duration duration = new Duration(espSale.getFirst().getTimeInMillis(), espSale.getLast().getTimeInMillis());
//      double dh = duration.getStandardSeconds() / MILLI_SECONDS_IN_HOURS;
//      BigDecimal rate = espSale.getNetSales().divide(BigDecimal.valueOf(dh),2,BigDecimal.ROUND_HALF_UP);
//      espSale.setRate(rate);
//      
//      return espSale;
//   }
//
//   private Calendar parseCalendar(Calendar date, String hour, String minute)
//   {
//      Calendar c = Calendar.getInstance();
//      c.set(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date
//            .get(Calendar.DATE), Integer.valueOf(hour), Integer
//            .valueOf(minute));
//      return c;
//   }
//
//   private static Map<String, List<Sales>> splitSales(List<Sales> sales)
//   {
//      Map<String, List<Sales>> operatorSalesMap = new HashMap<String, List<Sales>>();
//      
//      for (Sales sale : sales)
//      {
//         String operator = sale.getOperator();
//         if(!operatorSalesMap.containsKey(operator))
//         {
//            List<Sales> salesList = new ArrayList<Sales>();
//            salesList.add(sale);
//            operatorSalesMap.put(operator, salesList);
//         }
//         else
//         {
//            List<Sales> salesList = operatorSalesMap.get(operator);
//            salesList.add(sale);
//         }
//      }
//      
//      return operatorSalesMap;
//   }
//   
//   private String toHtml()
//   {
//      String html = 
//         "<html>\n\t<head>\n\t\t<title>Pizza Pizza Limited</title>" +
//         "\n\t<body bgcolor='#FFFFFF'>\n\t\t<center>\n\t\t<br>" +
//         "\n\t\t<table border=1>\n\t\t\t<tr " + STYLE_1 + 
//         "><td>Name</td><td>Net Sales</td><td>Orders</td><td>Largest</td>" +
//         "<td>Smallest</td><td>First</td><td>Last</td><td>Rate</td>" +
//         "<td>Errors</td><td>Cost of Errors</td></tr>\n";
//      
//      ESPSale sale = null;
//      for (String key : this.espSales.keySet())
//      {
//         sale = this.espSales.get(key);
//         html += "\t\t\t<tr align='right'>\n" +
//         "\t\t\t\t<td align='left'>" + key + "</td>" +
//         "\t\t\t\t<td>" + sale.getNetSales() + "</td>" +
//         "\t\t\t\t<td>" + sale.getOrders() + "</td>" +
//         "\t\t\t\t<td>" + sale.getLargest() + "</td>" +
//         "\t\t\t\t<td>" + sale.getSmallest() + "</td>" +
//         "\t\t\t\t<td>" + sale.firstTime() + "</td>" +
//         "\t\t\t\t<td>" + sale.lastTime() + "</td>" +
//         "\t\t\t\t<td>" + sale.getRate() + " / hr</td>" +
//         "\t\t\t\t<td>" + sale.getErrors() + "</td>" +
//         "\t\t\t\t<td>" + sale.getCostOfErrors() + "</td>" +
//         "\t\t\t</tr>\n";
//      }
//      
//      html += "\t\t</table>\n\t<br>\n\t<br>\n";
//      
//      return html;
//   }
//   
//   public  void send(String body,
//         String subject, String to)
//   {
//      try
//      {
//         Properties props = System.getProperties();
//         props.put("mail.smtp.host", ESPSalesReport.SMTP);
//         javax.mail.Session session = javax.mail.Session.getDefaultInstance(
//               props, null);
//         Message msg = new MimeMessage(session);
//         msg.setFrom(new InternetAddress(FROM));
//         msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
//         msg.setSubject(subject);
//         msg.setText(body);
//         msg.setSentDate(new Date());
//         msg.setHeader("Content-Type", "text/html");
////         msg.setHeader("charset", "'us-ascii'");
//         // -- Send the message --
//         Transport.send(msg);
//         System.out.println("Message sent OK.");
//      }
//      catch (Exception ex)
//      {
//         ex.printStackTrace();
//      }
//   }
}
