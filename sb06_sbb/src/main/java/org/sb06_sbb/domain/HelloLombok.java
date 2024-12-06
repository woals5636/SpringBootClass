package org.sb06_sbb.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
// @NoArgsConstructor
// @AllArgsConstructor
@RequiredArgsConstructor
public class HelloLombok {

	private final String hello;
	private final int lombok;
	
	/*
	public HelloLombok(String hello, int lombok) {
		super();
		this.hello = hello;
		this.lombok = lombok;
	}
	*/
}

/*
@Getter
@Setter
public class HelloLombok {

	private String hello;
	private int lombok;
	
	public static void main(String[] args) {
		// System.out.println("hello world");
		HelloLombok helloLombok = new  HelloLombok();
		helloLombok.setHello("헬로");
		helloLombok.setLombok(5);
		
		System.out.println(helloLombok.getHello());
		System.out.println(helloLombok.getLombok());
	}
}
*/