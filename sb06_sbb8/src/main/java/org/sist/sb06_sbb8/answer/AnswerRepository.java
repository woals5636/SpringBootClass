package org.sist.sb06_sbb8.answer;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AnswerRepository extends JpaRepository<Answer, Integer>{
	
	@Query("SELECT a FROM Answer a WHERE question.id=:qid")  // :subject 가
	List<Answer> findByQuestionId(@Param("qid") Integer question_id);            // @Param 안에 "subject"와 맵핑됨
}
