package com.kang.blog.controller.api;

import com.kang.blog.config.auth.PrincipalDetails;
import com.kang.blog.dto.BoardLikeDto;
import com.kang.blog.dto.ReplySaveRequestDto;
import com.kang.blog.dto.ResponseDto;
import com.kang.blog.dto.SaveBoardDto;
import com.kang.blog.model.Board;
import com.kang.blog.model.Reply;
import com.kang.blog.model.User;
import com.kang.blog.service.BoardLikeService;
import com.kang.blog.service.BoardService;
import com.kang.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardService boardService;
    private final BoardLikeService boardLikeService;

    //글 작성
    @PostMapping("/api/board")
    public ResponseDto<Integer> save(@RequestBody SaveBoardDto saveBoardDto, @AuthenticationPrincipal PrincipalDetails principal){
        boardService.register(saveBoardDto.toEntity(), principal.getUser().getId());
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    //글 삭제
    @DeleteMapping("/api/board/{id}")
    public ResponseDto<Integer> deleteById(@PathVariable Long id){
        boardLikeService.deleteBoardLike(id);   //관련 좋아요 삭제.
        boardService.delete(id);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    //글 수정하기
    @PutMapping("/api/board/{id}")
    public ResponseDto<Integer> update(@PathVariable Long id, @RequestBody Board board){
        boardService.updateBoard(id, board);
        return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
    }

    //댓글 작성
    @PostMapping("/api/board/{boardId}/reply")
    public ResponseDto<Integer> replySave(@RequestBody ReplySaveRequestDto replySaveRequestDto, @AuthenticationPrincipal PrincipalDetails principal){
        boardService.registerReply(replySaveRequestDto);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    //댓글 삭제
    @DeleteMapping("/api/board/{boardId}/reply/{replyId}")
    public ResponseDto<Integer> replyDelete(@PathVariable Long replyId){
        boardService.deleteReply(replyId);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    //좋아요 처리
    @PostMapping("/api/board/{boardId}/like")
    public ResponseDto<Integer> likeBoard(@PathVariable Long boardId, @RequestBody BoardLikeDto boardLikeDto){
        boardLikeService.pushLikeButton(boardLikeDto);
        return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
    }
}
