package com.pizza73.webapp.viewResolver;

import com.pizza73.model.DailySales;
import com.pizza73.model.Shop;
import com.pizza73.model.WeeklySales;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

public class ExportWeeklySalesView extends AbstractExcelView {

    private static final SimpleDateFormat cellDateFormat = new SimpleDateFormat(
            "MMM/dd/yyyy");
    private static final SimpleDateFormat DayFormat = new SimpleDateFormat("EEE");
    private static final SimpleDateFormat MonthAndDateFormat = new SimpleDateFormat(
            "MMM/dd");

    @SuppressWarnings("unchecked")
    @Override
    protected void buildExcelDocument(Map map, HSSFWorkbook wb,
            HttpServletRequest request, HttpServletResponse Response) throws Exception
    {
        ArrayList<DailySales> dailySalesReports = (ArrayList<DailySales>) map
                .get("DAILYSALESREPORTS");
        ArrayList<Calendar> selectedWeek = (ArrayList<Calendar>) map.get("SELECTEDWEEK");
        Shop shop = (Shop) request.getSession().getAttribute("DAILYSALES_SHOP");
        WeeklySales weeklySales = (WeeklySales) map.get("WEEKLYSALES");
        Object labourCost = map.get("LABOUR_COST");
        HSSFSheet sheet = wb.createSheet("Weekly_Sales_" + "_Shop_" + (1000+shop.getId()));
        int lastRow = 0;
        lastRow = writeHeader(selectedWeek, shop, sheet, lastRow);
        writeWeeklySalesSummary(weeklySales, weeklySales.getLabourCost(), sheet,
                lastRow + 2);
        writeWeeklySalesReports(selectedWeek, dailySalesReports, sheet, lastRow + 2);
    }

