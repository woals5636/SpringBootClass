package org.sist.sb06_sbb8.question;

import java.security.Principal;

import org.sist.sb06_sbb8.answer.AnswerForm;
import org.sist.sb06_sbb8.page.Criteria;
import org.sist.sb06_sbb8.page.PageDTO;
import org.sist.sb06_sbb8.user.SiteUser;
import org.sist.sb06_sbb8.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/question")
@Slf4j
@RequiredArgsConstructor
public class QuestionController {

	private final QuestionService questionService;
	private final UserService userService;
	
	/* [1]
	@GetMapping("/list")
	public void list(Model model) {
		log.info("QuestionController list().... 페이징 처리 안된 목록보기");

		List<Question> questionList = this.questionService.getList();
		model.addAttribute("questionList", questionList);
	}
	 */
/*
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
*/
	// 질문상세	/question/detail/2
	@GetMapping("/detail/{id}")
	public String detail(@PathVariable("id") Integer id , Model model, AnswerForm answerForm ) {
		log.info("QuestionController detail().... 상세보기");

		Question question = this.questionService.getQuestion(id);
		model.addAttribute("question", question);
		return "/question/detail";
	}

	// 질문 등록하기
	// 인증 X -> 강제로 로그인 페이지로 이동
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/create")
	public void questionCreate( QuestionForm questionForm ) {
		log.info("QuestionController questionCreate().... 질문등록");
	}

	// [2] 질문 등록하기 + 유효성검사
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create")
	public String questionCreate(
			@Valid QuestionForm questionForm
			, BindingResult bindingResult
			, Principal principal
			) {
		log.info("QuestionController questionCreate().... 질문등록 및 유효성 검사");
		// 1. 유효성 검사
		if (bindingResult.hasErrors()) {
			return "/question/create";
		}
		// 2. 질문 등록하기
		String subject = questionForm.getSubject();
		String content = questionForm.getContent();

		SiteUser siteUser = this.userService.getUser(principal.getName());
		
		this.questionService.create(subject, content,siteUser);
		// 3. 질문 목록으로 리다이렉트
		return "redirect:/question/list";
	}

	// 수정 버튼 클릭 시
	@PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String questionModify(QuestionForm questionForm, @PathVariable("id") Integer id, Principal principal) {
        Question question = this.questionService.getQuestion(id);
        if(!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        
        questionForm.setSubject(question.getSubject());
        questionForm.setContent(question.getContent());
        
        return "/question/create";
    }
	
	// 수정 처리..
	@PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String questionModify(@Valid QuestionForm questionForm
								    		, BindingResult bindingResult
								    		, Principal principal
								    		, @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "/question/create";
        }
        Question question = this.questionService.getQuestion(id);
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.questionService.modify(question, questionForm.getSubject(), questionForm.getContent());
        return String.format("redirect:/question/detail/%s", id);
    }
	
	// 삭제 버튼 클릭 시
	@PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String questionDelete(Principal principal, @PathVariable("id") Integer id) {
        Question question = this.questionService.getQuestion(id);
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.questionService.delete(question);
        return "redirect:/question/list";
    }
	
	// 추천
	@PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String questionVote(Principal principal, @PathVariable("id") Integer id) {
        Question question = this.questionService.getQuestion(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.questionService.vote(question, siteUser);
        return String.format("redirect:/question/detail/%s", id);
    }
	/*
	// [3] 검색기능 포함
		@GetMapping("/list")
		public void list(Model model
				,@RequestParam(value = "page", defaultValue = "0") int page
				, @RequestParam(value = "kw", defaultValue = "") String kw
				) {

			log.info("QuestionController list().... 페이징+검색 처리된 목록보기");

			Page<Question> paging = this.questionService.getList(page, kw);
			model.addAttribute("paging", paging);
			model.addAttribute("kw", kw);

			Criteria criteria = new Criteria(page+1, 10 ); 
			int total = (int)paging.getTotalElements();
			model.addAttribute("pageMaker",  new PageDTO(criteria, total));
		}
	*/
	
	// [4] 검색기능 포함 + Querydsl
	   @GetMapping("/list")
	   public void list(Model model
	         , @RequestParam( value = "page", defaultValue = "0") int page 
	         , @RequestParam( value = "type", defaultValue = "s") String type 
	         , @RequestParam( value = "kw", defaultValue = "") String keyword 
	         ) {      
	      // 페이징 처리가 된 객체  : paging
	      Page<Question> paging = this.questionService.getList(page, type, keyword);
	      model.addAttribute("paging", paging);
	      model.addAttribute("kw", keyword);

	      Criteria criteria = new Criteria(page+1, 10 ); 
	      int total = (int)paging.getTotalElements();
	      model.addAttribute("pageMaker",  new PageDTO(criteria, total));

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
