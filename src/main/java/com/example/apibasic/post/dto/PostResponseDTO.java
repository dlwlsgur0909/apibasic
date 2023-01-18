package com.example.apibasic.post.dto;

import com.example.apibasic.post.entity.HashTagEntity;
import com.example.apibasic.post.entity.PostEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class PostResponseDTO {

    private String author;
    private String title;
    private String content;
    private List<String> hashTags;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDateTime regDate;

    public PostResponseDTO(PostEntity entity) {
        this.author = entity.getWriter();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.regDate = entity.getCreateDate();
        this.hashTags = entity.getHashTags().stream()
                .map(HashTagEntity::getTagName)
                .collect(Collectors.toList());
    }


}
