package com.example.apibasic.res;

import com.example.apibasic.req.ApiController2;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@Slf4j
public class ApiController3 {

    @GetMapping("/res1")
    public String res1() {
        log.info("/res1 GET!!");
        return "hello";
    }

    @GetMapping(value = "/res2" , produces = "application/json") // produce = "application/json"은 default 값으로 생략 가능
    public List<String> res2() {
        log.info("/res2 GET!!");
        return List.of("짜장면", "짬뽕", "탕수육");
    }
    @GetMapping(value = "/res3" , produces = "application/json")
    public ApiController2.OrderInfo res3() {
        log.info("/res3 GET!!");
        ApiController2.OrderInfo orderInfo = new ApiController2.OrderInfo();
        orderInfo.setOrderNo(12L);
        orderInfo.setGoodsName("phone");
        orderInfo.setGoodsAmount(2);
        return orderInfo;
    }

    // 사원 목록 정보
    @GetMapping("/employees")
    public List<Employee> empList() {
        return List.of(
                new Employee("홍길동", "영업부", LocalDate.of(2019, 12, 11)),
                new Employee("박영희", "개발부", LocalDate.of(2010, 6, 22)),
                new Employee("김수호", "인사부", LocalDate.of(2022, 4, 3))
        );
    }


    @Setter @Getter @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    @Builder
    public static class Employee {
        private String empName;
        private String deptName;
        private LocalDate hireDate;

    }


    // 응답시에 응답 헤더 정보와 응답 상태 코드를 조작하기 위해 ResponseEntity객체 사용
    @GetMapping("/res4")
    public ResponseEntity<?> res4(String nick) {
        if(nick == null || nick.equals("")) {
            return ResponseEntity.badRequest().build();
        }

        // 응답 헤더 생성 방법
        HttpHeaders headers = new HttpHeaders();
        headers.set("department", "business");
        headers.set("blahblah", "hahahoho");

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new Employee(nick, "영업부", LocalDate.now()));
    }













}
