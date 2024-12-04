package org.sist.sb03_jpa_oracle;

import org.junit.jupiter.api.Test;
import org.sist.sb03_jpa_oracle.persistence.TimeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Sb03JpaOracleApplicationTests {

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
