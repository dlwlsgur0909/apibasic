package com.example.apibasic.req;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
@Slf4j
public class ApiController2 {

    // 요청 파라미터 읽기
    @GetMapping("/param1")
    public String param1(@RequestParam("username") String username, @RequestParam("age") int age) {
                        // 파라미터 이름과 변수명이 같을 경우 RequestParam은 생략 가능
        log.info("/param1?username={}&age={} GET!! ", username, age);
        log.info("내 이름은 {}이고 나이는 {}살이다!", username, age);
        return "";
    }

    @GetMapping("/param2")
    public String param2(OrderInfo orderInfo) {
        log.info("/param2?orderNo={}&goodsName={}&goodsAmount={}", orderInfo.getOrderNo(), orderInfo.getGoodsName(), orderInfo.getGoodsAmount());
        log.info("주문 정보: {}", orderInfo);
        return "";
    }

    // 커맨드 객체 : 클라이언트가 보낸 파라미터 이름과 필드명이 정확히 일치해야 함
    @Getter @Setter @ToString
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class OrderInfo {
        private Long orderNo;
        private String goodsName;
        private int goodsAmount;
    }

    // 요청 Body 읽기 (Get을 제외한 나머지 method)
    @PostMapping("/req-body")
    public String reqBody(@RequestBody OrderInfo orderInfo) {
        log.info("주문 정보: {}", orderInfo);
        return "";
    }


}
