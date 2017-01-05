package com.pizza73.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.pizza73.model.Menu;
import com.pizza73.model.Municipality;
import com.pizza73.model.Product;
import com.pizza73.model.Role;
import com.pizza73.model.Shop;
import com.pizza73.model.enums.ProductCategoryEnum;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(locations = 
   {"/test-applicationContext-resources.xml",
    "/applicationContext.xml"})
public class LookupDaoTest extends BaseDaoTest
{
   @Test
   public void testProductByShortName()
   {
      Product tempProductVar = this.getSampleProduct();
      clearHibernateFirstLevelCache();
      Product retrivedProduct = 
         this.lookupDao.productByShortName(tempProductVar.getName());
      
      assertTrue(this.objectMatch(tempProductVar, retrivedProduct));
      assertNotSame(tempProductVar, retrivedProduct);
   }

   @Test
   public void testProductByIdWithInteger()
   {
      Product tempProductVar = this.getSampleProduct();
      clearHibernateFirstLevelCache();
      Product retrivedProduct = this.lookupDao.productById(tempProductVar.getId());
      
      assertTrue(this.objectMatch(tempProductVar, retrivedProduct));
      assertNotSame(tempProductVar, retrivedProduct);
   }

   @Test
   public void testProductByIdWithString()
   {
      Product tempProductVar = this.getSampleProduct();
      clearHibernateFirstLevelCache();
      Product retrivedProduct = 
         this.lookupDao.productById(tempProductVar.getId().toString());
      
      assertTrue(this.objectMatch(tempProductVar, retrivedProduct));
      assertNotSame(tempProductVar, retrivedProduct);
   }

   @Test
   public void testRoleByName()
   {
      Role tempRoleVar = this.getSampleRole();
      clearHibernateFirstLevelCache();
      Role retrivedRole = this.lookupDao.roleByName(tempRoleVar.getName());
      
      assertTrue(this.objectMatch(tempRoleVar, retrivedRole));
      assertNotSame(tempRoleVar, retrivedRole);
   }

   @Test
   public void testMenuItemsForCategory()
   {
      Menu tempMenuItem = this.getSampleMenuItem();
      List<Menu> retrievedMenuItems = 
         this.lookupDao.menuItemsForCategory(
            tempMenuItem.getCategoryId(), tempMenuItem.getMenuId());
      assertTrue(retrievedMenuItems.contains(tempMenuItem));
   }

   @Test
   public void testMenuItemWithMenuId_ProductId_SizeId()
   {
      Menu tempMenuItem = this.getSampleMenuItem();
      clearHibernateFirstLevelCache();
      Menu retrivedMenuItem = 
         this.lookupDao.menuItem(tempMenuItem.getMenuId(),
            tempMenuItem.getProduct().getId(), tempMenuItem.getSize().getId());
      
      assertTrue(this.objectMatch(tempMenuItem, retrivedMenuItem));
      assertNotSame(tempMenuItem, retrivedMenuItem);
   }

   @Test
   public void testMenuItemWithMenuId()
   {
      Menu tempMenuItem = this.getSampleMenuItem();
      clearHibernateFirstLevelCache();
      Menu retrivedMenuItem = this.lookupDao.menuItem(tempMenuItem.getMenuId());
      
      assertTrue(this.objectMatch(tempMenuItem, retrivedMenuItem));
      assertNotSame(tempMenuItem, retrivedMenuItem);
   }

   @Test
   public void testMenuItemsForCategories()
   {
      Menu tempMenuItem = this.getSampleMenuItem();
      Integer[] categories = 
         {tempMenuItem.getCategoryId(),tempMenuItem.getCategoryId() + 1};
      List<Menu> retrievedMenuItems = 
         this.lookupDao.menuItemsForCategories(
            tempMenuItem.getMenuId(), categories, tempMenuItem.getSize().getId());
      assertTrue(retrievedMenuItems.contains(tempMenuItem));
   }

   @Test
   public void testMenuItemsForProduct()
   {
      Menu tempMenuItem = this.getSampleMenuItem();
      Product tempProduct = this.getSampleProduct();
      List<Menu> retrievedMenuItems = 
         this.lookupDao.menuItemsForProduct(tempProduct.getId(), tempMenuItem.getId());
      assertTrue(retrievedMenuItems.contains(tempMenuItem));
   }

   @Test
   public void testMenuItemsForSizeList()
   {
      Menu tempMenuItem = this.getSampleMenuItem();
      List<Menu> retrievedMenuItems = 
         this.lookupDao.menuItemsForSizeList(
            tempMenuItem.getId(), tempMenuItem.getCategoryId(), 
            tempMenuItem.getSize().getId());
      assertTrue(retrievedMenuItems.contains(tempMenuItem));
   }

   @Test
   public void testShopsForMunicipality()
   {
      Shop tempShop = this.getSampleShop();
      List<Shop> retrivedShopList = 
         this.lookupDao.activeShopsForMunicipality(tempShop.getCity().getId());
      assertTrue(retrivedShopList.contains(tempShop));
   }

   @Test
   public void testMunicipalities()
   {
      Shop tempShop = this.getSampleShop();
      List<Municipality> retrivedCityList = this.lookupDao.municipalities();
      assertTrue(retrivedCityList.contains(tempShop.getCity()));
   }

   // FIXME Incorrect result size: expected 1, actual 4
   // see LookupDaoHibernate.priceForSize
   @Test
   public void testPriceForSize()
   {
      Menu tempMenuItem = this.getSamplePopMenuItem();
      assertEquals(tempMenuItem.getPrice(), this.lookupDao.priceForSize(
         ProductCategoryEnum.POP.getId(), 1));
   }

