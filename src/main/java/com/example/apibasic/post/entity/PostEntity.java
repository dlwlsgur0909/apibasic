package com.example.apibasic.post.entity;

import com.example.apibasic.post.dto.PostResponseDTO;
import com.example.apibasic.post.dto.PostUpdateDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

// 게시물의 데이터 자바빈즈
@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="postNo")
@Builder
// JPA
@Entity
@Table(name = "tbl_post")
public class PostEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postNo; // 게시물 식별 번호
    @Column(nullable = false, unique = true)
    private String writer; // 작성자
    @Column(nullable = false)
    private String title; // 제목
    @Column(nullable = false)
    private String content; // 내용
//    private List<String> hashTags; // 해시태그 목록
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   @CreationTimestamp
    private LocalDateTime createDate; // 작성 시간
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   @UpdateTimestamp
    private LocalDateTime modifyDate; // 수정 시간


    public void updateEntity(PostUpdateDTO updateDTO) {
        if(updateDTO.getContent()!=null) {
            this.content = updateDTO.getContent();
        }
        if(updateDTO.getTitle()!=null) {
            this.title = updateDTO.getTitle();
        }

//        this.modifyDate = LocalDateTime.now();
    }



}
