package com.pizza73.dao.hibernate;

import com.pizza73.dao.LookupDao;
import com.pizza73.model.Blacklist;
import com.pizza73.model.BusinessHourException;
import com.pizza73.model.DailySales;
import com.pizza73.model.GL;
import com.pizza73.model.Info;
import com.pizza73.model.Menu;
import com.pizza73.model.Municipality;
import com.pizza73.model.MunicipalityBusinessHours;
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
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 * Hibernate implementation of LookupDao.
 * <p/>
 * <p>
 * <a href="LookupDaoHibernate.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Repository("lookupDao")
public class LookupDaoHibernate extends UniversalDaoHibernate implements LookupDao
{
    private final Logger log = Logger.getLogger(LookupDaoHibernate.class);

    private final Integer NISKU_ID = Integer.valueOf(18);

    private final Integer STONY_PLAIN_ID = Integer.valueOf(11);

    private static final String MENU = "menu";

    private static final String SPECIALS = "specials";

    private static final String CITIES = "cities";

    private static final String IPHONE_APP = "iphone_app";

    @Override
    @SuppressWarnings("unchecked")
    public List<Menu> activeMenuItems(final Integer menuId)
    {
        final Query query = super.getCurrentSession().createQuery("from Menu m where m.product.active=true and m.menuId=:mId order by m.categoryId, m.displayHint, m.size.id")
            .setInteger("mId", menuId);
        return query.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Shop> activeShops()
    {
        this.log.warn("attempting async query for shops");
        final Query query = super.getCurrentSession().createQuery("from Shop m where m.city.id!=0 and m.active='Y' order by m.city.id, m.name");
        return query.list();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.pizza73.dao.LookupDao#shopsForMunicipality(java.lang.String)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Shop> activeShopsForMunicipality(final Integer municipalityId)
    {
        Query query = null;
        // for municipalities that are serviced from a different location get
        // the shops
        // for the municipalities that serve them.
        if(municipalityId.equals(this.STONY_PLAIN_ID))
        {
            query = super.getCurrentSession().createQuery("from Shop m where m.city.id=10 and m.active='Y' order by m.id");
        }
        else if(municipalityId.equals(this.NISKU_ID))
        {
            query = super.getCurrentSession().createQuery("from Shop m where m.city.id=20 and m.active='Y' order by m.id");
        }
        else
        {
            query = super.getCurrentSession().createQuery("from Shop m where m.city.id=:val and m.active='Y' order by m.id").setInteger("val", municipalityId);
        }

        return query.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Shop> activeShopsWithCSC()
    {
        final Query query = super.getCurrentSession().createQuery("from Shop m where (m.city.id!=0 or m.shortName=:CSCShortName) and m.active='Y' order by m.city.id, m.name")
            .setString("CSCShortName", "CSC");
        return query.list();

    }

    @Override
    @SuppressWarnings("unchecked")
    public List<DailySales> allDailySales()
    {
        return super.getCurrentSession().createCriteria(DailySales.class).list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Shop> allShops()
    {
        final Query query = super.getCurrentSession().createQuery("from Shop m where (m.city.id!=0) and m.active='Y' order by m.city.id, m.name");
        return query.list();

    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Shop> allShopsForMunicipality(final Integer municipalityId)
    {
        Query query = null;
        if(municipalityId.equals(this.STONY_PLAIN_ID))
        {
            query = super.getCurrentSession().createQuery("from Shop m where m.city.id=10 order by m.id");
        }
        else if(municipalityId.equals(this.NISKU_ID))
        {
            query = super.getCurrentSession().createQuery("from Shop m where m.city.id=20 order by m.id");
        }
        else
        {
            query = super.getCurrentSession().createQuery("from Shop m where m.city.id=:val order by m.id").setInteger("val", municipalityId);
        }

        return query.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<BusinessHourException> businessHourExceptions()
    {
        final Query query = super.getCurrentSession().createQuery("from BusinessHourException m where m.active=true");
        return query.list();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.pizza73.dao.LookupDao#currentPromoMenuItems(com.pizza73.model.enums
     * .ProductCategoryEnum, java.lang.Integer)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Menu> currentPromoMenuItems(final Integer categoryId, final Integer menuId)
    {
        final Query query = super.getCurrentSession().createQuery("from Menu m where m.menuId=:val1 and m.categoryId=:val2 " + "and m.product.active=true").setInteger("val1",
            menuId).setInteger("val2", categoryId);
        return query.list();
    }

    @Override
    public DailySales dailySalesForShopAndDate(final Integer shopId, final Calendar date)
    {
        return (DailySales) super.getCurrentSession().createCriteria(DailySales.class).add(Restrictions.eq("shopId", shopId)).add(Restrictions.eq("salesDate", date))
            .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Integer> distinctDeliveryCostsForCity(final Integer cityId)
    {
        final Query query = super.getCurrentSession().createQuery("select distinct(m.deliverCharge) from Shop m where m.city.id=:val1").setInteger("val1", cityId);
        return query.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ReportMailingList> distinctReports()
    {
        final Query query = super.getCurrentSession().createQuery("select distinct(reportName) from ReportMailingList");
        return query.list();
    }

    @Override
    public DailySales earliestDailySalesForShop(final Integer shopId)
    {
        final DetachedCriteria earliestDate = DetachedCriteria.forClass(DailySales.class).setProjection(Property.forName("salesDate").min()).add(Restrictions.eq("shopId", shopId));
        return (DailySales) super.getCurrentSession().createCriteria(DailySales.class).add(Property.forName("salesDate").eq(earliestDate)).add(Restrictions.eq("shopId", shopId))
            .uniqueResult();
    }

    @Override
    public DailySales earliestSubmittedDailySalesForShop(final Integer shopId)
    {
        final DetachedCriteria earliestDate = DetachedCriteria.forClass(DailySales.class).setProjection(Property.forName("salesDate").min()).add(Restrictions.eq("submitted",
            true)).add(Restrictions.eq("shopId", shopId));
        return (DailySales) super.getCurrentSession().createCriteria(DailySales.class).add(Property.forName("salesDate").eq(earliestDate)).add(Restrictions.eq("shopId", shopId))
            .uniqueResult();
    }

    @Override
    public Blacklist getBlacklisted(final String phoneNumber)
    {
        final Query query = super.getCurrentSession().createQuery("from Blacklist bl where bl.phoneNumber=:val1").setString("val1", phoneNumber);
        return (Blacklist) query.uniqueResult();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.pizza73.dao.LookupDao#getMenuItemForProduct(java.lang.Integer,
     * com.pizza73.model.Product)
     */
    @Override
    public Menu getMenuItemForProduct(final Integer menuId, final Product product, final ProductSize size)
    {
        final Query query = super.getCurrentSession().createQuery("from Menu m where m.menuId=:val1 and m.product.id=:val2" + " and m.size.id=:val3").setInteger("val1", menuId)
            .setInteger("val2", product.getId()).setInteger("val3", size.getId());
        return (Menu) query.uniqueResult();
    }

    @Override
    public Menu getMenuItemForProduct(final Integer menuId, final Integer productId, final Integer sizeId)
    {
        final Query query = super.getCurrentSession().createQuery("from Menu m where m.menuId=:val1 and m.product.id=:val2" + " and m.size.id=:val3").setInteger("val1", menuId)
            .setInteger("val2", productId).setInteger("val3", sizeId);
        return (Menu) query.uniqueResult();
    }

    @Override
    public Shop getShop(final String companyNo, final Integer branchNo)
    {
        final Query query = super.getCurrentSession().createQuery("from Shop m where m.companyNumber=:val1 and m.branch=:val2").setString("val1", companyNo).setInteger("val2",
            branchNo);
        return (Shop) query.uniqueResult();
    }

    @Override
    public Shop getShop(final String newPayrollCompanyNo, final String newPayrollDepartmentNo)
    {
        final Query query = super.getCurrentSession().createQuery("from Shop m where m.newPayrollCompanyNo=:val1 and m.newPayrollDepartmentNo=:val2").setString("val1",
            newPayrollCompanyNo).setString("val2", newPayrollDepartmentNo);
        return (Shop) query.uniqueResult();
    }

    @Override
    public PayYearPeriod getYearPeriodByYear(final Integer year)
    {
        return (PayYearPeriod) super.getCurrentSession().createCriteria(PayYearPeriod.class).add(Restrictions.eq("year", year)).uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Integer> GLaccountsForPeriodAndShop(final Integer year, final Integer period, final Integer shop_id)
    {
        final Query query = super.getCurrentSession().createQuery("select distinct account from GL g where (g.year=:Year and g.period=:Period and g.shop_id=:shopId) order by g" +
            ".account").setInteger("Year", year).setInteger("Period", period).setInteger("shopId", shop_id);
        return query.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Integer> GLaccountsForYearAndShop(final Integer year, final Integer shop_id)
    {
        final Query query = super.getCurrentSession().createQuery("select distinct account from GL g where (g.year=:Year and g.shop_id=:shopId) order by g.account").setInteger
            ("Year", year).setInteger("shopId", shop_id);
        return query.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<GL> GLRecordsForPeriodAndShop(final Integer year, final Integer period, final Integer shop_id)
    {
        return super.getCurrentSession().createCriteria(GL.class).add(Restrictions.eq("year", year)).add(Restrictions.eq("period", period)).add(Restrictions.eq("shop_id",
            shop_id)).list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<GL> GLRecordsForPeriodAndShop(final Integer year, final Integer period, final Integer account, final Integer shop_id)
    {
        return super.getCurrentSession().createCriteria(GL.class).add(Restrictions.eq("year", year)).add(Restrictions.eq("period", period)).add(Restrictions.eq("account",
            account)).add(Restrictions.eq("shop_id", shop_id)).list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<GL> GLRecordsForYearAndShop(final Integer year, final Integer account, final Integer shop_id)
    {
        return super.getCurrentSession().createCriteria(GL.class).add(Restrictions.eq("year", year)).add(Restrictions.eq("account", account)).add(Restrictions.eq("shop_id",
            shop_id)).addOrder(Order.asc("period")).list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Integer> GLshopsForYear(final Integer year)
    {
        final Query query = super.getCurrentSession().createQuery("select distinct shop_id from GL g where g.year=:Year order by shop_id").setInteger("Year", year);
        return query.list();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.pizza73.dao.LookupDao#infoValueForVariable(java.lang.String)
     */
    @Override
    public String infoValueForVariable(final String variable)
    {
        final Query query = super.getCurrentSession().createQuery("from Info m where m.variable=:val1").setString("val1", variable);
        final Info info = (Info) query.uniqueResult();
        return StringUtils.trimToNull(info.getValue());
    }

    @Override
    public DailySales latestSubmittedDailySalesForShop(final Integer shopId)
    {
        final DetachedCriteria earliestDate = DetachedCriteria.forClass(DailySales.class).setProjection(Property.forName("salesDate").max()).add(Restrictions.eq("submitted",
            true)).add(Restrictions.eq("shopId", shopId));
        return (DailySales) super.getCurrentSession().createCriteria(DailySales.class).add(Property.forName("salesDate").eq(earliestDate)).add(Restrictions.eq("shopId", shopId))
            .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ReportMailingList> mailingListForReport(final String report)
    {
        final Query query = super.getCurrentSession().createQuery("from ReportMailingList bl where bl.reportName=:val1").setString("val1", report);
        return query.list();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.pizza73.dao.LookupDao#menuItem(java.lang.Integer)
     */
    @Override
    public Menu menuItem(final Integer menuId)
    {
        final Menu menuItem = (Menu) getCurrentSession().get(Menu.class, menuId);

        final Product p = menuItem.getProduct();
        Hibernate.initialize(p);
        Hibernate.initialize(p.getDetails());
        Hibernate.initialize(p.getCompositions());
        Hibernate.initialize(p.getProperties());

        Hibernate.initialize(menuItem.getSize());
        menuItem.getSize().getId();

        return menuItem;
    }

    @Override
    public Menu menuItem(final Integer menuId, final Integer productId, final Integer sizeId)
    {
        final Criteria criteria = super.getCurrentSession().createCriteria(Menu.class);

        criteria.add(Restrictions.eq("menuId", menuId));
        criteria.add(Restrictions.eq("product.id", productId));
        criteria.add(Restrictions.eq("size.id", sizeId));

        final Menu menu = (Menu) criteria.uniqueResult();

        if(menu != null)
        {
            Hibernate.initialize(menu.getProduct());

            Hibernate.initialize(menu.getSize());
        }

        return menu;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Menu> menuItemsForCategories(final Integer menuId, final Integer[] categories)
    {
        final Query query = super.getCurrentSession().createQuery("from Menu m where m.product.active=true and m.menuId=:mId and m.categoryId in (:catId) order by m" +
            ".displayHint").setInteger("mId", menuId).setParameterList("catId", categories);

        return query.list();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.pizza73.dao.LookupDao#menuItemsForCategories(java.lang.String,
     * java.lang.Integer[])
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Menu> menuItemsForCategories(final Integer menuId, final Integer[] categories, final Integer sizeId)
    {
        final Query query = super.getCurrentSession().createQuery("from Menu m where m.product.active=true and m.menuId=:mId and " + "m.size.id=:sizeId and m.categoryId in " +
            "(:catId) order by m.displayHint").setInteger("mId", menuId).setInteger("sizeId", sizeId).setParameterList("catId", categories);

        return query.list();
    }

    @SuppressWarnings("unchecked")
    public List<Menu> menuItemsForCategory(final Integer categoryId, final Integer menuId)
    {
        final Query query = super.getCurrentSession().createQuery("from Menu m where m.product.active=true and m.menuId=:mId and m.displayMenu=true and " + "m.categoryId=:catId " +
            "order by m.categoryId, m.displayHint, m.size.id").setInteger("mId", menuId).setInteger("catId", categoryId);
        return query.list();
    }

    @SuppressWarnings("unchecked")
    public List<Menu> menuItemsForCategory(final Integer categoryId, final Integer menuId, boolean displayMenu)
    {
        final Query query = super.getCurrentSession().createQuery("from Menu m where m.product.active=true and m.menuId=:mId and m.displayMenu=:displayMenu and " + "m" +
            ".categoryId=:catId order by m.categoryId, m.displayHint, m.size.id").setInteger("mId", menuId).setInteger("catId", categoryId).setBoolean("displayMenu", displayMenu);
        return query.list();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.pizza73.dao.LookupDao#menuItemsForProduct(java.lang.Integer,
     * java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    public List<Menu> menuItemsForProduct(final Integer productId, final Integer menuId)
    {
        final Query query = super.getCurrentSession().createQuery("from Menu m where m.menuId=:value1 and m.product.id=:value2").setInteger("value1", menuId).setInteger
            ("value2", productId);
        return query.list();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.pizza73.dao.LookupDao#menuItemsForProducts(java.lang.Integer,
     * java.lang.Integer[])
     */
    @SuppressWarnings("unchecked")
    public List<Menu> menuItemsForProducts(final Integer menuId, final Integer[] productIds)
    {
        final Query query = super.getCurrentSession().createQuery("from Menu m where m.menuId=:value1 and m.product.id in(:value2)").setInteger("value1", menuId)
            .setParameterList("value2", productIds);
        final List<Menu> menuItems = query.list();
        ;
        final Iterator<Menu> iter = menuItems.iterator();
        while (iter.hasNext())
        {
            final Menu menu = iter.next();
            Hibernate.initialize(menu.getSize());
            Hibernate.initialize(menu.getProduct());
        }

        return menuItems;
    }

    public List<Menu> menuItemsForProducts(final Integer menuId, final Integer[] productIds, Integer sizeId)
    {
        final Query query = super.getCurrentSession().createQuery("from Menu m where m.menuId=:value1 and m.product.id in(:value2) and m.sizeId=:value3").setInteger("value1",
            menuId).setParameterList("value2", productIds).setParameter("value3", sizeId);
        final List<Menu> menuItems = query.list();
        ;
        final Iterator<Menu> iter = menuItems.iterator();
        while (iter.hasNext())
        {
            final Menu menu = iter.next();
            Hibernate.initialize(menu.getSize());
            Hibernate.initialize(menu.getProduct());
        }

        return menuItems;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.pizza73.dao.LookupDao#menuItemsForSizeList(java.lang.Integer,
     * java.lang.Integer, java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    public List<Menu> menuItemsForSizeList(final Integer menuId, final Integer categoryId, final Integer sizeId)
    {
        final Query query = super.getCurrentSession().createQuery("from Menu m where m.menuId=:val1 and m.categoryId=:val2 and " + "m.size.id=:val3 and m.product.active=true " +
            "order by m.displayHint").setInteger("val1", menuId).setInteger("val2", categoryId).setInteger("val3", sizeId);
        return query.list();
    }

    @SuppressWarnings("unchecked")
    public List<Municipality> municipalities()
    {
        final Query query = super.getCurrentSession().createQuery("from Municipality m where m.displayWeb=true order by displayHint");
        return query.list();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.pizza73.dao.LookupDao#orderItemsForParent(java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    public List<OrderItem> orderItemsForParent(final Integer orderId, final Integer itemId)
    {
        final Query query = super.getCurrentSession().createQuery("from OrderItem m where m.orderId=:val1 and m.parentId=:val2").setInteger("val1", orderId).setInteger("val2",
            itemId);
        return query.list();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.pizza73.dao.LookupDao#parentOrderItemsForOrder(java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    public List<OrderItem> parentOrderItemsForOrder(final Integer orderId)
    {
        final Query query = super.getCurrentSession().createQuery("from OrderItem m where m.orderId=:val1 and m.parentId=0").setInteger("val1", orderId);
        return query.list();
    }

    @SuppressWarnings("unchecked")
    public List<com.pizza73.model.Order> parkedOrdersForBusinessDate()
    {
        final Query query = super.getCurrentSession().createQuery("from Order o where o.businessDate =:day and o.status = 'r' order by o.id desc");
        query.setCalendar("day", businessDate());
        return query.list();
    }

    // THEORETICAL FOOD COST
    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> prepInvItemDetails()
    {
        final String queryString = "select prep_inv_item_id, raw_inv_item_id, raw_portions from prep_inv_item_detail_current order by prep_inv_item_id";
        final Query query = super.getCurrentSession().createSQLQuery(queryString);
        return query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> prepInvItems()
    {
        final String queryString = "select * from prep_inv_item_current order by id";
        final Query query = super.getCurrentSession().createSQLQuery(queryString);
        return query.list();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.pizza73.dao.LookupDao#priceForSize(java.lang.Integer)
     *
     * @TODO - this method needs to be corrected. It is used to get a unique
     * price for pop based on size.
     */
    public Integer priceForSize(final Integer categoryId, final Integer sizeId)
    {
        final Query query = super.getCurrentSession().createQuery("select distinct(m.price) from Menu m where m.categoryId=:val1 " + "and m.size.id=:val2").setInteger("val1",
            categoryId).setInteger("val2", sizeId);
        return (Integer) query.uniqueResult();
    }

    public Product productById(final Integer id)
    {
        final Query query = super.getCurrentSession().createQuery("from Product p where p.id=:value").setInteger("value", id);
        final Product p = (Product) query.uniqueResult();

        return p;
    }

    public Product productById(final String id)
    {
        return productById(Integer.valueOf(id));
    }

    /*
     * (non-Javadoc)
     *
     * @see com.pizza73.dao.LookupDao#productByName(java.lang.String)
     */
    public Product productByShortName(final String name)
    {
        final Query query = super.getCurrentSession().createQuery("from Product p where p.name=:value").setString("value", name);
        return (Product) query.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> productCompositionPrepData()
    {
        final String queryString = "select pc.product_id, pc.description, pc.product_modifier_1, pc.product_modifier_2, pc.product_modifier_3, " + "pcd.prep_inv_item_id, pcd" +
            ".prep_portions " + "from product_composition_current pc, product_composition_detail_current pcd " + "where pc.id = pcd.product_composition_id " + "order by pc" +
            ".product_id, pc.product_modifier_1, pc.product_modifier_2, pc.product_modifier_3";
        final Query query = super.getCurrentSession().createSQLQuery(queryString);
        return query.list();
    }

    @SuppressWarnings("unchecked")
    public List<Product> productsByCategory(final Integer categoryId)
    {
        final Query query = super.getCurrentSession().createQuery("from Product m where m.category.id=:val1").setInteger("val1", categoryId);
        return query.list();
    }

    @SuppressWarnings("unchecked")
    public List<String> provinces()
    {
        final Query query = super.getCurrentSession().createQuery("select distinct(m.province) from Municipality m");
        return query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> rawInvItemUnitCost()
    {
        final String queryString = "select id, unit_cost from raw_inv_item_current order by id";
        final Query query = super.getCurrentSession().createSQLQuery(queryString);
        return query.list();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.pizza73.dao.LookupDao#roleByName(java.lang.String)
     */
    public Role roleByName(final String onlineCustomerRole)
    {
        final Query query = super.getCurrentSession().createQuery("from Role r where r.name=:value").setString("value", onlineCustomerRole);
        return (Role) query.uniqueResult();
    }

    @Override
    public Integer shopForPostalCode(final String postalCode)
    {
        final String update = "select shop_id from iq2_street_address where postal_code =:postalCode";
        final Query q = super.getCurrentSession().createSQLQuery(update);
        q.setParameter("postalCode", postalCode);
        return (Integer) q.uniqueResult();
    }

    public Shop shopForShortName(final String shopName)
    {
        final Query query = super.getCurrentSession().createQuery("from Shop m where m.shortName=:val1").setString("val1", shopName);
        return (Shop) query.uniqueResult();
    }

    public MunicipalityBusinessHours shopHourForMunicipalityAndDay(final Integer municipality_id, final Integer day)
    {
        return (MunicipalityBusinessHours) super.getCurrentSession().createCriteria(MunicipalityBusinessHours.class).add(Restrictions.eq("municipality.id", municipality_id)).add
            (Restrictions.eq("dayOfWeek", day)).uniqueResult();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.pizza73.dao.LookupDao#shopOrderSequence()
     */
    public Integer shopOrderSequence(final Integer shopId)
    {
        final Query query = super.getCurrentSession().createSQLQuery("select order_cnt_sequence('" + shopId + "')");
        final BigInteger sequence = (BigInteger) query.list().get(0);
        return Integer.valueOf(sequence.intValue());
    }

    @Override
    public int updatePostalCode(final String postalCode, final Integer shopId)
    {
        final String update = "update iq2_street_address set shop_id =:shopId where postal_code = :postalCode";
        final Query q = super.getCurrentSession().createSQLQuery(update);
        q.setParameter("shopId", shopId);
        q.setParameter("postalCode", postalCode);
        return q.executeUpdate();
    }

    public WeeklySales weeklySalesForShopAndWeek(final Integer shopId, final Calendar sundayOfWeek)
    {
        return (WeeklySales) super.getCurrentSession().createCriteria(WeeklySales.class).add(Restrictions.eq("shopId", shopId)).add(Restrictions.eq("sundayOfSalesWeek",
            sundayOfWeek)).uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> weeklySalesForShopStartingOn(final int shopId, final Calendar startDate)
    {
        final Calendar endDate = (Calendar) startDate.clone();
        endDate.add(Calendar.DATE, 6);
        final int startMonth = startDate.get(Calendar.MONTH) + 1;
        final int endMonth = endDate.get(Calendar.MONTH) + 1;
        final String sDate = "'" + startDate.get(Calendar.YEAR) + "-" + startMonth + "-" + startDate.get(Calendar.DATE) + "'";
        final String eDate = "'" + endDate.get(Calendar.YEAR) + "-" + endMonth + "-" + endDate.get(Calendar.DATE) + "'";
        System.out.println(sDate);
        System.out.println(eDate);
        final String queryString = "select s.sent_shop_id as shopId, p.group_id as prodId, " + "p.size as sizeId, p.crust as crustId, p.sauce as sauceId, sum(d.quantity) as " +
            "quantity " + "from iq_order_final_status s, orders o, orders_detail d, iq_product p " + "where s.order_id = o.id " + "and s.sent_shop_id =:shopId " + "and o.id = d" +
            ".order_id " + "and d.product_id = p.id " + "and s.final_confirmed_date >=:startDate " + "and s.final_confirmed_date <=:endDate " + "and o.status <> 'x' " + "group " +
            "by shopId, prodId, sizeId, crustId, sauceId " + "order by shopId, prodId, sizeId, crustId, sauceId";
        final Query query = super.getCurrentSession().createSQLQuery(queryString).addScalar("shopId", Hibernate.INTEGER).addScalar("prodId", Hibernate.INTEGER).addScalar
            ("sizeId", Hibernate.INTEGER).addScalar("crustId", Hibernate.INTEGER).addScalar("sauceId", Hibernate.INTEGER).addScalar("quantity", Hibernate.INTEGER);
        query.setParameter("shopId", shopId);
        query.setParameter("startDate", sDate);
        query.setParameter("endDate", eDate);

        return query.list();
    }

    @SuppressWarnings("unchecked")
    public List<GL> yearToDateEarningsForPeriodAndShop(final Integer year, final Integer period, final Integer shop_id)
    {
        return super.getCurrentSession().createCriteria(GL.class).add(Restrictions.eq("year", year)).add(Restrictions.eq("period", period)).add(Restrictions.ge("account", 3000))
            .add(Restrictions.lt("account", 4000)).add(Restrictions.eq("shop_id", shop_id)).list();

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ProductDetailOption> productDetailOptionsForDetail(Integer productDetailId)
    {
        final Query query = super.getCurrentSession().createQuery("from ProductDetailOption m where m.productDetailId=:detailId").setInteger("detailId", productDetailId);
        return query.list();
    }

    // IPHONE VERSIONS
    @Override
    public Integer iPhoneVersionForCities()
    {
        return this.iPhoneVersionFor(CITIES);
    }

    @Override
    public Integer iPhoneVersionForMenu()
    {
        return this.iPhoneVersionFor(MENU);
    }

    @Override
    public Integer iPhoneVersionForSpecials()
    {
        return this.iPhoneVersionFor(SPECIALS);
    }

    @Override
    public Integer iPhoneVersionForApp()
    {
        return this.iPhoneVersionFor(IPHONE_APP);
    }

    public Integer iPhoneVersionFor(String name)
    {
        final String update = "select version from iq2_iphone_menu_version where name =:name";
        final Query q = super.getCurrentSession().createSQLQuery(update);
        q.setParameter("name", name);
        return (Integer) q.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ProductOutage> findAllProductOutages()
    {
        final Query query = super.getCurrentSession().createQuery("from ProductOutage po");
        return query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Product> findActiveProducts()
    {
        final Query query = super.getCurrentSession().createQuery("from Product p where p.active=true");
        return query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Product> findDistinctProductsForOutages()
    {
        final Query query = super.getCurrentSession().createSQLQuery("select distinct (product_id) from product_outage");
        List<Integer> ids = query.list();

        final Query query2 = super.getCurrentSession().createQuery("from Product p where p.id in (:ids)");
        query2.setParameterList("ids", ids);
        return query2.list();
    }

    @Override
    public void deleteProductOutageForProduct(Integer productId)
    {
        Query query = super.getCurrentSession().createSQLQuery("delete from product_outage where product_id = :productId");
        query.setParameter("productId", productId);

        query.executeUpdate();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Integer> productOutagesForShop(Integer shopId)
    {
        final Query query = super.getCurrentSession().createSQLQuery("select distinct (product_id) from product_outage where shop_id =:shopId");
        query.setParameter("shopId", shopId);
        return query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Shop> shopsWithOutage(Integer productId)
    {
        List<Shop> result = new ArrayList<Shop>();
        final Query query = super.getCurrentSession().createSQLQuery("select shop_id from product_outage where product_id =:productId");
        query.setParameter("productId", productId);
        List<Integer> ids = query.list();

        if(!ids.isEmpty())
        {
            final Query query2 = super.getCurrentSession().createQuery("from Shop p where p.id in (:ids)");
            query2.setParameterList("ids", ids);
            result = query2.list();
        }

        return result;
    }

    @Override
    public void deleteProductOutageForProductAndShop(Integer productId, Integer shopId)
    {
        Query query = super.getCurrentSession().createSQLQuery("delete from product_outage where product_id = :productId and shop_id =:shopId");
        query.setParameter("productId", productId);
        query.setParameter("shopId", shopId);

        query.executeUpdate();
    }
}