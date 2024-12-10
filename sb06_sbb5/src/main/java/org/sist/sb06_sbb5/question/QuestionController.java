package org.sist.sb06_sbb5.question;

import org.sist.sb06_sbb5.answer.AnswerForm;
import org.sist.sb06_sbb5.page.Criteria;
import org.sist.sb06_sbb5.page.PageDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/question")
@Slf4j
@RequiredArgsConstructor
public class QuestionController {

	private final QuestionService questionService;
	/* [1]
	@GetMapping("/list")
	public void list(Model model) {
		log.info("QuestionController list().... 페이징 처리 안된 목록보기");

		List<Question> questionList = this.questionService.getList();
		model.addAttribute("questionList", questionList);
	}
	 */

	// [2]
	@GetMapping("/list")
	public void list(Model model
			,@RequestParam(value = "page", defaultValue = "0") int page
			, @RequestParam(value = "size", defaultValue = "10") int size
			) {

		log.info("QuestionController list().... 페이징 처리된 목록보기");

		Page<Question> paging = this.questionService.getList(page, size);
		model.addAttribute("paging", paging);

		Criteria criteria = new Criteria(page+1, 10 ); 
		int total = (int)paging.getTotalElements();
		model.addAttribute("pageMaker",  new PageDTO(criteria, total));
	}

	// 질문상세	/question/detail/2
	@GetMapping("/detail/{id}")
	public String detail(@PathVariable("id") Integer id , Model model, AnswerForm answerForm ) {
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
