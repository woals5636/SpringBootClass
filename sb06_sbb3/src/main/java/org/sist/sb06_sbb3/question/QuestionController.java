package org.sist.sb06_sbb3.question;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/question")
@Slf4j
@RequiredArgsConstructor
public class QuestionController {
	
	private final QuestionService questionService;

	@GetMapping("/list")
	public void list(Model model) {
		log.info("QuestionController list().... 목록보기");
		
		List<Question> questionList = this.questionService.getList();
		model.addAttribute("questionList", questionList);
	}

	// 질문상세	/question/detail/2
	@GetMapping("/detail/{id}")
	public String detail(@PathVariable("id") Integer id , Model model) {
		log.info("QuestionController detail().... 상세보기");
		
		Question question = this.questionService.getQuestion(id);
		model.addAttribute("question", question);
		return "/question/detail";
	}

	// 질문 등록하기
	@GetMapping("/create")
	public void questionCreate( QuestionForm questionForm ) {
		log.info("QuestionController questionCreate().... 질문등록");
	}
	
	// [2] 질문 등록하기 + 유효성검사
		@PostMapping("/create")
		public String questionCreate(
				@Valid QuestionForm questionForm
				,BindingResult bindingResult
				) {
			log.info("QuestionController questionCreate().... 질문등록 및 유효성 검사");
			// 1. 유효성 검사
			if (bindingResult.hasErrors()) {
				return "/question/create";
			}
			// 2. 질문 등록하기
			String subject = questionForm.getSubject();
			String content = questionForm.getContent();
			
			this.questionService.create(subject, content);
			// 3. 질문 목록으로 리다이렉트
			return "redirect:/question/list";
		}
	
/* 
	// [1] 질문 등록하기
	@PostMapping("/create")
	public String questionCreate(
			@RequestParam(value = "subject") String subject
			, @RequestParam(value = "content") String content
			) {
		log.info("QuestionController questionCreate().... 질문등록");
		// 1. 질문을 등록 작업
		this.questionService.create(subject, content);
		// 2. 질문 목록으로 리다이렉트
		return "redirect:/question/list";
	}
*/
/*	단순 확인용
	@GetMapping("/question/list")
	@ResponseBody
	public String list() {
		return "question list";
	}
*/
	
/* service 안만들었을 때
 	
 	private final QuestionRepository questionRepository;
 	
	@GetMapping("/list")
	public void list(Model model) {
		log.info("QuestionController list()....");
		
		List<Question> questionList = this.questionRepository.findAll();
		model.addAttribute("questionList", questionList);
	}
*/	
		
}
