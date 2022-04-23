package com.jimmy.async.service;

import com.jimmy.async.block.TaskExceptionBlock;
import com.jimmy.async.context.ContextHelper;
import com.jimmy.async.block.TaskOneBlock;
import com.jimmy.async.block.TaskThreeBlock;
import com.jimmy.async.block.TaskTwoBlock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;

/**
 * @Description
 * @Author zhangguoq
 * @Date 2022/4/23 10:41
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskOneTest {
    private static final Logger logger = LoggerFactory.getLogger(TaskOneTest.class);
    @Resource
    private TaskOneBlock taskOneBlock;
    @Resource
    private TaskTwoBlock taskTwoBlock;
    @Resource
    private TaskThreeBlock taskThreeBlock;
    @Resource
    private TaskExceptionBlock taskExceptionBlock;
    @Test
    public void Test(){
        try {
            logger.info("start");
            ContextHelper.setTraceId("123456");
            CompletableFuture<Integer> one = taskOneBlock.supplyAsync("one");
            logger.info("start_one");
            CompletableFuture<String> two = taskTwoBlock.supplyAsync(1);
            logger.info("start_two");
//            String param = one.get() + two.get();
            logger.info("start_param");
            CompletableFuture<Integer> three = taskThreeBlock.thenCombineAsync(one,two);
            CompletableFuture<Integer> exception = taskExceptionBlock.whenCompleteAsync(three);
            exception.get();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
