package com.example.apibasic.post.service;

import com.example.apibasic.post.dto.*;
import com.example.apibasic.post.entity.HashTagEntity;
import com.example.apibasic.post.entity.PostEntity;
import com.example.apibasic.post.repository.HashTagRepository;
import com.example.apibasic.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
//@Component // 스프링빈 등록 기본 어노테이션
@Service
public class PostService {

    private final PostRepository postRepository;
    private final HashTagRepository hashTagRepository;

    // 목록 조회 중간처리
    public PostListResponseDTO getList(PageRequestDTO pageRequestDTO) {

        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSizePerPage(),
                Sort.Direction.DESC,
                "createDate"
        );

        final Page<PostEntity> pageData = postRepository.findAll(pageable);

        List<PostEntity> list = pageData.getContent();

        if(list.isEmpty()) {
            throw new RuntimeException("조회 결과가 없어용~");
        }

        // entity 리스트를 DTO리스트로 변환해서 클라이언트에 응답
        List<PostResponseDTO> responseDTOList = list.stream()
                .map(PostResponseDTO::new)
                .collect(Collectors.toList());

        PostListResponseDTO listResponseDTO = PostListResponseDTO.builder()
                .count(responseDTOList.size())
                .pageInfo(new PageResponseDTO<PostEntity>(pageData))
                .posts(responseDTOList)
                .build();

        return listResponseDTO;
    }


    // 개별 조회 중간 처리
    public PostResponseOneDTO getDetail(Long postNo) {
        PostEntity post = postRepository
                .findById(postNo)
                .orElseThrow(() ->
                        new RuntimeException(postNo + "번 게시물이 존재하지 않습니다!")
                );

        // orElse: null일 경우 매개변수 안으로 대체하겠다
        // orElseThrow: null일 경우 매개변수 에러를 발생시키겠다


        // entity를 dto로 변환
        return new PostResponseOneDTO(post);
    }


    // 등록 중간처리
    @Transactional // DML 쿼리가 여러개 동시에 나가는 상황에서 트랜잭션 처리
    public PostResponseOneDTO insert(final PostCreateDTO createDTO)
            throws IllegalArgumentException, OptimisticLockingFailureException // RuntimeException의 종류
    {


        // dto를 entity로 변환
        final PostEntity entity = createDTO.toEntity();

        // entity 저장
        PostEntity savedPost = postRepository.save(entity);

        // hashtag를 DB에 저장
        List<String> hashTags = createDTO.getHashTags();

        // hashtag 문자열 리스트에서 문자열들을 하나하나 추출한 뒤 hashtag entity로 만들고
        // 그 entity들을 DB에 저장한다
        List<HashTagEntity> hashTagEntities = new ArrayList<>(); // 저장할 태그들을 담을 리스트
        hashTags.forEach(ht -> {
            HashTagEntity tagEntity = HashTagEntity.builder()
                    .post(savedPost)
                    .tagName(ht)
                    .build();

            HashTagEntity savedTag = hashTagRepository.save(tagEntity); // 태그를 DE에 저장하지만 Transactional로 인해 바로 조회가 불가능
            hashTagEntities.add(savedTag); // commit전에 태그들을 조회하기 위해서 리스트에 태그들을 담는다
        });

        savedPost.setHashTags(hashTagEntities); // setter를 통해서 저장된 해시태그 리스트들을 게시물 entity에 추가

        // 저장된 객체를 DTO로 변환
        return new PostResponseOneDTO(savedPost);
    }

    //  수정 중간 처리
    public PostResponseOneDTO update(Long postNo, final PostUpdateDTO updateDTO)
        throws RuntimeException
    {
        // DTO를 entity로 변환
        final PostEntity entity = postRepository
                .findById(postNo)
                .orElseThrow(() -> new RuntimeException("Data Does Not Exist"));
        // 수정
        entity.updateEntity(updateDTO);

        // 수정 된 entity 저장
        PostEntity modifiedPost = postRepository.save(entity);

        // 수정된 entity를 DTO로 변환
        return new PostResponseOneDTO(modifiedPost);
    }

    // 삭제 중간처리
    public void delete(final Long postNo)
        throws RuntimeException
    {
        postRepository.deleteById(postNo);
    }




}
