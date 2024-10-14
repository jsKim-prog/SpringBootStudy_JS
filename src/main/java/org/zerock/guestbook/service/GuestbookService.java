package org.zerock.guestbook.service;

import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.dto.PageRequestDTO;
import org.zerock.guestbook.dto.PageResultDTO;
import org.zerock.guestbook.entity.Guestbook;

public interface GuestbookService {
    //CRUD 추상메서드 생성
    //등록메서드(abstract 생략)
    Long register(GuestbookDTO dto); //dto를 받아 처리
    PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO); //PageRequestDTO 요청을 받아서 PageResultDTO 결과를 출력
    //public PageResultDTO(Page<EN> result, Function<EN, DTO> fn)

    //dto <-> entity 변환코드 추가
    default Guestbook dtoToEntity(GuestbookDTO dto){//default : 추상메서드로 처리 안함
        Guestbook entity = Guestbook.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();
        return entity;
    };

    default GuestbookDTO entityToDto(Guestbook entity){
        GuestbookDTO dto = GuestbookDTO.builder()
                .gno(entity.getGno())
                .title(entity.getTitle())
                .content(entity.getContent())
                .writer(entity.getWriter())
                .regDate(entity.getRegDate()) //db에 있는 날짜정보를 가져와야 함
                .modDate(entity.getModDate())
                .build();
        return dto;
    }
}
