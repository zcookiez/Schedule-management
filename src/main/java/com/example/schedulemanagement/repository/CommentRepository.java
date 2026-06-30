package com.example.schedulemanagement.repository;

import com.example.schedulemanagement.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByScheduleId(Long scheduleId);
    long countByScheduleId(Long scheduleId);
}
