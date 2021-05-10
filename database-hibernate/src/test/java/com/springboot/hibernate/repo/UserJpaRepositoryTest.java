package com.springboot.hibernate.repo;

import com.springboot.hibernate.bean.UserBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityManager;
import java.util.concurrent.TimeUnit;


/**
 * @author ZhXiQi
 * @Title:
 * @date 2020/6/18 09:51
 */
//@SpringBootApplication(scanBasePackages = "com.springboot.hibernate")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Validated
public class UserJpaRepositoryTest {

    @Autowired
    private UserJpaRepository userJpaRepository;
    @Autowired
    private EntityManager entityManager;

    @Test
    @Rollback(false)
    public void addUser() throws Exception {

        UserBean userOne = userJpaRepository.findById(1).get();
        userOne.setVersion(userOne.getVersion() + 1);
        entityManager.detach(userOne);
        userOne.setName("321");
        new Thread(new Runnable() {
            @Override
            public void run() {
                UserBean userById = userJpaRepository.findById(1).get();
                entityManager.detach(userById);
                System.out.println(userById.getVersion());
                userById.setName("123");
                UserBean save1 = userJpaRepository.save(userById);
                System.out.println(save1.getVersion());
                System.out.println(save1);
            }
        }).start();

        TimeUnit.MILLISECONDS.sleep(1000);
        UserBean save = userJpaRepository.save(userOne);
        System.out.println(save);
    }

}