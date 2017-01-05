package com.pizza73.webapp.viewResolver;

import com.pizza73.model.BatchRecord;
import com.pizza73.model.DailySales;
import com.pizza73.model.Employee;
import com.pizza73.model.Shop;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;

public class ExportDailySalesView extends AbstractExcelView {
    private static final SimpleDateFormat titleDateFormat = new SimpleDateFormat(
            "MMM_dd_yyyy");
    private static final SimpleDateFormat cellDateFormat = new SimpleDateFormat(
            "MMM-dd-yyyy");

    @SuppressWarnings("unchecked")
    @Override
    protected void buildExcelDocument(Map map, HSSFWorkbook wb,
            HttpServletRequest request, HttpServletResponse Response) throws Exception
    {
        DailySales dailySales = (DailySales) map.get("dailySales");
        Employee employee = (Employee) map.get("EMPLOYEE");
        Shop shop = (Shop) request.getSession().getAttribute("DAILYSALES_SHOP");
        Calendar requestDate = (Calendar) map.get("SELECTED_DATE");
        HSSFSheet sheet = wb.createSheet("Daily Sales_"
                + ExportDailySalesView.titleDateFormat.format(dailySales.getSalesDate()
                        .getTime()) + "_Shop_" + dailySales.getShopId());
        int lastCol = 0;
        lastCol = writeHeader(dailySales, employee, shop, requestDate, sheet, lastCol);
        writeNetSales(dailySales, sheet, lastCol + 2);
        int lastColGrossSales = writeGrossSales(dailySales, sheet, lastCol + 2);
        if (dailySales.getVersion() > 1)
            lastColGrossSales = writeAcutalCash(dailySales, sheet, lastCol + 2);
        lastCol = writeInstore(dailySales, sheet, lastColGrossSales + 4);
        lastCol = writeWirelssDriver(dailySales, sheet, lastCol + 1);
        lastCol = wirteGrandTotal(dailySales, sheet, lastCol + 1);
        int lastColInBatchForm = writeBatchRecord(dailySales, sheet,
                lastColGrossSales + 3);
    }

    private int writeBatchRecord(DailySales dailySales, HSSFSheet sheet, int lastCol)
    {
        lastCol = writeInStoreBatchRecord(dailySales, sheet, lastCol + 1);
        lastCol = writeWrielssBatchRecord(dailySales, sheet, lastCol + 1);
        return lastCol;
    }

