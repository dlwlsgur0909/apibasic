package com.example.apibasic.jpabasic.repository;

// junit5에서는 클래스, 메서드, 필드 default 제한만을 허용

import com.example.apibasic.jpabasic.entity.Gender;
import com.example.apibasic.jpabasic.entity.MemberEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@SpringBootTest // 스프링 컨테이너를 사용해서 스프링이 관리하는 객체를 주입받는 기능
class MemberRepositoryTest {

    // 스프링 빈을 주입 받을 때 필드주입을 사용
    @Autowired
    MemberRepository memberRepository;

    // @BeforeEach : 각각의 테스트를 진행하기 전에 공톡적으로 실행되는 내용
    @BeforeEach
    void bulkInsert() {
        MemberEntity saveMember1 = MemberEntity.builder()
                .account("test1234")
                .password("1234")
                .nickName("박꾸러긔")
                .gender(Gender.MALE)
                .build();
        MemberEntity saveMember2 = MemberEntity.builder()
                .account("abc1234")
                .password("4321")
                .nickName("궁예")
                .gender(Gender.MALE)
                .build();
        MemberEntity saveMember3 = MemberEntity.builder()
                .account("ppp999")
                .password("9888")
                .nickName("찬호박")
                .gender(Gender.FEMALE)
                .build();
        MemberEntity saveMember4 = MemberEntity.builder()
                .account("zzz1234")
                .password("4321")
                .nickName("관종")
                .gender(Gender.FEMALE)
                .build();
        MemberEntity saveMember5 = MemberEntity.builder()
                .account("666")
                .password("9123")
                .nickName("헬로")
                .gender(Gender.FEMALE)
                .build();

        memberRepository.save(saveMember1);
        memberRepository.save(saveMember2);
        memberRepository.save(saveMember3);
        memberRepository.save(saveMember4);
        memberRepository.save(saveMember5);

    }

    @Test
    @DisplayName("쿼리 메서드를 사용하여 여성회원만 조회해야 한다")
    void findByGenderTest() {

        // given
        Gender gender = Gender.FEMALE;

        // when
        List<MemberEntity> list = memberRepository.findByGender(gender);

        // then
        list.forEach(m -> {
            System.out.println(m);
            Assertions.assertTrue(m.getGender() == Gender.FEMALE);
        });

    }


    @Test
    @DisplayName("쿼리 메서드를 사용하여 계정명이 abc1234이면서 남성인 회원만 조회해야 한다")
    void findByAccountAndGenderTest() {

        // given
        String account = "abc1234";
        Gender gender = Gender.MALE;

        // when
        MemberEntity member = memberRepository.findByAccountAndGender(account, gender);

        // then
        System.out.println(member);
        Assertions.assertTrue(member.getGender() == Gender.MALE);
        Assertions.assertEquals("abc1234", member.getAccount());
    }


    @Test
    @DisplayName("쿼리 메서드를 사용하여 닉네임에 박이 포함된 회원만 조회해야 한다")
    void findByNickNameContainingTest() {

        // given
        String lastName = "박";

        // when
        List<MemberEntity> list = memberRepository.findByNickNameContaining(lastName);

        // then
        list.forEach( m -> {
            System.out.println(m);
            Assertions.assertTrue(m.getNickName().contains("박"));
        });


    }


    @Test
    @DisplayName("JPQL을 사용하여 계정명이 zzz1234인 회원만 조회해야 한다")
    void jpqlTest1() {

        // given
        String account = "zzz1234";

        // when
        MemberEntity member = memberRepository.getMemberByAccount(account);

        // then
        System.out.println(member);
        Assertions.assertEquals("zzz1234", member.getAccount());


    }

    @Test
    @DisplayName("JPQL을 사용하여 닉네임이 궁예이면서 성별이 남성인 회원을 조회해야 한다")
    void jpqlTest2() {

        // given
        String nickName = "궁예";
        Gender gender = Gender.MALE;

        // when
        List<MemberEntity> list = memberRepository.getMemberByNickAndGender(nickName, gender);

        // then
        list.forEach(m -> {
            System.out.println(m);
            Assertions.assertEquals(Gender.MALE, m.getGender());
            Assertions.assertEquals("궁예", m.getNickName());
        });

    }

