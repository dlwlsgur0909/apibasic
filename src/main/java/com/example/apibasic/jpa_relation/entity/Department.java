package com.example.apibasic.jpa_relation.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter @ToString(exclude = "employees") // employees 필드는 ToString에서 제외
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

    // 양방향 매핑에서는 상대방 엔터티의 정보를 수정할 수는 없고
    // 단순한 읽기(조회) 기능만 지원한다
    // mappedBy에는 상대방 엔터티의 조인되는 필드명을 쓴다
    @OneToMany(mappedBy = "department")
    private List<Employee> employees = new ArrayList<>(); // 해당 부서 소속 사원 목록


}
