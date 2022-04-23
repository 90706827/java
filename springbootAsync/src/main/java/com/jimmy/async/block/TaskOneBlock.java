package com.jimmy.async.block;

import com.jimmy.async.core.IdUtils;
import com.jimmy.async.core.SupplyAsyncBaseBlock;
import com.jimmy.async.entity.TestA;
import com.jimmy.async.mapper.TestAMapper;
import com.jimmy.async.mapper.TestBMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Description
 * @Author zhangguoq
 * @Date 2022/4/20 23:27
 **/
@Service
public class TaskOneBlock extends SupplyAsyncBaseBlock<String, Void, Integer> {
    private final static Logger logger = LoggerFactory.getLogger(TaskOneBlock.class);
    @Resource
    private IdUtils idUtils;
    @Resource
    private TestAMapper testAMapper;
    @Resource
    private TestBMapper testBMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer execute(String o) {
        String result = o + "A";
        logger.info(result);
        TestA testa = new TestA();
        testa.setId(idUtils.snowflakeId());
        testa.setName("A" + idUtils.randomUUID());
        testAMapper.insert(testa);
        sleep(5000);
        return 1;
    }

}
