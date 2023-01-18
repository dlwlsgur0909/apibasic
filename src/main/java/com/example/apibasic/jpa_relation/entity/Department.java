package com.example.apibasic.jpa_relation.entity;

import lombok.*;

import javax.persistence.*;

@Getter @Setter @ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "deptId")
@Builder
@Entity
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deptId; // 부서 번호

    private String deptName; // 부서명

//    @OneToMany(mappedBy = "department")
//    private List<Employee> empList; // 해당 부서 소속 사원 목록


}
