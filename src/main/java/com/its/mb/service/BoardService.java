package com.its.mb.service;

import com.its.mb.common.PagingConst;
import com.its.mb.dto.BoardDTO;
import com.its.mb.entity.BoardEntity;
import com.its.mb.entity.MemberEntity;
import com.its.mb.repository.BoardRepository;
import com.its.mb.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    public List<BoardDTO> findAll() {
        List<BoardEntity> boardEntityList = boardRepository.findAll();
        List<BoardDTO> boardDTOList = new ArrayList<>();
        for(BoardEntity board:boardEntityList){
            boardDTOList.add(BoardDTO.toBoardDTO(board));
        }
        return boardDTOList;
    }

    public Page<BoardDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber(); // 페이지값 가져옴
        // 요청한 페이지가 1이면 페이지값을 0으로 하고 1이 아니면 요청 페이지에서 1을 뺀다.
//        page = page - 1;
        // 삼항 연산자
        page = (page == 1)? 0: (page-1);
        Page<BoardEntity> boardEntities = boardRepository.findAll(PageRequest.of(page, PagingConst.PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "id")));
        // Page<BoardEntity> => Page<BoardPagingDTO>
        Page<BoardDTO> boardList = boardEntities.map(
                // BoardEntity 객체 -> BoardDTO 객체 변환
                // board: BoardEntity 객체
                // new BoardDTO() 생성자
                board -> new BoardDTO(board.getId(),
                        board.getBoardTitle(),
                        board.getMemberEntity().getMemberEmail(),
                        board.getBoardHits(),
                        board.getCreatedTime()
                ));
        return boardList;
    }

    public Long save(BoardDTO boardDTO) throws IOException {
        MultipartFile boardFile = boardDTO.getBoardFile();
        String boardFileName = boardFile.getOriginalFilename();
        boardFileName = System.currentTimeMillis() + "_" + boardFileName;
        String savePath = "C:\\development_hss\\springboot_img\\" + boardFileName;
        if (!boardFile.isEmpty()) {
            boardFile.transferTo((new File(savePath)));
        }
        boardDTO.setBoardFileName(boardFileName);

        // to SaveEntity 메서드에 회원 엔티티를 같이 전달해야함
        Optional<MemberEntity> optionalMemberEntity =
                memberRepository.findByMemberEmail(boardDTO.getBoardWriter());
        if (optionalMemberEntity.isPresent()) {
            MemberEntity memberEntity = optionalMemberEntity.get();
            BoardEntity boardEntity = BoardEntity.toBoardSaveEntity(boardDTO, memberEntity);
            Long id = boardRepository.save(boardEntity).getId();
            return id;
        } else {
            return null;
        }
    }

    public BoardDTO findById(Long id) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
        if(optionalBoardEntity.isPresent()){
            BoardEntity boardEntity = optionalBoardEntity.get();
            BoardDTO boardDTO = BoardDTO.toBoardDTO(boardEntity);
            return boardDTO;
        }
        return null;
    }

    @Transactional
    public void updateHits(Long id) {
        System.out.println("1");
        boardRepository.boardHits(id);
        System.out.println("2");
    }
}
