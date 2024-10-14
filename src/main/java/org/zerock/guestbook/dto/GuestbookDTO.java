package org.zerock.guestbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder //setter 대체용
@AllArgsConstructor
@NoArgsConstructor
@Data //toString, equals...
public class GuestbookDTO {
    //entity에 있는 정보를 객체화 시킴
    private Long gno;
    private  String title, content, writer;
    private LocalDateTime regDate, modDate;
}
