package com.jimmy.async.service;

import com.jimmy.async.core.IdUtils;
import com.jimmy.async.entity.TestA;
import com.jimmy.async.entity.TestB;
import com.jimmy.async.mapper.TestAMapper;
import com.jimmy.async.mapper.TestBMapper;
import com.jimmy.async.block.TaskOneBlock;
import com.jimmy.async.block.TaskTwoBlock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;

/**
 * @Description
 * @Author zhangguoq
 * @Date 2022/4/23 15:25
 **/
@Service
public class TestService {
    private final static Logger logger = LoggerFactory.getLogger(TestService.class);
    @Resource
    private IdUtils idUtils;
    @Resource
    private TestAMapper testAMapper;
    @Resource
    private TestBMapper testBMapper;
    @Resource
    private TaskOneBlock taskOneBlock;
    @Resource
    private TaskTwoBlock taskTwoBlock;

    public TestA getTestA(long id) {
        return testAMapper.selectById(id);
    }

    public String asyncInsert() throws Exception {
        CompletableFuture<Integer> one = taskOneBlock.supplyAsync("one");
        CompletableFuture<String> two = taskTwoBlock.supplyAsync(1);
        try {
            String param = one.get()+two.get();
            logger.info(param);
        } catch (Exception e) {
           throw new Exception("a");
        }
        return "成功";
    }
    @Transactional(rollbackFor = Exception.class)
    public String insert() {
        TestA testa = new TestA();
        testa.setId(idUtils.snowflakeId());
        testa.setName("A"+idUtils.randomUUID());
        testAMapper.insert(testa);

        TestB testb = new TestB();
        testb.setId(idUtils.snowflakeId());
        testb.setName("B"+idUtils.randomUUID());
        testBMapper.insert(testb);
        int a = 1/0;
        return "成功";
    }

}
