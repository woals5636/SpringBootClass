package org.sist.sb06_sbb8;

import org.junit.jupiter.api.Test;
import org.sist.sb06_sbb8.question.Question;
import org.sist.sb06_sbb8.question.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@SpringBootTest
class QueryDSLApplicationTests {
	
	@Autowired
	QuestionRepository questionRepository;
	
	@Test
	   public void testSearchAll() {
	      String [] types = { "s", "c" };
	      String keyword = "스프링";
	      
	      PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("id").descending());
	      
	      Page<Question> result = this.questionRepository.searchAll(types, keyword, pageRequest);
	       
	      result
	          .getContent()    // List<Question>
	          .forEach(question -> System.out.printf("[%s]%s\n"
	                , question.getSubject() 
	                , question.getContent()) 
	                );
	   }
	   
	
}// class
