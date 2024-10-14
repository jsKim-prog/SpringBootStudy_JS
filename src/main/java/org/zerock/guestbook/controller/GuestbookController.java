package org.zerock.guestbook.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.guestbook.dto.PageRequestDTO;
import org.zerock.guestbook.service.GuestbookService;

@Controller
@RequestMapping("/guestbook") //http://localhost/guestbook
@Log4j2
@RequiredArgsConstructor //생성자 자동주입(final용)
public class GuestbookController {
    private final GuestbookService service; //@RequiredArgsConstructor 용 필드

    @GetMapping("/")
    public String index(){
        //http://localhost/ -> list
        return "redirect:/guestbook/list";
    }


    @GetMapping({ "/list"}) //http://localhost/guestbook or //http://localhost/guestbook/list
    public String list(PageRequestDTO pageRequestDTO, Model model){
        log.info("GuestbookController.list메서드 실행......");
        model.addAttribute("result", service.getList(pageRequestDTO));
        //페이징처리+dto -> jpa -> entity-> 모든 결과가 model에 저장
        
        return "/guestbook/list"; //resources/templates/guestbook/list.html

    }
}
