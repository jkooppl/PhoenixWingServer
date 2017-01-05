package com.pizza73.model.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DoubleConstraint
{
    public boolean required() default false;

    public boolean hasMax() default false;

    public double maxValue() default 0;

    public boolean hasMin() default false;

    public double minValue() default 0;

    public String label() default "";
}
