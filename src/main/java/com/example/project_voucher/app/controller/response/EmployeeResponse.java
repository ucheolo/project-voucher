package com.example.project_voucher.app.controller.response;

import java.time.LocalDateTime;

public record EmployeeResponse(Long no, String name, String position, String department,
                               LocalDateTime createAt, LocalDateTime updateAt) {
}
