package com.pizza73.service.impl;

import com.pizza73.model.Cart;
import com.pizza73.model.Menu;
import com.pizza73.model.OrderItem;
import com.pizza73.model.Product;
import com.pizza73.model.ProductCategory;
import com.pizza73.model.ProductComposition;
import com.pizza73.model.ProductConstants;
import com.pizza73.model.ProductSize;
import com.pizza73.model.enums.ProductCategoryEnum;
import com.pizza73.model.enums.ProductEnum;
import com.pizza73.model.enums.SpecialEnum;
import com.pizza73.service.LookupManager;
import com.pizza73.service.PricingManager;
import com.pizza73.util.ProductUtil;
import com.pizza73.util.SpecialsUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.pizza73.model.enums.ProductCategoryEnum.PIZZA;
import static com.pizza73.model.enums.ProductCategoryEnum.POP;
import static com.pizza73.model.enums.ProductCategoryEnum.WING_MEAL;
import static com.pizza73.model.enums.ProductEnum.DORITOS;
import static com.pizza73.model.enums.ProductEnum.LAYS;

/**
 * PricingManagerImpl.java
 *
 * @author chris 11-Oct-06
 * @Copyright Flying Pizza 73
 */
@Service("pricingManager")
public class PricingManagerImpl implements PricingManager
{
    // @SuppressWarnings("unused")
    private static final Logger log = Logger.getLogger(PricingManagerImpl.class);

    private static final Integer POP_CAN_SIZE_ID = Integer.valueOf(1);

    private static final Integer POP_SIX_PACK_SIZE_ID = Integer.valueOf(2);

    private static final Integer POP_TWO_LITER_SIZE_ID = Integer.valueOf(3);

    private static final Integer POP_CATEGRY_ID = Integer.valueOf(10);

    private static final String SMALL_PIZZA = "smallPizza";

    private static final String MEDIUM_PIZZA = "mediumPizza";

    private static final String LARGE_PIZZA = "largPizza";

    private static final String SMALL_MEAL = "smallMeal";

    private static final String MEDIUM_MEAL = "mediumMeal";

    private static final String LARGE_MEAL = "largeMeal";

    private static final Integer TWO_TOPPER_ID = Integer.valueOf(1);

    private static final Integer TWO_LITER_POP_DISCOUNT = Integer.valueOf(-100);

    private static final int SMALL_NSB_DIFFERENTIAL = 145;

    private static final int SMALL_TOPPING_COST = 150;

    private static final int MEDIUM_NSB_DIFFERENTIAL = 250;

    private static final int MEDIUM_TOPPING_COST = 250;

    private static final int LARGE_NSB_DIFFERENTIAL = 275;

    private static final int LARGE_TOPPING_COST = 275;

    private static final int OLD_DIP_COST = 69;

    private LookupManager lookupMgr;

    public PricingManagerImpl()
    {
    }

    @Override
    public void calculateCost(final Cart cart)
    {
        cart.setTotalPrice(getTotalCost(cart));

        final Set<OrderItem> pops = cart.itemsForCategory(ProductCategoryEnum.POP);
        final Set<OrderItem> otherDrinks = cart.itemsForCategory(ProductCategoryEnum.OTHER_DRINK);
        pops.addAll(otherDrinks);

        Set<OrderItem> extraBeverages = cart.itemsForCategory(ProductCategoryEnum.EXTRA);
        for(OrderItem temp : extraBeverages)
        {
            Integer productId = temp.getProduct().getId();
            if(ProductEnum.LITRE_COKE.isEqualToProductId(productId) || ProductEnum.LITRE_DIET_COKE.isEqualToProductId(productId))
            {
                pops.add(temp);
            }
        }
        cart.setEnviroLevy(getEnviroLevy(pops));
        cart.setDeposit(getDeposit(pops, cart));
    }

    @Override
    public int getFreeOptionCount(final OrderItem item, final String optionType)
    {
        final Product p = item.getProduct();
        final ProductSize size = item.getSize();

        ProductComposition composition = null;
        for(ProductComposition tempComp : p.compositionsForSize(size.getId()))
        {
            if(tempComp.getSubProperty().equalsIgnoreCase(optionType))
            {
                composition = tempComp;
                break;
            }
        }

        if(composition != null)
        {
            return composition.getNumFree();
        }

        return 0;
    }

