package com.example.apibasic.jpa_relation;

import com.example.apibasic.jpa_relation.entity.Department;
import com.example.apibasic.jpa_relation.entity.Employee;
import com.example.apibasic.jpa_relation.repository.DepartmentRepository;
import com.example.apibasic.jpa_relation.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
class EmployeeTest {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Test
    void empTest1() {

        Department dept1 = Department.builder()
                .deptName("개발부")
                .build();

       Department dept2 = Department.builder()
                .deptName("영업부")
                .build();

        Employee emp1 = Employee.builder()
                .empName("뽀삐")
                .department(dept2)
                .build();

        departmentRepository.save(dept1);
        departmentRepository.save(dept2);

        employeeRepository.save(emp1);


    }
    
    @Test
    @Transactional
    void empTest2() {
        
        // given
        Employee foundEmp = employeeRepository
                .findById(1L)
                .orElseThrow();

        System.out.println("foundEmp = " + foundEmp.getEmpName());
        System.out.println("foundEmp = " + foundEmp.getDepartment().getDeptName());

    }


}