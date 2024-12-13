package org.sist.sb06_sbb8.question;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.sist.sb06_sbb8.answer.Answer;
import org.sist.sb06_sbb8.exception.DataNotFoundException;
import org.sist.sb06_sbb8.user.SiteUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
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
	public void create( String subject ,String content, SiteUser user ) {
		Question question = new Question();
		question.setSubject(subject);
		question.setContent(content);
		question.setCreateDate(LocalDateTime.now());

		question.setAuthor(user);

		this.questionRepository.save(question);
	}
	/* [1]
	// 질문목록 ( 페이징 처리 O )            ▼ 현재 페이지
	public Page<Question> getList(int page, int size){
		//											pageNumber, pageSize
		// Pageable pageable = PageRequest.of(page, 10);

		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("id"));
		Pageable pageable = PageRequest.of(page, size, Sort.by(sorts));

		return this.questionRepository.findAll(pageable);

	}
	 */
	// 질문 수정
	public void modify(Question question, String subject, String content) {
		question.setSubject(subject);
		question.setContent(content);
		question.setModifyDate(LocalDateTime.now());
		// this.questionRepository.save(question); // @Transactional 사용하면 JPA 가 자동으로 set~을 인지하여 insert 쿼리 날림
	}

	// 질문 삭제
	public void delete(Question question) {
		this.questionRepository.delete(question);
	}

	// 추천 등록                  질문                  추천인(로그인 회원)
	public void vote (Question question, SiteUser siteUser ) {
		question.getVoter().add(siteUser);
		this.questionRepository.save(question);
	}

	// Specification 을 활용한 검색 기능 구현
	private Specification<Question> search(String kw) {
		// 익명 클래스 객체 생성해서 반환....
		return new Specification<>() { // 인터페이스
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<Question> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// JPA API -> JPQL 로 바꿔준다~
				query.distinct(true);  // 중복을 제거 
				Join<Question, SiteUser> u1 = q.join("author", JoinType.LEFT);
				Join<Question, Answer> a = q.join("answerList", JoinType.LEFT);
				Join<Answer, SiteUser> u2 = a.join("author", JoinType.LEFT);
				return cb.or(
						cb.like(q.get("subject"), "%" + kw + "%"), // 제목 
						cb.like(q.get("content"), "%" + kw + "%"),      // 내용 
						cb.like(u1.get("username"), "%" + kw + "%"),    // 질문 작성자 
						cb.like(a.get("content"), "%" + kw + "%"),      // 답변 내용 
						cb.like(u2.get("username"), "%" + kw + "%")   // 답변 작성자 
						);
			}
		};
	}
	/*
	// [2] 질문목록 ( 페이징 처리 + 검색 )      ▼ 현재 페이지  ▼ 검색어
	public Page<Question> getList(int page, String kw){ //keyword

		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("id"));

		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));

		Specification <Question> spec = search(kw);
		return this.questionRepository.findAll(spec, pageable);

	}
	 */
	/*
	// [3] @Query 활용 질문목록 ( 페이징 처리 + 검색 )
	public Page<Question> getList(int page, String kw){ //keyword

		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("id"));

		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));

		return this.questionRepository.findAllByKeword(kw, pageable);
	}
	 */

	// [4] 페이징 처리 + 검색 + Querydsl
	public Page<Question>  getList(int page, String type, String keyword){  // keyword  

		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("id"));      
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));

		String [] types = type.split("");
		return  this.questionRepository.searchAll(types, keyword, pageable);

	}
}
