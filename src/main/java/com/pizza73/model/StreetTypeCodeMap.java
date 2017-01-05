package com.pizza73.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * StreetTypeCodeMap.java TODO comment me
 * 
 * @author chris 22-Dec-06
 * 
 * @Copyright Flying Pizza 73
 */
@Entity
@Table(name = "iq2_street_type_code_map", schema = "public", uniqueConstraints = {})
public class StreetTypeCodeMap implements Serializable, Comparable<StreetTypeCodeMap>
{
    // FIELDS
    private static final long serialVersionUID = -5961842363122525501L;

    @Id
    private Integer id = null;

    @Column(name = "street_type_alt")
    private String alternateTypeCode;

    @Column(name = "street_type_code")
    private String typeCode;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "display_hint")
    private Integer displayHint;

    public StreetTypeCodeMap()
    {
    }

    public Integer getId()
    {
        return this.id;
    }

    protected void setId(Integer id)
    {
        this.id = id;
    }

    /**
     * @return the alternateTypeCode
     */
    public String getAlternateTypeCode()
    {
        return this.alternateTypeCode.trim();
    }

    /**
     * @return the typeCode
     */
    public String getTypeCode()
    {
        return this.typeCode.trim();
    }

    public String getDisplayName()
    {
        return this.displayName.trim();
    }

    public Integer getDisplayHint()
    {
        return this.displayHint;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(StreetTypeCodeMap o)
    {
        StreetTypeCodeMap stcm = o;
        if (this.getDisplayHint() != null && stcm.getDisplayHint() != null)
        {
            return this.getDisplayHint().compareTo(stcm.getDisplayHint());
        }

        return 0;
    }
}