    private void writeWeeklySalesReports(ArrayList<Calendar> selectedWeek,
            ArrayList<DailySales> dailySalesReports, HSSFSheet sheet, int lastRow)
    {
        int i = 0;
        int col = 4;
        BigDecimal tempSum = BigDecimal.valueOf(0, 2);
        // DAY info
        super.getCell(sheet, lastRow, 3).setCellValue("DAY:");
        for (i = 0, col = 4; i < 7; i++)
            super.getCell(sheet, lastRow, col++)
                    .setCellValue(
                            ExportWeeklySalesView.DayFormat.format(selectedWeek.get(i)
                                    .getTime()));
        super.getCell(sheet, lastRow, col++).setCellValue("TOTAL");
        lastRow++;
        // date info
        super.getCell(sheet, lastRow, 3).setCellValue("DATE:");
        for (i = 0, col = 4; i < 7; i++)
            super.getCell(sheet, lastRow, col++).setCellValue(
                    ExportWeeklySalesView.MonthAndDateFormat.format(selectedWeek.get(i)
                            .getTime()));
        lastRow += 2;
        // cash total
        super.getCell(sheet, lastRow, 3).setCellValue("+CASH");
        for (i = 0, col = 4, tempSum = BigDecimal.valueOf(0, 2); i < 7; i++)
        {
            if (dailySalesReports.get(i).getId() == null)
                super.getCell(sheet, lastRow, col).setCellValue("N/A");
            else if (!dailySalesReports.get(i).isSubmitted())
                super.getCell(sheet, lastRow, col).setCellValue("EDITING");
            else
            {
                super.getCell(sheet, lastRow, col).setCellValue(
                        dailySalesReports.get(i).getCashTotal().doubleValue());
                tempSum = tempSum.add(dailySalesReports.get(i).getCashTotal());
            }
            col++;
        }
        super.getCell(sheet, lastRow, col++).setCellValue(tempSum.doubleValue());
        lastRow++;
        // debit total
        super.getCell(sheet, lastRow, 3).setCellValue("+DEBIT");
        for (i = 0, col = 4, tempSum = BigDecimal.valueOf(0, 2); i < 7; i++)
        {
            if (dailySalesReports.get(i).getId() == null)
                super.getCell(sheet, lastRow, col).setCellValue("N/A");
            else if (!dailySalesReports.get(i).isSubmitted())
                super.getCell(sheet, lastRow, col).setCellValue("EDITING");
            else
            {
                super.getCell(sheet, lastRow, col).setCellValue(
                        dailySalesReports.get(i).getDebitTotal().doubleValue());
                tempSum = tempSum.add(dailySalesReports.get(i).getDebitTotal());
            }
            col++;
        }
        super.getCell(sheet, lastRow, col++).setCellValue(tempSum.doubleValue());
        lastRow++;
        // visa total
        super.getCell(sheet, lastRow, 3).setCellValue("+VISA");
        for (i = 0, col = 4, tempSum = BigDecimal.valueOf(0, 2); i < 7; i++)
        {
            if (dailySalesReports.get(i).getId() == null)
                super.getCell(sheet, lastRow, col).setCellValue("N/A");
            else if (!dailySalesReports.get(i).isSubmitted())
                super.getCell(sheet, lastRow, col).setCellValue("EDITING");
            else
            {
                super.getCell(sheet, lastRow, col).setCellValue(
                        dailySalesReports.get(i).getVisaTotal().doubleValue());
                tempSum = tempSum.add(dailySalesReports.get(i).getVisaTotal());
            }
            col++;
        }
        super.getCell(sheet, lastRow, col++).setCellValue(tempSum.doubleValue());
        lastRow++;
        // amex total
        super.getCell(sheet, lastRow, 3).setCellValue("+AMEX");
        for (i = 0, col = 4, tempSum = BigDecimal.valueOf(0, 2); i < 7; i++)
        {
            if (dailySalesReports.get(i).getId() == null)
                super.getCell(sheet, lastRow, col).setCellValue("N/A");
            else if (!dailySalesReports.get(i).isSubmitted())
                super.getCell(sheet, lastRow, col).setCellValue("EDITING");
            else
            {
                super.getCell(sheet, lastRow, col).setCellValue(
                        dailySalesReports.get(i).getAmexTotal().doubleValue());
                tempSum = tempSum.add(dailySalesReports.get(i).getAmexTotal());
            }
            col++;
        }
        super.getCell(sheet, lastRow, col++).setCellValue(tempSum.doubleValue());
        lastRow++;
        // mastercard total
        super.getCell(sheet, lastRow, 3).setCellValue("+MASTERCARD");
        for (i = 0, col = 4, tempSum = BigDecimal.valueOf(0, 2); i < 7; i++)
        {
            if (dailySalesReports.get(i).getId() == null)
                super.getCell(sheet, lastRow, col).setCellValue("N/A");
            else if (!dailySalesReports.get(i).isSubmitted())
                super.getCell(sheet, lastRow, col).setCellValue("EDITING");
            else
            {
                super.getCell(sheet, lastRow, col).setCellValue(
                        dailySalesReports.get(i).getMastercardTotal().doubleValue());
                tempSum = tempSum.add(dailySalesReports.get(i).getMastercardTotal());
            }
            col++;
        }
        super.getCell(sheet, lastRow, col++).setCellValue(tempSum.doubleValue());
        lastRow++;
        // pizza card total
        super.getCell(sheet, lastRow, 3).setCellValue("+PIZZA CARD");
        for (i = 0, col = 4, tempSum = BigDecimal.valueOf(0, 2); i < 7; i++)
        {
            if (dailySalesReports.get(i).getId() == null)
                super.getCell(sheet, lastRow, col).setCellValue("N/A");
            else if (!dailySalesReports.get(i).isSubmitted())
                super.getCell(sheet, lastRow, col).setCellValue("EDITING");
            else
            {
                super.getCell(sheet, lastRow, col).setCellValue(
                        dailySalesReports.get(i).getGiftcardTotal().doubleValue());
                tempSum = tempSum.add(dailySalesReports.get(i).getGiftcardTotal());
            }
            col++;
        }

        super.getCell(sheet, lastRow, col++).setCellValue(tempSum.doubleValue());

        Calendar Version3StartDate=Calendar.getInstance();
        Version3StartDate.set(2013, Calendar.FEBRUARY, 23, 0, 0, 0);
        if (selectedWeek.get(0).after(Version3StartDate))
        {  lastRow++;
        // catering credit total
	        super.getCell(sheet, lastRow, 3).setCellValue("+CATERING CREDIT");
	        for (i = 0, col = 4, tempSum = BigDecimal.valueOf(0, 2); i < 7; i++)
	        {
	            if (dailySalesReports.get(i).getId() == null)
	                super.getCell(sheet, lastRow, col).setCellValue("N/A");
	            else if (!dailySalesReports.get(i).isSubmitted())
	                super.getCell(sheet, lastRow, col).setCellValue("EDITING");
	            else
	            {
//	                super.getCell(sheet, lastRow, col).setCellValue(
//	                        dailySalesReports.get(i).getCateringCredit()
//	                                .doubleValue());
//	                tempSum = tempSum.add(dailySalesReports.get(i)
//	                        .getCateringCredit());
	            }
	            col++;
	        }

        super.getCell(sheet, lastRow, col++).setCellValue(tempSum.doubleValue());
        }
        lastRow++;
        // gift certificate total
        super.getCell(sheet, lastRow, 3).setCellValue("+GIFT CERTIFICATE REDEEMED");
        for (i = 0, col = 4, tempSum = BigDecimal.valueOf(0, 2); i < 7; i++)
        {
            if (dailySalesReports.get(i).getId() == null)
                super.getCell(sheet, lastRow, col).setCellValue("N/A");
            else if (!dailySalesReports.get(i).isSubmitted())
                super.getCell(sheet, lastRow, col).setCellValue("EDITING");
            else
            {
                super.getCell(sheet, lastRow, col).setCellValue(
                        dailySalesReports.get(i).getGiftCertificateRedeemed()
                                .doubleValue());
                tempSum = tempSum.add(dailySalesReports.get(i)
                        .getGiftCertificateRedeemed());
            }
            col++;
        }
        super.getCell(sheet, lastRow, col++).setCellValue(tempSum.doubleValue());
        lastRow++;
        // TOTAL TENDER
        super.getCell(sheet, lastRow, 3).setCellValue("=TOTAL TENDER");
        for (i = 0, col = 4, tempSum = BigDecimal.valueOf(0, 2); i < 7; i++)
        {
            if (dailySalesReports.get(i).getId() == null)
                super.getCell(sheet, lastRow, col).setCellValue("N/A");
            else if (!dailySalesReports.get(i).isSubmitted())
                super.getCell(sheet, lastRow, col).setCellValue("EDITING");
            else
            {
                super.getCell(sheet, lastRow, col).setCellValue(
                        dailySalesReports.get(i).getTotalTender().doubleValue());
                tempSum = tempSum.add(dailySalesReports.get(i).getTotalTender());
            }
            col++;
        }
        super.getCell(sheet, lastRow, col++).setCellValue(tempSum.doubleValue());
        lastRow++;
        // over/short total
        super.getCell(sheet, lastRow, 3).setCellValue("OVER/SHORT");
        for (i = 0, col = 4, tempSum = BigDecimal.valueOf(0, 2); i < 7; i++)
        {
            if (dailySalesReports.get(i).getId() == null)
                super.getCell(sheet, lastRow, col).setCellValue("N/A");
            else if (!dailySalesReports.get(i).isSubmitted())
                super.getCell(sheet, lastRow, col).setCellValue("EDITING");
            else
            {
                super.getCell(sheet, lastRow, col).setCellValue(
                        dailySalesReports.get(i).getOverShort().doubleValue());
                tempSum = tempSum.add(dailySalesReports.get(i).getOverShort());
            }
            col++;
        }
        super.getCell(sheet, lastRow, col++).setCellValue(tempSum.doubleValue());
        lastRow++;
        lastRow++;
        // Net to Pizza 73 total
        super.getCell(sheet, lastRow, 3).setCellValue("+NET TO PIZZA 73");
        for (i = 0, col = 4, tempSum = BigDecimal.valueOf(0, 2); i < 7; i++)
        {
            if (dailySalesReports.get(i).getId() == null)
                super.getCell(sheet, lastRow, col).setCellValue("N/A");
            else if (!dailySalesReports.get(i).isSubmitted())
                super.getCell(sheet, lastRow, col).setCellValue("EDITING");
            else
            {
                super.getCell(sheet, lastRow, col).setCellValue(
                        dailySalesReports.get(i).getNetToPizza73().doubleValue());
                tempSum = tempSum.add(dailySalesReports.get(i).getNetToPizza73());
            }
            col++;
        }
        super.getCell(sheet, lastRow, col++).setCellValue(tempSum.doubleValue());
        lastRow++;
        // Discounts/Advertising total
        super.getCell(sheet, lastRow, 3).setCellValue("+DISCOUNTS/ADVERTISING");
        for (i = 0, col = 4, tempSum = BigDecimal.valueOf(0, 2); i < 7; i++)
        {
            if (dailySalesReports.get(i).getId() == null)
                super.getCell(sheet, lastRow, col).setCellValue("N/A");
            else if (!dailySalesReports.get(i).isSubmitted())
                super.getCell(sheet, lastRow, col).setCellValue("EDITING");
            else
            {
                super.getCell(sheet, lastRow, col).setCellValue(
                        dailySalesReports.get(i).getDiscountsAndAdvertising()
                                .doubleValue());
                tempSum = tempSum.add(dailySalesReports.get(i)
                        .getDiscountsAndAdvertising());
            }
            col++;
        }
        super.getCell(sheet, lastRow, col++).setCellValue(tempSum.doubleValue());
        lastRow++;
        // Coupons total
        super.getCell(sheet, lastRow, 3).setCellValue("+COUPONS");
        for (i = 0, col = 4, tempSum = BigDecimal.valueOf(0, 2); i < 7; i++)
        {
            if (dailySalesReports.get(i).getId() == null)
                super.getCell(sheet, lastRow, col).setCellValue("N/A");
            else if (!dailySalesReports.get(i).isSubmitted())
                super.getCell(sheet, lastRow, col).setCellValue("EDITING");
            else
            {
                super.getCell(sheet, lastRow, col).setCellValue(
                        dailySalesReports.get(i).getCoupons().doubleValue());
                tempSum = tempSum.add(dailySalesReports.get(i).getCoupons());
            }
            col++;
        }
        super.getCell(sheet, lastRow, col++).setCellValue(tempSum.doubleValue());
        lastRow++;
        // net sales total
        super.getCell(sheet, lastRow, 3).setCellValue("=NET SALES");
        for (i = 0, col = 4, tempSum = BigDecimal.valueOf(0, 2); i < 7; i++)
        {
            if (dailySalesReports.get(i).getId() == null)
                super.getCell(sheet, lastRow, col).setCellValue("N/A");
            else if (!dailySalesReports.get(i).isSubmitted())
                super.getCell(sheet, lastRow, col).setCellValue("EDITING");
            else
            {
                super.getCell(sheet, lastRow, col).setCellValue(
                        dailySalesReports.get(i).getNetSales().doubleValue());
                tempSum = tempSum.add(dailySalesReports.get(i).getNetSales());
            }
            col++;
        }
        super.getCell(sheet, lastRow, col++).setCellValue(tempSum.doubleValue());
        lastRow++;
        // GST TOTAL
        super.getCell(sheet, lastRow, 3).setCellValue("+GST");
        for (i = 0, col = 4, tempSum = BigDecimal.valueOf(0, 2); i < 7; i++)
        {
            if (dailySalesReports.get(i).getId() == null)
                super.getCell(sheet, lastRow, col).setCellValue("N/A");
            else if (!dailySalesReports.get(i).isSubmitted())
                super.getCell(sheet, lastRow, col).setCellValue("EDITING");
            else
            {
                super.getCell(sheet, lastRow, col).setCellValue(
                        dailySalesReports.get(i).getGst().doubleValue());
                tempSum = tempSum.add(dailySalesReports.get(i).getGst());
            }
            col++;
        }
        super.getCell(sheet, lastRow, col++).setCellValue(tempSum.doubleValue());
        lastRow++;
        // Gross Sales
        super.getCell(sheet, lastRow, 3).setCellValue("=GROSS SALES");
        for (i = 0, col = 4, tempSum = BigDecimal.valueOf(0, 2); i < 7; i++)
        {
            if (dailySalesReports.get(i).getId() == null)
                super.getCell(sheet, lastRow, col).setCellValue("N/A");
            else if (!dailySalesReports.get(i).isSubmitted())
                super.getCell(sheet, lastRow, col).setCellValue("EDITING");
            else
            {
                super.getCell(sheet, lastRow, col).setCellValue(
                        dailySalesReports.get(i).getGrossSales().doubleValue());
                tempSum = tempSum.add(dailySalesReports.get(i).getGrossSales());
            }
            col++;
        }
        super.getCell(sheet, lastRow, col++).setCellValue(tempSum.doubleValue());
        lastRow++;
        lastRow++;
        // computer sales total
        super.getCell(sheet, lastRow, 3).setCellValue("+COMPUTER SALES");
        for (i = 0, col = 4, tempSum = BigDecimal.valueOf(0, 2); i < 7; i++)
        {
            if (dailySalesReports.get(i).getId() == null)
                super.getCell(sheet, lastRow, col).setCellValue("N/A");
            else if (!dailySalesReports.get(i).isSubmitted())
                super.getCell(sheet, lastRow, col).setCellValue("EDITING");
            else
            {
                super.getCell(sheet, lastRow, col).setCellValue(
                        dailySalesReports.get(i).getComputerSales().doubleValue());
                tempSum = tempSum.add(dailySalesReports.get(i).getComputerSales());
            }
            col++;
        }
        super.getCell(sheet, lastRow, col++).setCellValue(tempSum.doubleValue());
        lastRow++;
        // walk-in sales total
        super.getCell(sheet, lastRow, 3).setCellValue("+WALK-IN SALES");
        for (i = 0, col = 4, tempSum = BigDecimal.valueOf(0, 2); i < 7; i++)
        {
            if (dailySalesReports.get(i).getId() == null)
                super.getCell(sheet, lastRow, col).setCellValue("N/A");
            else if (!dailySalesReports.get(i).isSubmitted())
                super.getCell(sheet, lastRow, col).setCellValue("EDITING");
            else
            {
                super.getCell(sheet, lastRow, col).setCellValue(
                        dailySalesReports.get(i).getWalkInSales().doubleValue());
                tempSum = tempSum.add(dailySalesReports.get(i).getWalkInSales());
            }
            col++;
        }
        super.getCell(sheet, lastRow, col++).setCellValue(tempSum.doubleValue());
        lastRow++;
        // misc sales total
        super.getCell(sheet, lastRow, 3).setCellValue("+MISC. SALES");
        for (i = 0, col = 4, tempSum = BigDecimal.valueOf(0, 2); i < 7; i++)
        {
            if (dailySalesReports.get(i).getId() == null)
                super.getCell(sheet, lastRow, col).setCellValue("N/A");
            else if (!dailySalesReports.get(i).isSubmitted())
                super.getCell(sheet, lastRow, col).setCellValue("EDITING");
            else
            {
                super.getCell(sheet, lastRow, col).setCellValue(
                        dailySalesReports.get(i).getMiscSales().doubleValue());
                tempSum = tempSum.add(dailySalesReports.get(i).getMiscSales());
            }
            col++;
        }
        super.getCell(sheet, lastRow, col++).setCellValue(tempSum.doubleValue());
        lastRow++;
        // Returns Total
        super.getCell(sheet, lastRow, 3).setCellValue("-RETURNS");
        for (i = 0, col = 4, tempSum = BigDecimal.valueOf(0, 2); i < 7; i++)
        {
            if (dailySalesReports.get(i).getId() == null)
                super.getCell(sheet, lastRow, col).setCellValue("N/A");
            else if (!dailySalesReports.get(i).isSubmitted())
                super.getCell(sheet, lastRow, col).setCellValue("EDITING");
            else
            {
                super.getCell(sheet, lastRow, col).setCellValue(
                        dailySalesReports.get(i).getReturns().doubleValue());
                tempSum = tempSum.add(dailySalesReports.get(i).getReturns());
            }
            col++;
        }
        super.getCell(sheet, lastRow, col++).setCellValue(tempSum.doubleValue());
        lastRow++;
        // Net to Pizza 73
        super.getCell(sheet, lastRow, 3).setCellValue("=NET TO PIZZA 73");
        for (i = 0, col = 4, tempSum = BigDecimal.valueOf(0, 2); i < 7; i++)
        {
            if (dailySalesReports.get(i).getId() == null)
                super.getCell(sheet, lastRow, col).setCellValue("N/A");
            else if (!dailySalesReports.get(i).isSubmitted())
                super.getCell(sheet, lastRow, col).setCellValue("EDITING");
            else
            {
                super.getCell(sheet, lastRow, col).setCellValue(
                        dailySalesReports.get(i).getNetToPizza73().doubleValue());
                tempSum = tempSum.add(dailySalesReports.get(i).getNetToPizza73());
            }
            col++;
        }
        super.getCell(sheet, lastRow, col++).setCellValue(tempSum.doubleValue());
        lastRow++;
        // GST total
        super.getCell(sheet, lastRow, 3).setCellValue("+GST");
        for (i = 0, col = 4, tempSum = BigDecimal.valueOf(0, 2); i < 7; i++)
        {
            if (dailySalesReports.get(i).getId() == null)
                super.getCell(sheet, lastRow, col).setCellValue("N/A");
            else if (!dailySalesReports.get(i).isSubmitted())
                super.getCell(sheet, lastRow, col).setCellValue("EDITING");
            else
            {
                super.getCell(sheet, lastRow, col).setCellValue(
                        dailySalesReports.get(i).getGst().doubleValue());
                tempSum = tempSum.add(dailySalesReports.get(i).getGst());
            }
            col++;
        }
        super.getCell(sheet, lastRow, col++).setCellValue(tempSum.doubleValue());
        lastRow++;
        // gift certificate sales total
        super.getCell(sheet, lastRow, 3).setCellValue("+GIFT CERTIFICATE SOLD");
        for (i = 0, col = 4, tempSum = BigDecimal.valueOf(0, 2); i < 7; i++)
        {
            if (dailySalesReports.get(i).getId() == null)
                super.getCell(sheet, lastRow, col).setCellValue("N/A");
            else if (!dailySalesReports.get(i).isSubmitted())
                super.getCell(sheet, lastRow, col).setCellValue("EDITING");
            else
            {
                super.getCell(sheet, lastRow, col).setCellValue(
                        dailySalesReports.get(i).getGiftCertificateSold().doubleValue());
                tempSum = tempSum.add(dailySalesReports.get(i).getGiftCertificateSold());
            }
            col++;
        }
        super.getCell(sheet, lastRow, col++).setCellValue(tempSum.doubleValue());
        lastRow++;
        // pizza card reload total
        super.getCell(sheet, lastRow, 3).setCellValue("+PIZZA CARD RELOAD");
        for (i = 0, col = 4, tempSum = BigDecimal.valueOf(0, 2); i < 7; i++)
        {
            if (dailySalesReports.get(i).getId() == null)
                super.getCell(sheet, lastRow, col).setCellValue("N/A");
            else if (!dailySalesReports.get(i).isSubmitted())
                super.getCell(sheet, lastRow, col).setCellValue("EDITING");
            else
            {
                super.getCell(sheet, lastRow, col).setCellValue(
                        dailySalesReports.get(i).getGiftcardReload().doubleValue());
                tempSum = tempSum.add(dailySalesReports.get(i).getGiftcardReload());
            }
            col++;
        }
        super.getCell(sheet, lastRow, col++).setCellValue(tempSum.doubleValue());
        lastRow++;
        // total sales total
        super.getCell(sheet, lastRow, 3).setCellValue("=TOTAL RECEIPTS");
        for (i = 0, col = 4, tempSum = BigDecimal.valueOf(0, 2); i < 7; i++)
        {
            if (dailySalesReports.get(i).getId() == null)
                super.getCell(sheet, lastRow, col).setCellValue("N/A");
            else if (!dailySalesReports.get(i).isSubmitted())
                super.getCell(sheet, lastRow, col).setCellValue("EDITING");
            else
            {
                super.getCell(sheet, lastRow, col).setCellValue(
                        dailySalesReports.get(i).getTotalReceipts().doubleValue());
                tempSum = tempSum.add(dailySalesReports.get(i).getTotalReceipts());
            }
            col++;
        }
        super.getCell(sheet, lastRow, col++).setCellValue(tempSum.doubleValue());
        lastRow++;
    }

