package com.pizza73.service;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
import com.pizza73.model.enums.MenuId;
import com.pizza73.model.enums.ProductCategoryEnum;
import com.pizza73.model.report.ReportMailingList;
import com.pizza73.model.theoretical.PrepItem;
import com.pizza73.model.theoretical.ProductCost;
import com.pizza73.model.theoretical.TheoreticalProduct;
import com.pizza73.model.theoretical.TheoreticalWeeklySales;

/**
 * Business Service Interface to talk to persistence layer and retrieve values
 * for drop-down choice lists.
 *
 * <p>
 * <a href="LookupManager.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public interface LookupManager extends UniversalManager
{
    public List<Menu> activeMenuItems(Integer menuId);

    public List<Shop> activeShops();

    /**
     * @param municipalityId
     * @return
     */
    public List<Shop> activeShopsForMunicipality(String municipalityId);

    public List<Shop> activeShopsWithCSC();

    public List<DailySales> allDailySales();

    public List<Shop> allShops();

    public List<Shop> allShopsForMunicipality(String municipalityId);

    public List<BusinessHourException> businessHourExceptions();

    /**
     * @param special
     * @param id
     * @return
     */
    public List<Menu> currentPromoMenuItems(ProductCategoryEnum special, Integer id);

    public DailySales dailySalesForShopAndDate(Integer shopId, Calendar date);

    public List<Integer> distinctDeliveryCostsForCity(String cityId);

    public List<ReportMailingList> distinctReports();

    public DailySales earliestDailySalesForShop(Integer shopId);

    public DailySales earliestSubmittedDailySalesForShop(Integer shopId);

    public Menu getMenuItem(Integer menuId, Product product, ProductSize size);

    /**
     * @param menuId
     * @param product
     * @return
     */
    public Menu getMenuItem(MenuId menuId, Product product, ProductSize size);

    /**
     * @param menuId
     * @return
     */
    public Menu getMenuItem(String menuId);

    public Shop getShop(String companyNo, String branchNo);

    public Shop getShopNewPayroll(String newPayrollCompanyNo, String newPayrollDepartmentNo);

    public PayYearPeriod getYearPeriodByYear(Integer year);

    public List<Integer> GLaccountsForPeriodAndShop(Integer year, Integer period, Integer shop_id);

    public List<Integer> GLaccountsForYearAndShop(Integer year, Integer shop_id);

    public List<GL> GLRecordsForPeriodAndShop(Integer year, Integer period, Integer shop_id);

    public List<GL> GLRecordsForPeriodAndShop(Integer year, Integer period, Integer account, Integer shop_id);

    public List<GL> GLRecordsForYearAndShop(Integer year, Integer account, Integer shop_id);

    public List<Integer> GLshopsForYear(Integer year);

    /**
     * @param string
     */
    @Override
    public String infoValueForVariable(String variable);

    public boolean isBlacklisted(String phoneNumber);

    public DailySales latestSubmittedDailySalesForShop(Integer shopId);

    public List<ReportMailingList> mailingListForReport(String report);

    public Menu menuItem(Integer menuId, Integer productId, Integer sizeId);

    /**
     * @param menuId
     * @param productId
     * @param sizeId
     * @return
     */
    public Menu menuItem(String menuId, String productId, String sizeId);

    public List<Menu> menuItemsForCategories(Integer menuId, Integer[] categories);

    /**
     * @param string
     * @param categories
     * @return
     */
    public List<Menu> menuItemsForCategories(String menuId, Integer[] categories, Integer sizeId);

    public List<Menu> menuItemsForCategory(ProductCategoryEnum category, Integer menuId);

    public List<Menu> menuItemsForCategory(final ProductCategoryEnum category, final Integer menuId, boolean displayMenu);

    public List<Menu> menuItemsForProduct(Integer productId, Integer menuId);

    /**
     * @param integer
     * @param integers
     * @return
     */
    public List<Menu> menuItemsForProducts(Integer menuId, Integer[] productIds);
    public List<Menu> menuItemsForProducts(final Integer menuId, final Integer[] productIds, Integer sizeId);

    /**
     * @param integer
     * @param wing_box
     * @param integer2
     * @return
     */
    public List<Menu> menuItemsForSize(Integer menuId, ProductCategoryEnum category, Integer sizeId);

    /**
     * @return
     */
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
    public Map<Integer, PrepItem> prepInvItems();

    /**
     * @param pop_can_size_id
     * @return
     */
    public Integer priceForSize(Integer categoryId, Integer sizeId);

    public Product productById(Integer id);

    public Product productById(String id);

    // ~ Methods
    // ================================================================
    /**
     * @param string
     * @return
     */
    public Product productByShortName(String name);

    public List<Product> productsByCategory(ProductCategoryEnum category);

    public List<String> provinces();

    public List<Shop> resetAllShopMessages();

    public List<Shop> resetAllShopRedirects();

    public Role roleByName(String online_customer_role);

    public Integer shopForPostalCode(String postalCode);

    public Shop shopForShortName(String shopId);

    public MunicipalityBusinessHours shopHourForMunicipalityAndDay(Integer municipality_id, Integer day);

    /**
     * @return
     */
    public Integer shopOrderSequence(Integer shopId);

    public Map<TheoreticalProduct, ProductCost> theoreticalProducUnitCost();

    public List<Shop> updateAllShopMessages(String message, String messageTwo);

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updateBusinessDate();

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updatePayrollPeriod();

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public int updatePostalCode(String postalCode, Integer shopId);

    public WeeklySales weeklySalesForShopAndWeek(Integer shopId, Calendar sundayOfWeek);

    public List<TheoreticalWeeklySales> weeklySalesForShopStartingOn(int shopId, Calendar businessDate);

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

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteProductOutageForProduct(Integer productId);

    public List<Integer> productOutagesForShop(Integer shopId);

    public List<Shop> shopsWithOutage(Integer productId);

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteProductOutageForProductAndShop(Integer productId, Integer shopId);

    public Map<String, String> streetTypeCodeMap();
}
