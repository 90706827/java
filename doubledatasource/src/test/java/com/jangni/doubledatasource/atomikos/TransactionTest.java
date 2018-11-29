package com.jangni.doubledatasource.atomikos;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description TODO
 * @Author Jangni
 * @Date 2018/11/24 9:56
 **/

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {DataSourceConfig.class, TransactionManagerConfig.class})
@Transactional(value = "transactionManager", rollbackFor = Exception.class)
public class TransactionTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private JdbcTemplate busJdbcTemplate;
    @Autowired
    private JdbcTemplate sysJdbcTemplate;

    @Test

    public void test() {
        sysJdbcTemplate.execute("insert into sys_a(name) values('name1')");
        busJdbcTemplate.execute("insert into bus_b(name) values('name')");
    }

}