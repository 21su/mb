package com.its.mb.service;

import com.its.mb.dto.MemberDTO;
import com.its.mb.entity.MemberEntity;
import com.its.mb.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Long save(MemberDTO memberDTO) throws IOException {
        MultipartFile memberProfile = memberDTO.getMemberProfile();
        String memberProfileName = memberProfile.getOriginalFilename();
        memberProfileName = System.currentTimeMillis() + "_" + memberProfileName;
        memberDTO.setMemberProfileName(memberProfileName);
        String savePath = "C:\\development_hss\\springboot_img\\" + memberProfileName;
        if (!memberProfile.isEmpty()){
            memberProfile.transferTo((new File(savePath)));
        }
        Long id = memberRepository.save(MemberEntity.toMemberSaveEntity(memberDTO)).getId();
        return id;
    }
    public Long save2(MemberDTO memberDTO){
        Long id = memberRepository.save(MemberEntity.toMemberSaveEntity(memberDTO)).getId();
        return id;
    }
    public MemberDTO login(MemberDTO memberDTO){
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
        if(optionalMemberEntity.isPresent()){
            MemberEntity memberEntity = optionalMemberEntity.get();
            MemberDTO loginDTO = MemberDTO.toMemberDTO(memberEntity);
            if(loginDTO.getMemberPassword().equals(memberDTO.getMemberPassword())){
                return loginDTO;
            }else {
                return null;
            }
        }else {
            return null;
        }
    }

    public MemberDTO findById(Long id){
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
        if (optionalMemberEntity.isPresent()){
            MemberEntity memberEntity = optionalMemberEntity.get();
            return MemberDTO.toMemberDTO(memberEntity);
        }
        else{
            return null;
        }
    }

    public Boolean findByMemberEmail(String memberEmail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(memberEmail);
        if (optionalMemberEntity.isPresent()){
            return false;
        }else{
            return true;
        }
    }

    public Long update(MemberDTO memberDTO) throws IOException {
        MultipartFile memberProfile = memberDTO.getMemberProfile();
        String memberProfileName = memberProfile.getOriginalFilename();
        memberProfileName = System.currentTimeMillis() + "_" + memberProfileName;
        String savePath =  "C:\\development_hss\\springboot_img\\" + memberProfileName;
        if(!memberProfile.isEmpty()) {
            memberProfile.transferTo((new File(savePath)));
        }
        memberDTO.setMemberProfileName(memberProfileName);
        Long id = memberRepository.save(MemberEntity.toMemberUpdateEntity(memberDTO)).getId();
        return id;
    }

    public List<MemberDTO> findAll() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();
        for(MemberEntity member:memberEntityList){
            memberDTOList.add(MemberDTO.toMemberDTO(member));
        }
        return memberDTOList;
    }

    public void deleteId(Long id) {
        memberRepository.deleteById(id);
    }
}
