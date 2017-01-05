package com.pizza73.service.impl;

import com.pizza73.dao.LookupDao;
import com.pizza73.model.BusinessHourException;
import com.pizza73.model.DailySales;
import com.pizza73.model.GL;
import com.pizza73.model.Info;
import com.pizza73.model.Menu;
import com.pizza73.model.Municipality;
import com.pizza73.model.MunicipalityBusinessHours;
import com.pizza73.model.Order;
import com.pizza73.model.OrderItem;
import com.pizza73.model.PayYearPeriod;
import com.pizza73.model.Product;
import com.pizza73.model.ProductDetailOption;
import com.pizza73.model.ProductOutage;
import com.pizza73.model.ProductSize;
import com.pizza73.model.Role;
import com.pizza73.model.Shop;
import com.pizza73.model.StreetTypeCodeMap;
import com.pizza73.model.WeeklySales;
import com.pizza73.model.enums.MenuId;
import com.pizza73.model.enums.ProductCategoryEnum;
import com.pizza73.model.report.ReportMailingList;
import com.pizza73.model.theoretical.PrepItem;
import com.pizza73.model.theoretical.PrepItemDetail;
import com.pizza73.model.theoretical.ProductComposition;
import com.pizza73.model.theoretical.ProductCost;
import com.pizza73.model.theoretical.RawItemUnitCost;
import com.pizza73.model.theoretical.TheoreticalProduct;
import com.pizza73.model.theoretical.TheoreticalWeeklySales;
import com.pizza73.service.LookupManager;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Implementation of LookupManager interface to talk to the persistence layer.
 * <p/>
 * <p>
 * <a href="LookupManagerImpl.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Service("lookupManager")
public class LookupManagerImpl extends UniversalManagerImpl implements LookupManager
{
    @SuppressWarnings("unused")
    private final Logger log = Logger.getLogger(LookupManagerImpl.class);

    @Autowired
    private LookupDao lookupDao;

    public LookupManagerImpl()
    {
    }

    @Override
    public List<Menu> activeMenuItems(final Integer regionId)
    {
        return this.lookupDao.activeMenuItems(regionId);
    }

    @Override
    public List<Shop> activeShops()
    {
        return this.lookupDao.activeShops();
    }

    @Override
    public List<Shop> activeShopsForMunicipality(final String municipalityId)
    {
        return this.lookupDao.activeShopsForMunicipality(Integer.valueOf(municipalityId));
    }

    @Override
    public List<Shop> activeShopsWithCSC()
    {
        return this.lookupDao.activeShopsWithCSC();
    }

    @Override
    public List<DailySales> allDailySales()
    {
        return this.lookupDao.allDailySales();
    }

    @Override
    public List<Shop> allShops()
    {
        return this.lookupDao.allShops();
    }

    @Override
    public List<Shop> allShopsForMunicipality(final String municipalityId)
    {
        return this.lookupDao.allShopsForMunicipality(Integer.valueOf(municipalityId));
    }

    @Override
    public List<BusinessHourException> businessHourExceptions()
    {
        return this.lookupDao.businessHourExceptions();
    }

    @Override
    public List<Menu> currentPromoMenuItems(final ProductCategoryEnum category, final Integer menuId)
    {
        return this.lookupDao.currentPromoMenuItems(category.getId(), menuId);
    }

    @Override
    public DailySales dailySalesForShopAndDate(final Integer shopId, final Calendar date)
    {
        return this.lookupDao.dailySalesForShopAndDate(shopId, date);
    }

    @Override
    public List<Integer> distinctDeliveryCostsForCity(final String cityId)
    {
        return this.lookupDao.distinctDeliveryCostsForCity(Integer.valueOf(cityId));
    }

    @Override
    public List<ReportMailingList> distinctReports()
    {
        return this.lookupDao.distinctReports();
    }

    @Override
    public DailySales earliestDailySalesForShop(final Integer shopId)
    {
        return this.lookupDao.earliestDailySalesForShop(shopId);
    }

    @Override
    public DailySales earliestSubmittedDailySalesForShop(final Integer shopId)
    {
        return this.lookupDao.earliestSubmittedDailySalesForShop(shopId);
    }

