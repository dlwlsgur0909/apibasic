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

import java.util.List;
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
                .empName("푸파파")
                .department(dept1)
                .build();

        Employee emp2 = Employee.builder()
                .empName("헬로키티")
                .department(dept1)
                .build();

        departmentRepository.save(dept1);
        departmentRepository.save(dept2);

        employeeRepository.save(emp1);
        employeeRepository.save(emp2);


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


    @Test
    @Transactional
    void empTest3() {

        Department dept = departmentRepository
                .findById(1L)
                .orElseThrow();

        System.out.println("dept = " + dept);

        List<Employee> employees = dept.getEmployees();

        employees.forEach(System.out::println);


    }


}