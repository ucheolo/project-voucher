package com.example.project_voucher.domain.service;

import com.example.project_voucher.common.type.VoucherStatusType;
import com.example.project_voucher.storage.voucher.VoucherEntity;
import com.example.project_voucher.storage.voucher.VoucherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    // 상품권 발행
    @Transactional
    public String publish(final LocalDate validFrom, final LocalDate validTo, final Long amount) {
        final String code = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
        final VoucherEntity voucherEntity = new VoucherEntity(code, VoucherStatusType.PUBLISH, validFrom, validTo, amount);

        return voucherRepository.save(voucherEntity).code();
    }


    // 상품권 사용 불가 처리
    @Transactional
    public void disable(final String code) {
        final VoucherEntity voucherEntity = voucherRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품권입니다."));

        voucherEntity.disable();

        // voucherRepository.save(voucherEntity);
    }

    // 상품권 사용
}
