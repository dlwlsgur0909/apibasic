package com.example.apibasic.post.repository;

import com.example.apibasic.post.dto.PageRequestDTO;
import com.example.apibasic.post.dto.PageResponseDTO;
import com.example.apibasic.post.entity.PostEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

@SpringBootTest
class PageTest {

    @Autowired
    PostRepository postRepository;

    @BeforeEach
    void bulkInsert() {
        for (int i = 1; i <= 433; i++) {
            PostEntity post = PostEntity.builder()
                    .title("안녕~~" + i)
                    .writer("김말종" + i)
                    .content("아무말아무말~~" + i)
                    .build();
            postRepository.save(post);
        }
    }

    @Test
    void pagingTest() {

        // 클라이언트가 전달한 페이지 정보
        PageRequestDTO dto = PageRequestDTO.builder()
                .page(4)
                .sizePerPage(10)
                .build();

        // 페이지 정보 생성 (정렬이 필요없다면 3번째 4번째 파라미터는 생략 가능)
        PageRequest pageInfo = PageRequest.of(
                dto.getPage()-1,
                dto.getSizePerPage(),
                Sort.Direction.DESC, // 내림차 정렬
                "createDate"); // 정렬 기준 필드

        Page<PostEntity> postEntities = postRepository.findAll(pageInfo);

        // 실제 조회된 데이터
        List<PostEntity> contents = postEntities.getContent();

        int totalPages = postEntities.getTotalPages();
        long totalElements = postEntities.getTotalElements();

        System.out.println("totalElements = " + totalElements);
        System.out.println("totalPages = " + totalPages);

        System.out.println(contents.size());
        contents.forEach(System.out::println);

    }

    @Test
    @DisplayName("제목에 3이 포함된 게시글 검색 후 1페이지 정보를 조회해야 한다")
    void pageTest2() {

        // given
        String title = "3";
        PageRequest pageRequest = PageRequest.of(
                0,
                10,
                Sort.Direction.DESC,
                "createDate");

        Page<PostEntity> postEntityPage = postRepository.findByTitleContaining(title, pageRequest);

        List<PostEntity> contents = postEntityPage.getContent();

        boolean next = postEntityPage.hasNext();
        boolean previous = postEntityPage.hasPrevious();

        System.out.println("next = " + next);
        System.out.println("previous = " + previous);

        contents.forEach(System.out::println);

        PageResponseDTO<PostEntity> pageResponseDTO = new PageResponseDTO<>(postEntityPage);
        System.out.println(pageResponseDTO);

    }




}
