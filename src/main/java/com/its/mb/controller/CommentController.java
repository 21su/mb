package com.its.mb.controller;

import com.its.mb.dto.CommentDTO;
import com.its.mb.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/save")
    public String save(@ModelAttribute CommentDTO commentDTO){
        commentService.save(commentDTO);
        return "redirect:/board/detail/" + commentDTO.getBoardId();
    }
}
