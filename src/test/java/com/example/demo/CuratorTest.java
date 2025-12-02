package com.example.demo;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
public class CuratorTest {
    /**
     * 建立连接
     */
    @Test
    public void testConnection() {
        //第一种方式
//        CuratorFramework client = CuratorFrameworkFactory.newClient("106.14.72.68:2181", new ExponentialBackoffRetry(1000, 3));
//        //开启连接
//        client.start();
//        //具体操作
//
//        //关闭连接
//        client.close();

        //第二种方式
        CuratorFramework client1 = CuratorFrameworkFactory.builder().connectString("106.14.72.68:2181").
                retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
        //开启连接
        client1.start();

        //具体操作

    }

    /**
     * 创建节点:持久 临时 有序 数据
     */
    @Test
    public void addNode() throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString("106.14.72.68:2181").
                retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .sessionTimeoutMs(60 * 1000)
                .connectionTimeoutMs(15 * 1000)
                .namespace("lxk")
                .build();
        //开启连接
        client.start();
        //1、创建节点
        //如果创建节点，没有指定数据，则默认将当前客户端的ip地址作为数据存储
        String s = client.create().forPath("/app1");
        //2、创建节点,带有数据
        String s1 = client.create().forPath("/app2", "123".getBytes());
        //3、创建节点，并设置节点类型
        String s2 = client.create().withMode(CreateMode.EPHEMERAL).forPath("/app3", "haha".getBytes());
        //4、创建多级节点
        String s3 = client.create().creatingParentsIfNeeded().forPath("/app4/p1", "hehe".getBytes());
    }

    /**
     * 查询节点
     */
    @Test
    public void queryNode() throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString("106.14.72.68:2181").
                retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .sessionTimeoutMs(60 * 1000)
                .connectionTimeoutMs(15 * 1000)
                .namespace("lxk")
                .build();
        //开启连接
        client.start();
        //1、查询节点数据：get
        byte[] bytes = client.getData().forPath("/app1");
        System.out.println(new String(bytes));
        //2、查询字节点：ls
        List<String> strings = client.getChildren().forPath("/");
        System.out.println(strings);
        //3、查询节点状态信息：ls -s
        Stat stat = new Stat();
        byte[] bytes1 = client.getData().storingStatIn(stat).forPath("/app1");
        System.out.println(new String(bytes1));
        System.out.println(stat);
    }


    /**
     * 修改节点
     */
    @Test
    public void updateNode() throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString("106.14.72.68:2181").
                retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .sessionTimeoutMs(60 * 1000)
                .connectionTimeoutMs(15 * 1000)
                .namespace("lxk")
                .build();
        //开启连接
        client.start();
        //修改节点数据，直接修改
        Stat stat1 = client.setData().forPath("/app1", "lxkssg".getBytes());
        System.out.println("stat1.version = " + stat1.getVersion());

        //修改节点数据，根据版本号修改
        Stat stat = new Stat();
        client.getData().storingStatIn(stat).forPath("/app1");
        System.out.println("stat.version = " + stat.getVersion());
        Stat stat2 = client.setData().withVersion(stat.getVersion()).forPath("/app1", "lxkssg".getBytes());
        System.out.println("stat2.version = " + stat2.getVersion());

    }

    /**
     * 删除节点
     */
    @Test
    public void deleteNode() throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString("106.14.72.68:2181").
                retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .sessionTimeoutMs(60 * 1000)
                .connectionTimeoutMs(15 * 1000)
                .namespace("lxk")
                .build();
        //开启连接
        client.start();
        //1、删除单个节点
        client.delete().forPath("/app1");
        //2、删除带有子节点的节点
        client.delete().deletingChildrenIfNeeded().forPath("/app4");
        //3、强制删除
        client.delete().guaranteed().forPath("/app2");
        //4、回调
        client.delete().guaranteed().inBackground((curatorFramework, curatorEvent) -> System.out.println("删除成功"))
                .forPath("/app2");
    }

    /**
     *NodeCache Watch事件
     */
    @Test
    public void testNodeCacheWatch() throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString("106.14.72.68:2181").
                retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .sessionTimeoutMs(60 * 1000)
                .connectionTimeoutMs(15 * 1000)
                .namespace("lxk")
                .build();
        //开启连接
        client.start();
        //1、监听节点的创建
        NodeCache cache = new NodeCache(client, "/app1");
        //2、注册监听
        cache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                System.out.println("节点发生变化");
                //获取修改节点后的数据
                System.out.println(new String(cache.getCurrentData().getData()));
            }
        });
        //3、开启监听
        cache.start();

        while (true) {

        }
    }
    /**
     * Watch事件
     */
    @Test
    public void testPathChildrenCacheWatch() throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString("106.14.72.68:2181").
                retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .sessionTimeoutMs(60 * 1000)
                .connectionTimeoutMs(15 * 1000)
                .namespace("lxk")
                .build();
        //开启连接
        client.start();
        //1、监听节点的创建
        PathChildrenCache cache = new PathChildrenCache(client, "/app1", true);
        //2、注册监听
        cache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                System.out.println("节点发生变化");
            }
        });
        //3、开启监听
        cache.start();

        while (true) {}
    }

    /**
     * Watch事件
     */
    @Test
    public void testTreeCacheWatch() throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString("106.14.72.68:2181").
                retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .sessionTimeoutMs(60 * 1000)
                .connectionTimeoutMs(15 * 1000)
                .namespace("lxk")
                .build();
        //开启连接
        client.start();
        //1、监听节点的创建
        TreeCache cache = new TreeCache(client, "/");
        //2、注册监听
        cache.getListenable().addListener(new TreeCacheListener(){

            @Override
            public void childEvent(CuratorFramework curatorFramework, TreeCacheEvent treeCacheEvent) throws Exception {

                System.out.println("节点发生变化");
            }
        });
        //3、开启监听
        cache.start();

        while (true) {}
    }

}
