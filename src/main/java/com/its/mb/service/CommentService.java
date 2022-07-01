package com.its.mb.service;

import com.its.mb.dto.CommentDTO;
import com.its.mb.entity.BoardEntity;
import com.its.mb.entity.CommentEntity;
import com.its.mb.entity.MemberEntity;
import com.its.mb.repository.BoardRepository;
import com.its.mb.repository.CommentRepository;
import com.its.mb.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    public List<CommentDTO> findAll(Long id){
        List<CommentEntity> commentEntityList = commentRepository.findByBoardId(id);
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for(CommentEntity comment:commentEntityList){
            commentDTOList.add(CommentDTO.toCommentDTO(comment));
        }
        return commentDTOList;
    }

    public Long save(CommentDTO commentDTO){
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(commentDTO.getCommentWriter());
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(commentDTO.getBoardId());
        if(optionalMemberEntity.isPresent()){
            MemberEntity memberEntity = optionalMemberEntity.get();
            if(optionalBoardEntity.isPresent()){
                BoardEntity boardEntity = optionalBoardEntity.get();
                    CommentEntity commentEntity = CommentEntity.toCommentSaveEntity(commentDTO,memberEntity,boardEntity);
                    Long id = commentRepository.save(commentEntity).getId();
                    return id;
            }else {
                return null;
            }
        }else{
            return null;
        }
    }
}
