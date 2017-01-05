package com.pizza73.service;

import com.pizza73.model.UniqueCoupon;

public interface CscSpinToWinManager
{
    public String[] generatePrize(Integer entryId, String email);

    public boolean sendPrizeConfirmation(Integer entryId, String email);

    public UniqueCoupon generatePrizeForMarketing(Integer orderId, String email);
}
