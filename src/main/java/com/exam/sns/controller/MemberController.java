package com.exam.sns.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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

	@Value("${file.root}")
	private String fileRootDir;
	
	@Value("${file.profile.default-file-name}")
	private String defaultProfileFileName;
	
	
	
	
	@GetMapping("/join")
	public String joinForm(Model model) {
		
		
		model.addAttribute("loggedInUser", new Member());
		
		return "join";
	}
	
	
	@PostMapping("/join")
	public String join(Member member, @RequestParam("profileImageFile") MultipartFile file) throws IOException, ServletException {
		
		System.out.println(member);
		/* 파일 업로드 로직*/
		if (!file.isEmpty()) {
    		UUID uuid = UUID.randomUUID();
    		String fileName = uuid.toString()+"_"+file.getOriginalFilename();
    		member.setProfilePhoto(fileName);
    		
    		System.out.println("final file Name = " + fileName);
    		file.transferTo(new File(fileRootDir+"/profile/"+fileName));
		} else {
			member.setProfilePhoto(defaultProfileFileName);
		}
		/*참고 : https://velog.io/@songs4805/Spring-MVC-%ED%8C%8C%EC%9D%BC-%EC%97%85%EB%A1%9C%EB%93%9C*/
		memberService.insertMember(member);
		
		return "redirect:";
	}
	
	@GetMapping("/profile/@{name}")
	public String profile(@PathVariable("name") String name, Model model, Principal principal) throws Exception {
		if(principal != null) {
    		String loggedInUsername = principal.getName();
    		Member loggedInUser = memberService.getMemberByEmail(loggedInUsername);
    		model.addAttribute("loggedInUser", loggedInUser);
    	} else {
    		model.addAttribute("loggedInUser", new Member());
    	}
		Member member = memberService.getMemberByName(name);
		model.addAttribute("member", member);

		List<Post> posts = postService.findByUserId(member.getId());
        model.addAttribute("posts", posts);
		
		return "profile/profile";
	}
	
	@GetMapping("/profile/edit")
	public String profileForm(Model model, Principal principal) throws Exception {
		
		if(principal != null) {
    		String loggedInUsername = principal.getName();
    		Member loggedInUser = memberService.getMemberByEmail(loggedInUsername);
    		model.addAttribute("loggedInUser", loggedInUser);
    		
    		return "profile/edit";
    	} else {
    		return "redirect:login";
    	}
	}

	@PostMapping("/profile/edit")
	public String profileEdit(Member member, @RequestParam("profileImageFile") MultipartFile file, Model model, Principal principal) throws Exception {
		
		System.out.println(member);
		
		/* 파일 업로드 로직*/
		if (!file.isEmpty()) {
    		UUID uuid = UUID.randomUUID();
    		String fileName = uuid.toString()+"_"+file.getOriginalFilename();
    		member.setProfilePhoto(fileName);
    		
    		System.out.println("final file Name = " + fileName);
    		file.transferTo(new File(fileRootDir+"/profile/"+fileName));
		}
		
		memberService.updateMember(member);
		
		return "redirect:profile";
	}
	
	@PostMapping("/profile/edit/password")
	@ResponseBody
	public String profileEditPassword(@RequestParam("newPassword") String newPassword, Principal principal) {
		
		memberService.updatePassword(newPassword, principal.getName());
		
		return "success";
	}
	
	@GetMapping("/test2")
	@ResponseBody
	public ResponseEntity<List<Post>> getPosts2() {
		List<Post> posts = postService.getAllPosts();
	    if (posts.isEmpty()) {
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    }
	    return new ResponseEntity<>(posts, HttpStatus.OK);
	}
	
	@GetMapping("/profile/photo/{imageName}")
	@ResponseBody
	public ResponseEntity<Resource> getProfileImage(@PathVariable("imageName") String imageName) throws Exception {
		File file = fileUtil.getProfileImageFile(imageName);
		Resource imageResource = fileUtil.getImageResource(file);
		
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageResource);
	}

	
}
