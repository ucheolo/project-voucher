package com.example.project_voucher.app.controller.request;

import com.example.project_voucher.common.type.RequesterType;
import com.example.project_voucher.common.type.VoucherAmountType;

public record VoucherPublishV3Request(
        RequesterType requesterType,
        String requesterId,
        String contractCode,
        VoucherAmountType amountType
) {
}
