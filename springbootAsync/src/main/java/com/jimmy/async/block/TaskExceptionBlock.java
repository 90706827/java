package com.jimmy.async.block;

import com.jimmy.async.core.SupplyAsyncBaseBlock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author zhangguoq
 * @Date 2022/4/23 23:18
 **/
@Service
public class TaskExceptionBlock extends SupplyAsyncBaseBlock<Integer,Exception,String> {
        private final static Logger logger = LoggerFactory.getLogger(TaskThreeBlock.class);
        @Override
        public String exception(Integer i,Exception s) {
            String result =s + "C";
            logger.info(result);
            sleep(5000);
            return result;
        }
}
