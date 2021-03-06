package com.its.mb.dto;

import com.its.mb.entity.BoardEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {
    private Long id;
    private String boardTitle;
    private String boardWriter;
    private String boardContents;
    private int boardHits;
    private MultipartFile boardFile;
    private String boardFileName;
    private LocalDateTime boardCreatedTime;

    public BoardDTO(Long id, String boardTitle, String memberEmail,int boardHits,LocalDateTime boardCreatedTime) {
        this.id = id;
        this.boardTitle = boardTitle;
        this.boardWriter = memberEmail;
        this.boardHits = boardHits;
        this.boardCreatedTime = boardCreatedTime;
    }

    public static BoardDTO toBoardDTO(BoardEntity boardEntity){
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(boardEntity.getId());
        boardDTO.setBoardTitle(boardEntity.getBoardTitle());
        boardDTO.setBoardWriter(boardEntity.getMemberEntity().getMemberEmail());
        boardDTO.setBoardContents(boardEntity.getBoardContents());
        boardDTO.setBoardHits(boardEntity.getBoardHits());
        boardDTO.setBoardFileName(boardEntity.getBoardFileName());
        boardDTO.setBoardCreatedTime(boardEntity.getCreatedTime());
        return boardDTO;
    }
}
