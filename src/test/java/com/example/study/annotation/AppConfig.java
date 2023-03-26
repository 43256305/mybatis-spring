package com.example.study.annotation;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;

@Configuration
@MapperScan("com.example.study.mapper")
public class AppConfig {

  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
    dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/mybatis");
    dataSource.setUsername("root");
    dataSource.setPassword("admin");
    return dataSource;
  }

  @Bean
  public DataSourceTransactionManager transactionManager() {
    return new DataSourceTransactionManager(dataSource());
  }

  @Bean
  public SqlSessionFactory sqlSessionFactory() throws Exception {
    //创建SqlSessionFactoryBean对象
    SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
    //设置数据源
    sessionFactory.setDataSource(dataSource());
    //设置Mapper.xml路径
    sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
      .getResources("classpath:mapper/*.xml"));

    return sessionFactory.getObject();
  }
}
