package com.example.project_voucher.domain.employee;

import com.example.project_voucher.app.controller.response.EmployeeResponse;
import com.example.project_voucher.storage.employee.EmployeeEntity;
import com.example.project_voucher.storage.employee.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // 사원 생성
    public Long createEmployee(final String name, final String position, final String department) {
        EmployeeEntity employeeEntity = employeeRepository.save(new EmployeeEntity(name, position, department));

        return employeeEntity.getId();
    }

    // 사원 조회
    public EmployeeResponse getEmployee(final Long no) {
        EmployeeEntity employeeEntity = employeeRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        return new EmployeeResponse(employeeEntity.getId(), employeeEntity.getName(), employeeEntity.getPosition(), employeeEntity.getDepartment());
    }
}
