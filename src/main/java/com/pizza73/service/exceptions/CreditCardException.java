package com.pizza73.service.exceptions;

/**
 * CreditCardException.java TODO comment me
 * 
 * @author chris 29-Jan-07
 * 
 * @Copyright Flying Pizza 73
 */
public class CreditCardException extends Exception
{
    private String responseCode = "000";

    /**
     * 
     */
    private static final long serialVersionUID = -6406804493985461065L;

    public CreditCardException(String message)
    {
        super(message);
    }

    public CreditCardException(String message, String responseCode)
    {
        super(message);
        this.responseCode = responseCode;
    }

    /**
     * @return the responseCode
     */
    public String getResponseCode()
    {
        return this.responseCode;
    }

    /**
     * @param responseCode
     *            the responseCode to set
     */
    public void setResponseCode(String responseCode)
    {
        this.responseCode = responseCode;
    }
}
