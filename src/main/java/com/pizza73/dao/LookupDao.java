package com.pizza73.dao;

import java.util.Calendar;
import java.util.List;

import com.pizza73.model.Blacklist;
import com.pizza73.model.BusinessHourException;
import com.pizza73.model.DailySales;
import com.pizza73.model.GL;
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
import com.pizza73.model.WeeklySales;
import com.pizza73.model.report.ReportMailingList;

/**
 * Lookup Data Access Object (Dao) interface. This is used to lookup values in
 * the database (i.e. for drop-downs).
 *
 * <p>
 * <a href="LookupDao.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public interface LookupDao extends UniversalDao
{
    public List<Menu> activeMenuItems(Integer menuId);

    public List<Shop> activeShops();

    /**
     * @param municipalityId
     * @return
     */
    public List<Shop> activeShopsForMunicipality(Integer municipalityId);

    public List<Shop> activeShopsWithCSC();

    public List<DailySales> allDailySales();

    public List<Shop> allShops();

    public List<Shop> allShopsForMunicipality(Integer municipalityId);

    public List<BusinessHourException> businessHourExceptions();

    /**
     * @param category
     * @param menuId
     * @return
     */
    public List<Menu> currentPromoMenuItems(Integer categoryId, Integer menuId);

    public DailySales dailySalesForShopAndDate(Integer shopId, Calendar date);

    public List<Integer> distinctDeliveryCostsForCity(Integer cityId);

    public List<ReportMailingList> distinctReports();

    public DailySales earliestDailySalesForShop(Integer shopId);

    public DailySales earliestSubmittedDailySalesForShop(Integer shopId);

    public Blacklist getBlacklisted(String phoneNumber);

    /**
     * @param id
     * @param product
     * @return
     */
    public Menu getMenuItemForProduct(Integer menuId, Product product, ProductSize size);

    public Shop getShop(String companyNo, Integer branchNo);

    public Shop getShop(String newPayrollCompanyNo, String newPayrollDepartmentNo);

    public PayYearPeriod getYearPeriodByYear(Integer year);

    public List<Integer> GLaccountsForPeriodAndShop(Integer year, Integer period, Integer shop_id);

    public List<Integer> GLaccountsForYearAndShop(Integer year, Integer shop_id);

    public List<GL> GLRecordsForPeriodAndShop(Integer year, Integer period, Integer shop_id);

    public List<GL> GLRecordsForPeriodAndShop(Integer year, Integer period, Integer account, Integer shop_id);

    public List<GL> GLRecordsForYearAndShop(Integer year, Integer account, Integer shop_id);

    public List<Integer> GLshopsForYear(Integer year);

    /**
     * @param variable
     */
    @Override
    public String infoValueForVariable(String variable);

    public DailySales latestSubmittedDailySalesForShop(Integer shopId);

    public List<ReportMailingList> mailingListForReport(String report);

    /**
     * @param integer
     * @return
     */
    public Menu menuItem(Integer menuId);

    /**
     * @param integer
     * @param integer2
     * @param integer3
     * @return
     */
    public Menu menuItem(Integer integer, Integer integer2, Integer integer3);

    public List<Menu> menuItemsForCategories(Integer menuId, Integer[] categories);

    /**
     * @param menuId
     * @param categories
     * @return
     */
    public List<Menu> menuItemsForCategories(Integer menuId, Integer[] categories, Integer sizeId);

    public List<Menu> menuItemsForCategory(Integer categoryId, Integer menuId);

    public List<Menu> menuItemsForCategory(Integer categoryId, Integer menuId, boolean displayMenu);

    /**
     * @param productId
     * @param menuId
     * @return
     */
    public List<Menu> menuItemsForProduct(Integer productId, Integer menuId);

    /**
     * @param menuId
     * @param productIds
     * @return
     */
    public List<Menu> menuItemsForProducts(Integer menuId, Integer[] productIds);
    public List<Menu> menuItemsForProducts(final Integer menuId, final Integer[] productIds, Integer sizeId);

    public List<Menu> menuItemsForSizeList(Integer menuId, Integer categoryId, Integer sizeId);

    public List<Municipality> municipalities();

    /**
     * @param itemId
     * @return
     */
    public List<OrderItem> orderItemsForParent(Integer orderId, Integer itemId);

    /**
     * @param orderId
     * @return
     */
    public List<OrderItem> parentOrderItemsForOrder(Integer orderId);

    public List<Order> parkedOrdersForBusinessDate();

    // Theoretical food cost
    public List<Object[]> prepInvItemDetails();

    public List<Object[]> prepInvItems();

    /**
     * @param sizeId
     * @return
     */
    public Integer priceForSize(Integer categoryId, Integer sizeId);

    public Product productById(Integer id);

    public Product productById(String id);

    // ~ Methods
    // ================================================================
    /**
     * @param name
     * @return
     */
    public Product productByShortName(String name);

    public List<Object[]> productCompositionPrepData();

    public List<Product> productsByCategory(Integer categoryId);

    public List<String> provinces();

    public List<Object[]> rawInvItemUnitCost();

    /**
     * @param onlineCustomerRole
     * @return
     */
    public Role roleByName(String onlineCustomerRole);

    public Integer shopForPostalCode(String postalCode);

    public Shop shopForShortName(String shopName);

    public MunicipalityBusinessHours shopHourForMunicipalityAndDay(Integer municipality_id, Integer day);

    /**
     * @return
     */
    public Integer shopOrderSequence(Integer shopId);

    public int updatePostalCode(String postalCode, Integer shopId);

    public WeeklySales weeklySalesForShopAndWeek(Integer shopId, Calendar sundayOfWeek);

    public List<Object[]> weeklySalesForShopStartingOn(int shopId, Calendar businessDate);

    public List<GL> yearToDateEarningsForPeriodAndShop(Integer year, Integer period, Integer shop_id);

    public List<ProductDetailOption> productDetailOptionsForDetail(Integer productDetailId);

    // iPhone Version
    public Integer iPhoneVersionForCities();

    public Integer iPhoneVersionForMenu();

    public Integer iPhoneVersionForSpecials();

    public Integer iPhoneVersionForApp();

    public List<ProductOutage> findAllProductOutages();

    public List<Product> findActiveProducts();

    public List<Product> findDistinctProductsForOutages();

    public void deleteProductOutageForProduct(Integer productId);

    public List<Integer> productOutagesForShop(Integer shopId);

    public List<Shop> shopsWithOutage(Integer productId);

    public void deleteProductOutageForProductAndShop(Integer productId, Integer shopId);

    public Menu getMenuItemForProduct(Integer menuId, Integer productId, Integer sizeId);
}