package com.exam.sns.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.sns.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {


}
