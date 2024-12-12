package org.sist.sb06_sbb7.question;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.sist.sb06_sbb7.answer.Answer;
import org.sist.sb06_sbb7.user.SiteUser;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity // 엔티티에서는 setter 안써도 된다. / 있어도 문제는 없다.
public class Question {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 시퀀스 개념 / Oracle 이 아니라 이거 사용한다.
	private Integer id;
	
	@Column(length = 200) // 크기
	private String subject;
	
	@Column(columnDefinition = "TEXT") // 글자수 제한이 없는 긴 문자열
	private String content;
	
	private LocalDateTime createDate; // create_date
	
	@OneToMany(mappedBy = "question"
			, cascade = CascadeType.REMOVE // 질문 제거하면 답변도 같이 삭제하도록 함
			, fetch = FetchType.EAGER)	// 즉시 방식
	private List<Answer> answerList;
	
	@ManyToOne
	private SiteUser author;
	
	private LocalDateTime modifyDate;
	
	// 질문 - 회원 다대다 관계
	@ManyToMany
	private Set<SiteUser> voter;	//	좋아요는 한번밖에 되지 않으니까 SET 컬렉션 사용
	
	
} // class