    private Integer everyDayDealCost(final Set<OrderItem> meals, final Set<OrderItem> pizzas, final Cart cart)
    {
        Integer cost = Integer.valueOf(0);
        if(!pizzas.isEmpty())
        {
            final Map<String, Set<OrderItem>> sizes = productsForSize(meals, pizzas);

            int mealCount = sizes.get(SMALL_MEAL).size();
            cost += pizzaCostForSize(sizes.get(SMALL_PIZZA), mealCount, cart, 1);
            cost += wingMealAdditionalCost(sizes.get(SMALL_MEAL));

            mealCount = sizes.get(MEDIUM_MEAL).size();
            cost += pizzaCostForSize(sizes.get(MEDIUM_PIZZA), mealCount, cart, 2);
            cost += wingMealAdditionalCost(sizes.get(MEDIUM_MEAL));

            mealCount = sizes.get(LARGE_MEAL).size();
            cost += pizzaCostForSize(sizes.get(LARGE_PIZZA), mealCount, cart, 3);
            cost += wingMealAdditionalCost(sizes.get(LARGE_MEAL));
            cost += wingMealExtraDipCost(meals);
        }

        return cost;
    }

    public Integer getDeposit(final Set<OrderItem> pops, final Cart cart)
    {
        Integer cost = Integer.valueOf(0);
        if(!pops.isEmpty())
        {
            for(OrderItem pop : pops)
            {
                cost += pop.getQuantity() * pop.getDeposit();
            }
        }

        if(!cart.isEmpty())
        {
            final Map<ProductCategoryEnum, Set<OrderItem>> pricingMap = cart.pricingMap();
            final Set<OrderItem> promos = pricingMap.get(ProductCategoryEnum.PROMO);
            for(OrderItem item : promos)
            {
                Integer productId = item.getProduct().getId();
                if(isMegaMeal(productId))
                {
                    int pops_count = 0;
                    for(OrderItem megaMealDetailItem : item.getChildren())
                    {
                        Integer categoryId = megaMealDetailItem.getProduct().getCategory().getId();
                        if(POP.isEqualToCategoryId(categoryId))
                        {
                            pops_count += megaMealDetailItem.getQuantity();
                        }
                    }
                    if(pops_count < 4)
                    {
                        cost += Integer.valueOf(10) * (4 - pops_count);
                    }
                }
            }
        }

        return cost;
    }

    public Integer getEnviroLevy(final Set<OrderItem> pops)
    {
        Integer cost = Integer.valueOf(0);
        if(!pops.isEmpty())
        {
            for(OrderItem pop : pops)
            {
                cost += pop.getQuantity() * pop.getEnviroLevy();
            }
        }

        return cost;
    }

    private Integer getExtraDipCount(final Set<OrderItem> items)
    {
        Integer extra = 0;
        for(OrderItem item : items)
        {
            int freeDips = getFreeOptionCount(item, ProductCategoryEnum.WING_DIP.getName());
            for(OrderItem child : item.getChildren())
            {
                int quantity = child.getQuantity();
                final ProductCategory pc = child.getProduct().getCategory();
                if(pc.getName().equalsIgnoreCase(ProductCategoryEnum.WING_DIP.getName()))
                {
                    if(freeDips > 0)
                    {
                        if(freeDips >= quantity)
                        {
                            freeDips -= quantity;
                            quantity = 0;
                        }
                        else
                        {
                            quantity -= freeDips;
                            freeDips = 0;
                        }
                    }
                    extra += quantity;
                }
            }
        }

        return extra;
    }

