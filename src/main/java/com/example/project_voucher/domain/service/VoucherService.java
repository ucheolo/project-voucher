package com.example.project_voucher.domain.service;

import com.example.project_voucher.common.dto.RequestContext;
import com.example.project_voucher.common.type.VoucherAmountType;
import com.example.project_voucher.common.type.VoucherStatusType;
import com.example.project_voucher.domain.service.validator.VoucherDisableValidator;
import com.example.project_voucher.domain.service.validator.VoucherPublishValidator;
import com.example.project_voucher.storage.voucher.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;
    private final ContractRepository contractRepository;
    private final VoucherPublishValidator voucherPublishValidator;
    private final VoucherDisableValidator voucherDisableValidator;

    public VoucherService(VoucherRepository voucherRepository, ContractRepository contractRepository, VoucherPublishValidator voucherPublishValidator, VoucherDisableValidator voucherDisableValidator) {
        this.voucherRepository = voucherRepository;
        this.contractRepository = contractRepository;
        this.voucherPublishValidator = voucherPublishValidator;
        this.voucherDisableValidator = voucherDisableValidator;
    }

    // 상품권 발행
    @Transactional
    public String publish(final LocalDate validFrom, final LocalDate validTo, final VoucherAmountType amount) {
        final String code = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
        final VoucherEntity voucherEntity = new VoucherEntity(code, VoucherStatusType.PUBLISH, amount, null, null);

        return voucherRepository.save(voucherEntity).code();
    }

    // 상품권 사용 불가 처리
    @Transactional
    public void disable(final String code) {
        final VoucherEntity voucherEntity = voucherRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품권입니다."));

        voucherEntity.disable(null);

        // voucherRepository.save(voucherEntity);
    }

    // 상품권 사용
    @Transactional
    public void use(final String code) {
        final VoucherEntity voucherEntity = voucherRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품권입니다."));

        voucherEntity.use(null);

        // voucherRepository.save(voucherEntity);
    }

    // 상품권 발행 v2
    @Transactional
    public String publishV2(final RequestContext requestContext, final LocalDate validFrom, final LocalDate validTo, final VoucherAmountType amount) {
        final String code = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
        final String orderId = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");

        final VoucherHistoryEntity voucherHistoryEntity = new VoucherHistoryEntity(orderId, requestContext.requesterType(),
                requestContext.requesterId(), VoucherStatusType.PUBLISH, "테스트 발행");
        final VoucherEntity voucherEntity = new VoucherEntity(code, VoucherStatusType.PUBLISH, amount, voucherHistoryEntity, null);

        return voucherRepository.save(voucherEntity).code();
    }

    // 상품권 사용 불가 처리 v2
    @Transactional
    public void disableV2(final RequestContext requestContext, final String code) {
        final String orderId = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");

        final VoucherEntity voucherEntity = voucherRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품권입니다."));

        final VoucherHistoryEntity voucherHistoryEntity = new VoucherHistoryEntity(orderId, requestContext.requesterType(),
                requestContext.requesterId(), VoucherStatusType.DISABLE, "테스트 사용 불가");

        voucherEntity.disable(voucherHistoryEntity);

        // voucherRepository.save(voucherEntity);
    }

    // 상품권 사용 v2
    @Transactional
    public void useV2(final RequestContext requestContext, final String code) {
        final String orderId = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");

        final VoucherEntity voucherEntity = voucherRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품권입니다."));

        final VoucherHistoryEntity voucherHistoryEntity = new VoucherHistoryEntity(orderId, requestContext.requesterType(),
                requestContext.requesterId(), VoucherStatusType.USE, "테스트 사용");

        voucherEntity.use(voucherHistoryEntity);

        // voucherRepository.save(voucherEntity);
    }


    // 상품권 발행 v3
    @Transactional
    public String publishV3(final RequestContext requestContext, final String contractCode, final VoucherAmountType amount) {
        final String code = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
        final String orderId = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");

        final ContractEntity contractEntity = contractRepository.findByCode(contractCode)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 계약입니다."));

        voucherPublishValidator.validate(contractEntity);

        final VoucherHistoryEntity voucherHistoryEntity = new VoucherHistoryEntity(orderId, requestContext.requesterType(),
                requestContext.requesterId(), VoucherStatusType.PUBLISH, "테스트 발행");
        final VoucherEntity voucherEntity = new VoucherEntity(code, VoucherStatusType.PUBLISH,
                amount, voucherHistoryEntity, contractEntity);

        return voucherRepository.save(voucherEntity).code();
    }


    // 상품권 사용 불가 처리 v3
    @Transactional
    public void disableV3(final RequestContext requestContext, final String code) {
        final String orderId = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");

        final VoucherEntity voucherEntity = voucherRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품권입니다."));

        voucherDisableValidator.validate(voucherEntity, requestContext);

        final VoucherHistoryEntity voucherHistoryEntity = new VoucherHistoryEntity(orderId, requestContext.requesterType(),
                requestContext.requesterId(), VoucherStatusType.DISABLE, "테스트 사용 불가");

        voucherEntity.disable(voucherHistoryEntity);
    }

    // 상품권 사용 v3
    @Transactional
    public void useV3(final RequestContext requestContext, final String code) {
        final String orderId = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");

        final VoucherEntity voucherEntity = voucherRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품권입니다."));

        final VoucherHistoryEntity voucherHistoryEntity = new VoucherHistoryEntity(orderId, requestContext.requesterType(),
                requestContext.requesterId(), VoucherStatusType.USE, "테스트 사용");

        voucherEntity.use(voucherHistoryEntity);
    }
}
