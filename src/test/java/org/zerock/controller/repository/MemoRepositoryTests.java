package org.zerock.controller.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.controller.entity.Memo;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Log4j2
@SpringBootTest //부트용 테스트 코드 실행기반
public class MemoRepositoryTests {
    @Autowired //객체 자동주입
    MemoRepository memoRepository; //MemoRepository memoRepository = new MemoRepository();

    @Test //메서드 기반 테스트
    public void testClass() {
        //객체 사용 여부를 체크
        log.info("testClass() 메서드 실행......" + memoRepository.getClass().getName());
    }

    @Test
    public void testInsertDummies() {
        //메모 테이블에 더미데이터 100개 입력 테스트 : save()
        IntStream.rangeClosed(1, 100).forEach(
                i -> {
                    Memo memo = Memo.builder()
                            .memoText("sampleMemo..." + i)
                            .writer("kkk" + i)
                            .build(); //setter 완료
                    memoRepository.save(memo); //insert into용 코드
                }
        );
    }

    @Test
    public void testSelect() {
        //검색하여 가져옴(select * from)
        Long mno = 100L;
        Optional<Memo> result = memoRepository.findById(mno);
        //Optional<객체> : 객체가 있으면 가져옴 
        //findById(값) : select * from tbl_memo where mno = 값
        log.info("========================================");
        if (result.isPresent()) {//객체가 있는지 확인용
            Memo memo = result.get(); //찾아온 값을 가져와 Memo 객체에 넣음
            log.info("100번 값 가져오기 결과 : " + memo); //100번 값 가져오기 결과 : Memo(mno=100, memoText=샘플메모...100, writer=작성자100)
        }
    }

    @Transactional //springFramework, 동시에 다중 쿼리용 --no Session 필수 코드
    @Test
    public void testSelect2() {
        //getOne() 메서드도 찾아오는 값 - 방식이 좀 다름
        Long mno = 100L;
        Memo memo = memoRepository.getOne(mno); //step2 -> 실행순서 다름(코드의 역행)->예외발생가능
        log.info("====================="); //step1
        log.info("100번 객체를 가져옴(getOne() 활용): " + memo); //step3
    }

    @Test
    public void testUpdate() {
        //1개를 가져와 수정하는 쿼리 : save()(없으면 insert, 있으면 update)
        Memo memo = Memo.builder()
                .mno(100L)
                .memoText("수정한 메모...")
                .writer("김수정").
                build(); //객체생성 완료
        memoRepository.save(memo); //update용 코드
//        Hibernate:  select 선행작업으로 기존 데이터 존재 파악 후 update 진행
//        select
//        m1_0.mno,
//                m1_0.memo_text,
//                m1_0.writer
//        from
//        tbl_memo m1_0
//        where
//        m1_0.mno=?
//        Hibernate:
//        update
//                tbl_memo
//        set
//        memo_text=?,
//        writer=?
//        where
//        mno=?
    }

    @Test
    public void testDelete(){
        //mno를 가지고 객체를 삭제한다.
        memoRepository.deleteById(100L);

//        Hibernate: select 후 delete 진행
//        select
//        m1_0.mno,
//                m1_0.memo_text,
//                m1_0.writer
//        from
//        tbl_memo m1_0
//        where
//        m1_0.mno=?
//        Hibernate:
//        delete
//                from
//        tbl_memo
//                where
//        mno=?
    }
    
    @Test
    public void testPageDefault(){
        Sort sort1 = Sort.by("mno").descending();
        Pageable pageable = PageRequest.of(0, 10, sort1);
        //Pageable 인터페이스의 PageRequest 구현클래스의 of 내장메서드를 사용함
        Page<Memo> result = memoRepository.findAll(pageable);
        //Page 인터페이스는 나뉘어진 블럭화 객체
        log.info("0페이지, 10개 값 출력" + result); //0페이지, 10개 값 출력Page 1 of 20 containing org.zerock.ex00.entity.Memo instances

        log.info("=============================================");
        System.out.println("총 페이지수 : "+result.getTotalPages());
        System.out.println("전체 개수 : "+ result.getTotalElements());
        System.out.println("현재 페이지 번호 : "+result.getNumber());
        System.out.println("페이지당 데이터 개수 : "+result.getSize());
        System.out.println("다음 페이지 여부 : "+result.hasNext());
        System.out.println("시작 페이지 여부" + result.isFirst());
        System.out.println("마지막 페이지 여부 : "+result.isLast());

        System.out.println("=======현재 결과물 출력============");
        for (Memo memo:result.getContent()){
            System.out.println(memo);
        }
//        Hibernate:
//        select
//        m1_0.mno,
//                m1_0.memo_text,
//                m1_0.writer
//        from
//        tbl_memo m1_0
//        order by -------- Sort 사용 결과
//        m1_0.mno desc
//        limit    -------- oracle의 rownum 대신
//                ?, ?
//        Hibernate:
//        select
//        count(m1_0.mno)
//        from
//        tbl_memo m1_0
    }

    //List<Memo> findByMnoBetweenOrderByMnoDesc(Long from, Long to);
    @Test
    public void testQueryMethods(){
        List<Memo> list = memoRepository.findByMnoBetweenOrderByMnoDesc(40L, 50L);
        for (Memo memo:list){
            System.out.println("결과물 : " + memo);
        }
    }

    @Test
    public void testQueryMethodWithPageAndSort(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());
        Page<Memo> result = memoRepository.findByMnoBetween(10L,50L,pageable);
        result.get().forEach(memo -> {
            System.out.println(memo);
        });
    }

}