    private Integer getItemCost(final OrderItem item, Cart cart)
    {
        Integer cost = (item.getPrice() + item.getPriceModifier()) * item.getQuantity();

        int freeToppings = getFreeOptionCount(item, ProductCategoryEnum.TOPPING.getName());
        int freeDips = getFreeOptionCount(item, ProductCategoryEnum.WING_DIP.getName());
        for(OrderItem child : item.getChildren())
        {
            int quantity = child.getQuantity();
            final ProductCategory pc = child.getProduct().getCategory();
            Integer price = child.getPrice();
            if(pc.getName().equalsIgnoreCase(ProductCategoryEnum.TOPPING.getName()))
            {
                // CHECK TO SEE IF EXTRA SAUCE
                if(!child.getProduct().getId().equals(82))
                {
                    if(freeToppings > 0)
                    {
                        if(freeToppings >= quantity)
                        {
                            freeToppings -= quantity;
                            quantity = 0;
                        }
                        else
                        {
                            quantity -= freeToppings;
                            freeToppings = 0;
                        }
                    }
                }
            }
            if(pc.getName().equalsIgnoreCase(ProductCategoryEnum.WING_DIP.getName()))
            {
                if(freeDips > 0)
                {
                    if(freeDips >= quantity)
                    {
                        freeDips -= quantity;
                        quantity = 0;
                    }
                    else
                    {
                        quantity -= freeDips;
                        freeDips = 0;
                    }
                }
            }

            cost += price * quantity;
        }

        return cost;
    }

