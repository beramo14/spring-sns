package com.exam.sns;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.exam.sns.model.Member;

@SpringBootTest
public class MemberTest {
	
	@DisplayName("Entity null test")
	@Test
	public void EntityNullTest() {
		Member member = new Member();
		System.out.println(member.getId());
		System.out.println(member.getId() == null);
	}

}
