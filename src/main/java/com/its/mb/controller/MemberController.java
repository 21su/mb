package com.its.mb.controller;

import com.its.mb.dto.MemberDTO;
import com.its.mb.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/save-form")
    public String saveForm(){
        return "/memberPages/save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute MemberDTO memberDTO) throws IOException {
        memberService.save(memberDTO);
        return "index";
    }

    @PostMapping("/id-check/{memberEmail}")
    public @ResponseBody Boolean idCheck(@PathVariable("memberEmail") String memberEmail){
        Boolean result = memberService.findByMemberEmail(memberEmail);
        return result;
    }

    @GetMapping("/login")
    public String loginForm(){
        return "/memberPages/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute MemberDTO memberDTO,
                        HttpSession session){
        MemberDTO loginDTO = memberService.login(memberDTO);
        if(loginDTO != null){
            session.setAttribute("memberId", loginDTO.getId());
            session.setAttribute("memberEmail", loginDTO.getMemberEmail());
            return "index";
        }
        return "/memberPages/login";
    }
}
