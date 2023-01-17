package com.example.apibasic.jpabasic.repository;

import com.example.apibasic.jpabasic.entity.Gender;
import com.example.apibasic.jpabasic.entity.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

// JPA로 CRUD Operation을 하려면 JpaRepository 인터페이스를 상속
// 제너릭 타입으로 첫번째는 CRUD할 Entitiy 클래스의 타입, 두번째는 해당 Entity의 Id 타입
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {


    // 쿼리 메서드는 함수 명을 규칙에 맞게 작성해야 한다

    // 성별로 검색
    List<MemberEntity> findByGender(Gender gender);

    // 계정과 성별로 검색
    MemberEntity findByAccountAndGender (String account, Gender gender);

    // 닉네임에 박이 들어간 회원 검색
    List<MemberEntity> findByNickNameContaining(String lastName);

    // JPQL 메서드 명은 내 마음대로 지어도 된다
    // select 별칭 from 엔터티클래스명 as 별칭 where 별칭.필드명
    // native-sql   : select m.user_code from tbl_member as m
    // jpql         : select m.userId from MemberEntity as m

    // 계정명으로 회원 조회
//    @Query("Select m from MemberEntity as m where m.account=?1")
    @Query("Select m from MemberEntity as m where m.account=:acc")
    MemberEntity getMemberByAccount(@Param("acc") String acc);

    // 닉네임과 성별 동시만족 조건으로 회원 조회
    @Query("select m from MemberEntity as m where m.nickName=:nick and m.gender=:gender")
    List<MemberEntity> getMemberByNickAndGender(@Param("nick") String nick, @Param("gender") Gender gender);

    @Query("select m from MemberEntity m where m.nickName like %:nick%")
    List<MemberEntity> getMembersByNickName(@Param("nick") String nick);

    @Modifying // 수정 삭제할 때 붙이기
    @Query("delete from MemberEntity as m where m.nickName=:nick")
    void deleteByNickName(@Param("nick") String nick);






}
