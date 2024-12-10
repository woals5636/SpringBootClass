package org.sist.sb06_sbb4.question;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import org.sist.sb06_sbb4.exception.DataNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionService {
	
	private final QuestionRepository questionRepository;
	
	// 질문목록 ( 페이징 처리 X )
	public List<Question> getList(){
		
		return this.questionRepository.findAll();
	}

	// id에 해당하는 질문
	public Question getQuestion(Integer id){
		Optional<Question> oQuestion = this.questionRepository.findById(id);
		if(oQuestion.isPresent()) {
			return oQuestion.get();	// Optional<Question> -> Question
		}else {
			// 강제로 사용자 정의 예외를 발생시키겠다.
			// exception 패키지 추가 + DataNotFoundException 예외 클래스 추가
			throw new DataNotFoundException("question not found.");
		}
	}
	
	// 질문을 등록 하는 메서드
	public void create( String subject ,String content ) {
		Question question = new Question();
		question.setSubject(subject);
		question.setContent(content);
		question.setCreateDate(LocalDateTime.now());
		
		this.questionRepository.save(question);
	}
	
	// 질문목록 ( 페이징 처리 O )            ▼ 현재 페이지
	public Page<Question> getList(int page, int size){
		//											pageNumber, pageSize
		// Pageable pageable = PageRequest.of(page, 10);
		
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("id"));
		Pageable pageable = PageRequest.of(page, size, Sort.by(sorts));
		
		return this.questionRepository.findAll(pageable);
		
	}
}
