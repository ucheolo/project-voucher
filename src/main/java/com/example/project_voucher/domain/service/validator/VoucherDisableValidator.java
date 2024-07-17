package com.example.project_voucher.domain.service.validator;

import com.example.project_voucher.common.dto.RequestContext;
import com.example.project_voucher.storage.voucher.VoucherEntity;
import org.springframework.stereotype.Component;

@Component
public class VoucherDisableValidator {

    public void validate(VoucherEntity voucherEntity, RequestContext requestContext) {
        상품권_사용_불가_처리_권한이_있는지_확인(voucherEntity, requestContext);
    }

    public static void 상품권_사용_불가_처리_권한이_있는지_확인(VoucherEntity voucherEntity, RequestContext requestContext) {
        // 상품권 사용 불가 처리 권한이 있는지 확인
        if (voucherEntity.publishHistory().requesterType() != requestContext.requesterType()
                || !voucherEntity.publishHistory().requesterId().equals(requestContext.requesterId())) {
            throw new IllegalArgumentException("사용 불가 처리 권한이 없는 상품권 입니다.");
        }
    }
}
