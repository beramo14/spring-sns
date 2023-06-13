package com.exam.sns.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.sns.model.Like;


public interface LikeRepository extends JpaRepository<Like, Long> {

	Optional<Like> findByPostId(Long id);

	Iterable<Like> findAllByPostId(Long id);

	Optional<Like> findByUserIdAndPostId(Long userId, Long postId);

}
