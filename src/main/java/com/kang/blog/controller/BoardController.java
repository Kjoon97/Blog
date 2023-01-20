package com.kang.blog.controller;

import com.kang.blog.config.auth.PrincipalDetails;
import com.kang.blog.dto.BoardLikeDto;
import com.kang.blog.dto.BoardLikeResponseDto;
import com.kang.blog.model.Board;
import com.kang.blog.service.BoardLikeService;
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
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static java.lang.Math.min;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final BoardLikeService boardLikeService;

    //홈 - 게시물들 조회(페이징, 카테고리, 검색)
    @GetMapping(value = {"/blog/{category}", "/blog"})
    public String index(Model model, @PageableDefault(page = 0, size=3) Pageable pageable,
                        @RequestParam(required = false,defaultValue = "") String search, @PathVariable(required = false) String category){
        System.out.println("category = " + category);
        //List<Board> boards = boardService.boardList();
        Page<Board> boardPage = boardService.findBoardPage(pageable, search, category);   //페이징 객체와 검색어를 넘김.
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
        return "home/home";
    }

    //글 작성 페이지
    @GetMapping("/board/saveForm")
    public String saveForm(){
        return "board/saveForm";
    }

    //상세보기(조회수 증가)
    @GetMapping("/board/{id}")
    public String findById(@PathVariable Long id, Model model, @AuthenticationPrincipal PrincipalDetails principal){
        Board board = boardService.readDetail(id);
        BoardLikeResponseDto boardLikeInfo = boardLikeService.getBoardLikeInfo(new BoardLikeDto(principal.getUser().getId(), id));
        model.addAttribute("principal", principal);  //로그인한 사용자 정보 넘겨주기
        model.addAttribute("board",board);
        model.addAttribute("boardLikeInfo",boardLikeInfo);  //좋아요 정보(체크 사항, 좋아요 수)
        return "board/detail";
    }

    //수정하기
    @GetMapping("/board/{id}/updateForm")
    public String updateForm(@PathVariable Long id, Model model){
        Board board = boardService.readDetail(id);
        model.addAttribute("board",board);
        return "board/updateForm";
    }

    @ResponseBody
    @GetMapping("/")
    public String awsCheck(){
        return "ok";
    }
}
