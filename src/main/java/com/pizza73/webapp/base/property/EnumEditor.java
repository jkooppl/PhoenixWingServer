package com.pizza73.webapp.base.property;

import java.beans.PropertyEditorSupport;

/**
 * EnumEditor.java TODO comment me
 * 
 * @author chris 27-Nov-06
 * 
 * @Copyright Flying Pizza 73
 */
public class EnumEditor extends PropertyEditorSupport
{
   @SuppressWarnings({"rawtypes" })
   private Class<? extends Enum> cls;
   private boolean useOrdinal;

   @SuppressWarnings("rawtypes")
   public void setCls(Class<? extends Enum> cls)
   {
      this.cls = cls;
   }

   public void setUseOrdinal(boolean useOrdinal)
   {
      this.useOrdinal = useOrdinal;
   }

   @SuppressWarnings("rawtypes")
   Enum getConstantByOrdinal(Integer ordinal)
   {

      Enum[] enumConstants = cls.getEnumConstants();

      for (int i = 0; i < enumConstants.length; i++)
      {
         if (enumConstants[i].ordinal() == ordinal)
         {
            return enumConstants[i];
         }
      }
      throw new IllegalArgumentException("No item with ordinal =" + ordinal
            + " in " + cls);
   }

   @SuppressWarnings("rawtypes")
   Enum getConstantByName(String name)
   {

      Enum[] enumConstants = cls.getEnumConstants();

      for (int i = 0; i < enumConstants.length; i++)
      {
         if (enumConstants[i].name().equals(name))
         {
            return enumConstants[i];
         }
      }
      throw new IllegalArgumentException("No item with name =" + name + " in "
            + cls);
   }

   @Override
   @SuppressWarnings("rawtypes")
   public void setAsText(String text) throws IllegalArgumentException
   {
      if ((text != null) && (text.length() > 0))
      {

         try
         {
            Enum val;
            if (useOrdinal)
            {
               final int constantValue = Integer.parseInt(text);
               val = getConstantByOrdinal(constantValue);
            }
            else
            {
               val = getConstantByName(text);
            }
            setValue(val);
         }
         catch (Exception e)
         {
            throw new IllegalArgumentException("Could not set status:" + e);
         }
      }
      else
      {
         setValue(null);
      }
   }

   @Override
   @SuppressWarnings("rawtypes")
   public String getAsText()
   {
      Enum p = (Enum) getValue();
      if (p != null)
      {
         if (useOrdinal)
         {
            return p.ordinal() + "";
         }
         return p.name();
      }
      return "";
   }
}
