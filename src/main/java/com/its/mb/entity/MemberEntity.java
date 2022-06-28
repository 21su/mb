package com.its.mb.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "member_table1")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "memberId",length = 50, unique = true)
    private String memberId;

    @Column(name = "memberPassword", length = 50, nullable = false)
    private String memberPassword;

    @Column(name = "memberName", length = 50, nullable = false)
    private String memberName;

    @Column(name = "memberEmail", length = 50, nullable = false)
    private String memberEmail;

    @Column(name = "memberMobile", length = 50, nullable = false)
    private String memberMobile;

    @Column(name = "memberProfileName", length = 100)
    private String memberProfileName;

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BoardEntity> boardEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CommentEntity> commentEntityList = new ArrayList<>();
}
