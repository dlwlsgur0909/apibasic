package com.example.apibasic.post.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Page;

@Getter @Setter @ToString
public class PageResponseDTO<T> {

    /*
        PageResponseDTO Example
        pageInfo : {
                    "startPage" : 1,
                    "endPage" : 10,
                    "currentPage" : 3,
                    "prev" : false,
                    "next" : true
                    "totalCount" : 500
                   }
     */


    private int startPage;
    private int endPage;
    private int currentPage;
    private boolean prev;
    private boolean next;
    private int totalCount;

    // 페이지 개수
    private static final int PAGE_COUNT = 10;

    public PageResponseDTO(Page<T> pageData) {
        this.totalCount = (int) pageData.getTotalElements();
        this.currentPage = pageData.getPageable().getPageNumber() + 1;
        this.endPage = (int) (Math.ceil((double) currentPage / PAGE_COUNT) * PAGE_COUNT);
        this.startPage = endPage - (PAGE_COUNT-1);

        // 페이지 마지막 구간에서 endPage값 보정
        // 실제 끝페이지 숫자를 구함
        int realEnd = pageData.getTotalPages();

        // 언제 보정해야 하나? 마지막 구간에서만!
        if(endPage > realEnd) {
            this.endPage = realEnd;

        }
        this.prev = startPage > 1;
        this.next = endPage < realEnd;
    }


}
