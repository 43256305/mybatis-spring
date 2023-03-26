package com.example.study.mapper;

import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.sample.domain.User;

import java.util.List;

public interface TestUserMapper {

  @Select("select * from users")
  List<User> list();

}
