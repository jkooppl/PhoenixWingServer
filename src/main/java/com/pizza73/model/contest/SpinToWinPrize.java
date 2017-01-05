/**
 * 
 */
package com.pizza73.model.contest;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author chris
 * 
 */

@Entity
@Table(name = "spin_to_win_prizes", schema = "public", uniqueConstraints = {})
public class SpinToWinPrize implements Serializable
{
    // FIELDS
    private static final long serialVersionUID = -6454510584648725268L;

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "prize")
    private String prize = "";

    @Column(name = "coupon_desc")
    private String couponDescription = "";

    @Column(name = "max_prizes")
    private Integer maxPrizes;

    @Column(name = "max_per_week")
    private Integer maxPerWeek;

    @Column(name = "count")
    private Integer count;

    @Column(name = "active")
    private boolean active;

    public SpinToWinPrize()
    {
    }

    public Integer getId()
    {
        return this.id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getPrize()
    {
        return this.prize;
    }

    public void setPrize(String code)
    {
        this.prize = code;
    }

    public Integer getMaxPrizes()
    {
        return maxPrizes;
    }

    public Integer getMaxPerWeek()
    {
        return maxPerWeek;
    }

    public Integer getCount()
    {
        return this.count;
    }

    public String getCouponDescription()
    {
        return this.couponDescription;
    }

    public void setCount(Integer discount)
    {
        this.count = discount;
    }

    public boolean getActive()
    {
        return this.active;
    }
}