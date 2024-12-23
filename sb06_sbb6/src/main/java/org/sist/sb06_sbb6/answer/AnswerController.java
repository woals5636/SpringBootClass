package org.sist.sb06_sbb6.answer;

import java.security.Principal;

import org.sist.sb06_sbb6.question.Question;
import org.sist.sb06_sbb6.question.QuestionService;
import org.sist.sb06_sbb6.user.SiteUser;
import org.sist.sb06_sbb6.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

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

	// 답변 수정
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String answerModify(AnswerForm answerForm, @PathVariable("id") Integer id, Principal principal) {
		Answer answer = this.answerService.getAnswer(id);
		if (!answer.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		}
		answerForm.setContent(answer.getContent());
		return "/answer/answer_form";
	}

	// 답변 수정
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String answerModify(@Valid AnswerForm answerForm, BindingResult bindingResult,
			@PathVariable("id") Integer id, Principal principal) {
		if (bindingResult.hasErrors()) {
			return "/answer/answer_form";
		}
		Answer answer = this.answerService.getAnswer(id);
		if (!answer.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		}
		this.answerService.modify(answer, answerForm.getContent());
		return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
	}

	// 답변 삭제
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String answerDelete(Principal principal, @PathVariable("id") Integer id) {
		Answer answer = this.answerService.getAnswer(id);
		if (!answer.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
		}
		this.answerService.delete(answer);
		return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
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
