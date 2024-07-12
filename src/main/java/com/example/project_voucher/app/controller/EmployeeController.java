package com.example.project_voucher.app.controller;

import com.example.project_voucher.app.controller.request.EmployeeCreateRequest;
import com.example.project_voucher.app.controller.response.EmployeeResponse;
import com.example.project_voucher.domain.employee.EmployeeService;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // 사원 생성
    @PostMapping("/api/v1/employee")
    public Long createEmployee(@RequestBody final EmployeeCreateRequest request) {
        return employeeService.createEmployee(request.name(), request.position(), request.department());
    }

    // 사원 조회
    @GetMapping("/api/v1/employee/{no}")
    public EmployeeResponse getEmployee(@PathVariable("no") final Long no) {
        return employeeService.getEmployee(no);
    }
}
