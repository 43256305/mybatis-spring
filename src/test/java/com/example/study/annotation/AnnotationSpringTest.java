package com.example.study.annotation;

import com.example.study.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AnnotationSpringTest {

  @Test
  public void test(){
    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.example.study.annotation");
    UserMapper userMapper = (UserMapper) context.getBean("userMapper");
    System.out.println(userMapper.list());
  }

}
