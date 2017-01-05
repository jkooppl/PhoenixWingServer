package com.pizza73.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "iq2_daily_sales", schema = "public", uniqueConstraints = {})
@SequenceGenerator(name = "daily_sales_sequence", sequenceName = "iq2_daily_sales_id", allocationSize = 1)
public class DailySales implements Serializable
{

    // FIELDS
    private static final long serialVersionUID = -9115311419626562468L;

    @Id
    @Column(name = "daily_sales_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "daily_sales_sequence")
    private Integer id;
    @Column(name = "shop_id")
    private Integer shopId;
    @Column(name = "employee_id")
    private Integer employeeId;

    @Column(name = "sales_date")
    @Temporal(TemporalType.DATE)
    private Calendar salesDate;

    // NET SALES
    @Column(name = "computer_sales")
    private BigDecimal computerSales = BigDecimal.valueOf(0, 2);
    @Column(name = "walk_in_sales")
    private BigDecimal walkInSales = BigDecimal.valueOf(0, 2);
    @Column(name = "misc_sales")
    private BigDecimal miscSales = BigDecimal.valueOf(0, 2);
    @Column(name = "gst")
    private BigDecimal gst = BigDecimal.valueOf(0, 2);
    @Column(name = "gift_certificate_sales")
    private BigDecimal giftCertificateSold = BigDecimal.valueOf(0, 2);
    @Column(name = "giftcard_reload")
    private BigDecimal giftcardReload = BigDecimal.valueOf(0, 2);
    @Column(name = "returns")
    private BigDecimal returns = BigDecimal.valueOf(0, 2);

    // GROSS SALES
    @Column(name = "free_pizza")
    private BigDecimal freePizza = BigDecimal.valueOf(0, 2);
    @Column(name = "discounts_and_advertising")
    private BigDecimal discountsAndAdvertising = BigDecimal.valueOf(0, 2);
    @Column(name = "coupons")
    private BigDecimal coupons = BigDecimal.valueOf(0, 2);

    // ACTUAL CASH
    @Column(name = "one_hundred__dollar_bill")
    private Integer oneHundredDollarBill = 0;
    @Column(name = "fifty_dollar_bill")
    private Integer fiftyDollarBill = 0;
    @Column(name = "twenty_dollar_bill")
    private Integer twentyDollarBill = 0;
    @Column(name = "ten_dollar_bill")
    private Integer tenDollarBill = 0;
    @Column(name = "five_dollar_bill")
    private Integer fiveDollarBill = 0;
    @Column(name = "two_dollar_bill")
    private Integer twoDollarBill = 0;
    @Column(name = "one_dollar_bill")
    private Integer oneDollarBill = 0;
    @Column(name = "twenty_five_cent_bill")
    private Integer twentyFiveCentBill = 0;
    @Column(name = "ten_cent_bill")
    private Integer tenCentBill = 0;
    @Column(name = "five_cent_bill")
    private Integer fiveCentBill = 0;
    @Column(name = "one_cent_bill")
    private Integer oneCentBill = 0;
    @Column(name = "cheques_total")
    private BigDecimal chequesTotal = BigDecimal.valueOf(0, 2);

    // IN STORE
    @Column(name = "actual_cash")
    private BigDecimal actualCash = BigDecimal.valueOf(0, 2);
    @Column(name = "in_store_gift_certificate")
    private BigDecimal giftCertificateRedeemed = BigDecimal.valueOf(0, 2);

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "iq2_daily_sales_batch_record", joinColumns = { @JoinColumn(name = "daily_sales_id") }, inverseJoinColumns = { @JoinColumn(name = "batch_record_id", unique = true) })
    private List<BatchRecord> BatchRecords = new ArrayList<BatchRecord>();

    // FLAG
    @Column(name = "submitted")
    private boolean submitted = false;

    // version flag

    @Column(name = "version")
    private Integer version = 2;

    public Integer getVersion()
    {
        return version;
    }

    public void setVersion(Integer version)
    {
        this.version = version;
    }

    public BigDecimal getComputerSales()
    {
        return computerSales;
    }

    public void setComputerSales(BigDecimal computerSales)
    {
        this.computerSales = computerSales;
    }

    public BigDecimal getWalkInSales()
    {
        return walkInSales;
    }

    public void setWalkInSales(BigDecimal walkInSales)
    {
        this.walkInSales = walkInSales;
    }

    public BigDecimal getMiscSales()
    {
        return miscSales;
    }

    public void setMiscSales(BigDecimal miscSales)
    {
        this.miscSales = miscSales;
    }

    public BigDecimal getGst()
    {
        return gst;
    }

    public void setGst(BigDecimal gst)
    {
        this.gst = gst;
    }

