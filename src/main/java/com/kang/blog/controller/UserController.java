package com.kang.blog.controller;

import com.kang.blog.config.auth.PrincipalDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/auth/joinForm")
    public String joinForm(){
        return "user/joinForm";
    }

    @GetMapping("/auth/loginForm")
    public String loginForm(){
        return "user/loginForm";
    }

    @GetMapping("/auth/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg", "username 또는 비밀번호를 확인해주세요");
        return "user/loginForm";
    }

    @GetMapping("/user/updateForm")
    public String updateForm(Model model, @AuthenticationPrincipal PrincipalDetails principal){
        System.out.println("principal = " + principal.getUser().getId());
        model.addAttribute("principal",principal);
        return "user/updateForm";
    }

    @GetMapping("/auth/findPwdForm")
    public String findForm(){
        return "user/findForm";
    }
}
