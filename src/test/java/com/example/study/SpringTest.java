package com.example.study;

import com.example.study.mapper.TestUserMapper;
import com.example.study.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

public class SpringTest {

  @Test
  public void test(){

    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
    UserMapper userMapper = (UserMapper) context.getBean("userMapper");
    // 没有事务时，每次调用Mapper接口的方法，都会创建一个全新的defaultSqlSession，执行之后强制提交
    System.out.println(userMapper.list());
    // 没有事务，所以这里不会走缓存
    System.out.println(userMapper.list());
    // 可以看到这个Mapper的sqlSessionTemplate与上面Mapper的不是同一个，所以SqlSessionTemplate与Mapper时一对一关系
    // 如果我们配置了一个sqlSessionTemplate，则这里会共享使用此sqlSessionTemplate
    TestUserMapper testUserMapper = (TestUserMapper) context.getBean("testUserMapper");
    System.out.println();

  }

  @Test
  public void transactionTest(){
    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
    UserMapper userMapper = (UserMapper) context.getBean("userMapper");
    PlatformTransactionManager transactionManager = context.getBean(PlatformTransactionManager.class);
    TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
    // 这里调用transactionTemplate.execute，会会先开启事务，执行完成业务方法后会提交事务。事务的提交与回滚是交给transactionManager完成的。
    // 详情查看事务源码
    transactionTemplate.execute(new TransactionCallbackWithoutResult() {
      protected void doInTransactionWithoutResult(TransactionStatus status) {
        System.out.println(userMapper.list());
        // 因为同一个事务，所以这里走了缓存
        System.out.println(userMapper.list());
      }
    });
  }
}