    public Integer getTotalCost(final Cart cart)
    {
        Integer cost = Integer.valueOf(0);

        if(!cart.isEmpty())
        {
            final Map<ProductCategoryEnum, Set<OrderItem>> pricingMap = cart.pricingMap();
            final Set<OrderItem> cansOfPop = pricingMap.get(ProductCategoryEnum.POP);
            final Set<OrderItem> meals = pricingMap.get(ProductCategoryEnum.WING_MEAL);
            final Set<OrderItem> pizzas = pricingMap.get(ProductCategoryEnum.PIZZA);
            final Set<OrderItem> theRest = pricingMap.get(ProductCategoryEnum.EXTRA);
            final Set<OrderItem> promos = pricingMap.get(ProductCategoryEnum.PROMO);
            final Set<OrderItem> specials = pricingMap.get(ProductCategoryEnum.SPECIAL);

            cost += everyDayDealCost(meals, pizzas, cart);
            Integer extraDipCount = 0;
            extraDipCount += getExtraDipCount(meals);
            extraDipCount += getExtraDipCount(theRest);

            for(OrderItem item : theRest)
            {
                if(item.getChildren().isEmpty())
                {
                    int itemQuantity = item.getQuantity();
                    int price = item.getPrice();

                    cost += (price + item.getPriceModifier()) * itemQuantity;
                }
                else
                {
                    cost += getItemCost(item, cart);
                }
            }

            // DEAL WITH PROMO PRICING MODIFICATION
                for(OrderItem item : promos)
            {
                final Product pProduct = item.getProduct();
                Integer pProductId = pProduct.getId();
                if(SpecialEnum.OKOTOKS_JAN_2013.isEqualToSpecialId(pProductId))
                {
                    cost += item.getPrice();
                    int extraToppings = 0;
                    Integer pizzaId = 0;
                    Integer popCount = item.childCountForCategory(ProductCategoryEnum.POP.getId());

                    for(OrderItem sChild : item.getChildren())
                    {
                        Product sChildProduct = sChild.getProduct();
                        final Integer catId = sChildProduct.getCategory().getId();
                        if(catId.equals(ProductCategoryEnum.PIZZA.getId()))
                        {
                            pizzaId = sChildProduct.getId();
                            extraToppings = sChild.childCountForCategory(ProductCategoryEnum.TOPPING.getId());
                        }
                    }

                    if(!pizzaId.equals(Integer.valueOf(0)))
                    {
                        if(ProductEnum.TWO_TOPPER.isEqualToProductId(pizzaId))
                        {
                            if(extraToppings >= 2)
                            {
                                extraToppings -= 2;
                            }
                        }
                        int freeExtraToppings = ProductUtil.OKOTOKS_PRICING_MAP.get(pizzaId);
                        extraToppings -= freeExtraToppings;
                        if(extraToppings < 0)
                        {
                            extraToppings = 0;
                        }
                        cost += extraToppings * LARGE_TOPPING_COST;
                    }

                    cost -= popCount * 110;
                }
                else if(SpecialEnum.POUTINE_COMBO.isEqualToSpecialId(pProductId))
                {
                    if(SpecialsUtil.hasChildProduct(ProductEnum.CHILLI.getId(), item))
                    {
                        cost += 100;
                    }
                }
                else if(SpecialEnum.TASTY_22.isEqualToSpecialId(pProductId) || SpecialEnum.TASTY_20.isEqualToSpecialId(pProductId))
                {
                    if(SpecialsUtil.hasChildProduct(ProductEnum.WEDGIE_12.getId(), item))
                    {
                        cost += -76;
                    }
                }

                cost += item.getPriceModifier();
            }

            for(OrderItem item : promos)
            {
                Integer itemProductId = item.getProduct().getId();
                if(isMegaMeal(itemProductId))
                {
                    int pops = 0;
                    int chips = 0;
                    int dips = 0;
                    for(OrderItem megaMealDetailitem : item.getChildren())
                    {
                        Product product = megaMealDetailitem.getProduct();
                        Integer categoryId = product.getCategory().getId();
                        if(ProductCategoryEnum.POP.isEqualToCategoryId(categoryId))
                        {
                            pops += megaMealDetailitem.getQuantity();
                        }
                        else
                        {
                            final Integer productId = product.getId();
                            if(LAYS.isEqualToProductId(productId) || DORITOS.isEqualToProductId(productId))
                            {
                                chips += megaMealDetailitem.getQuantity();
                            }
                            else if(!(PIZZA.isEqualToCategoryId(categoryId) || WING_MEAL.isEqualToCategoryId(categoryId)))
                            {
                                dips += megaMealDetailitem.getQuantity();
                            }
                        }
                    }
                    if(pops < 4)
                    {
                        cost += Integer.valueOf(110) * (4 - pops);
                    }
                    if(isMegaMeal(itemProductId) && chips < 4)
                    {
                        cost += Integer.valueOf(125) * (4 - chips);
                    }
                    if(dips < 2)
                    {
                        cost += Integer.valueOf(ProductConstants.DIP_COST) * (2 - dips);
                    }
                }
            }

            for(OrderItem item : specials)
            {
                cost += item.getPrice();
                Integer pProductId = item.getProduct().getId();

                if(SpecialEnum.FOUR_PAK.isEqualToSpecialId(pProductId) || SpecialEnum.HOLIDAY_HELPER.isEqualToSpecialId(pProductId))
                {
                    if(SpecialsUtil.hasChildProduct(ProductEnum.TWO_DOLLAR_OFF.getId(), item))
                    {
                        cost += -200;
                    }
                }

                for(OrderItem specialItem : item.getChildren())
                {
                    final Integer catId = specialItem.getProduct().getCategory().getId();
                    if(catId.equals(ProductCategoryEnum.PIZZA.getId()))
                    {
                        if(!specialItem.getToppings().isEmpty())
                        {
                            int freeToppings = getFreeOptionCount(specialItem, ProductCategoryEnum.TOPPING.getName());
                            for(OrderItem topping : specialItem.getToppings())
                            {
                                int quantity = topping.getQuantity();
                                final ProductCategory pc = topping.getProduct().getCategory();
                                if(pc.getName().equalsIgnoreCase(ProductCategoryEnum.TOPPING.getName()))
                                {
                                    // CHECK TO SEE IF EXTRA SAUCE
                                    if(!topping.getProduct().getId().equals(82))
                                    {
                                        if(freeToppings > 0)
                                        {
                                            if(freeToppings >= quantity)
                                            {
                                                freeToppings -= quantity;
                                                quantity = 0;
                                            }
                                            else
                                            {
                                                quantity -= freeToppings;
                                                freeToppings = 0;
                                            }
                                        }
                                        // cost for toppings is halfed b/c
                                        // with
                                        // the 4 pak
                                        // we are
                                        // are dealing with single pizzas
                                        if(specialItem.getSize().getId() == 1)
                                        {
                                            cost += 125 * quantity;
                                        }
                                        else
                                        {
                                            cost += 225 * quantity;
                                        }
                                    }

                                }
                            }
                        }
                    }
                    else
                    {
                        int freeDips = getFreeOptionCount(specialItem, ProductCategoryEnum.WING_DIP.getName());
                        Integer adjuster = 0;
                        final OrderItem specialItemParent = specialItem.getParent();
                        if(specialItemParent != null && specialItem.getProduct().getCategory().getId() != 1)
                        {
                            final Integer specialItemParentId = specialItemParent.getProduct().getId();
                            if(!SpecialsUtil.isFourPak(specialItemParentId))
                            {
                                adjuster = 1;
                            }
                        }
                        freeDips += adjuster;
                        for(OrderItem child : specialItem.getChildren())
                        {
                            int quantity = child.getQuantity();
                            final ProductCategory pc = child.getProduct().getCategory();
                            if(pc.getName().equalsIgnoreCase(ProductCategoryEnum.WING_DIP.getName()))
                            {
                                if(freeDips > 0)
                                {
                                    if(freeDips >= quantity)
                                    {
                                        freeDips -= quantity;
                                        quantity = 0;
                                    }
                                    else
                                    {
                                        quantity -= freeDips;
                                        freeDips = 0;
                                    }
                                }
                            }
                            extraDipCount += quantity;
                            cost += child.getPrice() * quantity;
                        }
                    }
                }
            }

            cost += minimalPopCost(cansOfPop, promos);
        }
        return cost;
    }

