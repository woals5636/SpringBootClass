package org.sist.sb06_sbb8.answer;

import java.time.LocalDateTime;
import java.util.Set;

import org.sist.sb06_sbb8.question.Question;
import org.sist.sb06_sbb8.user.SiteUser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity // 엔티티에서는 setter 안써도 된다. / 있어도 문제는 없다.
// @Builder
public class Answer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 시퀀스 개념 / Oracle 이 아니라 이거 사용한다.
	private Integer id;
	
	@Column(columnDefinition = "TEXT") // 글자수 제한이 없는 긴 문자열
	private String content;
	
	private LocalDateTime createDate;
	
	@ManyToOne					// 다 대 일 연관관계
	private Question question; // 주의 
	
	@ManyToOne
	private SiteUser author;
	
	private LocalDateTime modifyDate; //수정한 날짜 
	
	// 질문 - 회원 다대다 관계
	@ManyToMany
	private Set<SiteUser> voter;	//	좋아요는 한번밖에 되지 않으니까 SET 컬렉션 사용
		
} // class
