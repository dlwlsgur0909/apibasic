package com.example.apibasic.jpa_relation.entity;

import lombok.*;

import javax.persistence.*;

@Setter @Getter @ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "empId")
@Builder
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long empId; // 사원 번호

    private String empName; // 사원 이름

    /*
        EAGER : 항상 조인을 하도록 설정 (Default 값)
        LAZY : 부서 정보가 필요할 때만 조인 (실무에서는 ManyToOne시 무조건 LAZY 설정을 한다)
     */

    @ManyToOne (fetch = FetchType.LAZY) // 다대일 연관 관계 매핑
    @JoinColumn(name = "dept_id") // FK 컬럼 이름 지정
    private Department department; // 부서 정보





}
