package org.sparta.schedule.repository;

import org.sparta.schedule.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByIdAndSchedule_Id(Long commentId, Long scheduleId);
}
