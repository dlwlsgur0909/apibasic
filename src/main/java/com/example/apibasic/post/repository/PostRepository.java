package com.example.apibasic.post.repository;

import com.example.apibasic.post.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
// 기존의 @Repository 어노테이션을 사용하지 않아도 JpaRepository를 상속 받으면 자동으로 등록해준다 (프록시?)
}