    private boolean isMegaMeal(Integer productId)
    {
        return SpecialEnum.MEGA_MEAL_12.isEqualToSpecialId(productId) || SpecialEnum.MEGA_MEAL_14.isEqualToSpecialId(productId);
    }

    /**
     * Give the customer the best price for pops. 5 cans individually priced is
     * more expensive than a six pack so don't charge individual costs for cans
     * when 6 pack pricing would be cheaper.
     */
    private Integer minimalPopCost(final Set<OrderItem> cans, final Set<OrderItem> promos)
    {
        int canInPromo = 0;
        for(OrderItem promo : promos)
        {
            final Integer promoProductId = promo.getProduct().getId();
            Integer productId = promoProductId;
            if(isMegaMeal(productId) || SpecialEnum.HH_FOUR_CAN_POP.isEqualToSpecialId(promoProductId) || SpecialEnum.AFTER_SCHOOL.isEqualToSpecialId(promoProductId)
                || SpecialEnum.DINNER_MOVIE_MEDIUM.isEqualToSpecialId(promoProductId) || SpecialEnum.HOLIDAY_HELPER.isEqualToSpecialId(promoProductId)
                || SpecialEnum.FAN_FAVOURITE.isEqualToSpecialId(promoProductId) || SpecialEnum.D_AND_M.isEqualToSpecialId(promoProductId) || SpecialEnum.PLENTY_FOR_20.isEqualToSpecialId(promoProductId))
            {
                canInPromo += 4;
            }
            else if(SpecialEnum.OKOTOKS_JAN_2013.isEqualToSpecialId(productId))
            {
                canInPromo += 3;
            }
            else if(SpecialEnum.DINNER_MOVIE_LARGE.isEqualToSpecialId(promoProductId))
            {
                canInPromo += 6;
            }
            else if(SpecialEnum.LUNCH_COMBO.isEqualToSpecialId(promoProductId) || SpecialEnum.DINNER_MOVIE_SMALL.isEqualToSpecialId
                (promoProductId))
            {
                canInPromo += 2;
            }
        }

        Integer cost = Integer.valueOf(0);
        if(!cans.isEmpty())
        {
            int canCount = 0;
            int sixPackCanCount = 0;
            int twoLiterCount = 0;
            int sixPackCount = 0;
            final Integer canPrice = this.lookupMgr.priceForSize(POP_CATEGRY_ID, POP_CAN_SIZE_ID);
            final Integer sixPackPrice = this.lookupMgr.priceForSize(POP_CATEGRY_ID, POP_SIX_PACK_SIZE_ID);
            final Integer twoLiterPrice = this.lookupMgr.priceForSize(POP_CATEGRY_ID, POP_TWO_LITER_SIZE_ID);
            for(OrderItem item : cans)
            {
                final ProductSize size = item.getSize();
                if(size.getId().compareTo(POP_CAN_SIZE_ID) == 0)
                {
                    canCount += item.getQuantity();
                }
                else if(size.getId().compareTo(POP_SIX_PACK_SIZE_ID) == 0)
                {
                    sixPackCount += item.getQuantity();
                }
                else if(size.getId().compareTo(POP_TWO_LITER_SIZE_ID) == 0)
                {
                    twoLiterCount += item.getQuantity();
                }

            }

            canCount += sixPackCount * 6;
            sixPackCanCount = canCount - canInPromo;
            if(sixPackCanCount < 0)
            {
                sixPackCanCount = 0;
            }
            final BigDecimal canQuantity = BigDecimal.valueOf(sixPackCanCount);
            final BigDecimal[] result = canQuantity.divideAndRemainder(BigDecimal.valueOf(6));

            sixPackCount = result[0].intValue();
            canCount = canCount - sixPackCount * 6;

            cost = (canCount * canPrice) + (sixPackCount * sixPackPrice) + (twoLiterCount * twoLiterPrice);
        }

        return cost;
    }

