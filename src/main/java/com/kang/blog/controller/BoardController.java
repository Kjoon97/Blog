package com.kang.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

    @GetMapping("/")
    public String index(){
        return "index";
    }

    //글 작성 페이지
    @GetMapping("/board/saveForm")
    public String saveForm(){
        return "board/saveForm";
    }
    
}
