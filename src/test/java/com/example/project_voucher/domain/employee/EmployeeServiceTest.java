package com.example.project_voucher.domain.employee;

import com.example.project_voucher.app.controller.response.EmployeeResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class EmployeeServiceTest {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeServiceTest(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @DisplayName("회원 생성 후 조회가 가능")
    @Test
    public void test1() {
        // given
        String name = "우철3";
        String position = "사원3";
        String department = "개발팀3";

        // when
        Long no = employeeService.createEmployee(name, position, department);
        System.out.println(no);
        EmployeeResponse response = employeeService.getEmployee(no);

        // then
        assertThat(response).isNotNull();
        assertThat(response.no()).isEqualTo(no);
        assertThat(response.name()).isEqualTo(name);
        assertThat(response.position()).isEqualTo(position);
        assertThat(response.department()).isEqualTo(department);

        System.out.print(response);
    }
}