    private int writeInStoreBatchRecord(DailySales dailySales, HSSFSheet sheet,
            int lastCol)
    {
        int i = 0;
        BatchRecord inStoreBatchRecord = null;
        super.getCell(sheet, lastCol++, 3).setCellValue("FRONT-COUNTER MACHINES");
        super.getCell(sheet, lastCol, 3).setCellValue("MACHINE");
        super.getCell(sheet, lastCol, 4).setCellValue("DEBIT");
        super.getCell(sheet, lastCol, 5).setCellValue("VISA");
        super.getCell(sheet, lastCol, 6).setCellValue("AMEX");
        super.getCell(sheet, lastCol, 7).setCellValue("MASTERCARD");
        super.getCell(sheet, lastCol, 8).setCellValue("BATCH NUMBER");
        super.getCell(sheet, lastCol, 9).setCellValue("MACHINE TOTAL");
        if (dailySales.getBatchRecords() != null)
        {
            Iterator<BatchRecord> it = dailySales.getBatchRecords().iterator();
            while (it.hasNext())
            {
                inStoreBatchRecord = it.next();
                if (inStoreBatchRecord.isWireless() == false)
                {
                    i++;
                    lastCol++;
                    super.getCell(sheet, lastCol, 3).setCellValue(i);
                    super.getCell(sheet, lastCol, 4).setCellValue(
                            inStoreBatchRecord.getDebit().doubleValue());
                    super.getCell(sheet, lastCol, 5).setCellValue(
                            inStoreBatchRecord.getVisa().doubleValue());
                    super.getCell(sheet, lastCol, 6).setCellValue(
                            inStoreBatchRecord.getAmex().doubleValue());
                    super.getCell(sheet, lastCol, 7).setCellValue(
                            inStoreBatchRecord.getMastercard().doubleValue());
                    super.getCell(sheet, lastCol, 8).setCellValue(
                            inStoreBatchRecord.getGiftcard().doubleValue());
                    super.getCell(sheet, lastCol, 9).setCellValue(
                            inStoreBatchRecord.getBatchNumber());
                    super.getCell(sheet, lastCol, 10).setCellValue(
                            inStoreBatchRecord.getBatchRecordTotal().doubleValue());
                }
            }
        }
        if (i == 0)
        {
            lastCol++;
            super.getCell(sheet, lastCol, 3).setCellValue(i);
            super.getCell(sheet, lastCol, 4).setCellType(HSSFCell.CELL_TYPE_BLANK);
            super.getCell(sheet, lastCol, 5).setCellType(HSSFCell.CELL_TYPE_BLANK);
            super.getCell(sheet, lastCol, 6).setCellType(HSSFCell.CELL_TYPE_BLANK);
            super.getCell(sheet, lastCol, 7).setCellType(HSSFCell.CELL_TYPE_BLANK);
            super.getCell(sheet, lastCol, 8).setCellType(HSSFCell.CELL_TYPE_BLANK);
            super.getCell(sheet, lastCol, 9).setCellType(HSSFCell.CELL_TYPE_BLANK);
            super.getCell(sheet, lastCol, 10).setCellType(HSSFCell.CELL_TYPE_BLANK);
        }
        lastCol++;
        super.getCell(sheet, lastCol, 3).setCellValue("SUBTOTAL");
        super.getCell(sheet, lastCol, 4).setCellValue(
                dailySales.getInStoreDebit().doubleValue());
        super.getCell(sheet, lastCol, 5).setCellValue(
                dailySales.getInStoreVisa().doubleValue());

        super.getCell(sheet, lastCol, 6).setCellValue(
                dailySales.getInStoreAmex().doubleValue());
        super.getCell(sheet, lastCol, 7).setCellValue(
                dailySales.getInStoreMastercard().doubleValue());
        super.getCell(sheet, lastCol, 8).setCellValue(
                dailySales.getInStoreGiftcard().doubleValue());
        super.getCell(sheet, lastCol, 9).setCellValue("N/A");
        super.getCell(sheet, lastCol, 10).setCellValue(
                dailySales.getInStoreTotal().doubleValue());
        return lastCol;
    }

