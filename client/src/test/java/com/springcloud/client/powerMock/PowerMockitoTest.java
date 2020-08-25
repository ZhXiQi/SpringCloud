package com.springcloud.client.powerMock;

import com.springcloud.client.redis.demo.User;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author ZhXiQi
 *
 * @BeforeClass – 表示在类中的任意public static void方法执行之前执行
 * @AfterClass  – 表示在类中的任意public static void方法执行之后执行
 * @Before      – 表示在任意使用@Test注解标注的public void方法执行之前执行
 * @After       – 表示在任意使用@Test注解标注的public void方法执行之后执行
 * @Test        – 使用该注解标注的public void方法会表示为一个测试方法
 *
 * @Title:
 * @date 2020/8/25 20:59
 */
@RunWith(PowerMockRunner.class) //在任何需要用到 PowerMock 的类开始之前，需要做此声明，让测试运行于 PowerMock 环境
@PrepareForTest({}) //如果需要测试静态方法，需要将静态方法的类提供给 PowerMock
public class PowerMockitoTest {

    @InjectMocks    //此注解表示这个对象需要被注入mock对象
    private User user;
    @Mock           //此注解会自动创建一个mock对象并注入到 @InjectMocks 对象中，也可在 @Before 中手动进行 mock 对象的创建和注入
    private Object object;

    @Before
    public void setUp() {
        /*
        user = new User();
        object = PowerMockito.mock(Object.class);   //创建mock对象
        Whitebox.setInternalState(user,"fieldName",object); //注入依赖对象
        */
        MockitoAnnotations.initMocks(this);
//        PowerMockito.mockStatic();
    }
}
