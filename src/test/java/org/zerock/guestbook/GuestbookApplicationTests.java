package org.zerock.guestbook;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.guestbook.entity.Guestbook;
import org.zerock.guestbook.entity.QGuestbook;
import org.zerock.guestbook.repository.GuestbookRepository;

@SpringBootTest
class GuestbookApplicationTests {
   @Autowired
    GuestbookRepository guestbookRepository;

    @Test
    void contextLoads() {
    }

    @Test
    public void testQuery1(){
        //쿼리dsl 이용해서 단일검색용 -> 0페이지, 10개, 정렬(gno, 내림), 제목=1 들어있는 조건
        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());
        // 0페이지, 10개, 정렬(gno, 내림)
        QGuestbook qGuestbook = QGuestbook.guestbook; //querydsl 로 객체생성
        String keyword = "1";
        BooleanBuilder builder = new BooleanBuilder(); //where 조건이 있냐? 없냐?
        BooleanExpression expression = qGuestbook.title.contains(keyword); //제목에 1을 넣음
        //**주의! import com.querydsl.core.types.dsl.BooleanExpression;
        builder.and(expression); //검색 where문 붙임
        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable); //찾은 값을 적용
        //builder -> where, pagable -> 페이징 적용
        result.stream().forEach(guestbook -> {
            System.out.println(guestbook);
        });

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
//        where
//        g1_0.title like ? escape '!'
//        order by
//        g1_0.gno desc
//        limit
//                ?, ?
//        Hibernate:
//        select
//        count(g1_0.gno)
//        from
//        guestbook g1_0
//        where
//        g1_0.title like ? escape '!'
//        Guestbook(gno=291, title=제목291, content=내용291, writer=user1)
//        Guestbook(gno=281, title=제목281, content=내용281, writer=user1)
//        Guestbook(gno=271, title=제목271, content=내용271, writer=user1)
//        Guestbook(gno=261, title=제목261, content=내용261, writer=user1)
//        Guestbook(gno=251, title=제목251, content=내용251, writer=user1)
//        Guestbook(gno=241, title=제목241, content=내용241, writer=user1)
//        Guestbook(gno=231, title=제목231, content=내용231, writer=user1)
//        Guestbook(gno=221, title=제목221, content=내용221, writer=user1)
//        Guestbook(gno=219, title=제목219, content=내용219, writer=user9)
//        Guestbook(gno=218, title=제목218, content=내용218, writer=user8)
    }

    @Test
    public void testQueryMulti(){
        //제목과 내용을 where문으로 다중검색
        Pageable pageable = PageRequest.of(0, 7, Sort.by("gno").descending());
        QGuestbook qGuestbook = QGuestbook.guestbook;
        String keyword = "2";
        BooleanBuilder builder = new BooleanBuilder(); //where 생성용
        BooleanExpression exTitle = qGuestbook.title.contains(keyword); //조건1
        BooleanExpression exContent = qGuestbook.content.contains(keyword); //조건2
        //조건들을 합체
        BooleanExpression exAll = exTitle.or(exContent); //where title or content
        builder.and(exAll); //조건문 합체
        builder.and(qGuestbook.gno.gt(0L)); //pk의 index 를 활용하여 빠른 추출
        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);
        result.stream().forEach(guestbook -> {
            System.out.println(guestbook);
        });

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
//        where
//                (
//                        g1_0.title like ? escape '!'
//        or g1_0.content like ? escape '!'
//        )
//        and g1_0.gno>?
//                order by
//        g1_0.gno desc
//        limit
//                ?, ?
//        Hibernate:
//        select
//        count(g1_0.gno)
//        from
//        guestbook g1_0
//        where
//                (
//                        g1_0.title like ? escape '!'
//        or g1_0.content like ? escape '!'
//        )
//        and g1_0.gno>?
//                Guestbook(gno=299, title=제목299, content=내용299, writer=user9)
//        Guestbook(gno=298, title=제목298, content=내용298, writer=user8)
//        Guestbook(gno=297, title=제목297, content=내용297, writer=user7)
//        Guestbook(gno=296, title=제목296, content=내용296, writer=user6)
//        Guestbook(gno=295, title=제목295, content=내용295, writer=user5)
//        Guestbook(gno=294, title=제목294, content=내용294, writer=user4)
//        Guestbook(gno=293, title=제목293, content=내용293, writer=user3)
    }

}
