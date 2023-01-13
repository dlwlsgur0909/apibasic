package com.example.apibasic.post.api;

import com.example.apibasic.post.dto.PostCreateDTO;
import com.example.apibasic.post.dto.PostResponseDTO;
import com.example.apibasic.post.dto.PostResponseOneDTO;
import com.example.apibasic.post.dto.PostUpdateDTO;
import com.example.apibasic.post.entity.PostEntity;
import com.example.apibasic.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
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
        
        // entity 리스트를 DTO리스트로 변환해서 클라이언트에 응답
        List<PostResponseDTO> responseDTOList = list.stream()
                .map(entity -> new PostResponseDTO(entity))
                .collect(Collectors.toList());

        return ResponseEntity
                .ok()
                .body(responseDTOList);
    }

    // 게시물 개별 조회
    @GetMapping("/{postNo}")
    public ResponseEntity<?> detail(@PathVariable("postNo") Long postNo) { // 파라미터 이름과 변수명이 같으면 PathVariable 안의 내용은 생략 가능
        log.info("/posts/{} GET request", postNo);
        PostEntity entity = postRepository.findOne(postNo);

        // entity를 DTO로 변환
        PostResponseOneDTO responseOneDTO = new PostResponseOneDTO(entity);

        return ResponseEntity
                .ok()
                .body(responseOneDTO);
    }

    // 게시글 등록
    @PostMapping
    public ResponseEntity<?> create(@RequestBody PostCreateDTO createDTO) {
        log.info("/post POST request");
        log.info("게시물 정보: {}", createDTO);

        // DTO를 entity로 변환
        PostEntity entity = createDTO.toEntity();
        boolean flag = postRepository.save(entity);
        return flag
                ? ResponseEntity.ok().body("INSERT-SUCCESS")
                : ResponseEntity.badRequest().body("INSERT-FAIL");
    }

    // 게시글 수정
    @PatchMapping("/{postNo}")
    public ResponseEntity<?> modify(@PathVariable Long postNo, @RequestBody PostUpdateDTO updateDTO) {
        log.info("/post/{} PATCH request", postNo);

        // DTO를 entity로 변환
        PostEntity entity = postRepository.findOne(postNo);
        entity.updateEntity(updateDTO);
        boolean flag = postRepository.save(entity);
        return flag
                ? ResponseEntity.ok().body("UPDATE-SUCCESS")
                : ResponseEntity.badRequest().body("UPDATE-FAIL")
                ;
    }

    // 게시글 삭제
    @DeleteMapping("/{postNo}")
    public ResponseEntity<?> remove(@PathVariable Long postNo) {
        log.info("/post/{} DELETE request", postNo);
        boolean flag = postRepository.delete(postNo);
        return flag
                ? ResponseEntity.ok().body("DELETE-SUCCESS")
                : ResponseEntity.badRequest().body("DELETE-FAIL");
    }








}
