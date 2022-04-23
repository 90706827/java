package com.jimmy.async.block;

import com.jimmy.async.core.SupplyAsyncBaseBlock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author zhangguoq
 * @Date 2022/4/23 14:15
 **/
@Service
public class TaskThreeBlock extends SupplyAsyncBaseBlock<Integer,String,Integer> {
    private final static Logger logger = LoggerFactory.getLogger(TaskThreeBlock.class);
    @Override
    public Integer execute(Integer i,String s) {
        String result =s + "C";
        logger.info(result);
        sleep(5000);
        return 2;
    }
}
