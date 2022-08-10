package com.example.demo.controller;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.example.demo.bean.User;
import com.example.demo.service.UserTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

/**
 * @author lixk
 * @date 2022/7/1 18:31
 */
@RestController
@RequestMapping("/api")
public class ExeclText {
    @Autowired
    private UserTest userTest;

    @GetMapping("/excel")
    public String excel(HttpServletResponse response) throws IOException, InterruptedException, ExecutionException {
        UserTest userTest = new UserTest();
        String filename = "user.xlsx" ;
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(filename, "utf-8"));
        response.flushBuffer();
//        EasyExcel.write(response.getOutputStream(),User.class).sheet().doWrite(userTest.getUserData());
        //创建excel
        ExcelWriter excel = EasyExcel.write(response.getOutputStream(),User.class).build();

//        ExcelWriter excel = EasyExcel.write(filename).build();
        //创建sheet页
        WriteSheet sheet1 = EasyExcel.writerSheet(1, "用户1").head(User.class).build();
        WriteSheet sheet2 = EasyExcel.writerSheet(2, "用户2").head(User.class).build();

        //往sheet页里写数据
//        excel.write(userTest.getUserData(), sheet1);
//        excel.write(userTest.getUserData1(),sheet2);
        excel.write(userTest.getUserData2(),sheet2);
        excel.finish();
        return  "ok";
    }

    @GetMapping("/test")
    public String test(HttpServletResponse response){
        try {
            userTest.test(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  "ok";
    }
}
