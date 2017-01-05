/**
 * 
 */
package com.pizza73.model.comparator;

import java.util.Comparator;

import com.pizza73.model.ShopPayroll;

/**
 * @author chris
 * 
 */
public class ShopEEComparator implements Comparator<ShopPayroll>
{

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public int compare(ShopPayroll sp1, ShopPayroll sp2)
    {
        if (sp1 == null && sp2 == null)
        {
            return 0;
        }
        else if (sp1 == null)
        {
            return -1;
        }
        else if (sp2 == null)
        {
            return 1;
        }
        String shopOneCompanyName = sp1.getShop().getCompanyNumber();
        String shopTwoCompanyName = sp2.getShop().getCompanyNumber();
        Integer shopOneBranch = sp1.getShop().getBranch();
        Integer shopTwoBranch = sp2.getShop().getBranch();

        int companyNameComparator = shopOneCompanyName.compareTo(shopTwoCompanyName);
        if (companyNameComparator == 0)
        {
            return shopOneBranch.compareTo(shopTwoBranch);
        }

        return companyNameComparator;
    }

}
