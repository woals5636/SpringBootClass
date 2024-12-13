package org.sist.sb06_sbb8.question;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionRepository extends JpaRepository<Question, Integer>, QuestionSearch{

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
	@Override
	Page<Question> findAll(Pageable pagealbe);

	// 페이징 처리 + 검색
	Page<Question> findAll(Specification<Question> spec , Pageable pagealbe);

	// 페이징 처리 + 검색 : @Query 사용
	@Query("select "
			+ "distinct q "
			+ "from Question q " 
			+ "left outer join SiteUser u1 on q.author=u1 "
			+ "left outer join Answer a on a.question=q "
			+ "left outer join SiteUser u2 on a.author=u2 "
			+ "where "
			+ "   q.subject like %:kw% "
			+ "   or q.content like %:kw% "
			+ "   or u1.username like %:kw% "
			+ "   or a.content like %:kw% "
			+ "   or u2.username like %:kw% ")
	Page<Question> findAllByKeword(@Param("kw") String kw, Pageable pagealbe);
}
