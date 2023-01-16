package com.example.apibasic.post.dto;

import lombok.*;

import java.util.List;

@Setter @Getter @ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class PostListResponseDTO {

    private int count;
    private List<PostResponseDTO> posts;

}
