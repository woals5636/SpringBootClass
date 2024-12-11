package org.sist.sb06_sbb5.answer;

import java.security.Principal;

import org.sist.sb06_sbb5.question.Question;
import org.sist.sb06_sbb5.question.QuestionService;
import org.sist.sb06_sbb5.user.SiteUser;
import org.sist.sb06_sbb5.user.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/answer")
@Slf4j
@RequiredArgsConstructor
public class AnswerController {
	
	private final AnswerService answerService;
	private final QuestionService questionService;
	private final UserService userService;

	// <form th:action="@{|/answer/create/${questionDetail.id}|}" method="post">
		@PreAuthorize("isAuthenticated()")
		@PostMapping("/create/{id}")
		public String createAnswer(@PathVariable("id") Integer id
				, @Valid AnswerForm answerForm
				,BindingResult bindingResult
				,Model model,
				Principal principal
				) {
			log.info("> AnswerController createAnswer... 답변 생성 및 유효성 검사");
			
			// 1. 유효성 검사
			Question question = this.questionService.getQuestion(id);
			SiteUser siteUser = this.userService.getUser(principal.getName());
			if (bindingResult.hasErrors()) {
				model.addAttribute("question",question);
				return "/question/detail";
			}
			// 2. 답변 등록하기
			this.answerService.create(question, answerForm.getContent(),siteUser);
			
			// 3. 질문 상세로 리다이렉트
			return String.format("redirect:/question/detail/%s", id);
		}
/*
	// <form th:action="@{|/answer/create/${questionDetail.id}|}" method="post">
	@PostMapping("/create/{id}")
	public String createAnswer(@PathVariable("id") Integer id, @RequestParam("content") String content) {
		log.info("> AnswerController createAnswer... 답변 생성");
		
		Question question = this.questionService.getQuestion(id);
		this.answerService.create(question, content);
		
		return String.format("redirect:/question/detail/%s", id);
	}
*/	
}