    public BigDecimal getFreePizza()
    {
        return freePizza;
    }

    public void setFreePizza(BigDecimal freePizza)
    {
        this.freePizza = freePizza;
    }

    public BigDecimal getDiscountsAndAdvertising()
    {
        return discountsAndAdvertising;
    }

    public void setDiscountsAndAdvertising(BigDecimal discountsAndAdvertising)
    {
        this.discountsAndAdvertising = discountsAndAdvertising;
    }

    public BigDecimal getInStoreDebit()
    {
        Iterator<BatchRecord> it = this.BatchRecords.iterator();
        BigDecimal sum = BigDecimal.valueOf(0, 2);
        BatchRecord temp = null;
        while (it.hasNext())
        {
            temp = it.next();
            if (!temp.isWireless())
                sum = sum.add(temp.getDebit());
        }
        return sum;
    }

    public BigDecimal getInStoreGiftcard()
    {
        Iterator<BatchRecord> it = this.BatchRecords.iterator();
        BigDecimal sum = BigDecimal.valueOf(0, 2);
        BatchRecord temp = null;
        while (it.hasNext())
        {
            temp = it.next();
            if (!temp.isWireless())
                sum = sum.add(temp.getGiftcard());
        }
        return sum;
    }

    public BigDecimal getCoupons()
    {
        return coupons;
    }

    public void setCoupons(BigDecimal coupons)
    {
        this.coupons = coupons;
    }

    public BigDecimal getActualCash()
    {
        return actualCash;
    }

    public void setActualCash(BigDecimal actualCash)
    {
        this.actualCash = actualCash;
    }

    public BigDecimal getBillsTotal()
    {
        return BigDecimal.valueOf(100).multiply(BigDecimal.valueOf(this.oneHundredDollarBill))
            .add(BigDecimal.valueOf(50).multiply(BigDecimal.valueOf(this.fiftyDollarBill)))
            .add(BigDecimal.valueOf(20).multiply(BigDecimal.valueOf(this.twentyDollarBill)))
            .add(BigDecimal.valueOf(10).multiply(BigDecimal.valueOf(this.tenDollarBill)))
            .add(BigDecimal.valueOf(5).multiply(BigDecimal.valueOf(this.fiveDollarBill)));
    }

    public BigDecimal getCoinsTotal()
    {
        return BigDecimal.valueOf(2).multiply(BigDecimal.valueOf(this.twoDollarBill))
            .add(BigDecimal.valueOf(this.oneDollarBill))
            .add(BigDecimal.valueOf(25, 2).multiply(BigDecimal.valueOf(this.twentyFiveCentBill)))
            .add(BigDecimal.valueOf(1, 1).multiply(BigDecimal.valueOf(this.tenCentBill)))
            .add(BigDecimal.valueOf(5, 2).multiply(BigDecimal.valueOf(this.fiveCentBill)))
            .add(BigDecimal.valueOf(1, 2).multiply(BigDecimal.valueOf(this.oneCentBill)));
    }

    public BigDecimal getInStoreVisa()
    {
        Iterator<BatchRecord> it = this.BatchRecords.iterator();
        BigDecimal sum = BigDecimal.valueOf(0, 2);
        BatchRecord temp = null;
        while (it.hasNext())
        {
            temp = it.next();
            if (!temp.isWireless())
                sum = sum.add(temp.getVisa());
        }
        return sum;
    }

    public BigDecimal getInStoreMastercard()
    {
        Iterator<BatchRecord> it = this.BatchRecords.iterator();
        BigDecimal sum = BigDecimal.valueOf(0, 2);
        BatchRecord temp = null;
        while (it.hasNext())
        {
            temp = it.next();
            if (!temp.isWireless())
                sum = sum.add(temp.getMastercard());
        }
        return sum;
    }

    public BigDecimal getInStoreAmex()
    {
        Iterator<BatchRecord> it = this.BatchRecords.iterator();
        BigDecimal sum = BigDecimal.valueOf(0, 2);
        BatchRecord temp = null;
        while (it.hasNext())
        {
            temp = it.next();
            if (!temp.isWireless())
                sum = sum.add(temp.getAmex());
        }
        return sum;
    }

