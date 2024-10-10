package org.zerock.controller.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity //JPA가 entity를 담당
@Table(name = "tbl_memo") //table 담당(테이블명)
@ToString //객체가 문자열 처리
@Getter //게터메서드
@Builder //빌더 패턴(세터 대신 활용, "."으로 값 추가)-> Memo,builder().memoText("값").memoWriter("값").builder();
@AllArgsConstructor //모든 필드 값으로 생성자 만듦
@NoArgsConstructor //빈 메서드로 생성자 만듦 Memo memo = new Memo();
public class Memo {
    //필드
    @Id //기본키 역할(primary key)
    @GeneratedValue(strategy = GenerationType.IDENTITY) //자동번호 생성(마리아DB용)
    //@GeneratedValue(strategy = GenerationType.SEQUENCE) //자동번호 생성(오라클 시퀀스용)
    //@GeneratedValue(strategy = GenerationType.AUTO) //자동번호 생성(알아서 자동생성)
    //@GeneratedValue(strategy = GenerationType.UUID) //자동번호 생성(16진수화 번호생성(영어, 숫자))
    private Long mno;

    @Column(length = 200, nullable = false) //메모글(길이:200자, null 허용안함)
    private String memoText;

    @Column(length = 20, nullable = false) //작성자(길이: 20자, null 허용안함)
    private String writer;
    //생성자

    //메서드

}

//Hibernate:
//        create table tbl_memo (
//        mno bigint not null auto_increment,
//        memo_text varchar(200) not null,
//        writer varchar(20) not null,
//        primary key (mno)
//        ) engine=InnoDB