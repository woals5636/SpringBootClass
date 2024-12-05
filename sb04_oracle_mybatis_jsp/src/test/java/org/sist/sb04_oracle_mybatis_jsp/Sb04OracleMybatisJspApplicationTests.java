package org.sist.sb04_oracle_mybatis_jsp;

import java.sql.Connection;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.sist.sb04_oracle_mybatis_jsp.persistence.TimeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.java.Log;

@SpringBootTest
@Log
class Sb04OracleMybatisJspApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	TimeMapper timeMapper;
	
	@Test
	void timeMapperTest() {
		System.out.println(" this.timeMapper.getTime() " + this.timeMapper.getTime());
	}
	
	@Autowired
	DataSource dataSource;
	
	@Test
	void testConnection() {
		try (Connection con = this.dataSource.getConnection()){
			log.info("> CONNECTION : " + con);
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

}
