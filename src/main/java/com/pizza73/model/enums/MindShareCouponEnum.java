package com.pizza73.model.enums;

/**
 update iq2_coupon set start_date = '2014-04-04', end_date = '2014-07-04' where coupon_id in (48,49);
 update iq2_coupon set coupon_code = 'P96711', description = 'COUPON P96711 MShare Onion Ring', name ='P96711 MShare Onion Rings' where coupon_id in (48);
 update iq2_coupon set coupon_code = 'P69696', description = 'COUPON P69696 MShare Wedgies', name ='P69696 MShare Wedgies' where coupon_id in (49);
 */
public enum MindShareCouponEnum
{
    MIND_SHARE_ONION_RINGS_JAN_2014(541,52, ProductEnum.ONION_RING.getId(), 74),
    MIND_SHARE_WEDGIE_JAN_2014(542,51, ProductEnum.WEDGIE_12.getId(),75),

    MIND_SHARE_ONION_RINGS_DEC_2013(589,48, ProductEnum.ONION_RING.getId(), 74),
    MIND_SHARE_WEDGIE_DEC_2013(590,49, ProductEnum.WEDGIE_12.getId(), 75);

    public static final Integer[] MIND_SHARE_COUPONS = new Integer[]{
        MindShareCouponEnum.MIND_SHARE_ONION_RINGS_JAN_2014.getCouponId(),
        MindShareCouponEnum.MIND_SHARE_WEDGIE_JAN_2014.getCouponId(),
        MindShareCouponEnum.MIND_SHARE_WEDGIE_DEC_2013.getCouponId(),
        MindShareCouponEnum.MIND_SHARE_ONION_RINGS_DEC_2013.getCouponId()};
    public static final Integer[] MIND_SHARE_ONION_RING_COUPONS = new Integer[]{
        MindShareCouponEnum.MIND_SHARE_ONION_RINGS_JAN_2014.getCouponId(),
        MindShareCouponEnum.MIND_SHARE_ONION_RINGS_DEC_2013.getCouponId()};

    private Integer productId;
    private Integer couponId;
    private Integer requiredProductId;
    private Integer reportId;

    private MindShareCouponEnum(Integer id, Integer couponId, Integer requiredProductId, Integer reportId)
    {
        this.productId = id;
        this.couponId = couponId;
        this.requiredProductId = requiredProductId;
        this.reportId = reportId;
    }

    public Integer getProductId()
    {
        return this.productId;
    }

    public Integer getCouponId()
    {
        return this.couponId;
    }

    public Integer getRequiredProductId()
    {
        return requiredProductId;
    }

    public Integer getReportId()
    {
        return reportId;
    }

    public static MindShareCouponEnum valueForCouponId(Integer couponId)
    {
        MindShareCouponEnum msType = null;

        MindShareCouponEnum[] msTypes = MindShareCouponEnum.values();
        for (MindShareCouponEnum ms : msTypes)
        {
            if (ms.getCouponId().equals(couponId))
            {
                msType = ms;
                break;
            }
        }

        return msType;
    }

    public boolean isEqualToId(Integer productId)
    {
        return null != productId ? this.productId.equals(productId) : false;
    }

    public boolean isEqualToCouponCode(Integer couponId)
    {
        return null != couponId ? this.couponId.equals(couponId) : false;
    }
}
