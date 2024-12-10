package org.sist.sb06_sbb5;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.sist.sb06_sbb5.answer.Answer;
import org.sist.sb06_sbb5.answer.AnswerRepository;
import org.sist.sb06_sbb5.question.Question;
import org.sist.sb06_sbb5.question.QuestionRepository;
import org.sist.sb06_sbb5.question.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class Sb06Sbb5ApplicationTests {
/*
	@Autowired
	private QuestionRepository questionRepository;

	@Test
	void testJpa() {
		// 질문 등록 테스트
		Question q1 = new Question();

		q1.setSubject("sbb가 무엇인가요?");
		q1.setContent("sbb에 대해서 알고 싶습니다.");
		q1.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q1);


		Question q2 = new Question();

		q2.setSubject("스프링부트 모델 질문입니다.");
		q2.setContent("id는 자동으로 생성되나요?");
		q2.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q2);

	}
	
	@Test
	void test2Jpa() {
		// 모든 질문 조회
		List<Question> list = this.questionRepository.findAll();
		System.out.println("> " + list.size() );

		list.stream().forEach(q->System.out.println(q.getSubject()));
	}
	
	@Test
	void test3Jpa() {
		// 질문 ID 에 해당하는 질문을 조회
		Optional<Question> optional= this.questionRepository.findById(2);
		if(optional.isPresent()){ 	// 값이 존재한다면
			 Question q = optional.get();
			 System.out.println(q.getSubject() + "/" + q.getContent());
		}
	}
	
	@Test
	void test4Jpa() {
		// 제목으로 검색
		// this.questionRepository.findby컬럼명()
		Question q = this.questionRepository.findBySubject("sbb가 무엇인가요?	");
		System.out.println(">> " + q.getContent());
	}
	@Test
	void test5Jpa() {

		// 제목으로 검색
		// [1] 쿼리 메서드 사용
		List<Question> list = this.questionRepository.findBySubjectLike("sbb");
		System.out.println(">>" + list.size());
		

		// List<Question> list = this.questionRepository.findBySubjectLike("sbb");
		// System.out.println(">>" + list.size());

	}
	
	@Test
	void test6Jpa() {
		Question q = this.questionRepository.findBySubjectAndContent("sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");

		System.out.println(">>" + q.getId());

	}
	
	// 질문 수정( 제목만 수정 )
	@Test
	void test7Jpa() {
		Optional<Question> optional = this.questionRepository.findById(1);
		if(optional.isPresent()) {
			Question q1 = optional.get();
			q1.setSubject("수정된 제목");
			this.questionRepository.save(q1);
		}// if
	}
	
	// 질문 삭제
		@Test
		void test8Jpa() {
			System.out.println(" 총 질문 수 : " + this.questionRepository.count());

			// this.questionRepository.deleteById(1);
			Optional<Question> oq = this.questionRepository.findById(1);
			Question q1 = oq.get();
			this.questionRepository.delete(q1);

			System.out.println(" 총 질문 수 : " + this.questionRepository.count());
		}
*/
	/*--------------------------------------------------------------------------------------------------- */

/*
	// 1. 답변 저장
	@Autowired
	private AnswerRepository answerRepository;

		@Test
		void testAnswer1Jpa() {
			Optional<Question> optional = this.questionRepository.findById(2);

			if(optional.isPresent()) {
				Question q = optional.get();

				Answer a = new Answer();
				a.setContent("2번째 답글");
				a.setCreateDate(LocalDateTime.now());
				a.setQuestion(q);
				this.answerRepository.save(a);
			}
		}
			
	// 질문 2에 대한 모든 답변글 조회
	@Test
	void testAnswer2Jpa() {
		List<Answer> alist = answerRepository.findByQuestionId(2);
		alist.stream().forEach(a -> System.out.println("answer 드가자 : " + a.getContent() ) );
	}

	// 데이터를 가져오는 방식
	// 1. 즉시 방식( Eager )
	// 2. 지연 방식( Lazy )
	@Transactional
	@Test
	void testAnswer3Jpa() {
		Optional<Question> optional = this.questionRepository.findById(2);
		if(optional.isPresent()) {
			Question q = optional.get();
			List<Answer> answerList = q.getAnswerList();
			answerList.stream().forEach(a -> System.out.println("answer 확인 : " + a.getContent() ) );
		}
	}
*/
	@Autowired
	private QuestionService questionService;
	
	// 질문 추가
	@Test
	void testAnswer4Jpa() {
		for (int i = 1; i < 285; i++) {
			String subject = "질문" + i;
			String content = "질문 내용" + i;
			
			this.questionService.create(subject, content);
			
		}// for
	}
		
}// class
