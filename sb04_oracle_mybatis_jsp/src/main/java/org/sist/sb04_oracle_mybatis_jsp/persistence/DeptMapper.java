package org.sist.sb04_oracle_mybatis_jsp.persistence;

import java.util.List;

import org.sist.sb04_oracle_mybatis_jsp.domain.DeptVO;

public interface DeptMapper {
	
	List<DeptVO> selectDeptList() throws Exception;
}