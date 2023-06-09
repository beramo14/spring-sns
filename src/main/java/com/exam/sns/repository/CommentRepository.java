package com.exam.sns.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.sns.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
