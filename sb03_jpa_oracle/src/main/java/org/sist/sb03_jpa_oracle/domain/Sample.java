package org.sist.sb03_jpa_oracle.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "tbl_sample")
public class Sample {
	
	@Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	// 오라클은 자동을 증가해주는게 없음 -> 아래와 같이 시퀀스 생성
	
	@GeneratedValue(generator = "seq_tablsample", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "seq_tablsample"
								, sequenceName="seq_tablsample"
								, initialValue = 1
								, allocationSize = 1)
	private Long sno;
	
	private String col1;
	private String col2;
	
}
