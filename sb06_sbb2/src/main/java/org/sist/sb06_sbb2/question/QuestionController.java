package org.sist.sb06_sbb2.question;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
