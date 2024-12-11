package org.sist.sb06_sbb6.user;

import lombok.Getter;

@Getter
public enum UserRole {
	
	ADMIN("ROLE_ADMIN")	,	USER("ROLE_USER");

	UserRole(String value) {
		this.value = value;
	}
	
	private String value;
	
}

// 사용자 권한 열거형 : ADMIN, USER 상수의 값으로 ROLE_ADMIN, ROLE_USER 설정

// 값을 읽어갈 일만 있기 때문에 getter 만 필요