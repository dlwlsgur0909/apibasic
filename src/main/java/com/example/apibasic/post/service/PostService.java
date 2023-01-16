package com.example.apibasic.post.service;

import com.example.apibasic.post.dto.*;
import com.example.apibasic.post.entity.PostEntity;
import com.example.apibasic.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
//@Component // 스프링빈 등록 기본 어노테이션
@Service
public class PostService {

    private final PostRepository postRepository;

    // 목록 조회 중간처리
    public PostListResponseDTO getList() {
        List<PostEntity> list = postRepository.findAll();

        if(list.isEmpty()) {
            throw new RuntimeException("조회 결과가 없어용~");
        }

        // entity 리스트를 DTO리스트로 변환해서 클라이언트에 응답
        List<PostResponseDTO> responseDTOList = list.stream()
                .map(PostResponseDTO::new)
                .collect(Collectors.toList());

        return PostListResponseDTO.builder()
                .count(responseDTOList.size())
                .posts(responseDTOList)
                .build();
    }


    // 개별 조회 중간 처리
    public PostResponseOneDTO getDetail(Long postNo) {
        PostEntity post = postRepository.findOne(postNo);

        if(post == null) throw new RuntimeException(postNo + "번 게시물이 존재하지 않습니다!");

        // entity를 dto로 변환

        return new PostResponseOneDTO(post);
    }


    // 등록 중간처리
    public boolean insert(final PostCreateDTO createDTO) {

        // dto를 entity로 변환
        final PostEntity entity = createDTO.toEntity();
        return postRepository.save(entity);
    }

    //  수정 중간 처리
    public boolean update(Long postNo, final PostUpdateDTO updateDTO) {
        // DTO를 entity로 변환
        final PostEntity entity = postRepository.findOne(postNo);
        entity.updateEntity(updateDTO);

        return postRepository.save(entity);
    }

    // 삭제 중간처리
    public boolean delete(Long postNo) {
        return postRepository.delete(postNo);
    }




}
