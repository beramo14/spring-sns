package com.exam.sns.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.exam.sns.model.Member;
import com.exam.sns.repository.MemberRepository;

@Service
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	MemberRepository memberRepo;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public Member getMemberByEmail(String loggedInUsername) throws Exception {
		Optional<Member> findMember = memberRepo.findByEmail(loggedInUsername);
		
		if(findMember.isPresent()) {
			return findMember.get();
		} else {
			throw new UsernameNotFoundException(loggedInUsername + " : EmailNotFound");
		}
		
	}
	
	@Override
	public void insertMember(Member member) {
		member.setPassword(passwordEncoder.encode(member.getPassword()));
		memberRepo.save(member);
	}

	@Override
	public void updateMember(Member member) throws Exception {
		
		Optional<Member> findMember = memberRepo.findById(member.getId());
		
		if(findMember.isPresent()) {
			Member item =  findMember.get();
			
			if(member.getName() != null && member.getName().isBlank() == false) {
				item.setName(member.getName());
			}
			if(member.getBio()  != null &&  member.getBio().isBlank() == false) {
				item.setBio(member.getBio());
			}
			if(member.getProfilePhoto() != null && member.getProfilePhoto().isBlank() == false) {
				item.setProfilePhoto(member.getProfilePhoto());
			}
			if(member.getPassword() != null && member.getPassword().isBlank() == false) {
				item.setPassword(member.getPassword());
			}
			memberRepo.save(item);
		} else {
			throw new Exception(member.getId() + " : MemberNotFound");
		}
	}

	@Override
	public void updatePassword(String newPassword, String name) {
		memberRepo.updatePassword(name, passwordEncoder.encode(newPassword));
		
	}

	@Override
	public Member getMemberByName(String name) throws Exception {
		Optional<Member> findMember = memberRepo.findByName(name);
		if(findMember.isPresent()) {
			return findMember.get();
		} else {
			throw new Exception(name + " : MemberNotFound");
		}
		
		
	}
	

}
