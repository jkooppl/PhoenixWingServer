package com.pizza73.model.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface StringConstraint
{
    public boolean required() default false;

    public boolean strict() default false;

    public int maxLength() default 0;

    public String regexp() default "";

    public String label() default "";
}
