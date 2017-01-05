package com.pizza73.reporting;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pizza73.model.Product;
import com.pizza73.model.Shop;
import com.pizza73.model.theoretical.ProductTotalCost;
import com.pizza73.model.theoretical.TheoreticalWeeklySales;
import com.pizza73.service.LookupManager;
import com.pizza73.service.TheoreticalFoodCostManager;
import com.pizza73.type.PackagingTypeEnum;

/**
 * 
 * @author chris
 * 
 *         Select iq_order_final_status.sent_shop_id, orders_detail.quantity,
 *         iq_product.group_id, iq_product.size, iq_product.crust,
 *         iq_product.sauce from iq_order_final_status, orders, orders_detail,
 *         iq_product where iq_order_final_status.order_id=orders.id and
 *         orders.id=orders_detail.order_id and
 *         iq_order_final_status.final_confirmed_date='2010-12-07' and
 *         orders_detail.product_id=iq_product.id and orders.status<>'x' order
 *         by iq_order_final_status.sent_shop_id, iq_product.group_id,
 *         iq_product.size, iq_product.crust, iq_product.sauce
 */
public class TheoreticalFoodCost
{
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final Integer[] TWO_BOX_PRODUCT_IDS = new Integer[] { Integer.valueOf(12), Integer.valueOf(190),
        Integer.valueOf(247) };
    private static final String SMTP = "mercury.nthodom1.ca";
    private static final String FROM = "webadmin@pizza73.com";
    private static final String TO = "chris@ctrlspace.com";

    protected ClassPathXmlApplicationContext ctx;
    private TheoreticalFoodCostManager mgr;
    private LookupManager lMgr;

    private static Map<Integer, BigDecimal> packagingCostMap;

    public void setUp()
    {
        String[] paths = { "util-applicationContext-resources.xml", "applicationContext.xml" };
        ctx = new ClassPathXmlApplicationContext(paths);
        mgr = (TheoreticalFoodCostManager) ctx.getBean("theoreticalFoodCostManager");
        lMgr = (LookupManager) ctx.getBean("lookupManager");

        packagingCostMap = mgr.packagingCosts();
    }

    public static void main(String[] args) throws Exception
    {
        TheoreticalFoodCost tfc = new TheoreticalFoodCost();
        tfc.setUp();
        if (0 == args.length)
        {
            System.out.println("Usage error: list of email recipients is required.");
            return;
        }
        String recipients = args[0];

        Calendar date = Calendar.getInstance();
        if (2 == args.length)
        {
            try
            {
                Date enteredDate = DATE_FORMAT.parse(args[1]);
                date.setTime(enteredDate);
            }
            catch (ParseException e)
            {
                System.out.println("Usage error: Date must be in the form: yyyy-MM-dd");
                return;
            }
        }

        Map<Integer, Map<Integer, ProductTotalCost>> costMap = tfc.mgr.allProductCosts();

        PrintWriter error = new PrintWriter(new BufferedWriter(new FileWriter(new File("error-output.txt"))));

        String endDate = DATE_FORMAT.format(date.getTime());
        date.add(Calendar.DATE, -7);
        date.set(Calendar.HOUR, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.AM_PM, Calendar.AM);
        date.set(Calendar.SECOND, 0);
        String startDate = DATE_FORMAT.format(date.getTime());

        String data = "Theoretical Food Cost Report for " + startDate + " - " + endDate + "\n";
        data += "SHOP ID, SHOP NAME, FOOD COST, PACKAGING COST, TOTAL COST\n";

        final List<Shop> shops = tfc.lMgr.activeShops();
        for (final Shop shop : shops)
        {
            List<TheoreticalWeeklySales> weeklySalesForShop = tfc.mgr.weeklySalesForShopStartingOn(shop.getId(), date);
            BigDecimal totalFoodCost = BigDecimal.ZERO;

            error.println("Shop: " + shop.getId() + " - " + shop.getName());

            Map<PackagingTypeEnum, Integer> packagingSizeMap = new HashMap<PackagingTypeEnum, Integer>();
            for (final TheoreticalWeeklySales tws : weeklySalesForShop)
            {
                final Integer productId = tws.getProductId();
                final Integer sizeId = tws.getSizeId();
                ProductTotalCost prodCost = totalCostForProductAndSize(costMap, productId, sizeId);

                if (null != prodCost && null != prodCost.getCost())
                {
                    tfc.updatePackagingSizeMap(packagingSizeMap, productId, sizeId, tws.getQuantity());
                    totalFoodCost = totalFoodCost.add(prodCost.getCost().multiply(new BigDecimal(tws.getQuantity())));
                }
                else
                {
                    if (null == prodCost)
                    {
                        error.println(productId);
                    }
                    else if (null == prodCost.getCost())
                    {
                        error.println("ProdCost.getCost() is null for: " + productId);
                    }
                }
            }

            BigDecimal totalPackagingCost = calculatePackagingCost(packagingSizeMap);
            if (null != totalPackagingCost)
            {
                totalPackagingCost = totalPackagingCost.round(MathContext.DECIMAL32);
            }
            else
            {
                totalPackagingCost = BigDecimal.ZERO;
            }
            totalFoodCost = totalFoodCost.round(MathContext.DECIMAL32);
            System.out.println("SHOP: " + shop.getNewPayrollDepartmentNo() + " - " + shop.getName().trim());
            System.out.println("Food Cost: $" + totalFoodCost.toPlainString());
            System.out.println("Packaging Cost: $" + totalPackagingCost.toPlainString());
            System.out.println("Total Cost: $" + totalPackagingCost.add(totalFoodCost).toPlainString());
            System.out.println("###########################################");
            data += shop.getNewPayrollDepartmentNo() + "," + shop.getName().trim() + ","
                + totalFoodCost.toPlainString() + "," + totalPackagingCost.toPlainString() + ","
                + totalPackagingCost.add(totalFoodCost).toPlainString() + "\n";
            // data += "\nSHOP: " + shop.getNewPayrollDepartmentNo() + " - " +
            // shop.getName().trim() + "\n";
            // data += "Food Cost: $" + totalFoodCost.toPlainString() + "\n";
            // data += "Packaging Cost: $" + totalPackagingCost.toPlainString()
            // + "\n";
            // data += "Total Cost: $" +
            // totalPackagingCost.add(totalFoodCost).toPlainString() + "\n";
            // data += "###########################################\n";
        }
        // tfcOutput.close();
        error.close();

        String subject = "[Pizza 73] Online order report for: " + startDate + " - " + endDate;

        // String recipients = "";
        // for (int i = 0; i < args.length; i++)
        // {
        // recipients += args[i] + " ";
        // }
        if (StringUtils.isBlank(recipients))
        {
            recipients = TO;
        }
        System.out.println(recipients);
        send(SMTP, recipients, FROM, subject, data);
    }