    private Integer pizzaCostForSize(final Set<OrderItem> pizzas, final int mealCount, final Cart cart, final Integer size)
    {

        Integer cost = Integer.valueOf(0);
        if(!pizzas.isEmpty())
        {
            final List<Integer> pizzaCosts = new ArrayList<Integer>();
            final Menu twoTopper = this.lookupMgr.menuItem(cart.getMenuId().getId() + "", TWO_TOPPER_ID + "", size + "");

            Integer itemCost = Integer.valueOf(0);

            int NSBPromoCount = 0;
            for(OrderItem item : pizzas)
            {
                if(item.getParent() != null)
                {
                    Integer parentProductId = item.getParent().getProduct().getId();
                    final boolean isSinglePizzas = SpecialEnum.SINGLE_TWO_TOPPER_PLUS_ONE_LITRE.isEqualToSpecialId(parentProductId)
                        || SpecialEnum.SINGLE_HAWAIIAN_PLUS_ONE_LITRE.isEqualToSpecialId(parentProductId) || SpecialEnum.POUTINE_COMBO.isEqualToSpecialId(parentProductId)
                        || SpecialEnum.SINGLE_PESTO_GARDEN_VEGGIE_PLUS_ONE_LITRE.isEqualToSpecialId(parentProductId) || SpecialEnum.BIG_TASTE.isEqualToSpecialId(parentProductId)
                        || SpecialEnum.SINGLE_CHILI_PLUS_ONE_LITRE.isEqualToSpecialId(parentProductId) || SpecialEnum.VALENTINES_DELIGHT.isEqualToSpecialId(parentProductId);
                    if(isSinglePizzas)
                    {
                        cost += getItemCost(item, cart);
                        continue;
                    }
                    else if(SpecialEnum.OKOTOKS_JAN_2013.isEqualToSpecialId(parentProductId))
                    {
                        continue;
                    }
                }
                // add any price modification (ie boneless wings).
                cost += item.getPriceModifier();
                itemCost = getItemCost(item, cart);
                pizzaCosts.add(itemCost);
            }
            // Sort the cost list via the natural order (least to greatest)
            Collections.sort(pizzaCosts);
            // reverse the list so the greatest cost is first.
            Collections.reverse(pizzaCosts);

            // For each wing meal get the highest priced pizza unless pizza is
            // NSB then charge two topper price.
            for(int i = 0; i < mealCount; i++)
            {
                if(!pizzaCosts.isEmpty())
                {
                    int pizzaCost = pizzaCosts.get(0);
                    // If it's an NSB and there's a wing meal associated to it
                    // charge the two topper price.
                    if(pizzaCost < twoTopper.getPrice())
                    {
                        pizzaCost = twoTopper.getPrice();
                    }

                    cost += pizzaCost;
                    pizzaCosts.remove(0);
                }
            }

            if(NSBPromoCount > 0)
            {
                int nsbCostIncrement = NSBPromoCount / 2;
                log.warn("NSB count: " + NSBPromoCount);
                log.warn("NSB cost increment count: " + nsbCostIncrement);
                cost += nsbCostIncrement * 200;
            }
            for(int i = 0; i < pizzaCosts.size(); i = i + 2)
            {
                cost += pizzaCosts.get(i);
            }
        }
        return cost;
    }

    private int priceDifferentialForSize(Integer sizeId)
    {
        int priceDifferential = 0;
        if(sizeId.equals(Integer.valueOf(1)))
        {
            priceDifferential = SMALL_NSB_DIFFERENTIAL;
        }
        else if(sizeId.equals(Integer.valueOf(2)))
        {
            priceDifferential = MEDIUM_NSB_DIFFERENTIAL;
        }
        else if(sizeId.equals(Integer.valueOf(3)))
        {
            priceDifferential = LARGE_NSB_DIFFERENTIAL;
        }

        return priceDifferential;
    }

