package com.exam.sns.controller;


import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.exam.sns.dto.LikeDTO;
import com.exam.sns.model.Comment;
import com.exam.sns.model.Like;
import com.exam.sns.model.Member;
import com.exam.sns.model.Post;
import com.exam.sns.service.CommentService;
import com.exam.sns.service.LikeService;
import com.exam.sns.service.MemberService;
import com.exam.sns.service.PostService;

@Controller
public class TimelineController {

	@Autowired
    private PostService postService;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private LikeService likeService;
	

    @GetMapping("/")
    public String home(Model model, Principal principal) throws Exception {
    	Member loggedInUser = new Member();
    	if(principal != null) {
    		String loggedInUsername = principal.getName();
    		loggedInUser = memberService.getMemberByEmail(loggedInUsername);
    	}
    	
    	model.addAttribute("loggedInUser", loggedInUser);
    	
        List<Post> posts = postService.getAllPosts(loggedInUser);
        model.addAttribute("posts", posts);
        return "timeline";
    }
 
    @PostMapping("/posts/create")
    public String createPost(Post post) {
        postService.createPost(post);
        return "redirect:/";
    }

    @PostMapping("/posts/{postId}/comments")
    public String createComment(@PathVariable("postId") Long postId, @ModelAttribute("comment") Comment comment) throws NotFoundException {
        postService.createComment(postId, comment);
        return "redirect:/";
    }
    
    @PostMapping("/post/modify")
    public ResponseEntity<String> postModify(Post post) {
    	
    	Post findPost = postService.findById(post.getId());
    	findPost.setContent(post.getContent());
    	postService.updatePost(findPost);
    	
    	return new ResponseEntity<String>("success", HttpStatus.OK);
    }
    
    @PostMapping("/comment/modify")
    public ResponseEntity<?> commentModify(Comment comment) {
    	
    	Comment findComment = commentService.findById(comment.getId());
    	findComment.setContent(comment.getContent());
    	commentService.updateComment(findComment);
    	return new ResponseEntity<String>("success", HttpStatus.OK);
    }
    
    @GetMapping("/post/delete/{id}")
    public String deletePost(@PathVariable("id") Long id) {
    	System.out.println("#############id = "+id);
    	postService.deletePost(id);
    	
    	return "redirect:/";
    }
    
    
    @GetMapping("/post/like/{postId}")
    @ResponseBody
    public ResponseEntity<?> doPostLike(@PathVariable("postId") Long postId, Principal principal) throws Exception {

    	/*로그인 확인*/
		if(principal == null) {
			return new ResponseEntity<String>("not logged", HttpStatus.BAD_REQUEST);
		}
		Post post = postService.findById(postId);
		Member member = memberService.getMemberByEmail(principal.getName());
		
		likeService.addPostLike(post, member);
    	
    	return new ResponseEntity<String>("success", HttpStatus.OK);
    }
    
    @GetMapping("/post/unlike/{postId}")
    public ResponseEntity<?> doPostUnlike(@PathVariable("postId") Long postId, Principal principal) throws Exception {
    	
    	/*로그인 확인*/
		if(principal == null) {
			return new ResponseEntity<String>("not logged", HttpStatus.BAD_REQUEST);
		}
		Post post = postService.findById(postId);
		Member member = memberService.getMemberByEmail(principal.getName());
		
		Like findLike = likeService.findByPostIdAndMemberId(post, member);
		likeService.deletePostLike(findLike);
		
    	return new ResponseEntity<String>("success", HttpStatus.OK);
    }

    @GetMapping("/post/like/{id}/get")
    @ResponseBody
    public ResponseEntity<List<LikeDTO>> getPostLike(@PathVariable("id") Long id) {
    	List<Like> likes = likeService.findAllLikes(id);
    	List<LikeDTO> likesToView = LikeDTO.convertToDTOList(likes);
    	
    	
    	return new ResponseEntity<List<LikeDTO>>(likesToView, HttpStatus.OK);
    }
    
}
