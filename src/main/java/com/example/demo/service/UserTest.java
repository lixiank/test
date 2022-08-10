package com.example.demo.service;



import cn.hutool.core.collection.CollUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.example.demo.bean.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author lixk
 * @date 2022/7/1 18:07
 */
@Service
@Slf4j
public class UserTest {
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    //开启线程池
    ExecutorService threadPool = Executors.newFixedThreadPool(10);
    public List<User> getUserData2() {
        List<User> users = new ArrayList<>();
        for (int i = 1; i <= 1000000; i++) {

            int finalI = i;
            taskExecutor.submit(new Runnable() {
                @Override
                public void run() {
                    User user = User.builder()
                            .userId(finalI)
                            .userName("admin\t\n" + finalI)
                            .gender(finalI % 2 == 0 ? "男" : "女")
                            .salary(finalI * 1000.00)
                            .hireDate(new Date())
                            .build();
                    users.add(user);
                    log.info("当前线程添加" + Thread.currentThread().getName(),user.getUserId());
                    try {
                        Thread.sleep(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        return  users;
    }

    // 根据user模板构建数据
    public List<User> getUserData() throws InterruptedException, ExecutionException {
        Future<List<User>> submit = null;
        List<User> users = new ArrayList<>();
        for (int i = 1; i <= 1000000; i++) {
            int finalI = i;

            submit = threadPool.submit(new Callable<List<User>>() {
                @Override
                public List<User> call() throws Exception {
                        User user = User.builder()
                                .userId(finalI)
                                .userName("admin\t\n" + finalI)
                                .gender(finalI % 2 == 0 ? "男" : "女")
                                .salary(finalI * 1000.00)
                                .hireDate(new Date())
                                .build();
                    users.add(user);
                        System.out.println("当前线程：" + Thread.currentThread().getName());
                        try {
                            Thread.sleep(0);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return users;
                    }
                });
        }
        return submit.get();
    }

    public List<User> getUserData1() throws InterruptedException, ExecutionException {
        Future<List<User>> submit = null;
        List<User> users = new ArrayList<>();
        for (int i = 1; i <= 1000000; i++) {
            int finalI = i;
            submit = threadPool.submit(new Callable<List<User>>() {
                @Override
                public List<User> call() throws Exception {
                    User user = User.builder()
                            .userId(finalI)
                            .userName("lxk\t\n" + finalI)
                            .gender(finalI % 2 == 0 ? "男" : "女")
                            .salary(finalI * 1000.00)
                            .hireDate(new Date())
                            .build();
                    users.add(user);
                    System.out.println("当前线程：" + Thread.currentThread().getName());
                    try {
                        Thread.sleep(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return users;
                }
            });
        }
        return submit.get();
    }

    public void test(HttpServletResponse response) throws IOException {
        String filename = "user.xlsx" ;
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(filename, "utf-8"));
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        WriteSheet sheet3 = EasyExcel.writerSheet(1, "用户2").head(User.class).build();
        ExcelWriter excel = EasyExcel.write(response.getOutputStream(),User.class).build();

        List<User> users = new ArrayList<>();
        for (int i = 1; i <= 1000000; i++) {
            User user = User.builder()
                    .userId(i)
                    .userName("lxk\t\n" + i)
                    .gender(i % 2 == 0 ? "男" : "女")
                    .salary(i * 1000.00)
                    .hireDate(new Date())
                    .build();
            users.add(user);
        }
        List<List<User>> data = CollUtil.split(users, 1000);
        users.clear();
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(20, 20, 0L,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        List<CompletableFuture<Integer>> futures = data.stream()
                .map(item -> CompletableFuture.supplyAsync(() -> test1(item,excel,sheet3), threadPool))
                .collect(Collectors.toList());
        Integer result = futures.stream().map(CompletableFuture::join).mapToInt(Integer::valueOf).sum();
        // 关闭线程池
        threadPool.shutdown();
        // 清理内存
        futures.clear();
        data.clear();
    }
    private Integer test1(List<User> list ,ExcelWriter excel, WriteSheet sheet3 )  {
            excel.write(list,sheet3);
            excel.finish();
            return 0;
    }
}