    @Override
    public Menu getMenuItem(final Integer menuId, final Product product, final ProductSize size)
    {
        return this.lookupDao.getMenuItemForProduct(menuId, product, size);
    }

    @Override
    public Menu getMenuItem(final MenuId menuId, final Product product, final ProductSize size)
    {
        return this.lookupDao.getMenuItemForProduct(menuId.getId(), product, size);
    }

    @Override
    public Menu getMenuItem(final String menuId)
    {
        return this.lookupDao.menuItem(Integer.valueOf(menuId));
    }

    @Override
    public Shop getShop(final String companyNo, final String branchNo)
    {
        return this.lookupDao.getShop(companyNo, Integer.valueOf(branchNo));
    }

    @Override
    public Shop getShopNewPayroll(final String newPayrollCompanyNo, final String newPayrollDepartmentNo)
    {
        return this.lookupDao.getShop(newPayrollCompanyNo, newPayrollDepartmentNo);
    }

    @Override
    public PayYearPeriod getYearPeriodByYear(final Integer year)
    {
        return this.lookupDao.getYearPeriodByYear(year);
    }

    @Override
    public List<Integer> GLaccountsForPeriodAndShop(final Integer year, final Integer period, final Integer shop_id)
    {
        return this.lookupDao.GLaccountsForPeriodAndShop(year, period, shop_id);
    }

    @Override
    public List<Integer> GLaccountsForYearAndShop(final Integer year, final Integer shop_id)
    {
        return this.lookupDao.GLaccountsForYearAndShop(year, shop_id);
    }

    @Override
    public List<GL> GLRecordsForPeriodAndShop(final Integer year, final Integer period, final Integer shop_id)
    {
        return this.lookupDao.GLRecordsForPeriodAndShop(year, period, shop_id);
    }

    @Override
    public List<GL> GLRecordsForPeriodAndShop(final Integer year, final Integer period, final Integer account, final Integer shop_id)
    {
        return this.lookupDao.GLRecordsForPeriodAndShop(year, period, account, shop_id);
    }

    @Override
    public List<GL> GLRecordsForYearAndShop(final Integer year, final Integer account, final Integer shop_id)
    {
        return this.lookupDao.GLRecordsForYearAndShop(year, account, shop_id);
    }

