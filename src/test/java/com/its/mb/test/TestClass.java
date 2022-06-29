package com.its.mb.test;

import com.its.mb.dto.MemberDTO;
import com.its.mb.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;
@SpringBootTest
public class TestClass {
    @Autowired
    private MemberService memberService;

    public MemberDTO newMember(int i){
        MemberDTO boardDTO =
                new MemberDTO("testId"+i,"testPassword"+i,"testName"+i,"testEmail"+i,"testMobile"+i);
        return boardDTO;
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("save 테스트")
    public void saveTest() throws IOException {
        MemberDTO memberDTO = newMember(1);
        Long id = memberService.save2(memberDTO);
        MemberDTO resultDTO = memberService.findById(id);
        System.out.println("resultDTO = " + resultDTO);
        assertThat(memberDTO.getMemberId()).isEqualTo(resultDTO.getMemberId());
    }
}
