package org.sist.sb06_sbb5.question;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionRepository extends JpaRepository<Question, Integer>{
	
	// CRUD 메서드 이미 내장되어 있음..
	Question findBySubject(String subject);
	
	// 1. Query Method
	// 메소드 이름으로 쿼리를 자동으로 생성해주는 기능
	// find컬럼명Like
	// find컬럼명Containing
	List<Question> findBySubjectContaining(String subject);
	/*
	// 2. @Query
	@Query("SELECT q FROM Question q WHERE q.subject LIKE %:subject%")
	List<Question> findBySubjectLike(@Param("subject") String subject);
	*/
	// 2-2. 쿼리 메서드
	List<Question> findBySubjectLike(String subject);
	
	// WHERE subject=? AND content=?
	Question findBySubjectAndContent(String subject, String content);
	
	// 페이징 처리 ( * 암기 * )
	Page<Question> findAll(Pageable pagealbe);
}
