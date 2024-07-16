package com.example.project_voucher.app.controller.request;


import com.example.project_voucher.common.type.RequesterType;

public record VoucherDisableV2Request(
        RequesterType requesterType,
        String requesterId,
        String code
) {

}
