package com.example.apibasic.post.api;

import com.example.apibasic.post.entity.PostEntity;
import com.example.apibasic.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    private final PostRepository postRepository; // final로 선언하면 @RequiredArgsConstructor로 인해 자동으로 생성자 주입이 된다

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
        List<PostEntity> list = postRepository.findAll();
        return ResponseEntity
                .ok()
                .body(list);
    }

    // 게시물 개별 조회
    @GetMapping("/{postNo}")
    public ResponseEntity<?> detail(@PathVariable("postNo") Long postNo) { // 파라미터 이름과 변수명이 같으면 PathVariable 안의 내용은 생략 가능
        log.info("/posts/{} GET request", postNo);
        return ResponseEntity
                .ok()
                .body(postRepository.findOne(postNo));
    }

    // 게시글 등록
    @PostMapping
    public ResponseEntity<?> create(@RequestBody PostEntity entity) {
        log.info("/post POST request");
        log.info("게시물 정보: {}", entity);
        boolean flag = postRepository.save(entity);
        return ResponseEntity.ok().body("INSERT-SUCCESS");
    }

    // 게시글 수정
    @PatchMapping("/{postNo}")
    public ResponseEntity<?> modify(@PathVariable Long postNo) {
        log.info("/post/{} PATCH request", postNo);
        return null;
    }

    // 게시글 삭제
    @DeleteMapping("/{postNo}")
    public ResponseEntity<?> remove(@PathVariable Long postNo) {
        log.info("/post/{} DELETE request", postNo);
        return null;
    }








}
