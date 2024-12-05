package org.sist.sb05_oracle_mybatis_thymeleaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.java.Log;

@Controller
@Log
@RequestMapping("/board/*")
public class BoardController {
	
	@GetMapping("/list")
	public void list() {
		log.info("BoardController list()....");
	}
	
}