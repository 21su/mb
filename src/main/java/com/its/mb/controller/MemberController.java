package com.its.mb.controller;

import com.its.mb.dto.MemberDTO;
import com.its.mb.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/id-check/{memberId}")
    public @ResponseBody Boolean idCheck(@PathVariable("memberId") String memberId){
        Boolean result = memberService.findByMemberUserId(memberId);
        return result;
    }

}