    private int writeWrielssBatchRecord(DailySales dailySales, HSSFSheet sheet,
            int lastCol)
    {
        int i = 0;
        BatchRecord wirelessBatchRecord = null;
        super.getCell(sheet, lastCol++, 3).setCellValue("WIRELESS MACHINES");
        super.getCell(sheet, lastCol, 3).setCellValue("MACHINE");
        super.getCell(sheet, lastCol, 4).setCellValue("DEBIT");
        super.getCell(sheet, lastCol, 5).setCellValue("VISA");
        super.getCell(sheet, lastCol, 6).setCellValue("AMEX");
        super.getCell(sheet, lastCol, 7).setCellValue("MASTERCARD");
        super.getCell(sheet, lastCol, 8).setCellValue("PIZZA CARD");
        super.getCell(sheet, lastCol, 9).setCellValue("BATCH NUMBER");
        super.getCell(sheet, lastCol, 10).setCellValue("MACHINE TOTAL");
        if (dailySales.getBatchRecords() != null)
        {
            Iterator<BatchRecord> it = dailySales.getBatchRecords().iterator();
            while (it.hasNext())
            {
                wirelessBatchRecord = it.next();
                if (wirelessBatchRecord.isWireless() == true)
                {
                    i++;
                    lastCol++;
                    super.getCell(sheet, lastCol, 3).setCellValue(i);
                    super.getCell(sheet, lastCol, 4).setCellValue(
                            wirelessBatchRecord.getDebit().doubleValue());
                    super.getCell(sheet, lastCol, 5).setCellValue(
                            wirelessBatchRecord.getVisa().doubleValue());
                    super.getCell(sheet, lastCol, 6).setCellValue(
                            wirelessBatchRecord.getAmex().doubleValue());
                    super.getCell(sheet, lastCol, 7).setCellValue(
                            wirelessBatchRecord.getMastercard().doubleValue());
                    super.getCell(sheet, lastCol, 8).setCellValue(
                            wirelessBatchRecord.getGiftcard().doubleValue());
                    super.getCell(sheet, lastCol, 9).setCellValue(
                            wirelessBatchRecord.getBatchNumber());
                    super.getCell(sheet, lastCol, 10).setCellValue(
                            wirelessBatchRecord.getBatchRecordTotal().doubleValue());
                }
            }
        }
        if (i == 0)
        {
            lastCol++;
            super.getCell(sheet, lastCol, 3).setCellValue(i);
            super.getCell(sheet, lastCol, 4).setCellType(HSSFCell.CELL_TYPE_BLANK);
            super.getCell(sheet, lastCol, 5).setCellType(HSSFCell.CELL_TYPE_BLANK);
            super.getCell(sheet, lastCol, 6).setCellType(HSSFCell.CELL_TYPE_BLANK);
            super.getCell(sheet, lastCol, 7).setCellType(HSSFCell.CELL_TYPE_BLANK);
            super.getCell(sheet, lastCol, 8).setCellType(HSSFCell.CELL_TYPE_BLANK);
            super.getCell(sheet, lastCol, 9).setCellType(HSSFCell.CELL_TYPE_BLANK);
            super.getCell(sheet, lastCol, 10).setCellType(HSSFCell.CELL_TYPE_BLANK);
        }
        lastCol++;
        super.getCell(sheet, lastCol, 3).setCellValue("SUBTOTAL");
        super.getCell(sheet, lastCol, 4).setCellValue(
                dailySales.getWirelessDriverDebitTotal().doubleValue());
        super.getCell(sheet, lastCol, 5).setCellValue(
                dailySales.getWirelessDriverVisaTotal().doubleValue());
        super.getCell(sheet, lastCol, 6).setCellValue(
                dailySales.getWirelessDriverAmexTotal().doubleValue());
        super.getCell(sheet, lastCol, 7).setCellValue(
                dailySales.getWirelessDriverMastercardTotal().doubleValue());
        super.getCell(sheet, lastCol, 8).setCellValue(
                dailySales.getWirelessDriverGiftcardTotal().doubleValue());
        super.getCell(sheet, lastCol, 9).setCellValue("N/A");
        super.getCell(sheet, lastCol, 10).setCellValue(
                dailySales.getWirelessDriverTotal().doubleValue());

        // sum up in-store and wireless batch form
        lastCol++;
        super.getCell(sheet, lastCol, 3).setCellValue("TOTAL");
        super.getCell(sheet, lastCol, 4).setCellValue(
                dailySales.getDebitTotal().doubleValue());
        super.getCell(sheet, lastCol, 5).setCellValue(
                dailySales.getVisaTotal().doubleValue());
        super.getCell(sheet, lastCol, 6).setCellValue(
                dailySales.getAmexTotal().doubleValue());
        super.getCell(sheet, lastCol, 7).setCellValue(
                dailySales.getMastercardTotal().doubleValue());
        super.getCell(sheet, lastCol, 8).setCellValue(
                dailySales.getGiftcardTotal().doubleValue());
        super.getCell(sheet, lastCol, 9).setCellValue("N/A");
        super.getCell(sheet, lastCol, 10).setCellValue(
                dailySales.getInStoreTotal().add(dailySales.getWirelessDriverTotal())
                        .doubleValue());
        return lastCol;
    }

    private int writeInstore(DailySales dailySales, HSSFSheet sheet, int lastCol)
    {
        super.getCell(sheet, lastCol++, 0).setCellValue("TOTAL");
        super.getCell(sheet, lastCol, 0).setCellValue("ACTUAL CASH");
        super.getCell(sheet, lastCol++, 1).setCellValue(
                dailySales.getActualCash().doubleValue());
        if (dailySales.getVersion()>2){
        super.getCell(sheet, lastCol, 0).setCellValue("CATERING CREDIT");
//        super.getCell(sheet, lastCol++, 1).setCellValue(
//                dailySales.getCateringCredit().doubleValue());
        }
        super.getCell(sheet, lastCol, 0).setCellValue("GIFT CERTIFICATE REDEEMED");
        super.getCell(sheet, lastCol++, 1).setCellValue(
                dailySales.getGiftCertificateRedeemed().doubleValue());
        super.getCell(sheet, lastCol++, 0).setCellValue("FRONT-COUNTER MACHINES");
        super.getCell(sheet, lastCol, 0).setCellValue("DEBIT");
        super.getCell(sheet, lastCol++, 1).setCellValue(
                dailySales.getInStoreDebit().doubleValue());
        super.getCell(sheet, lastCol, 0).setCellValue("VISA");
        super.getCell(sheet, lastCol++, 1).setCellValue(
                dailySales.getInStoreVisa().doubleValue());
        super.getCell(sheet, lastCol, 0).setCellValue("AMEX");
        super.getCell(sheet, lastCol++, 1).setCellValue(
                dailySales.getInStoreAmex().doubleValue());
        super.getCell(sheet, lastCol, 0).setCellValue("MASTERCARD");
        super.getCell(sheet, lastCol++, 1).setCellValue(
                dailySales.getInStoreMastercard().doubleValue());
        super.getCell(sheet, lastCol, 0).setCellValue("PIZZA CARD");
        super.getCell(sheet, lastCol++, 1).setCellValue(
                dailySales.getInStoreGiftcard().doubleValue());
        super.getCell(sheet, lastCol, 0).setCellValue("TOTAL");
        super.getCell(sheet, lastCol, 1).setCellValue(
                dailySales.getInStoreTotal().doubleValue());
        return lastCol;
    }