    private void writeWeeklySalesSummary(WeeklySales weeklySales, Object labourCost,
            HSSFSheet sheet, int lastRow)
    {
        super.getCell(sheet, lastRow++, 0).setCellValue(
                "FOOD INVENTORY AND COST CALCULATIONS");
        if (weeklySales != null)
        {
            super.getCell(sheet, lastRow, 0).setCellValue("OPENING INVENTORY");
            super.getCell(sheet, lastRow++, 1).setCellValue(
                    weeklySales.getOpeningInventory().doubleValue());
            super.getCell(sheet, lastRow++, 0).setCellValue("PURCHASES");
            super.getCell(sheet, lastRow, 0).setCellValue("COMMISSARY PURCHASES");
            super.getCell(sheet, lastRow++, 1).setCellValue(
                    weeklySales.getCommissaryPurchases().doubleValue());
            super.getCell(sheet, lastRow, 0).setCellValue("SYSCO");
            super.getCell(sheet, lastRow++, 1).setCellValue(
                    weeklySales.getSysco().doubleValue());
            super.getCell(sheet, lastRow, 0).setCellValue("LILYDALE");
            super.getCell(sheet, lastRow++, 1).setCellValue(
                    weeklySales.getLilydale().doubleValue());
            super.getCell(sheet, lastRow, 0).setCellValue("PEPSI");
            super.getCell(sheet, lastRow++, 1).setCellValue(
                    weeklySales.getPepsi().doubleValue());
            super.getCell(sheet, lastRow, 0).setCellValue("PETTY CASH");
            super.getCell(sheet, lastRow++, 1).setCellValue(
                    weeklySales.getPettyCash().doubleValue());
            super.getCell(sheet, lastRow, 0).setCellValue("OTHERS");
            super.getCell(sheet, lastRow++, 1).setCellValue(
                    weeklySales.getOthers().doubleValue());
            super.getCell(sheet, lastRow, 0).setCellValue("TOTAL PURCHASES");
            super.getCell(sheet, lastRow++, 1).setCellValue(
                    weeklySales.getTotalPurchases().doubleValue());
            super.getCell(sheet, lastRow, 0).setCellValue("CLOSING INVENTORY");
            super.getCell(sheet, lastRow++, 1).setCellValue(
                    weeklySales.getClosingInventory().doubleValue());
            super.getCell(sheet, lastRow, 0).setCellValue("COST OF SALES");
            super.getCell(sheet, lastRow++, 1).setCellValue(
                    weeklySales.getCostOfSales().doubleValue());
            super.getCell(sheet, lastRow, 0).setCellValue("FOOD AND BEVERAGE SALES");
            super.getCell(sheet, lastRow++, 1).setCellValue(
                    weeklySales.getFoodAndBeverageSales().doubleValue());
            super.getCell(sheet, lastRow, 0).setCellValue("FOOD COST");
            super.getCell(sheet, lastRow++, 1).setCellValue(
                    weeklySales.getFoodCost().doubleValue());
            super.getCell(sheet, lastRow, 0).setCellValue("EMPLOYEES LABOUR COST");
            super.getCell(sheet, lastRow++, 1).setCellValue(
                    weeklySales.getLabourCost().doubleValue());
            Calendar sundayOfWeek = weeklySales.getSundayOfSalesWeek();
            if (sundayOfWeek.get(Calendar.YEAR) == 2012)
            {
                if ((sundayOfWeek.get(Calendar.MONTH) == Calendar.JANUARY && sundayOfWeek.get(Calendar.DAY_OF_MONTH) == 22)
                    ||(sundayOfWeek.get(Calendar.MONTH) == Calendar.FEBRUARY && sundayOfWeek.get(Calendar.DAY_OF_MONTH) == 19)
                    ||(sundayOfWeek.get(Calendar.MONTH) == Calendar.MARCH && sundayOfWeek.get(Calendar.DAY_OF_MONTH) == 25)
                    ||(sundayOfWeek.get(Calendar.MONTH) == Calendar.APRIL && sundayOfWeek.get(Calendar.DAY_OF_MONTH) == 22)
                    ||(sundayOfWeek.get(Calendar.MONTH) == Calendar.MAY && sundayOfWeek.get(Calendar.DAY_OF_MONTH) == 20)
                    ||(sundayOfWeek.get(Calendar.MONTH) == Calendar.JUNE && sundayOfWeek.get(Calendar.DAY_OF_MONTH) == 24)
                    ||(sundayOfWeek.get(Calendar.MONTH) == Calendar.JULY && sundayOfWeek.get(Calendar.DAY_OF_MONTH) == 22)
                    ||(sundayOfWeek.get(Calendar.MONTH) == Calendar.AUGUST && sundayOfWeek.get(Calendar.DAY_OF_MONTH) == 19)
                    || (sundayOfWeek.get(Calendar.MONTH) == Calendar.SEPTEMBER && sundayOfWeek.get(Calendar.DAY_OF_MONTH) == 23)
                    || (sundayOfWeek.get(Calendar.MONTH) == Calendar.OCTOBER && sundayOfWeek.get(Calendar.DAY_OF_MONTH) == 21)
                    || (sundayOfWeek.get(Calendar.MONTH) == Calendar.NOVEMBER && sundayOfWeek.get(Calendar.DAY_OF_MONTH) == 18)
                    || (sundayOfWeek.get(Calendar.MONTH) == Calendar.DECEMBER && sundayOfWeek.get(Calendar.DAY_OF_MONTH) == 23))
                {
                super.getCell(sheet, lastRow, 0).setCellValue("PAPER CLOSING");
                super.getCell(sheet, lastRow++, 1).setCellValue(
                        weeklySales.getPaperClosingInventory().doubleValue());
                super.getCell(sheet, lastRow, 0).setCellValue("CLEANING CLOSING");
                super.getCell(sheet, lastRow, 1).setCellValue(
                        weeklySales.getCleaningClosingInventory().doubleValue());
               }
            }
            if (sundayOfWeek.get(Calendar.YEAR) == 2013)
            {
                if ((sundayOfWeek.get(Calendar.MONTH) == Calendar.JANUARY && sundayOfWeek.get(Calendar.DAY_OF_MONTH) == 20)
                    ||(sundayOfWeek.get(Calendar.MONTH) == Calendar.FEBRUARY && sundayOfWeek.get(Calendar.DAY_OF_MONTH) == 17)
                    ||(sundayOfWeek.get(Calendar.MONTH) == Calendar.MARCH && sundayOfWeek.get(Calendar.DAY_OF_MONTH) == 24)
                    ||(sundayOfWeek.get(Calendar.MONTH) == Calendar.APRIL && sundayOfWeek.get(Calendar.DAY_OF_MONTH) == 21)
                    ||(sundayOfWeek.get(Calendar.MONTH) == Calendar.MAY && sundayOfWeek.get(Calendar.DAY_OF_MONTH) == 19)
                    ||(sundayOfWeek.get(Calendar.MONTH) == Calendar.JUNE && sundayOfWeek.get(Calendar.DAY_OF_MONTH) == 23)
                    ||(sundayOfWeek.get(Calendar.MONTH) == Calendar.JULY && sundayOfWeek.get(Calendar.DAY_OF_MONTH) == 21)
                    ||(sundayOfWeek.get(Calendar.MONTH) == Calendar.AUGUST && sundayOfWeek.get(Calendar.DAY_OF_MONTH) == 18)
                    || (sundayOfWeek.get(Calendar.MONTH) == Calendar.SEPTEMBER && sundayOfWeek.get(Calendar.DAY_OF_MONTH) == 22)
                    || (sundayOfWeek.get(Calendar.MONTH) == Calendar.OCTOBER && sundayOfWeek.get(Calendar.DAY_OF_MONTH) == 20)
                    || (sundayOfWeek.get(Calendar.MONTH) == Calendar.NOVEMBER && sundayOfWeek.get(Calendar.DAY_OF_MONTH) == 17)
                    || (sundayOfWeek.get(Calendar.MONTH) == Calendar.DECEMBER && sundayOfWeek.get(Calendar.DAY_OF_MONTH) == 22))
                {
                super.getCell(sheet, lastRow, 0).setCellValue("PAPER CLOSING");
                super.getCell(sheet, lastRow++, 1).setCellValue(
                        weeklySales.getPaperClosingInventory().doubleValue());
                super.getCell(sheet, lastRow, 0).setCellValue("CLEANING CLOSING");
                super.getCell(sheet, lastRow, 1).setCellValue(
                        weeklySales.getCleaningClosingInventory().doubleValue());
               }
            }
        }
        else
        {
            super.getCell(sheet, lastRow++, 0).setCellValue("NOT SUBMITTED YET");
        }
    }

    private int writeHeader(ArrayList<Calendar> selectedWeek, Shop shop, HSSFSheet sheet,
            int lastRow)
    {
        super.getCell(sheet, lastRow++, 0).setCellValue("Weekly Sales Report");
        super.getCell(sheet, lastRow, 0).setCellValue("Week:");
        super.getCell(sheet, lastRow, 1).setCellValue(
                ExportWeeklySalesView.cellDateFormat
                        .format(selectedWeek.get(0).getTime())
                        + " - "
                        + ExportWeeklySalesView.cellDateFormat.format(selectedWeek.get(6)
                                .getTime()));
        super.getCell(sheet, lastRow, 3).setCellValue("Shop:");
        super.getCell(sheet, lastRow, 4).setCellValue(
                Integer.valueOf(shop.getId()+1000).toString() + " - " + shop.getName());
        return lastRow;
    }

}
