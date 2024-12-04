package org.sist.sb02_mybatis;

import org.junit.jupiter.api.Test;
import org.sist.sb02_mybatis.persistence.TimeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Sb02MybatisApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	TimeMapper timeMapper;
	
	@Test
	void timeMapperTest() {
		System.out.println(" this.timeMapper.getTime() " + this.timeMapper.getTime());
	}

}
