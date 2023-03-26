package com.example.study.mapper;

import com.example.study.entity.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper {

  @Select("select * from users")
  public List<User> list();

}
