package com.pizza73.type;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum PackagingTypeEnum
{

    LARGE_DOUBLE_BOX(1, "14\" Double Box", 3, Arrays.asList(new Integer[]{Integer.valueOf(1),Integer.valueOf(2)}),Arrays.asList(new Integer[]{})), //
    MEDIUM_DOUBLE_BOX(2, "12\" Double Box", 2, Arrays.asList(new Integer[]{Integer.valueOf(1),Integer.valueOf(2)}),Arrays.asList(new Integer[]{})), //
    SMALL_DOUBLE_BOX(3, "9\" Double Box", 1, Arrays.asList(new Integer[]{Integer.valueOf(1),Integer.valueOf(2)}),Arrays.asList(new Integer[]{Integer.valueOf(190)})), //
    FOUR_PAK_BOX(4, "18\" 4 Pack Box", 0, Arrays.asList(new Integer[]{}),Arrays.asList(new Integer[]{})), //
    LARGE_SINGLE_BOX(5, "14\" Single box", 3, Arrays.asList(new Integer[]{Integer.valueOf(1)}),Arrays.asList(new Integer[]{})), //
    MEDIUM_SINGLE_BOX(6, "12\" Single Box", 2, Arrays.asList(new Integer[]{Integer.valueOf(1),Integer.valueOf(12)}),Arrays.asList(new Integer[]{})), //
    SMALL_SINGLE_BOX(7, "9\" Single Box", 1, Arrays.asList(new Integer[]{Integer.valueOf(1),Integer.valueOf(12)}),Arrays.asList(new Integer[]{})), //
    SCHOOL_PIZZA_BOX(8, "6\" School Box", 0, Arrays.asList(new Integer[]{Integer.valueOf(1)}),Arrays.asList(new Integer[]{})), //
    LARGE_FRY_BOX(9, "Large Side Box", 0, Arrays.asList(new Integer[]{Integer.valueOf(7),Integer.valueOf(22),Integer.valueOf(20)}),Arrays.asList(new Integer[]{Integer.valueOf(12),Integer.valueOf(136)})), //
    SMALL_FRY_BOX(10, "Small Fry Box", 0, Arrays.asList(new Integer[]{Integer.valueOf(19),Integer.valueOf(2)}),Arrays.asList(new Integer[]{})), //
    SLICE_TRAY(11, "Slice Trays", 0, Arrays.asList(new Integer[]{Integer.valueOf(1),Integer.valueOf(2)}),Arrays.asList(new Integer[]{})), //
    BAG(12, "Dippza Bag", 0, Arrays.asList(new Integer[]{}),Arrays.asList(new Integer[]{17})), //
    SALAD_CONTAINER(13, "Salad Container", 0, Arrays.asList(new Integer[]{Integer.valueOf(23)}),Arrays.asList(new Integer[]{})); //

    private Integer id;
    private String name;
    private Integer sizeId;
    private List<Integer> productTypes = new ArrayList<Integer>();
    private List<Integer> productIds = new ArrayList<Integer>() ;

    private PackagingTypeEnum(Integer id, String name, Integer sizeId, List<Integer> productTypes, List<Integer> productIds)
    {
        this.id = id;
        this.name = name;
        this.sizeId = sizeId;
        this.productTypes = productTypes;
        this.productIds = productIds;
    }
    
    public static PackagingTypeEnum valueFor(Integer categoryId, Integer sizeId, Integer productId)
    {
        for (PackagingTypeEnum packageEnum : PackagingTypeEnum.values())
        {
            Integer sId = packageEnum.getSizeId();
            List<Integer> types = packageEnum.getProductTypes();
            List<Integer> products = packageEnum.getProductIds();
            if(sId.equals(sizeId))
            {
                if(products.contains(productId))
                {
                    return packageEnum;
                }
                else if(types.contains(categoryId))
                {
                    return packageEnum;
                }
            }
        }
        
        return null;
    }

    public Integer getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public Integer getSizeId()
    {
        return sizeId;
    }

    public List<Integer> getProductTypes()
    {
        return productTypes;
    }

    public List<Integer> getProductIds()
    {
        return productIds;
    }
}
