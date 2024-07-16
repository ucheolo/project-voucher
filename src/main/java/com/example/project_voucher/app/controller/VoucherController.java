package com.example.project_voucher.app.controller;

import com.example.project_voucher.app.controller.request.VoucherPublishRequest;
import com.example.project_voucher.app.controller.response.VoucherPublishResponse;
import com.example.project_voucher.domain.service.VoucherService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class VoucherController {
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    // 상품권 발행
    @PostMapping("/api/v1/voucher")
    public VoucherPublishResponse publish(@RequestBody final VoucherPublishRequest request) {
        final String publishedVoucherCode = voucherService.publish(LocalDate.now(), LocalDate.now().plusDays(1830L), request.amountType());
        return new VoucherPublishResponse(publishedVoucherCode);
    }

    // 상품권 사용
    @PutMapping("/api/v1/voucher/use")
    public void use(@RequestBody final String code) {
        voucherService.use(code);
    }

    // 상품권 폐기
    @PutMapping("/api/v1/voucher/disable")
    public void disable(@RequestBody final String code) {
        voucherService.disable(code);
    }
}
