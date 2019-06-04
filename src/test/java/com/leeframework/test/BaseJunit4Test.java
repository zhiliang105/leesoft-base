package com.leeframework.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:context/applicationContext-spring.xml" })
// @Transactional
// @TransactionConfiguration(transactionManager = "tx", defaultRollback = false)
public class BaseJunit4Test {

    @Test
    public void test() {

    }
}
