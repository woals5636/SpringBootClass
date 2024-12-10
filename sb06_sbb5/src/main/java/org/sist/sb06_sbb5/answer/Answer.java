package org.sist.sb06_sbb5.answer;

import java.time.LocalDateTime;

import org.sist.sb06_sbb5.question.Question;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	
} // class
