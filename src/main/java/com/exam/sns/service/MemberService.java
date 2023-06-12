package com.exam.sns.service;

import com.exam.sns.model.Member;

public interface MemberService {

	void insertMember(Member member);

	Member getMemberByEmail(String loggedInUsername) throws Exception;

	void updateMember(Member member) throws Exception;

	void updatePassword(String newPassword, String name);

	Member getMemberByName(String name) throws Exception;

}