    @Override
    public List<Integer> GLshopsForYear(final Integer year)
    {
        return this.lookupDao.GLshopsForYear(year);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.pizza73.service.LookupManager#infoValueForVariable(java.lang.String)
     */
    @Override
    public String infoValueForVariable(final String variable)
    {
        return this.lookupDao.infoValueForVariable(variable);
    }

    @Override
    public boolean isBlacklisted(final String phoneNumber)
    {
        if(this.lookupDao.getBlacklisted(phoneNumber) != null)
        {
            return true;
        }

        return false;
    }

    @Override
    public DailySales latestSubmittedDailySalesForShop(final Integer shopId)
    {
        return this.lookupDao.latestSubmittedDailySalesForShop(shopId);
    }

    @Override
    public List<ReportMailingList> mailingListForReport(final String report)
    {
        return this.lookupDao.mailingListForReport(report);
    }

    @Override
    public Menu menuItem(final Integer menuId, final Integer productId, final Integer sizeId)
    {
        return this.lookupDao.menuItem(menuId, productId, sizeId);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.pizza73.service.LookupManager#menuItem(java.lang.String,
     * java.lang.String, java.lang.String)
     */
    @Override
    public Menu menuItem(final String menuId, final String productId, final String sizeId)
    {
        return this.menuItem(Integer.valueOf(menuId), Integer.valueOf(productId), Integer.valueOf(sizeId));
    }

    @Override
    public List<Menu> menuItemsForCategories(final Integer menuId, final Integer[] categories)
    {
        return this.lookupDao.menuItemsForCategories(menuId, categories);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.pizza73.service.LookupManager#menuItemsForCategories(java.lang.String
     * , java.lang.Integer[])
     */
    @Override
    public List<Menu> menuItemsForCategories(final String menuId, final Integer[] categories, final Integer sizeId)
    {
        return this.lookupDao.menuItemsForCategories(Integer.valueOf(menuId), categories, sizeId);
    }

    @Override
    public List<Menu> menuItemsForCategory(final ProductCategoryEnum category, final Integer menuId)
    {
        return this.lookupDao.menuItemsForCategory(category.getId(), menuId);
    }

    @Override
    public List<Menu> menuItemsForCategory(final ProductCategoryEnum category, final Integer menuId, boolean displayMenu)
    {
        return this.lookupDao.menuItemsForCategory(category.getId(), menuId, displayMenu);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.pizza73.service.LookupManager#menuItemsForProduct(java.lang.Integer,
     * java.lang.Integer)
     */
    @Override
    public List<Menu> menuItemsForProduct(final Integer productId, final Integer menuId)
    {
        return this.lookupDao.menuItemsForProduct(productId, menuId);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.pizza73.service.LookupManager#menuItemsForProducts(java.lang.Integer,
     * java.lang.Integer[])
     */
    @Override
    public List<Menu> menuItemsForProducts(final Integer menuId, final Integer[] productIds)
    {
        return this.lookupDao.menuItemsForProducts(menuId, productIds);
    }

    public List<Menu> menuItemsForProducts(final Integer menuId, final Integer[] productIds, Integer sizeId)
    {
        return this.lookupDao.menuItemsForProducts(menuId, productIds, sizeId);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.pizza73.service.LookupManager#menuItemsForSize(java.lang.Integer,
     * com.pizza73.model.enums.ProductCategoryEnum, java.lang.Integer)
     */
    @Override
    public List<Menu> menuItemsForSize(final Integer menuId, final ProductCategoryEnum category, final Integer sizeId)
    {
        return this.lookupDao.menuItemsForSizeList(menuId, category.getId(), sizeId);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.pizza73.service.LookupManager#municipalities()
     */
    @Override
    public List<Municipality> municipalities()
    {
        return this.lookupDao.municipalities();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.pizza73.service.LookupManager#orderItemsForParent(java.lang.Integer)
     */
    @Override
    public List<OrderItem> orderItemsForParent(final Integer orderId, final Integer itemId)
    {
        return this.lookupDao.orderItemsForParent(orderId, itemId);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.pizza73.service.LookupManager#topLevelItems(java.lang.Integer)
     */
    @Override
    public List<OrderItem> parentOrderItemsForOrder(final Integer orderId)
    {
        return this.lookupDao.parentOrderItemsForOrder(orderId);
    }

    @Override
    public List<Order> parkedOrdersForBusinessDate()
    {
        return this.lookupDao.parkedOrdersForBusinessDate();
    }

    @Override
    public Map<Integer, PrepItem> prepInvItems()
    {
        final Map<Integer, PrepItem> prepItemsMap = new HashMap<Integer, PrepItem>();

        final Map<Integer, RawItemUnitCost> rawCosts = rawInvItemUnitCost();
        final Map<Integer, List<PrepItemDetail>> prepItemDetails = prepInvItemDetails();

        final List<Object[]> prepItems = this.lookupDao.prepInvItems();
        for(final Object[] obj : prepItems)
        {
            final Integer prepItemId = (Integer) obj[0];
            final String desc = (String) obj[1];
            final Integer yield = ((BigDecimal) obj[2]).intValue();
            final String portion = (String) obj[3];
            final PrepItem prepItem = new PrepItem(prepItemId, desc, yield, portion, prepItemDetails.get(prepItemId));
            prepItem.calculateCost(rawCosts);
            prepItemsMap.put(prepItemId, prepItem);
        }

        return prepItemsMap;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.pizza73.service.LookupManager#priceForSize(java.lang.Integer)
     */
    @Override
    public Integer priceForSize(final Integer categoryId, final Integer sizeId)
    {
        return this.lookupDao.priceForSize(categoryId, sizeId);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.pizza73.service.LookupManager#productById(java.lang.Integer)
     */
    @Override
    public Product productById(final Integer id)
    {
        return this.lookupDao.productById(id);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.pizza73.service.LookupManager#productById(java.lang.String)
     */
    @Override
    public Product productById(final String id)
    {
        return productById(Integer.valueOf(id));
    }

    /*
     * (non-Javadoc)
     *
     * @see com.pizza73.service.LookupManager#productByName(java.lang.String)
     */
    @Override
    public Product productByShortName(final String name)
    {
        return this.lookupDao.productByShortName(name);
    }

    @Override
    public List<Product> productsByCategory(final ProductCategoryEnum category)
    {
        return this.lookupDao.productsByCategory(category.getId());
    }

    @Override
    public List<String> provinces()
    {
        return this.lookupDao.provinces();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Shop> resetAllShopMessages()
    {
        final List<Shop> shops = (List<Shop>) this.lookupDao.getAll(Shop.class);
        final Iterator<Shop> iter = shops.iterator();
        Shop shop = null;
        while (iter.hasNext())
        {
            shop = iter.next();
            shop.setMessage("X");
            shop.setMessageTwo("");
            this.lookupDao.save(shop);
        }

        return shops;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Shop> resetAllShopRedirects()
    {
        final List<Shop> shops = (List<Shop>) this.lookupDao.getAll(Shop.class);
        final Iterator<Shop> iter = shops.iterator();
        Shop shop = null;
        while (iter.hasNext())
        {
            shop = iter.next();
            if(!shop.getId().equals(shop.getRedirectShop()))
            {
                shop.setRedirectShop(shop.getId());
                this.lookupDao.save(shop);
            }
        }

        return shops;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.pizza73.service.LookupManager#roleByName(java.lang.String)
     */
    @Override
    public Role roleByName(final String onlineCustomerRole)
    {
        return this.lookupDao.roleByName(onlineCustomerRole);
    }

    @Override
    public Integer shopForPostalCode(final String postalCode)
    {
        return this.lookupDao.shopForPostalCode(postalCode);
    }

    @Override
    public Shop shopForShortName(final String shopId)
    {
        return this.lookupDao.shopForShortName(shopId);
    }

    @Override
    public MunicipalityBusinessHours shopHourForMunicipalityAndDay(final Integer municipality_id, final Integer day)
    {
        return this.lookupDao.shopHourForMunicipalityAndDay(municipality_id, day);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.pizza73.service.LookupManager#shopOrderSequence()
     */
    @Override
    public Integer shopOrderSequence(final Integer shopId)
    {
        return this.lookupDao.shopOrderSequence(shopId);
    }

    @Override
    public Map<TheoreticalProduct, ProductCost> theoreticalProducUnitCost()
    {
        final Map<TheoreticalProduct, ProductCost> productCostMap = new HashMap<TheoreticalProduct, ProductCost>();

        final Map<Integer, PrepItem> prepItems = prepInvItems();
        final Map<TheoreticalProduct, List<ProductComposition>> prodComps = productCompositionPrepData();
        for(final TheoreticalProduct product : prodComps.keySet())
        {
            final ProductCost productCost = new ProductCost(product);
            productCost.calculateProductCost(prodComps.get(product), prepItems);
            productCostMap.put(product, productCost);
        }

        return productCostMap;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Shop> updateAllShopMessages(String message, String messageTwo)
    {
        final List<Shop> shops = (List<Shop>) this.lookupDao.getAll(Shop.class);
        final Iterator<Shop> iter = shops.iterator();
        Shop shop = null;
        if(StringUtils.isBlank(message))
        {
            message = "X";
            messageTwo = "";
        }
        while (iter.hasNext())
        {
            shop = iter.next();
            shop.setMessage(message);
            shop.setMessageTwo(messageTwo);
            this.lookupDao.save(shop);
        }

        return shops;
    }

    // Theoretical food cost

    /*
     * (non-Javadoc)
     *
     * @see com.pizza73.service.LookupManager#updateBusinessDate()
     */
    @Override
    public void updateBusinessDate()
    {
        final Calendar businessDay = businessDate();
        businessDay.add(Calendar.DATE, 1);
        final int year = businessDay.get(Calendar.YEAR);
        String month = (businessDay.get(Calendar.MONTH) + 1) + "";
        String day = businessDay.get(Calendar.DATE) + "";

        if(month.length() == 1)
        {
            month = "0" + month;
        }
        if(day.length() == 1)
        {
            day = "0" + day;
        }

        final Info info = new Info();
        info.setValue(year + month + day);
        info.setVariable("business_date");
        update(info);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.pizza73.service.LookupManager#updatePayrollPeriod()
     */
    @Override
    public void updatePayrollPeriod()
    {
        final String payPeriod = this.lookupDao.infoValueForVariable("payroll_period");
        int pp = Integer.parseInt(payPeriod);
        boolean updateYear = false;

        if(pp == 26)
        {
            pp = 1;
            updateYear = true;
        }
        else
        {
            pp++;
        }
        final Info periodInfo = new Info("payroll_period", pp + "");
        save(periodInfo);

        if(updateYear)
        {
            final String payrollYear = this.lookupDao.infoValueForVariable("payroll_year");
            int py = Integer.parseInt(payrollYear);
            py++;
            final Info yearInfo = new Info("payroll_year", py + "");
            save(yearInfo);
        }
    }

    @Override
    public int updatePostalCode(final String postalCode, final Integer shopId)
    {
        return this.lookupDao.updatePostalCode(postalCode, shopId);
    }

    @Override
    public WeeklySales weeklySalesForShopAndWeek(final Integer shopId, final Calendar sundayOfWeek)
    {
        return this.lookupDao.weeklySalesForShopAndWeek(shopId, sundayOfWeek);
    }

    /**
     * sent_shop_id | group_id | size | crust | sauce | sum
     */
    @Override
    public List<TheoreticalWeeklySales> weeklySalesForShopStartingOn(final int shopId, final Calendar businessDate)
    {
        final List<TheoreticalWeeklySales> salesDetails = new ArrayList<TheoreticalWeeklySales>();
        final List<Object[]> weeklySalesForProduct = this.lookupDao.weeklySalesForShopStartingOn(shopId, businessDate);
        Integer productId = null;
        Integer sizeId = null;
        Integer crustId = null;
        Integer sauceId = null;
        Integer storeId = null;
        Integer sum = null;
        for(final Object[] weeklySaleForProduct : weeklySalesForProduct)
        {
            storeId = (Integer) weeklySaleForProduct[0];
            productId = (Integer) weeklySaleForProduct[1];
            sizeId = (Integer) weeklySaleForProduct[2];
            crustId = (Integer) weeklySaleForProduct[3];
            sauceId = (Integer) weeklySaleForProduct[4];
            sum = (Integer) weeklySaleForProduct[5];
            final TheoreticalWeeklySales tws = new TheoreticalWeeklySales(productId, sizeId, crustId, sauceId, storeId, sum);
            salesDetails.add(tws);
        }

        return salesDetails;
    }

    @Override
    public List<GL> yearToDateEarningsForPeriodAndShop(final Integer year, final Integer period, final Integer shop_id)
    {
        return this.lookupDao.yearToDateEarningsForPeriodAndShop(year, period, shop_id);
    }

    private Map<Integer, List<PrepItemDetail>> prepInvItemDetails()
    {
        final Map<Integer, List<PrepItemDetail>> prepItemDetailsMap = new HashMap<Integer, List<PrepItemDetail>>();
        final List<Object[]> prepItemDetails = this.lookupDao.prepInvItemDetails();
        for(final Object[] obj : prepItemDetails)
        {
            final Integer prepItemId = (Integer) obj[0];
            final Integer rawItemId = (Integer) obj[1];
            final BigDecimal portion = (BigDecimal) obj[2];
            final PrepItemDetail prepItemDetail = new PrepItemDetail(prepItemId, rawItemId, portion);
            if(prepItemDetailsMap.containsKey(prepItemId))
            {
                final List<PrepItemDetail> prepItemList = prepItemDetailsMap.get(prepItemId);
                prepItemList.add(prepItemDetail);
            }
            else
            {
                final List<PrepItemDetail> prepItemList = new ArrayList<PrepItemDetail>();
                prepItemList.add(prepItemDetail);
                prepItemDetailsMap.put(prepItemId, prepItemList);
            }
        }

        return prepItemDetailsMap;
    }

    private Map<TheoreticalProduct, List<ProductComposition>> productCompositionPrepData()
    {
        final Map<TheoreticalProduct, List<ProductComposition>> prodCompMap = new HashMap<TheoreticalProduct, List<ProductComposition>>();
        final List<Object[]> productComps = this.lookupDao.productCompositionPrepData();
        // pc.product_id, pc.product_modifier_1, pc.product_modifier_2,
        // pc.product_modifier_3, " +
        // pcd.prep_inv_item_id, pcd.prep_portions
        for(final Object[] obj : productComps)
        {
            final Integer prodId = ((BigDecimal) obj[0]).intValue(); // product_id
            final String description = (String) obj[1];
            final Integer one = (Integer) obj[2]; // modifier 1
            final Integer two = (Integer) obj[3]; // modifier 2
            final Integer three = (Integer) obj[4]; // modifier 3
            final Integer prepInvId = (Integer) obj[5]; // prep_inv_item_id
            final BigDecimal portion = (BigDecimal) obj[6]; // prep_inv_portions_size
            final TheoreticalProduct product = new TheoreticalProduct(prodId, one, two, three);
            product.setDescription(description);
            final ProductComposition comp = new ProductComposition(product, prepInvId, portion);
            if(prodCompMap.containsKey(product))
            {
                prodCompMap.get(product).add(comp);
            }
            else
            {
                final List<ProductComposition> compList = new ArrayList<ProductComposition>();
                compList.add(comp);
                prodCompMap.put(product, compList);
            }
        }

        return prodCompMap;
    }

    private Map<Integer, RawItemUnitCost> rawInvItemUnitCost()
    {
        final Map<Integer, RawItemUnitCost> rawCosts = new HashMap<Integer, RawItemUnitCost>();
        final List<Object[]> rows = this.lookupDao.rawInvItemUnitCost();
        for(final Object[] obj : rows)
        {
            final Integer rawInvItemId = (Integer) obj[0]; // raw_inv_item_id
            final BigDecimal cost = (BigDecimal) obj[1]; // unit_cost
            final RawItemUnitCost unitCost = new RawItemUnitCost(rawInvItemId, cost);
            rawCosts.put(rawInvItemId, unitCost);
        }

        return rawCosts;
    }

    @Override
    public List<ProductDetailOption> productDetailOptionsForDetail(Integer productDetailId)
    {
        return this.lookupDao.productDetailOptionsForDetail(productDetailId);
    }

    // iPhone Version
    @Override
    public Integer iPhoneVersionForCities()
    {
        return this.lookupDao.iPhoneVersionForCities();
    }

    @Override
    public Integer iPhoneVersionForMenu()
    {
        return this.lookupDao.iPhoneVersionForMenu();
    }

    @Override
    public Integer iPhoneVersionForSpecials()
    {
        return this.lookupDao.iPhoneVersionForSpecials();
    }

    @Override
    public Integer iPhoneVersionForApp()
    {
        return this.lookupDao.iPhoneVersionForApp();
    }

    @Override
    public List<ProductOutage> findAllProductOutages()
    {
        return this.lookupDao.findAllProductOutages();
    }

    @Override
    public List<Product> findActiveProducts()
    {
        return this.lookupDao.findActiveProducts();
    }

    @Override
    public List<Product> findDistinctProductsForOutages()
    {
        return this.lookupDao.findDistinctProductsForOutages();
    }

    @Override
    public void deleteProductOutageForProduct(Integer productId)
    {
        this.lookupDao.deleteProductOutageForProduct(productId);
    }

    public List<Integer> productOutagesForShop(Integer shopId)
    {
        return this.lookupDao.productOutagesForShop(shopId);
    }

    @Override
    public List<Shop> shopsWithOutage(Integer productId)
    {
        return this.lookupDao.shopsWithOutage(productId);
    }

    @Override
    public void deleteProductOutageForProductAndShop(Integer productId, Integer shopId)
    {
        this.lookupDao.deleteProductOutageForProductAndShop(productId, shopId);
    }

    public Map<String, String> streetTypeCodeMap()
    {
        @SuppressWarnings("unchecked") final List<StreetTypeCodeMap> codes = (List<StreetTypeCodeMap>) getAll(StreetTypeCodeMap.class);
        final Map<String, String> codeMap = new TreeMap<String, String>();

        for(StreetTypeCodeMap code : codes)
        {
            codeMap.put(code.getAlternateTypeCode().trim(), code.getTypeCode().trim());
        }

        return codeMap;
    }
}