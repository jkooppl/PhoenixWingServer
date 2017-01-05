package com.pizza73.model.mobile;

public class StoreOpen
{
    private Boolean open = Boolean.FALSE;
    private String message = "";

    public StoreOpen()
    {
    }

    public StoreOpen(Boolean open, String message)
    {
        this.open = open;
        this.message = message;
    }

    public Boolean isOpen()
    {
        return this.open;
    }

    public String getMessage()
    {
        return this.message;
    }
}
