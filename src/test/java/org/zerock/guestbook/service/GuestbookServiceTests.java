package org.zerock.guestbook.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.dto.PageRequestDTO;
import org.zerock.guestbook.dto.PageResultDTO;
import org.zerock.guestbook.entity.Guestbook;

@SpringBootTest
@Log4j2
public class GuestbookServiceTests {
    @Autowired
    private GuestbookService service;

    @Test
    public void testRegister(){
        GuestbookDTO dto = GuestbookDTO.builder()
                .title("서비스 테스트용 제목...")
                .content("서비스 테스트용 내용...")
                .writer("서비스 사용자")
                .build();
        log.info("입력 dto"+dto); //입력 dtoGuestbookDTO(gno=null, title=서비스 테스트용 제목..., content=서비스 테스트용 내용..., writer=서비스 사용자, regDate=null, modDate=null)
        log.info("서비스 변환 entity : "+service.register(dto));
        //변환된 entity : Guestbook(gno=null, title=서비스 테스트용 제목..., content=서비스 테스트용 내용..., writer=서비스 사용자)
        // 서비스 변환 entity : 301

    }

    @Test
    public void testList(){
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1).size(10).build();
        PageResultDTO<GuestbookDTO, Guestbook> resultDTO = service.getList(pageRequestDTO);
        for(GuestbookDTO guestbookDTO:resultDTO.getDtoList()){
            System.out.println("방명록 리스트"+guestbookDTO);
        }
        System.out.println("페이징 처리"+pageRequestDTO);
        System.out.println("이전페이지 : "+resultDTO.isPrev());
        System.out.println("다음페이지 : "+resultDTO.isNext());
        System.out.println("총페이지 : "+resultDTO.getTotalPage());
        System.out.println("------------------------------");
        resultDTO.getPageList().forEach(i-> System.out.print(i+" "));
//        Hibernate:
//        select
//        g1_0.gno,
//                g1_0.content,
//                g1_0.moddate,
//                g1_0.regdate,
//                g1_0.title,
//                g1_0.writer
//        from
//        guestbook g1_0
//        order by
//        g1_0.gno desc
//        limit
//                ?, ?
//        Hibernate:
//        select
//        count(g1_0.gno)
//        from
//        guestbook g1_0
//        방명록 리스트GuestbookDTO(gno=301, title=서비스 테스트용 제목..., content=서비스 테스트용 내용..., writer=서비스 사용자, regDate=2024-10-14T10:57:05.576831, modDate=2024-10-14T10:57:05.576831)
//        방명록 리스트GuestbookDTO(gno=300, title=수정된 제목..., content=수정된 내용..., writer=user0, regDate=2024-10-11T15:48:34.540753, modDate=2024-10-11T15:57:58.815768)
//        방명록 리스트GuestbookDTO(gno=299, title=제목299, content=내용299, writer=user9, regDate=2024-10-11T15:48:34.531746, modDate=2024-10-11T15:48:34.531746)
//        방명록 리스트GuestbookDTO(gno=298, title=제목298, content=내용298, writer=user8, regDate=2024-10-11T15:48:34.513895, modDate=2024-10-11T15:48:34.513895)
//        방명록 리스트GuestbookDTO(gno=297, title=제목297, content=내용297, writer=user7, regDate=2024-10-11T15:48:34.501751, modDate=2024-10-11T15:48:34.501751)
//        방명록 리스트GuestbookDTO(gno=296, title=제목296, content=내용296, writer=user6, regDate=2024-10-11T15:48:34.496741, modDate=2024-10-11T15:48:34.496741)
//        방명록 리스트GuestbookDTO(gno=295, title=제목295, content=내용295, writer=user5, regDate=2024-10-11T15:48:34.489755, modDate=2024-10-11T15:48:34.489755)
//        방명록 리스트GuestbookDTO(gno=294, title=제목294, content=내용294, writer=user4, regDate=2024-10-11T15:48:34.486755, modDate=2024-10-11T15:48:34.486755)
//        방명록 리스트GuestbookDTO(gno=293, title=제목293, content=내용293, writer=user3, regDate=2024-10-11T15:48:34.482801, modDate=2024-10-11T15:48:34.482801)
//        방명록 리스트GuestbookDTO(gno=292, title=제목292, content=내용292, writer=user2, regDate=2024-10-11T15:48:34.477757, modDate=2024-10-11T15:48:34.477757)
//        페이징 처리PageRequestDTO(page=1, size=10)
/*        이전페이지 : false
        다음페이지 : true
        총페이지 : 31
                ------------------------------
        1 2 3 4 5 6 7 8 9 10*/
    }
}