    private int writeWirelssDriver(DailySales dailySales, HSSFSheet sheet, int lastCol)
    {
        super.getCell(sheet, lastCol++, 0).setCellValue("WIRELESS MACHINES");
        super.getCell(sheet, lastCol, 0).setCellValue("DEBIT");
        super.getCell(sheet, lastCol++, 1).setCellValue(
                dailySales.getWirelessDriverDebitTotal().doubleValue());
        super.getCell(sheet, lastCol, 0).setCellValue("VISA");
        super.getCell(sheet, lastCol++, 1).setCellValue(
                dailySales.getWirelessDriverVisaTotal().doubleValue());
        super.getCell(sheet, lastCol, 0).setCellValue("AMEX");
        super.getCell(sheet, lastCol++, 1).setCellValue(
                dailySales.getWirelessDriverAmexTotal().doubleValue());
        super.getCell(sheet, lastCol, 0).setCellValue("MASTERCARD");
        super.getCell(sheet, lastCol++, 1).setCellValue(
                dailySales.getWirelessDriverMastercardTotal().doubleValue());
        super.getCell(sheet, lastCol, 0).setCellValue("PIZZA CARD");
        super.getCell(sheet, lastCol++, 1).setCellValue(
                dailySales.getWirelessDriverGiftcardTotal().doubleValue());
        super.getCell(sheet, lastCol, 0).setCellValue("TOTAL");
        super.getCell(sheet, lastCol, 1).setCellValue(
                dailySales.getWirelessDriverTotal().doubleValue());
        return lastCol;
    }

    private int wirteGrandTotal(DailySales dailySales, HSSFSheet sheet, int lastCol)
    {
        super.getCell(sheet, lastCol++, 0).setCellValue("GRAND TOTAL");
        super.getCell(sheet, lastCol, 0).setCellValue("CASH");
        super.getCell(sheet, lastCol++, 1).setCellValue(
                dailySales.getCashTotal().doubleValue());
        if (dailySales.getVersion()>2){
        super.getCell(sheet, lastCol, 0).setCellValue("CATERING CREDIT");
//        super.getCell(sheet, lastCol++, 1).setCellValue(
//                dailySales.getCateringCredit().doubleValue());
        }
        super.getCell(sheet, lastCol, 0).setCellValue("GIFT CERTIFICATE REDEEMED");
        super.getCell(sheet, lastCol++, 1).setCellValue(
                dailySales.getGiftCertificateRedeemed().doubleValue());
        super.getCell(sheet, lastCol, 0).setCellValue("DEBIT");
        super.getCell(sheet, lastCol++, 1).setCellValue(
                dailySales.getDebitTotal().doubleValue());
        super.getCell(sheet, lastCol, 0).setCellValue("VISA");
        super.getCell(sheet, lastCol++, 1).setCellValue(
                dailySales.getVisaTotal().doubleValue());
        super.getCell(sheet, lastCol, 0).setCellValue("AMEX");
        super.getCell(sheet, lastCol++, 1).setCellValue(
                dailySales.getAmexTotal().doubleValue());
        super.getCell(sheet, lastCol, 0).setCellValue("MESATERCARD");
        super.getCell(sheet, lastCol++, 1).setCellValue(
                dailySales.getMastercardTotal().doubleValue());
        super.getCell(sheet, lastCol, 0).setCellValue("PIZZA CARD");
        super.getCell(sheet, lastCol++, 1).setCellValue(
                dailySales.getGiftcardTotal().doubleValue());
        super.getCell(sheet, lastCol, 0).setCellValue("TOTAL TENDER");
        super.getCell(sheet, lastCol, 1).setCellValue(
                dailySales.getTotalTender().doubleValue());
        return lastCol;
    }

