package org.zerock.guestbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Builder
@AllArgsConstructor
//@NoArgsConstructor --기본 생성자 수동으로 만들었기 때문에 안먹힘
@Data
public class PageRequestDTO {
    //리스트 요청 시 페이징 처리 사용하는 데이터를 재사용하기 위함
    //페이지번호, 목록의 개수, 검색 조건 등...
    //필드
    private int page;
    private int size;
    
    //기본생성자
    public PageRequestDTO(){
        this.page = 1; //기본 페이지 번호
        this.size=10; //기본 게시물수
    }
    
    //메서드
    public Pageable getPageable(Sort sort){
        return PageRequest.of(page-1, size, sort);
        // Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());
    }
    
}
