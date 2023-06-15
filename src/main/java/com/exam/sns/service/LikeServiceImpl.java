package com.exam.sns.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.sns.model.Like;
import com.exam.sns.model.LikeType;
import com.exam.sns.model.Member;
import com.exam.sns.model.Post;
import com.exam.sns.repository.LikeRepository;

@Service
public class LikeServiceImpl implements LikeService{
	
	@Autowired
	private LikeRepository likeRepo;
	
	public Like findByPostId(Post post) {
		return likeRepo.findByPostId(post.getId()).get();
	}
	
	@Override
	public void addPostLike(Post post, Member member) {
		Like like = new Like();
		like.setPost(post);
		like.setUser(member);
		like.setType(LikeType.POST);
		
		likeRepo.save(like);
	}

	@Override
	@Transactional
	public void deletePostLike(Like like) {
		likeRepo.delete(like);
	}

	@Override
	public List<Like> findAllLikes(Long id) {
		Iterable<Like> likes = likeRepo.findAllByPostId(id);
		return (List<Like>) likes;
	}

	@Override
	public boolean likeDuplicationCheck(Long userId, Long postId) {
		Optional<Like> findLike = likeRepo.findByUserIdAndPostId(userId, postId);
		
		System.out.println(findLike.isPresent());
		return findLike.isPresent();
	}

	@Override
	public Like findByPostIdAndMemberId(Post post, Member member) throws Exception {
		Optional<Like> findLike = likeRepo.findByUserIdAndPostId(member.getId(), post.getId());

		return findLike.orElseThrow( () -> new Exception("좋아요를 찾을 수 없습니다.") );
	}

}