    private int writeAcutalCash(DailySales dailySales, HSSFSheet sheet, int lastCol)
    {
        super.getCell(sheet, lastCol++, 6).setCellValue("BILLS");
        super.getCell(sheet, lastCol, 6).setCellValue(
                "$100 X " + dailySales.getOneHundredDollarBill() + " =");
        super.getCell(sheet, lastCol++, 7).setCellValue(
                dailySales.getOneHundredDollarBill() * 100);
        super.getCell(sheet, lastCol, 6).setCellValue(
                "$50  X " + dailySales.getFiftyDollarBill() + " =");
        super.getCell(sheet, lastCol++, 7).setCellValue(
                dailySales.getFiftyDollarBill() * 50);
        super.getCell(sheet, lastCol, 6).setCellValue(
                "$20  X " + dailySales.getTwentyDollarBill() + " =");
        super.getCell(sheet, lastCol++, 7).setCellValue(
                dailySales.getTwentyDollarBill() * 20);
        super.getCell(sheet, lastCol, 6).setCellValue(
                "$10  X " + dailySales.getTenDollarBill() + " =");
        super.getCell(sheet, lastCol++, 7).setCellValue(
                dailySales.getTenDollarBill() * 10);
        super.getCell(sheet, lastCol, 6).setCellValue(
                "$5   X " + dailySales.getFiveDollarBill() + " =");
        super.getCell(sheet, lastCol++, 7).setCellValue(
                dailySales.getTenDollarBill() * 10);
        super.getCell(sheet, lastCol, 6).setCellValue("BILLS TOTAL");
        super.getCell(sheet, lastCol++, 7).setCellValue(
                dailySales.getBillsTotal().doubleValue());
        super.getCell(sheet, lastCol++, 6).setCellValue("COINS");
        super.getCell(sheet, lastCol, 6).setCellValue(
                "$2   X " + dailySales.getTwoDollarBill() + " =");
        super.getCell(sheet, lastCol++, 7)
                .setCellValue(dailySales.getTwoDollarBill() * 2);
        super.getCell(sheet, lastCol, 6).setCellValue(
                "$1   X " + dailySales.getOneDollarBill() + " =");
        super.getCell(sheet, lastCol++, 7).setCellValue(dailySales.getOneDollarBill());
        super.getCell(sheet, lastCol, 6).setCellValue(
                "$0.25 X " + dailySales.getTwentyFiveCentBill() + " =");
        super.getCell(sheet, lastCol++, 7).setCellValue(
                dailySales.getTwentyFiveCentBill() * 0.25);
        super.getCell(sheet, lastCol, 6).setCellValue(
                "$0.10 X " + dailySales.getTenCentBill() + " =");
        super.getCell(sheet, lastCol++, 7)
                .setCellValue(dailySales.getTenCentBill() * 0.1);
        super.getCell(sheet, lastCol, 6).setCellValue(
                "$0.05 X " + dailySales.getFiveCentBill() + " =");
        super.getCell(sheet, lastCol++, 7).setCellValue(
                dailySales.getFiveCentBill() * 0.05);
        super.getCell(sheet, lastCol, 6).setCellValue(
                "$0.01 X " + dailySales.getOneCentBill() + " =");
        super.getCell(sheet, lastCol++, 7).setCellValue(
                dailySales.getOneCentBill() * 0.01);
        super.getCell(sheet, lastCol, 6).setCellValue("COINS TOTAL");
        super.getCell(sheet, lastCol++, 7).setCellValue(
                dailySales.getCoinsTotal().doubleValue());
        super.getCell(sheet, lastCol++, 6).setCellValue("CHEQUES");
        super.getCell(sheet, lastCol, 6).setCellValue("CHEQUES TOTAL");
        super.getCell(sheet, lastCol++, 7).setCellValue(
                dailySales.getChequesTotal().doubleValue());
        super.getCell(sheet, lastCol, 6).setCellValue("ACTUAL CASH");
        super.getCell(sheet, lastCol, 7).setCellValue(
                dailySales.getActualCash().doubleValue());
        return lastCol;
    }

