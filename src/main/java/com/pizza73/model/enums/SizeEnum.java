package com.pizza73.model.enums;

public enum SizeEnum
{
    SMALL(Integer.valueOf(1)),
    MEDIUM(Integer.valueOf(1)),
    LARGE(Integer.valueOf(1));

    private Integer id;

    SizeEnum(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return this.id;
    }

    public static SizeEnum sizeForId(Integer id)
    {
        SizeEnum pc = null;
        SizeEnum[] sizes = SizeEnum.values();
        for (SizeEnum size : sizes)
        {
            if (size.getId().equals(id))
            {
                pc = size;
                break;
            }
        }

        return pc;
    }

    public boolean isEqualToSizeId(Integer sizeId)
    {
        return null != sizeId ? this.id.equals(sizeId) : false;
    }
}
