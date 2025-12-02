package com.example.demo.test;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;

/**
 * @author lidaye
 */
@Component

public class XxlJobTest {
    @XxlJob("test1")
    public ReturnT<String> Test(String param) throws Exception {
        for (int i = 0; i < 5; i++) {
            System.out.println("调度了");

        }
        return ReturnT.SUCCESS;
    }

}