    private int writeGrossSales(DailySales dailySales, HSSFSheet sheet, int lastCol)
    {
        super.getCell(sheet, lastCol++, 3).setCellValue("GROSS SALES");
        super.getCell(sheet, lastCol, 3).setCellValue("+NET TO PIZZA73");
        super.getCell(sheet, lastCol++, 4).setCellValue(
                dailySales.getNetToPizza73().doubleValue());
        super.getCell(sheet, lastCol, 3).setCellValue("+DISCOUNTS/ADVERTISING");
        super.getCell(sheet, lastCol++, 4).setCellValue(
                dailySales.getDiscountsAndAdvertising().doubleValue());
        super.getCell(sheet, lastCol, 3).setCellValue("+COUPONS");
        super.getCell(sheet, lastCol++, 4).setCellValue(
                dailySales.getCoupons().doubleValue());
        super.getCell(sheet, lastCol, 3).setCellValue("=NET SALES");
        super.getCell(sheet, lastCol++, 4).setCellValue(
                dailySales.getNetSales().doubleValue());
        super.getCell(sheet, lastCol, 3).setCellValue("+G.S.T.");
        super.getCell(sheet, lastCol++, 4)
                .setCellValue(dailySales.getGst().doubleValue());
        super.getCell(sheet, lastCol, 3).setCellValue("=GROESS SALES");
        super.getCell(sheet, lastCol, 4).setCellValue(
                dailySales.getGrossSales().doubleValue());
        return lastCol;
    }

    private void writeNetSales(DailySales dailySales, HSSFSheet sheet, int lastCol)
    {
        super.getCell(sheet, lastCol++, 0).setCellValue("NET SALES");
        super.getCell(sheet, lastCol, 0).setCellValue("+COMPUTER SALES");
        super.getCell(sheet, lastCol++, 1).setCellValue(
                dailySales.getComputerSales().doubleValue());
        super.getCell(sheet, lastCol, 0).setCellValue("+WALK-IN SALES");
        super.getCell(sheet, lastCol++, 1).setCellValue(
                dailySales.getWalkInSales().doubleValue());
        super.getCell(sheet, lastCol, 0).setCellValue("+MISC. SALES");
        super.getCell(sheet, lastCol++, 1).setCellValue(
                dailySales.getMiscSales().doubleValue());
        super.getCell(sheet, lastCol, 0).setCellValue("-RETURNS");
        super.getCell(sheet, lastCol++, 1).setCellValue(
                dailySales.getReturns().doubleValue());
        super.getCell(sheet, lastCol, 0).setCellValue("=NET TO PIZZA73");
        super.getCell(sheet, lastCol++, 1).setCellValue(
                dailySales.getNetToPizza73().doubleValue());
        super.getCell(sheet, lastCol, 0).setCellValue("+G.S.T");
        super.getCell(sheet, lastCol++, 1)
                .setCellValue(dailySales.getGst().doubleValue());
        super.getCell(sheet, lastCol, 0).setCellValue("+GIFT CERTIFICATE SOLD");
        super.getCell(sheet, lastCol++, 1).setCellValue(
                dailySales.getGiftCertificateSold().doubleValue());
        super.getCell(sheet, lastCol, 0).setCellValue("+PIZZA CARD RELOAD");
        super.getCell(sheet, lastCol++, 1).setCellValue(
                dailySales.getGiftcardReload().doubleValue());
        super.getCell(sheet, lastCol, 0).setCellValue("=TOTAL RECEIPTS");
        super.getCell(sheet, lastCol, 1).setCellValue(
                dailySales.getTotalReceipts().doubleValue());
        super.getCell(sheet, lastCol, 0).setCellValue("CASH OVER/SHORT");
        super.getCell(sheet, lastCol, 1).setCellValue(
                dailySales.getOverShort().doubleValue());
    }

    private int writeHeader(DailySales dailySales, Employee employee, Shop shop,
            Calendar requestDate, HSSFSheet sheet, int lastCol)
    {
        super.getCell(sheet, lastCol, 0).setCellValue("Daily Sales Report");
        super.getCell(sheet, lastCol, 3).setCellValue("Date:");
        super.getCell(sheet, lastCol++, 4).setCellValue(
                ExportDailySalesView.cellDateFormat.format(requestDate.getTime()));
        super.getCell(sheet, ++lastCol, 0).setCellValue("COMPLETED BY:");
        super.getCell(sheet, lastCol, 1).setCellValue(
                employee.getLastName() + ", " + employee.getName());
        super.getCell(sheet, lastCol, 3).setCellValue("Shop:");
        super.getCell(sheet, lastCol, 4).setCellValue(
                shop.getId().toString() + " - " + shop.getName());
        return lastCol;
    }

}
