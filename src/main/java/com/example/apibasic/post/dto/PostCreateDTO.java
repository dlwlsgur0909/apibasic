package com.example.apibasic.post.dto;

import com.example.apibasic.post.entity.PostEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class PostCreateDTO {

    private String writer;
    private String title;
    private String content;
    private List<String> hashTags;

    // PostEntity로 변환하는 유틸 메서드
    public PostEntity toEntity() {
        return PostEntity.builder()
                .postNo(PostEntity.sequence++)
                .writer(this.writer)
                .title(this.title)
                .content(this.content)
                .hashTags(this.hashTags)
                .createDate(LocalDateTime.now())
                .build();
    }

}
