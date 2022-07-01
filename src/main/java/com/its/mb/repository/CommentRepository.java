package com.its.mb.repository;

import com.its.mb.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    @Modifying
    @Query(value = "select *from comment_table1 where board_id = :board_id",nativeQuery = true)
    List<CommentEntity> findByBoardId(@Param("board_id") Long board_id);
}
