package org.sb06_sbb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.Getter;
import lombok.Setter;

@Controller
public class HelloController {
	
	@GetMapping("/hello")
	@ResponseBody
	public String hello() {
		return "hello world~!";
	}
	
	// http://localhost/hi/kenik/20 요청
	// 응답 : "kenik(20)님 hi~"
	@GetMapping("/hi/{id}/{age}")
    @ResponseBody
    public String hi(@PathVariable(name = "id") String id, @PathVariable("age") int age) {
    //public String hi(@PathVariable String id, @PathVariable int age) {
        
      return String.format("\"%s\"(%d)님 hi~"  , id, age);
   
    }
	
	@GetMapping("/hi/{id}/{age}")
	@ResponseBody
	public User hi() {
		User user = new User(20, "kenik");
		return user;
	}
		
	
	@Setter
	@Getter
	static class User{
		
		private int age;
		private String name;
		
		public User(int age, String name) {
			super();
			this.age = age;
			this.name = name;
		}
		
	}

}
