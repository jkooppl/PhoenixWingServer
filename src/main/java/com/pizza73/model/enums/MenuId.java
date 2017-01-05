package com.pizza73.model.enums;

/**
 * MenuId.java TODO comment me
 * 
 * @author chris 2-Feb-07
 * 
 * @Copyright Flying Pizza 73
 */
public enum MenuId
{
    AB_CSC(Integer.valueOf(1)), BC_CSC(Integer.valueOf(2)), SASK_CSC(Integer.valueOf(3)), AB_WEB(Integer.valueOf(4)), BC_WEB(
        Integer.valueOf(5)), SASK_WEB(Integer.valueOf(6)), AB_POS(Integer.valueOf(7)), BC_POS(Integer.valueOf(8)), SASK_POS(
        Integer.valueOf(9)), FT_MAC_WEB(Integer.valueOf(10)), AB_MOBILE(Integer.valueOf(11)), BC_MOBILE(Integer.valueOf(12)), SASK_MOBILE(
        Integer.valueOf(13));

    private Integer id;

    private MenuId(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return this.id;
    }

    /**
     * @param webMenuId
     * @return
     */
    public static MenuId valueForId(Integer webMenuId)
    {
        MenuId mi = null;

        MenuId[] menus = MenuId.values();
        for (MenuId menu : menus)
        {
            if (menu.getId().equals(webMenuId))
            {
                mi = menu;
                break;
            }
        }

        return mi;
    }

    public boolean isEqualToMenuId(Integer menuId)
    {
        return null != menuId ? this.id.equals(menuId) : false;
    }
}
