package org.sist.sb05_oracle_mybatis_thymeleaf.persistence;

import java.util.List;

import org.sist.sb05_oracle_mybatis_thymeleaf.domain.DeptVO;

public interface DeptMapper {
	
	List<DeptVO> selectDeptList() throws Exception;
}