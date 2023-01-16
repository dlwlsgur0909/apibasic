package com.example.apibasic.post.api;

import com.example.apibasic.post.dto.*;
import com.example.apibasic.post.entity.PostEntity;
import com.example.apibasic.post.repository.PostRepository;
import com.example.apibasic.post.service.PostService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// 리소스 : 게시물(Post)
/*
    게시물 목록 조회:  /posts               -   GET
    게시물 개별 조회:  /posts/{id}          -   GET
    게시물 등록:      /posts/{id}          -   POST
    게시물 수정:      /posts/{id}          -   PATCH
    게시물 삭제:      /posts/{id}          -   DELETE
 */

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostApiController {

    // PostRepository에게 의존
    private final PostService postService; // final로 선언하면 @RequiredArgsConstructor로 인해 자동으로 생성자 주입이 된다

    // 생성자 주입
    /*
        @Autowired // 스프링 컨테이너에게 의존 객체를 자동주입 해달라 요청하는 어노테이션
        public PostApiController(PostRepository postRepository) {
            this.postRepository = postRepository;
        }
     */

    // Setter 주입 -> 원하면 아무때나 호출할 수 있기에 사용하면 안된다
    /*
         public void setPostRepository(PostRepository postRepository) {
            this.postRepository = postRepository;
         }
     */

    // field 주입 -> 만약 의존하는 클래스가 많아진다면 하나씩 전부다 변경해야 되기에 사용하지 않는다
    /*
        private PostRepository postRepository = new PostRepository();
     */

    // 게시물 목록 조회
    @GetMapping
    public ResponseEntity<?> list() {
        log.info("/posts GET request");

        try {
            PostListResponseDTO listResponseDTO = postService.getList();

            return ResponseEntity
                    .ok()
                    .body(listResponseDTO);
        } catch (Exception e) {
            return ResponseEntity
                    .noContent()
                    .build();
        }
    }

    // 게시물 개별 조회
    @GetMapping("/{postNo}")
    public ResponseEntity<?> detail(@PathVariable("postNo") Long postNo) { // 파라미터 이름과 변수명이 같으면 PathVariable 안의 내용은 생략 가능
        log.info("/posts/{} GET request", postNo);
        // entity를 DTO로 변환
        try {
            PostResponseOneDTO responseOneDTO = postService.getDetail(postNo);

            return ResponseEntity
                    .ok()
                    .body(responseOneDTO);
        } catch (Exception e) {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    // 게시글 등록
    @PostMapping
    @Parameters({
            @Parameter(name = "작성자", description = "게시물 작성자를 입력", example = "김철수"),
            @Parameter(name = "게시글 제목", description = "게시글 제목을 입력", example = "1번 게시글")
    })

    public ResponseEntity<?> create(@Validated @RequestBody PostCreateDTO createDTO,
                                    BindingResult result // 검증 에러 정보를 갖고 있는 객체
                                    ) {

        if(result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            fieldErrors.forEach(err -> {
                log.warn("invalidated client data -> {}", err.toString());
            });
            return ResponseEntity
                    .badRequest()
                    .body(fieldErrors);
        }

        log.info("/post POST request");
        log.info("게시물 정보: {}", createDTO);

        // DTO를 entity로 변환
        return postService.insert(createDTO)
                ? ResponseEntity.ok().body("INSERT-SUCCESS")
                : ResponseEntity.badRequest().body("INSERT-FAIL");
    }

    // 게시글 수정
    @PatchMapping("/{postNo}")
    public ResponseEntity<?> modify(@PathVariable Long postNo, @RequestBody PostUpdateDTO updateDTO) {
        log.info("/post/{} PATCH request", postNo);

        // DTO를 entity로 변환
        return postService.update(postNo, updateDTO)
                ? ResponseEntity.ok().body("UPDATE-SUCCESS")
                : ResponseEntity.badRequest().body("UPDATE-FAIL")
                ;
    }

    // 게시글 삭제
    @DeleteMapping("/{postNo}")
    public ResponseEntity<?> remove(@PathVariable Long postNo) {
        log.info("/post/{} DELETE request", postNo);
        return postService.delete(postNo)
                ? ResponseEntity.ok().body("DELETE-SUCCESS")
                : ResponseEntity.badRequest().body("DELETE-FAIL");
    }








}
