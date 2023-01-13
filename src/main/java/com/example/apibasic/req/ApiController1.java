package com.example.apibasic.req;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.net.http.HttpHeaders;
import java.util.Enumeration;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller // 클라이언트가 보낸 요청을 받을 수 있는 어노테이션
@ResponseBody
@Slf4j // 로그를 찍을 때 사용하는 어노테이션
@RequestMapping("/say") // 클래스 레벨에 어노테이션을 붙이면 url 레벨을 관리하기 용이하다
public class ApiController1 {

    // 요청을 받아서 처리할 메서드
    @RequestMapping(value = "/hello", method = {GET, POST})
    public String hello(HttpServletRequest request) {
        log.trace("trace 로그~~~~");
        log.debug("debug 로그~~~~");
        log.info("hello~~ spring! - {}", request.getMethod());
        log.warn("warn 로그~~~~");
        log.error("error 로그~~~~");
        return "";
    }


    // Get 요청만 따로 처리하겠다
    @GetMapping("/greet") // allow method가 두 개 이상일 경우는 위처럼 처리해야 함
    public String greet() {
        log.info("/greet GET 요청");
        return "";
    }
    @PostMapping("/greet") // allow method가 두 개 이상일 경우는 위처럼 처리해야 함
    public String greet2() {
        log.info("/greet POST 요청");
        return "";
    }


    // 요청 헤더 읽기
    @GetMapping("/header")
    public String header(HttpServletRequest request) {

        String host = request.getHeader("Host");
        String accept = request.getHeader("Accept");
        String pet = request.getHeader("pet"); // Postman에서 임의로 만든 request header
        log.info("===== header info =====");
        log.info("# Host : {}", host);
        log.info("# Accept : {}", accept);
        log.info("# Pet : {}", pet);
        return "";
    }


}
