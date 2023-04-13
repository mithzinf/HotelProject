package com.boot.hotel;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HotelProjectApplication {
	
	@Autowired
	ApplicationContext applicationContext;
	
	public static void main(String[] args) {
		SpringApplication.run(HotelProjectApplication.class, args);
	}
	
	@Bean
	public SqlSessionFactory sqlSesstionFactory(DataSource dataSource)
			throws Exception{
		
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		
		sessionFactory.setDataSource(dataSource);
		
		sessionFactory.setMapperLocations(
				applicationContext.getResources("classpath:mybatis/mapper/*.xml"));
		
		return sessionFactory.getObject();
		
	}
	

}
