package com.example.apibasic.post.dto;

import com.example.apibasic.post.entity.PostEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PostResponseOneDTO extends PostResponseDTO {

    // PostResponseDTO를 상속 받으면 중복되는 필드를 생략할 수 있잖아...
    // 응용 좀 잘하자...

    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDateTime modifyDate;


    public PostResponseOneDTO(PostEntity entity) {
        super(entity);
        this.modifyDate = entity.getModifyDate();
    }

}