    @Test
    @DisplayName("JPQL을 사용하여 닉네임에 박이 포함된 회원만 조회해야 한다")
    void jpqlTest3() {

        // given
        String lastName = "박";

        // when
        List<MemberEntity> list = memberRepository.getMembersByNickName(lastName);

        // then
        list.forEach( m -> {
            System.out.println(m);
            Assertions.assertTrue(m.getNickName().contains("박"));
        });


    }




    // 테스트 메서드
    // 테스트는 여러번 돌려도 성공한 테스트는 계속 성공해야 한다!!!
    // 단언 (Assertion) : 강력히 주장한다
    @Test
    @DisplayName("회원의 가입 정보를 데이터베이스에 저장해야 한다")
    @Transactional
    @Rollback // 테스트가 끝나면 롤백해라
    void saveTest() {
        // given - when - then 패턴
        // given: 테스트시 주어지는 데이터
        MemberEntity saveMember = MemberEntity.builder()
                .account("zzz1234")
                .password("1234")
                .nickName("꾸러긔")
                .gender(Gender.FEMALE)
                .build();

        // when: 실제 테스트 상황
        memberRepository.save(saveMember); // INSERT 쿼리 실행

        Optional<MemberEntity> foundMember = memberRepository.findById(4L); // pk기반 단일 행 조회

        // then: 테스트 결과 단언
        // 회원이 조회되었을 것이다.
        MemberEntity member = foundMember.get();
        Assertions.assertNotNull(member);

        // 회원 테이블에 저장된 회원의 수는 몇 명? => 1명

        // 저장된 회원의 닉네임은 뭘까요? => 꾸러긔
        // param1: 내 기대값, param2: 실제값
        Assertions.assertEquals("꾸러긔", member.getNickName());

    }
    
    @Test
    @DisplayName("회원 목록을 조회하면 3명의 회원정보가 조회되어야 한다")
    @Transactional
    @Rollback
    void findAllTest() {

        // given


        // when


        List<MemberEntity> memberEntityList = memberRepository.findAll();

        // then
        // 조회된 리스트의 사이즈는 3이어야 한다
        Assertions.assertEquals(3, memberEntityList.size());

        // 조회된 리스트의 2번째 회원 닉네임은 궁예여야한다.
        Assertions.assertEquals("궁예", memberEntityList.get(1).getNickName());

        memberEntityList.forEach(System.out::println);

        
    }

    @Test
    @DisplayName("회원 데이터를 3개 등록하고 그 중 하나의 회원을 삭제해야 한다")
    @Transactional
    @Rollback
    void deleteTest() {

        // given
        Long userCode = 2L;


        // when
        memberRepository.deleteById(userCode);
        Optional<MemberEntity> foundMember = memberRepository.findById(userCode);

        // then
        Assertions.assertFalse(foundMember.isPresent());
        Assertions.assertEquals(2, memberRepository.findAll().size());


    }


    @Test
    @DisplayName("2번 회원의 닉네임과 성별을 수정해야 한다")
    @Transactional
    @Rollback
    void modifyTest() {

        // given
        Long userCode = 2L;
        String newNickName = "닭강정";
        Gender newGender = Gender.FEMALE;


        // when
        // JPA에서 수정은 조회 후 setter로 변경 후 다시 save
        Optional<MemberEntity> foundMember = memberRepository.findById(userCode);
        foundMember.ifPresent(m -> {
            m.setNickName(newNickName);
            m.setGender(newGender);
            memberRepository.save(m);
        });


        Optional<MemberEntity> modifiedMember = memberRepository.findById(userCode);

        // then
        Assertions.assertEquals(newNickName, modifiedMember.get().getNickName());
        Assertions.assertEquals(newGender, modifiedMember.get().getGender());

    }



    



}