   // TODO construct a complex order
   @Test
   public void testParentOrderItemsForOrder()
   {

   }

   // TODO construct a complex order
   @Test
   public void testOrderItemsForParent()
   {

   }

   @Test
   public void testMenuItemsForProducts()
   {
      Menu tempMenuItem = this.getSampleMenuItem();
      Product tempProduct = this.getSampleProduct();
      Integer[] ProductIdList = { tempProduct.getId() };
      List<Menu> retrievedMenuItems = 
         this.lookupDao.menuItemsForProducts(tempProduct.getId(), ProductIdList);
      assertTrue(retrievedMenuItems.contains(tempMenuItem));
   }

   @Test
   public void testGetMenuItemForProduct()
   {
      Menu tempMenuItem = this.getSampleMenuItem();
      clearHibernateFirstLevelCache();
      Menu retrivedMenuItem = 
         this.lookupDao.getMenuItemForProduct(
            tempMenuItem.getId(), tempMenuItem.getProduct(), tempMenuItem.getSize());
      
      assertTrue(this.objectMatch(tempMenuItem, retrivedMenuItem));
      assertNotSame(tempMenuItem, retrivedMenuItem);
   }

   @Test
   public void testCurrentPromoMenuItems()
   {
      Menu tempMenuItem = this.getSamplePromoMenuItem();
      List<Menu> retrievedMenuItems = 
         this.lookupDao.currentPromoMenuItems(
            tempMenuItem.getCategoryId(), tempMenuItem.getId());
      assertTrue(retrievedMenuItems.contains(tempMenuItem));
   }

   @Test
   public void testShopForShortName()
   {
      Shop tempShop = this.getSampleShop();
      Shop retrivedShop = this.lookupDao.shopForShortName(tempShop.getShortName());
      assertTrue(this.objectMatch(tempShop, retrivedShop));

   }

   @Test
   public void testGetDistinctDeliveryChargeForCity()
   {
      List<Integer> deliveryCharges = this.lookupDao.distinctDeliveryCostsForCity(1);
      assertTrue(deliveryCharges.size() == 1);
   }
   
   @Test
   public void testActiveShops()
   {
      Shop tempShop = this.getActiveSampleShop();
      List<Shop> retrivedShopList = this.lookupDao.activeShops();
      assertTrue(retrivedShopList.contains(tempShop));
   }

   @Test
   public void testGetShop()
   {
      Shop tempShop = this.getSampleShop();
      Shop retrivedShop = 
         this.lookupDao.getShop(tempShop.getCompanyNumber(),tempShop.getBranch());
      assertTrue(this.objectMatch(tempShop, retrivedShop));
   }

   @Test
   public void testProductsByCategory()
   {
      Product tempProductVar = this.getSampleProduct();
      List<Product> retrivedProductList = 
         this.lookupDao.productsByCategory(tempProductVar.getCategory().getId());
      assertTrue(retrivedProductList.contains(tempProductVar));
   }

   @Override
   @Test
   public void testGet()
   {
      Product tempProductVar = this.getSampleProduct();
      Product retrivedProduct = 
         (Product)this.lookupDao.get(Product.class,tempProductVar.getId());
      assertTrue(this.objectMatch(tempProductVar, retrivedProduct));
   }

   @Override
   @SuppressWarnings("unchecked")
   @Test
   public void testGetAll()
   {
      Product tempProductVar = this.getSampleProduct();
      List<Product> retrivedProductList = 
         (List<Product>) this.lookupDao.getAll(Product.class);
      assertTrue(retrivedProductList.contains(tempProductVar));
   }

   // No need to implement
   @Override
   @Test
   public void testRemove()
   {}

   // No need to implement
   @Override
   @Test
   public void testSave()
   {}

   // No need to implement
   @Override
   @Test
   public void testExists()
   {}
   
   private Product getSampleProduct()
   {
      return this.lookupDao.productById(1);
   }

   private boolean objectMatch(Product p1, Product p2)
   {
      if (p1.getId().equals(p2.getId()))
      {
         return true;
      }
      else
      {
         return false;
      }
   }

   private Role getSampleRole()
   {
      return this.lookupDao.roleByName("ONLINE_CUSTOMER");
   }

   private boolean objectMatch(Role r1, Role r2)
   {
      if (r1.getId().equals(r2.getId()))
      {
         return true;
      }
      else
      {
         return false;
      }
   }

   private Menu getSampleMenuItem()
   {
      return this.lookupDao.menuItem(1);
   }
   
   private Menu getSamplePopMenuItem()
   {
      return this.lookupDao.menuItem(1,19,1);
   }

   private Menu getSamplePromoMenuItem()
   {
      return this.lookupDao.menuItem(1);
   }

   private boolean objectMatch(Menu m1, Menu m2)
   {
      if (m1.getId().equals(m2.getId()))
      {
         return true;
      }
      else
      {
         return false;
      }
   }

   private Shop getSampleShop()
   {
      return this.lookupDao.getShop("C0SH", 1);
   }

   private Shop getActiveSampleShop()
   {
      return this.lookupDao.getShop("C0SH", 1);
   }

   private boolean objectMatch(Shop s1, Shop s2)
   {
      if (s1.getId().equals(s2.getId()))
      {
         return true;
      }
      else
      {
         return false;
      }
   }
}