    public DailySales()
    {
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getShopId()
    {
        return this.shopId;
    }

    public void setShopId(Integer shopId)
    {
        this.shopId = shopId;
    }

    public Integer getEmployeeId()
    {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId)
    {
        this.employeeId = employeeId;
    }

    public Calendar getSalesDate()
    {
        return this.salesDate;
    }

    public void setSalesDate(Calendar salesDate)
    {
        this.salesDate = salesDate;
    }

    public boolean isSubmitted()
    {
        return submitted;
    }

    public void setSubmitted(boolean submitted)
    {
        this.submitted = submitted;
    }

    public BigDecimal getNetToPizza73()
    {
        return this.computerSales.add(this.walkInSales).add(this.miscSales).subtract(this.returns);
    }

    public BigDecimal getTotalReceipts()
    {
        return this.getNetToPizza73().add(this.gst).add(this.giftCertificateSold).add(this.giftcardReload);
    }

    public BigDecimal getNetSales()
    {
        return this.getNetToPizza73().add(this.freePizza).add(this.discountsAndAdvertising).add(this.coupons);
    }

    public BigDecimal getGrossSales()
    {
        return this.getNetSales().add(this.gst);
    }

    public BigDecimal getInStoreTotal()
    {
        return this.getInStoreDebit().add(this.getInStoreVisa()).add(this.getInStoreMastercard()).add(this.getInStoreAmex())
            .add(this.getInStoreGiftcard());
    }

    public BigDecimal getWirelessDriverGiftcardTotal()
    {
        Iterator<BatchRecord> it = this.BatchRecords.iterator();
        BigDecimal sum = BigDecimal.valueOf(0, 2);
        BatchRecord temp = null;
        while (it.hasNext())
        {
            temp = it.next();
            if (temp.isWireless())
                sum = sum.add(temp.getGiftcard());
        }
        return sum;
    }

    public BigDecimal getWirelessDriverDebitTotal()
    {
        Iterator<BatchRecord> it = this.BatchRecords.iterator();
        BigDecimal sum = BigDecimal.valueOf(0, 2);
        BatchRecord temp = null;
        while (it.hasNext())
        {
            temp = it.next();
            if (temp.isWireless())
                sum = sum.add(temp.getDebit());
        }
        return sum;
    }

    public BigDecimal getWirelessDriverVisaTotal()
    {
        Iterator<BatchRecord> it = this.BatchRecords.iterator();
        BigDecimal sum = BigDecimal.valueOf(0, 2);
        BatchRecord temp = null;
        while (it.hasNext())
        {
            temp = it.next();
            if (temp.isWireless())
                sum = sum.add(temp.getVisa());
        }
        return sum;
    }

    public BigDecimal getWirelessDriverMastercardTotal()
    {
        Iterator<BatchRecord> it = this.BatchRecords.iterator();
        BigDecimal sum = BigDecimal.valueOf(0, 2);
        BatchRecord temp = null;
        while (it.hasNext())
        {
            temp = it.next();
            if (temp.isWireless())
                sum = sum.add(temp.getMastercard());
        }
        return sum;
    }

    public BigDecimal getWirelessDriverAmexTotal()
    {
        Iterator<BatchRecord> it = this.BatchRecords.iterator();
        BigDecimal sum = BigDecimal.valueOf(0, 2);
        BatchRecord temp = null;
        while (it.hasNext())
        {
            temp = it.next();
            if (temp.isWireless())
                sum = sum.add(temp.getAmex());
        }
        return sum;
    }

    public BigDecimal getWirelessDriverTotal()
    {
        return this.getWirelessDriverDebitTotal().add(this.getWirelessDriverVisaTotal())
            .add(this.getWirelessDriverMastercardTotal()).add(this.getWirelessDriverAmexTotal())
            .add(this.getWirelessDriverGiftcardTotal());
    }

    public BigDecimal getTotalTender()
    {
        return this.getInStoreTotal().add(this.getWirelessDriverTotal()).add(this.getCashTotal())
            .add(this.giftCertificateRedeemed);
    }

    public BigDecimal getOverShort()
    {
        return this.getTotalTender().subtract(this.getTotalReceipts());
    }

    public BigDecimal getCashTotal()
    {
        return this.getActualCash();
    }

    public BigDecimal getGiftcardTotal()
    {
        return this.getInStoreGiftcard().add(this.getWirelessDriverGiftcardTotal());
    }

    public BigDecimal getVisaTotal()
    {
        return this.getInStoreVisa().add(this.getWirelessDriverVisaTotal());
    }

    public BigDecimal getMastercardTotal()
    {
        return this.getInStoreMastercard().add(this.getWirelessDriverMastercardTotal());
    }

    public BigDecimal getAmexTotal()
    {
        return this.getInStoreAmex().add(this.getWirelessDriverAmexTotal());
    }

    public BigDecimal getDebitTotal()
    {
        return this.getInStoreDebit().add(this.getWirelessDriverDebitTotal());
    }

    public void trimNullBatchRecords()
    {
        List<BatchRecord> temp = new ArrayList<BatchRecord>();
        BatchRecord tempRecord = null;
        for (int i = 0; i < this.BatchRecords.size(); i++)
        {
            tempRecord = this.BatchRecords.get(i);
            if (tempRecord.getAmex() == null || tempRecord.getVisa() == null || tempRecord.getMastercard() == null
                || tempRecord.getDebit() == null || tempRecord.getBatchNumber() == null || tempRecord.getGiftcard() == null)
            {
                if (tempRecord.getId() != null)
                    tempRecord.restoreDefault();
                temp.add(this.BatchRecords.get(i));
            }
        }
        this.BatchRecords.removeAll(temp);
    }

    public int getNumberofFrontCounterMachines()
    {
        int num = 0;
        if (this.BatchRecords != null)
            for (int i = 0; i < this.BatchRecords.size(); i++)
            {
                if (this.BatchRecords.get(i).isWireless() == false)
                    num++;
            }
        return num;
    }

    public int getNumberofWirelessMachines()
    {
        int num = 0;
        if (this.BatchRecords != null)
            for (int i = 0; i < this.BatchRecords.size(); i++)
            {
                if (this.BatchRecords.get(i).isWireless() == true)
                    num++;
            }
        return num;
    }

    public List<BatchRecord> getBatchRecords()
    {
        return BatchRecords;
    }

    public void setBatchRecords(List<BatchRecord> batchRecords)
    {
        BatchRecords = batchRecords;
    }

    public Integer getOneHundredDollarBill()
    {
        return oneHundredDollarBill;
    }

    public void setOneHundredDollarBill(Integer oneHundredDollarBill)
    {
        this.oneHundredDollarBill = oneHundredDollarBill;
    }

    public Integer getFiftyDollarBill()
    {
        return fiftyDollarBill;
    }

    public void setFiftyDollarBill(Integer fiftyDollarBill)
    {
        this.fiftyDollarBill = fiftyDollarBill;
    }

    public Integer getTwentyDollarBill()
    {
        return twentyDollarBill;
    }

    public void setTwentyDollarBill(Integer twentyDollarBill)
    {
        this.twentyDollarBill = twentyDollarBill;
    }

    public Integer getTenDollarBill()
    {
        return tenDollarBill;
    }

    public void setTenDollarBill(Integer tenDollarBill)
    {
        this.tenDollarBill = tenDollarBill;
    }

    public Integer getFiveDollarBill()
    {
        return fiveDollarBill;
    }

    public void setFiveDollarBill(Integer fiveDollarBill)
    {
        this.fiveDollarBill = fiveDollarBill;
    }

    public Integer getTwoDollarBill()
    {
        return twoDollarBill;
    }

    public void setTwoDollarBill(Integer twoDollarBill)
    {
        this.twoDollarBill = twoDollarBill;
    }

    public Integer getOneDollarBill()
    {
        return oneDollarBill;
    }

    public void setOneDollarBill(Integer oneDollarBill)
    {
        this.oneDollarBill = oneDollarBill;
    }

    public Integer getTwentyFiveCentBill()
    {
        return twentyFiveCentBill;
    }

    public void setTwentyFiveCentBill(Integer twentyFiveCentBill)
    {
        this.twentyFiveCentBill = twentyFiveCentBill;
    }

    public Integer getTenCentBill()
    {
        return tenCentBill;
    }

    public void setTenCentBill(Integer tenCentBill)
    {
        this.tenCentBill = tenCentBill;
    }

    public Integer getFiveCentBill()
    {
        return fiveCentBill;
    }

    public void setFiveCentBill(Integer fiveCentBill)
    {
        this.fiveCentBill = fiveCentBill;
    }

    public Integer getOneCentBill()
    {
        return oneCentBill;
    }

    public void setOneCentBill(Integer oneCentBill)
    {
        this.oneCentBill = oneCentBill;
    }

    public BigDecimal getChequesTotal()
    {
        return chequesTotal;
    }

    public void setChequesTotal(BigDecimal chequesTotal)
    {
        this.chequesTotal = chequesTotal;
    }

    public BigDecimal getReturns()
    {
        return returns;
    }

    public void setReturns(BigDecimal returns)
    {
        this.returns = returns;
    }

    public BigDecimal getGiftCertificateSold()
    {
        return giftCertificateSold;
    }

    public void setGiftCertificateSold(BigDecimal giftCertificateSold)
    {
        this.giftCertificateSold = giftCertificateSold;
    }

    public BigDecimal getGiftCertificateRedeemed()
    {
        return giftCertificateRedeemed;
    }

    public void setGiftCertificateRedeemed(BigDecimal giftCertificateRedeemed)
    {
        this.giftCertificateRedeemed = giftCertificateRedeemed;
    }

    public BigDecimal getGiftcardReload()
    {
        return giftcardReload;
    }

    public void setGiftcardReload(BigDecimal giftcardReload)
    {
        this.giftcardReload = giftcardReload;
    }

}