    private Map<String, Set<OrderItem>> productsForSize(final Set<OrderItem> meals, final Set<OrderItem> pizzas)
    {
        final Map<String, Set<OrderItem>> map = new HashMap<String, Set<OrderItem>>();
        Set<OrderItem> small = new HashSet<OrderItem>();
        Set<OrderItem> medium = new HashSet<OrderItem>();
        Set<OrderItem> large = new HashSet<OrderItem>();

        for(OrderItem item : pizzas)
        {
            Integer sizeId = item.getSize().getId();
            if(sizeId.compareTo(Integer.valueOf(1)) == 0)
            {
                small.add(item);
            }
            else if(sizeId.compareTo(Integer.valueOf(2)) == 0)
            {
                medium.add(item);
            }
            else if(sizeId.compareTo(Integer.valueOf(3)) == 0)
            {
                large.add(item);
            }
        }
        map.put(SMALL_PIZZA, small);
        map.put(MEDIUM_PIZZA, medium);
        map.put(LARGE_PIZZA, large);

        // reset collections
        small = new HashSet<OrderItem>();
        medium = new HashSet<OrderItem>();
        large = new HashSet<OrderItem>();

        for(OrderItem item : meals)
        {
            Integer sizeId = item.getSize().getId();
            if(sizeId.compareTo(Integer.valueOf(1)) == 0)
            {
                small.add(item);
            }
            else if(sizeId.compareTo(Integer.valueOf(2)) == 0)
            {
                medium.add(item);
            }
            else if(sizeId.compareTo(Integer.valueOf(3)) == 0)
            {
                large.add(item);
            }
        }
        map.put(SMALL_MEAL, small);
        map.put(MEDIUM_MEAL, medium);
        map.put(LARGE_MEAL, large);

        return map;
    }

    @Autowired
    public void setLookupManager(final LookupManager mgr)
    {
        this.lookupMgr = mgr;
    }

    private int toppingDifferentialForSize(Integer sizeId)
    {
        int priceDifferential = 0;
        if(sizeId.equals(Integer.valueOf(1)))
        {
            priceDifferential = SMALL_TOPPING_COST;
        }
        else if(sizeId.equals(Integer.valueOf(2)))
        {
            priceDifferential = MEDIUM_TOPPING_COST;
        }
        else if(sizeId.equals(Integer.valueOf(3)))
        {
            priceDifferential = LARGE_TOPPING_COST;
        }

        return priceDifferential;
    }

    public Integer twoLiterPopSpecialDiscount(int specialCount, Set<OrderItem> pops)
    {
        int discount = 0;
        if(specialCount > 0)
        {
            int popCount = 0;
            for(OrderItem popItem : pops)
            {
                if(popItem.getSize().getId().equals(POP_TWO_LITER_SIZE_ID))
                {
                    popCount += popItem.getQuantity();
                }
            }
            int discountProductCount = specialCount;
            if(specialCount > popCount)
            {
                discountProductCount = (specialCount - (specialCount - popCount));
            }

            discount = TWO_LITER_POP_DISCOUNT * discountProductCount;
        }

        return discount;
    }

    /*
     * rule for popCost changed by Junjie Zhu since 6 pack's price drops from
     * 4.25 to 3.99
     */
    private Integer wingMealAdditionalCost(final Set<OrderItem> wingMeals)
    {
        Integer cost = 0;
        for(OrderItem wingMeal : wingMeals)
        {
            cost += wingMeal.getPriceModifier();
        }
        return cost;
    }

    private Integer wingMealExtraDipCost(final Set<OrderItem> meals)
    {
        Integer cost = Integer.valueOf(0);

        for(OrderItem wingMeal : meals)
        {
            if(!wingMeal.getChildren().isEmpty())
            {
                int freeDips = getFreeOptionCount(wingMeal, ProductCategoryEnum.WING_DIP.getName());
                for(OrderItem child : wingMeal.getChildren())
                {
                    int quantity = child.getQuantity();
                    final ProductCategory pc = child.getProduct().getCategory();

                    if(pc.getId().equals(ProductCategoryEnum.WING_DIP.getId()))
                    {
                        if(freeDips > 0)
                        {
                            if(freeDips >= quantity)
                            {
                                freeDips -= quantity;
                                quantity = 0;
                            }
                            else
                            {
                                quantity -= freeDips;
                                freeDips = 0;
                            }
                        }
                    }
                    cost += child.getPrice() * quantity;
                }
            }
        }

        return cost;
    }
}
