package com.pizza73.service;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public interface GenericManager<T, PK extends Serializable>
{
    /**
     * Generic method used to get a all objects of a particular type.
     * 
     * @param clazz
     *            the type of objects
     * @return List of populated objects
     */
    List<T> getAll();

    /**
     * Generic method to get an object based on class and identifier.
     * 
     * @param clazz
     *            model class to lookup
     * @param id
     *            the identifier (primary key) of the class
     * @return a populated object
     * @see org.springframework.orm.ObjectRetrievalFailureException
     */
    T get(PK id);

    /**
     * Generic method to save an object.
     * 
     * @param o
     *            the object to save
     */
    @Transactional(readOnly = false)
    T save(T o);

    /**
     * Checks for existence of an object of type T using the id arg.
     * 
     * @param id
     *            the identifier (primary key) of the object to get
     * @return - true if it exists, false if it doesn't
     */
    boolean exists(PK id);

    /**
     * Generic method to delete an object based on class and id
     * 
     * @param clazz
     *            model class to lookup
     * @param id
     *            the identifier of the class
     */
    @Transactional(readOnly = false)
    void remove(PK id);

    public Calendar businessDate();

    public String infoValueForVariable(String variable);
}
