package com.kang.blog.controller;

import com.kang.blog.config.auth.PrincipalDetails;
import com.kang.blog.model.Board;
import com.kang.blog.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static java.lang.Math.min;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/")
    public String index(Model model, @PageableDefault(page = 0, size=3) Pageable pageable,
                        @RequestParam(required = false,defaultValue = "") String search){
        //List<Board> boards = boardService.boardList();
        Page<Board> boardPage = boardService.findBoardPage(pageable, search);   //페이징 객체와 검색어를 넘김.
        List<Board> boards = boardPage.getContent();
        int nowPage = boardPage.getPageable().getPageNumber();
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 4, boardPage.getTotalPages());
        if(endPage==0) endPage+=1;
        model.addAttribute("boards",boards);
        model.addAttribute("nowPage",nowPage);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("boardPage",boardPage);
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
