package org.zerock.guestbook.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass //entity지만 직접 테이블을 생성하지 않고, 부모 클래스 역할을 제공
@EntityListeners(value = {AuditingEntityListener.class}) //main 메서드 동작시 감시체제 발동!!
@Getter
public class BaseEntity {
    //모든 table에는 등록날짜+수정시간이 공통으로 들어간다.
    //감시체제를 활용하여 객체 수정 시 날짜 수정까지 제공함

    @CreatedDate //최초로 생성시에만 동작
    @Column(name = "regdate", updatable = false) //db 저장시 필드명은 regdate, 업데이트는 금지
    private LocalDateTime regDate; //등록일

    @LastModifiedDate //마지막 수정일 동작
    @Column(name = "moddate") //db 저장시 필드명은 moddate
    private LocalDateTime modDate; //수정일
}
