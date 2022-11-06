package com.kang.blog.controller;

import com.kang.blog.config.auth.PrincipalDetails;
import com.kang.blog.model.Board;
import com.kang.blog.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    //상세보기
    @GetMapping("/board/{id}")
    public String findById(@PathVariable int id, Model model, @AuthenticationPrincipal PrincipalDetails principal){
        Board board = boardService.readDetail(id);
        model.addAttribute("principal", principal);  //로그인한 사용자 정보 넘겨주기
        model.addAttribute("board",board);
        return "board/detail";
    }

    //수정하기
    @GetMapping("/board/{id}/updateForm")
    public String updateForm(@PathVariable int id, Model model){
        Board board = boardService.readDetail(id);
        model.addAttribute("board",board);
        return "board/updateForm";
    }
    
}
