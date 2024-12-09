package org.sist.sb06_sbb3.answer;

import org.sist.sb06_sbb3.question.Question;
import org.sist.sb06_sbb3.question.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/answer")
@Slf4j
@RequiredArgsConstructor
public class AnswerController {
	
	private final AnswerService answerService;
	private final QuestionService questionService;
	
	
	// <form th:action="@{|/answer/create/${questionDetail.id}|}" method="post">
	@PostMapping("/create/{id}")
	public String createAnswer(@PathVariable("id") Integer id, @RequestParam("content") String content) {
		log.info("> AnswerController createAnswer... 답변 생성");
		
		Question question = this.questionService.getQuestion(id);
		this.answerService.create(question, content);
		
		return String.format("redirect:/question/detail/%s", id);
	}
	
}
