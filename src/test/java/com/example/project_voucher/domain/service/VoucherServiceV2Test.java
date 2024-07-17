package com.example.project_voucher.domain.service;

import com.example.project_voucher.common.dto.RequestContext;
import com.example.project_voucher.common.type.RequesterType;
import com.example.project_voucher.common.type.VoucherAmountType;
import com.example.project_voucher.common.type.VoucherStatusType;
import com.example.project_voucher.storage.voucher.VoucherEntity;
import com.example.project_voucher.storage.voucher.VoucherHistoryEntity;
import com.example.project_voucher.storage.voucher.VoucherRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class VoucherServiceV2Test {
    @Autowired
    private VoucherService voucherService;

    @Autowired
    private VoucherRepository voucherRepository;

    @DisplayName("발행된 상품권은 code로 조회할 수 있어야 된다.")
    @Test
    public void publish() {
        // given
        final RequestContext requestContext = new RequestContext(RequesterType.PARTNER, UUID.randomUUID().toString());
        final LocalDate validFrom = LocalDate.now();
        final LocalDate validTo = LocalDate.now().plusDays( 30);
        final VoucherAmountType amount = VoucherAmountType.KRW_30000;

        final String code = voucherService.publishV2(requestContext, validFrom, validTo, amount);

        // when
        final VoucherEntity voucherEntity = voucherRepository.findByCode(code).get();

        // then
        assertThat(voucherEntity.code()).isEqualTo(code);
        assertThat(voucherEntity.status()).isEqualTo(VoucherStatusType.PUBLISH);
        assertThat(voucherEntity.validFrom()).isEqualTo(validFrom);
        assertThat(voucherEntity.validTo()).isEqualTo(validTo);
        assertThat(voucherEntity.amount()).isEqualTo(amount);

        // history
        final VoucherHistoryEntity voucherHistoryEntity = voucherEntity.histories().get(0);
        assertThat(voucherHistoryEntity.orderId()).isNotNull();
        assertThat(voucherHistoryEntity.requesterType()).isEqualTo(requestContext.requesterType());
        assertThat(voucherHistoryEntity.requesterId()).isEqualTo(requestContext.requesterId());
        assertThat(voucherHistoryEntity.status()).isEqualTo(VoucherStatusType.PUBLISH);
        assertThat(voucherHistoryEntity.description()).isEqualTo("테스트 발행");
    }


    @DisplayName("발행된 상품권은 사용 불가 처리 할 수 있다.")
    @Test
    public void disable() {
        // given
        final RequestContext requestContext = new RequestContext(RequesterType.PARTNER, UUID.randomUUID().toString());
        final LocalDate validFrom = LocalDate.now();
        final LocalDate validTo = LocalDate.now().plusDays(30);
        final VoucherAmountType amount = VoucherAmountType.KRW_30000;

        final String code = voucherService.publishV2(requestContext, validFrom, validTo, amount);

        final RequestContext disableRequestContext = new RequestContext(RequesterType.PARTNER, UUID.randomUUID().toString());

        // when
        voucherService.disableV2(disableRequestContext, code);
        final VoucherEntity voucherEntity = voucherRepository.findByCode(code).get();

        // then
        assertThat(voucherEntity.code()).isEqualTo(code);
        assertThat(voucherEntity.status()).isEqualTo(VoucherStatusType.DISABLE);
        assertThat(voucherEntity.validFrom()).isEqualTo(validFrom);
        assertThat(voucherEntity.validTo()).isEqualTo(validTo);
        assertThat(voucherEntity.amount()).isEqualTo(amount);
        assertThat(voucherEntity.getUpdateAt()).isNotEqualTo(voucherEntity.getCreateAt());

        System.out.println("### voucherEntity.createAt() = " + voucherEntity.getCreateAt());
        System.out.println("### voucherEntity.updateAt() = " + voucherEntity.getUpdateAt());

        // history
        final VoucherHistoryEntity voucherHistoryEntity = voucherEntity.histories().get(voucherEntity.histories().size() - 1);
        assertThat(voucherHistoryEntity.orderId()).isNotNull();
        assertThat(voucherHistoryEntity.requesterType()).isEqualTo(disableRequestContext.requesterType());
        assertThat(voucherHistoryEntity.requesterId()).isEqualTo(disableRequestContext.requesterId());
        assertThat(voucherHistoryEntity.status()).isEqualTo(VoucherStatusType.DISABLE);
        assertThat(voucherHistoryEntity.description()).isEqualTo("테스트 사용 불가");
    }

    @DisplayName("발행된 상품권은 사용할 수 있다.")
    @Test
    public void use() {
        // given
        final RequestContext requestContext = new RequestContext(RequesterType.PARTNER, UUID.randomUUID().toString());
        final LocalDate validFrom = LocalDate.now();
        final LocalDate validTo = LocalDate.now().plusDays(30);
        final VoucherAmountType amount = VoucherAmountType.KRW_30000;

        final String code = voucherService.publishV2(requestContext, validFrom, validTo, amount);

        final RequestContext useRequestContext = new RequestContext(RequesterType.PARTNER, UUID.randomUUID().toString());

        // when
        voucherService.useV2(useRequestContext, code);
        final VoucherEntity voucherEntity = voucherRepository.findByCode(code).get();

        // then
        assertThat(voucherEntity.code()).isEqualTo(code);
        assertThat(voucherEntity.status()).isEqualTo(VoucherStatusType.USE);
        assertThat(voucherEntity.validFrom()).isEqualTo(validFrom);
        assertThat(voucherEntity.validTo()).isEqualTo(validTo);
        assertThat(voucherEntity.amount()).isEqualTo(amount);
        assertThat(voucherEntity.getUpdateAt()).isNotEqualTo(voucherEntity.getCreateAt());

        System.out.println("### voucherEntity.createAt() = " + voucherEntity.getCreateAt());
        System.out.println("### voucherEntity.updateAt() = " + voucherEntity.getUpdateAt());

        // history
        final VoucherHistoryEntity voucherHistoryEntity = voucherEntity.histories().get(voucherEntity.histories().size() - 1);
        assertThat(voucherHistoryEntity.orderId()).isNotNull();
        assertThat(voucherHistoryEntity.requesterType()).isEqualTo(useRequestContext.requesterType());
        assertThat(voucherHistoryEntity.requesterId()).isEqualTo(useRequestContext.requesterId());
        assertThat(voucherHistoryEntity.status()).isEqualTo(VoucherStatusType.USE);
        assertThat(voucherHistoryEntity.description()).isEqualTo("테스트 사용");
    }
}