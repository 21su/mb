package com.its.mb.controller;

import com.its.mb.dto.MemberDTO;
import com.its.mb.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

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

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "index";
    }

    @GetMapping("/my-page/{id}")
    public String myPage(@PathVariable("id") Long id,
                         Model model){
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("memberDTO", memberDTO);
        return "/memberPages/myPage";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute MemberDTO memberDTO) throws IOException {
        memberService.update(memberDTO);
        return "redirect:/member/my-page/" + memberDTO.getId();
    }

    @GetMapping("/admin")
    public String admin(){
        return "/memberPages/admin";
    }

    @GetMapping("/delete/{id}")
    public String memberDelete(@PathVariable("id") Long id){
        memberService.deleteId(id);
        return "redirect:/member/findAll";
    }

    @GetMapping("/findAll")
    public String findAll(Model model){
        List<MemberDTO> memberList = memberService.findAll();
        model.addAttribute("memberList", memberList);
        return "/memberPages/findAll";
    }
}