    private static BigDecimal calculatePackagingCost(Map<PackagingTypeEnum, Integer> packagingSizeMap)
    {
        BigDecimal cost = BigDecimal.ZERO;
        for (PackagingTypeEnum packagingType : packagingSizeMap.keySet())
        {
            BigDecimal count = BigDecimal.valueOf(packagingSizeMap.get(packagingType).longValue());
            if (packagingType.getId().equals(1) || packagingType.getId().equals(2) || packagingType.getId().equals(3))
            {
                count = count.divide(BigDecimal.valueOf(2));
            }
            BigDecimal packageCost = packagingCostMap.get(packagingType.getId());
            cost = cost.add(packageCost.multiply(count));
        }
        return cost;
    }

    private void updatePackagingSizeMap(Map<PackagingTypeEnum, Integer> packagingSizeMap, Integer productId,
        Integer sizeId, Integer quantity)
    {
        Product product = this.lMgr.productById(productId);
        if (null != product)
        {
            Integer categoryId = product.getCategory().getId();
            PackagingTypeEnum typeEnum = PackagingTypeEnum.valueFor(categoryId, sizeId, productId);
            if (null != typeEnum)
            {
                Integer add = Integer.valueOf(quantity);
                if (ArrayUtils.contains(TWO_BOX_PRODUCT_IDS, productId))
                {
                    add = add * 2;
                }

                if (packagingSizeMap.containsKey(typeEnum))
                {
                    Integer count = packagingSizeMap.get(typeEnum);
                    packagingSizeMap.put(typeEnum, count + add);
                }
                else
                {
                    packagingSizeMap.put(typeEnum, add);
                }
            }
        }
    }

    private static ProductTotalCost totalCostForProductAndSize(Map<Integer, Map<Integer, ProductTotalCost>> costMap,
        Integer productId, Integer sizeId)
    {
        if (costMap.containsKey(productId))
        {
            Map<Integer, ProductTotalCost> sizeMap = costMap.get(productId);
            if (sizeMap.containsKey(sizeId))
            {
                ProductTotalCost cost = sizeMap.get(sizeId);
                return cost;
            }
        }
        return null;
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
