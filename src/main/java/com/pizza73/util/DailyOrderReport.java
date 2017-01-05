package com.pizza73.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.pizza73.model.enums.OrderOrigin;
import com.pizza73.service.OrderManager;

/**
 * StreetAddressMappingTest.java TODO comment me
 * 
 * @author chris 9-Mar-07
 * 
 * @Copyright Flying Pizza 73
 */
public class DailyOrderReport
{
    protected FileSystemXmlApplicationContext ctx;
    private OrderManager orderManager = null;

    private static final String smtp = "mercury.nthodom1.ca";
    private static final String from = "webadmin@pizza73.com";
    private static final String to = "chris@ctrlspace.com";

    public void setUp()
    {
        String[] paths = { "applicationContext.xml", "applicationContext-resources.xml" };
        ctx = new FileSystemXmlApplicationContext(paths);
        orderManager = (OrderManager) ctx.getBean("orderManager");
    }

    public static void main(String[] args) throws Exception
    {
        DateFormat dFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
        DailyOrderReport dor = new DailyOrderReport();
        dor.setUp();

        Calendar businessDate = dor.orderManager.businessDate();
        // System.out.println(businessDate.get(Calendar.YEAR) + "-" +
        // businessDate.get(Calendar.MONTH) + "-" +
        // businessDate.get(Calendar.DATE)) ;
        // businessDate.add(Calendar.DATE, -8);
        // System.out.println("After: " + businessDate.get(Calendar.YEAR) + "-"
        // + businessDate.get(Calendar.MONTH) + "-" +
        // businessDate.get(Calendar.DATE)) ;

        Long totalOrders = dor.orderManager.orderCountForBusinessDate(businessDate);
        Long activeOrders = dor.orderManager.activeOrderCountForBusinessDate(businessDate);
        Long activeInternetCount = dor.orderManager.activeInternetOperatorCountForBusinessDate(businessDate);

        Long internetOnlyOrdersCount = dor.orderManager.orderCountForBusinessDateAndOrigin(businessDate, OrderOrigin.ONLINE);
        Long activeInternetOnlyOrdersCount = dor.orderManager.activeOrderCountForBusinessDateAndOrigin(businessDate, OrderOrigin.ONLINE);
        
        Long mobileOrdersCount = dor.orderManager.orderCountForBusinessDateAndOrigin(businessDate, OrderOrigin.MOBILE);
        Long activeMobileOrdersCount = dor.orderManager.activeOrderCountForBusinessDateAndOrigin(businessDate, OrderOrigin.MOBILE);

        Long totalActiveSales = dor.orderManager.totalActiveSalesForBusinessDate(businessDate);
        BigDecimal percentage = null;
        if (activeOrders != 0)
        {
            percentage = BigDecimal.valueOf(activeInternetCount.doubleValue() / activeOrders.doubleValue());
            percentage = percentage.multiply(BigDecimal.valueOf(100));
        }

        String date = dFormat.format(new Date(businessDate.getTimeInMillis()));
        String formattedPercentage = "N/A";
        if (percentage != null)
        {
            NumberFormat format = new DecimalFormat("###,###.00");
            formattedPercentage = format.format(percentage);
        }

        BigDecimal totalPercentage = null;
        if (totalActiveSales != 0)
        {
            totalPercentage = BigDecimal.valueOf(activeOrders.doubleValue() / totalActiveSales.doubleValue());
            totalPercentage = totalPercentage.multiply(BigDecimal.valueOf(100));
        }
        String formattedTotalPercentage = "N/A";
        NumberFormat format = new DecimalFormat("###,###.00");
        if (totalPercentage != null)
        {
            formattedTotalPercentage = format.format(totalPercentage);
        }

        BigDecimal totalOnlineDollar = getNetOnlineOrderTotal(businessDate, dor.orderManager);

        String body = "Total online/mobile order count: " + totalOrders + "\n";
        body += "Orders sent straight to the shop: " + activeInternetCount + "\n";
        body += "Percentage sent straight through: " + formattedPercentage + "%\n";
        body += "\nTotal orders: " + totalActiveSales + "\n";
        body += "Percentage of online orders: " + formattedTotalPercentage + "%\n\n";

        body += "Total online sales dollar amount: $";
        if (totalOnlineDollar != null)
        {
            body += format.format(totalOnlineDollar.floatValue()) + "\n";
        }
        else
        {
            body += " UNABLE TO CALCULATE TOTAL SALES FIGURE.";
        }
        
        body += "Online order count: " + internetOnlyOrdersCount + "\n";
        body += "Online order count sent directly to shop: " + activeInternetOnlyOrdersCount + "\n";
        body += "Mobile order count: " + mobileOrdersCount + "\n";
        body += "Mobile order count sent directly to shop: " + activeMobileOrdersCount + "\n";
        
        Long cancelledOrders = totalOrders - activeOrders;
        body += "\n\n\nTotal cancelled web/mobile orders (by CSC Admin): " + cancelledOrders;

        String subject = "[Pizza 73] Online order report for: " + date;

        String recipients = "";
        for (int i = 0; i < args.length; i++)
        {
            recipients += args[i] + " ";
        }
        if (StringUtils.isBlank(recipients))
        {
            recipients = DailyOrderReport.to;
        }
        System.out.println(recipients);
        send(DailyOrderReport.smtp, recipients, DailyOrderReport.from, subject, body);
    }

    private static BigDecimal getNetOnlineOrderTotal(Calendar businessDate, OrderManager orderManager)
    {
        BigDecimal totalOnlineDollar = null;
        Long totalActiveOnlineSalesDollar = orderManager.onlineOrderActiveSumForBusinessDate(businessDate);
        Long totalActiveOnlineSalesGST = orderManager.onlineOrderActiveGSTSumForBusinessDate(businessDate);
        Long totalActiveOnlineSalesDeliveryCharge = orderManager
            .onlineOrderActiveDeliverySumForBusinessDate(businessDate);
        if (totalActiveOnlineSalesDollar != null && totalActiveOnlineSalesGST != null
            && totalActiveOnlineSalesDeliveryCharge != null)
        {
            totalActiveOnlineSalesDollar = totalActiveOnlineSalesDollar - totalActiveOnlineSalesDeliveryCharge
                - totalActiveOnlineSalesGST;
            totalOnlineDollar = BigDecimal.valueOf(totalActiveOnlineSalesDollar);
            totalOnlineDollar = totalOnlineDollar.multiply(BigDecimal.valueOf(0.01));
        }

        return totalOnlineDollar;
    }

    public static void send(String smtpServer, String to, String from, String subject, String body)
    {
        try
        {
            Properties props = System.getProperties();
            props.put("mail.smtp.host", smtpServer);
            javax.mail.Session session = javax.mail.Session.getDefaultInstance(props, null);
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
            msg.setSubject(subject);
            msg.setText(body);
            // -- Set some other header information --
            // msg.setHeader("X-Mailer", "LOTONtechEmail");
            msg.setSentDate(new Date());
            // -- Send the message --
            Transport.send(msg);
            System.out.println("Message sent OK.");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
