import com.qiangke.StaticTest;
import com.qiangke.TestA;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Method;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

/**
 * @author zhangguoq
 * @description
 * @date 2024/5/29 14:29
 **/
@ExtendWith(MockitoExtension.class)
public class TestAbc {

    MockedStatic<StaticTest> mockedStatic;
    @Mock
    TestA testA;

    @BeforeEach
    public void before() throws Exception {
        mockedStatic = Mockito.mockStatic(StaticTest.class);
    }

    @AfterEach
    public void after() throws Exception {
        mockedStatic.close();
    }


    @Test
    public void staticTest() throws Exception {
        String name = TestA.getStaticName("test");
        Assertions.assertEquals("test-A", name);
    }

    @Test
    public void innerStaticTest() throws Exception {
        mockedStatic.when(() -> StaticTest.getStaticName(anyString())).thenReturn("test-A");
        String name = TestA.doStaticName("test");
        Assertions.assertEquals("test-A", name);
    }


    @Test
    public void privateMethodTest() throws Exception {
        Method method = TestA.class.getDeclaredMethod("getPrivateName", String.class);
        method.setAccessible(true);
        String result =  (String) method.invoke(testA, "test");
        Assertions.assertEquals("test-你好", result);
    }

}
