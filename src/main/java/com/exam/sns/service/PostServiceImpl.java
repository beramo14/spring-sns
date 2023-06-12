package com.exam.sns.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.exam.sns.model.Comment;
import com.exam.sns.model.Member;
import com.exam.sns.model.Post;
import com.exam.sns.repository.CommentRepository;
import com.exam.sns.repository.MemberRepository;
import com.exam.sns.repository.PostRepository;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
    private PostRepository postRepo;
	
	@Autowired
	private CommentRepository commentRepo;
	
	@Autowired
	private MemberRepository memberRepo;

	public List<Post> getAllPosts() {
	    return postRepo.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));	    
	}

    public List<Post> findByUserId(Long id) {
        return postRepo.findByUserIdOrderByCreatedAtDesc(id);
    }

    public void createPost(Post post) {
    	// 로그인한 사용자의 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // 사용자 정보를 통해 Member 객체 조회
        Member user = memberRepo.findByEmail(username).get();
        
        post.setUser(user);
        
    	postRepo.save(post);
    }

    public void createComment(Long postId, Comment comment) throws NotFoundException {
    	
    	// 로그인한 사용자의 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // 사용자 정보를 통해 Member 객체 조회
        Member user = memberRepo.findByEmail(username).get();

        // 해당 게시물 조회
        Post post = postRepo.findById(postId).get();

        // Comment 객체 설정
        comment.setPost(post);
        comment.setUser(user);

        // Comment 저장
        commentRepo.save(comment);
    	
    	
    }

}
