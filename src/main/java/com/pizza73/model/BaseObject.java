package com.pizza73.model;

import java.io.Serializable;

/**
 * BaseObject.java TODO comment me
 * 
 * @author chris 8-Sep-06
 * 
 * @Copyright Flying Pizza 73
 */
@SuppressWarnings("serial")
public abstract class BaseObject implements Serializable
{
    public abstract String toString();

    public abstract boolean equals(Object o);

    public abstract int hashCode();
}
