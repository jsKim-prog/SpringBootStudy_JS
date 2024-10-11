package org.zerock.controller.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.controller.entity.Memo;

import java.util.List;

public interface MemoRepository extends JpaRepository<Memo, Long> {
    //JpaRepository를 상속받으면 모든 기능을 사용함<Class 객체, PK의 Type>
    //jpa를 활용하여 CRUD, paging, sort 까지 알아서 처리하는 객체

    //JpaRepository의 내장된 메서드는 save, findById(키타입), getOne(키타입), deleteByID(키타입), delete(entity 객체)
    //save 메서드는 없으면 생성, 있으면 수정

    //Query Method : 메서드 명을 이용하여 SQL문이 생성됨
    List<Memo> findByMnoBetweenOrderByMnoDesc(Long from, Long to);
    //from~to 까지 mno 값을 이용해서 찾아오고 내림차순 정렬 -> 결과는 List 객체
    
   // Page<Memo> findByMnoBetween(Long from, Long to, Pageable pageable, Sort sort); --sort는 pageable에 바로 넣음
    Page<Memo> findByMnoBetween(Long from, Long to, Pageable pageable);
    //from~to 까지 mno 값을 이용해서 찾아오고 결과를 페이징 처리와 정렬기법을 적용
    
    //@Query : SQL문을 자바용으로 사용(entity 객체를 사용함)
    @Query("select m from Memo m order by m.mno desc ")
    List<Memo> getListDesc();
    //Memo 클래스에 있는 테이블을 mno 기준으로 내림차순 정렬하여 Memo 객체를 리스트에 연결하여 리턴함

    //@Query 파라미터 바인딩(입력값을 받아 처리)
    @Transactional //트랜잭션 처리(동시에 여러 값이 처리될 때)
    @Modifying //수정작업 시 트랜잭션 처리
    @Query("update Memo m set m.memoText = :memoText where m.mno = :mno")
    int updateMemoText(@Param("mno") Long mno, @Param("memoText") String memoText);
    //test 코드에서 int result = memoRepository.updateMemoText(100L, "수정내용"); -> 값을 전달 받음

    //객체를 전달받는 용
    @Transactional
    @Modifying
    @Query("update Memo m set m.memoText = :#{#param.memoText} where m.mno = :#{#param.mno}" )
    int updateMemoBean(@Param("param") Memo memo);

}
