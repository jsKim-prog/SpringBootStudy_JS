package org.zerock.controller;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Log4j2 //build.gradle에 테스트 롬북 코드 추가
@SpringBootTest
class Ex00ApplicationTests {

    @Test
    void contextLoads() {
        System.out.println("contextLoads 메서드 테스트");
        //단축 sout 자동완성 enter
        log.info("contextLoads 메서드 테스트");

    }

}
