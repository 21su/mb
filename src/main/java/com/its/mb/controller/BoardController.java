package com.its.mb.controller;

import com.its.mb.common.PagingConst;
import com.its.mb.dto.BoardDTO;
import com.its.mb.dto.CommentDTO;
import com.its.mb.dto.MemberDTO;
import com.its.mb.service.BoardService;
import com.its.mb.service.CommentService;
import com.its.mb.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;
    private final MemberService memberService;
    private final CommentService commentService;

    @GetMapping("/save-form/{id}")
    public String saveForm(@PathVariable("id") Long id,
                           Model model){
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("memberDTO", memberDTO);
        return "/boardPages/save";
    }

    @GetMapping
    public String findAll(@PageableDefault(page = 1) Pageable pageable, Model model){
        Page<BoardDTO> boardList = boardService.paging(pageable);
        model.addAttribute("boardList", boardList);
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / PagingConst.BLOCK_LIMIT))) - 1) * PagingConst.BLOCK_LIMIT + 1;
        int endPage = ((startPage + PagingConst.BLOCK_LIMIT - 1) < boardList.getTotalPages()) ? startPage + PagingConst.BLOCK_LIMIT - 1 : boardList.getTotalPages();
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "/boardPages/findAll";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute BoardDTO boardDTO) throws IOException {
        boardService.save(boardDTO);
        return "redirect:/board";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id,
                         Model model){
        List<CommentDTO> commentList = commentService.findAll(id);
        boardService.updateHits(id);
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("boardDTO", boardDTO);
        model.addAttribute("commentList", commentList);
        return "/boardPages/detail";
    }

    @GetMapping("/deleteId/{id}")
    public String deleteId(@PathVariable("id") Long id){
        boardService.deleteId(id);
        return "redirect:/board";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable("id") Long id,
                         Model model){
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("boardDTO", boardDTO);
        return "boardPages/update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute BoardDTO boardDTO) throws IOException {
        boardService.update(boardDTO);
        return "redirect:/board/detail/" + boardDTO.getId();
    }

    @GetMapping("/search")
    public String search(@RequestParam("q") String q, Model model){
        System.out.println(1);
        List<BoardDTO> searchList = boardService.search(q);
        System.out.println(2);
        model.addAttribute("boardList", searchList);
        return "boardPages/search";

    }
}
