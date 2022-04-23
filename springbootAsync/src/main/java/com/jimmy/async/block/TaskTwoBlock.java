package com.jimmy.async.block;

import com.jimmy.async.core.IdUtils;
import com.jimmy.async.core.SupplyAsyncBaseBlock;
import com.jimmy.async.entity.TestB;
import com.jimmy.async.mapper.TestBMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description
 * @Author zhangguoq
 * @Date 2022/4/23 13:58
 **/
@Service
public class TaskTwoBlock extends SupplyAsyncBaseBlock<Integer,Void, String> {
    private final static Logger logger = LoggerFactory.getLogger(TaskTwoBlock.class);
    @Resource
    private TestBMapper testBMapper;
    @Resource
    private IdUtils idUtils;

    @Override
    public String execute(Integer s) {
        String result = s + "B";
        logger.info(result);
        TestB testa = new TestB();
        testa.setId(idUtils.snowflakeId());
        testa.setName("B" + idUtils.randomUUID());
        testBMapper.insert(testa);
        sleep(3000);
        return result;
    }
}
