package com.kang.blog.controller;

import com.kang.blog.model.Board;
import com.kang.blog.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/")
    public String index(Model model){
        List<Board> boards = boardService.boardList();
        model.addAttribute("boards",boards);
        return "index";
    }

    //글 작성 페이지
    @GetMapping("/board/saveForm")
    public String saveForm(){
        return "board/saveForm";
    }
    
}
