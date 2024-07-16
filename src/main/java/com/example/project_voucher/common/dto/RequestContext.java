package com.example.project_voucher.common.dto;

import com.example.project_voucher.common.type.RequesterType;

public record RequestContext(
        RequesterType requesterType,
        String requesterId) {
}
