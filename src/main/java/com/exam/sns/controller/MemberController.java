package com.exam.sns.controller;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.exam.sns.model.Member;
import com.exam.sns.model.Post;
import com.exam.sns.service.MemberService;
import com.exam.sns.service.PostService;
import com.exam.sns.util.FileUtil;

@Controller
public class MemberController {

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileUtil fileUtil;
	
	@GetMapping("/join")
	public String joinForm(Model model) {
		
		
		model.addAttribute("loggedInUser", new Member());
		
		return "join";
	}
	
	
	@PostMapping("/join")
	public String join(Member member, HttpServletRequest request) throws IOException, ServletException {
		
		System.out.println(member);
		Collection<Part> parts = request.getParts();
//        System.out.println("parts=" + parts);
        
        for(Part part : parts) {
        	if("profileImageFile".equals(part.getName())) {
        		if(part.getSize() == 0) {
        			break;
        		}
        		
        		/* 파일 업로드 로직*/
        		/*참고 : https://velog.io/@songs4805/Spring-MVC-%ED%8C%8C%EC%9D%BC-%EC%97%85%EB%A1%9C%EB%93%9C*/
        		
        	}
        }
        
		//memberService.insertMember(member);
		
		return "redirect:";
	}
	
	@GetMapping("/profile")
	public String profileForm(Model model, Principal principal) {
		if(principal != null) {
    		String loggedInUsername = principal.getName();
    		Member loggedInUser = memberService.getMemberByEmail(loggedInUsername);
    		model.addAttribute("loggedInUser", loggedInUser);
    		
    		return "profile";
    	} else {
    		return "redirect:login";
    	}
	}
	
	
	@ResponseBody
	@GetMapping("/test2")
	public ResponseEntity<List<Post>> getPosts2() {
		List<Post> posts = postService.getAllPosts();
	    if (posts.isEmpty()) {
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    }
	    return new ResponseEntity<>(posts, HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping("/profile/photo/{imageName}")
	public ResponseEntity<Resource> getImage(@PathVariable("imageName") String imageName) throws Exception {
		File file = fileUtil.getImageFile(imageName);
		
		
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(fileUtil.getImageResource(file));
	}
}
