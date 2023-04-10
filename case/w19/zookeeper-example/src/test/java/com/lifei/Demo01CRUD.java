package com.lifei;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Demo01CRUD {
    CuratorFramework client;

    @Before
    public void initClient() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(3000,3);
        client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", retryPolicy);
        // start client
        client.start();
        System.out.println("ZK Client has connected to the ZK server.");
    }

    @Test
    public void create() throws Exception {
        String path = "/cu2";
        // create a node
//        client.create().forPath(path);
        // write data to a node
//        client.setData().forPath(path, "lifei writes /cu2".getBytes());
        // create node with multi level
//        client.create().creatingParentsIfNeeded().forPath("/cu3/a", "I am a multi-level node".getBytes());
        // create ephemeral node
//        client.create().withMode(CreateMode.EPHEMERAL).forPath("/cu4", "ephemeral node".getBytes());
        // create persistent sequential node
        client.create().withMode(CreateMode.PERSISTENT_SEQUENTIAL).forPath("/cu5", "persistent sequential1".getBytes());
        client.create().withMode(CreateMode.PERSISTENT_SEQUENTIAL).forPath("/cu5", "persistent sequential2".getBytes());
    }

    @Test
    public void testListenNode() throws Exception {
        //1. create node listener NodeCache: set listener node and callback method
        NodeCache nodeCache = new NodeCache(client, "/lifei");

        // set listener node
        ChildData currentData = nodeCache.getCurrentData();
        System.out.println("Current node value = " + currentData);

        // start to listen
        nodeCache.start(true);

        // set listening callback method
        nodeCache.getListenable().addListener(() -> {
            // get the value of current node
            ChildData currentData1 = nodeCache.getCurrentData();
            // get the latest node name
            String path = currentData1.getPath();
            // get the data of the latest node
            byte[] currentDataByte = currentData1.getData();
            System.out.println("Modified Data: " + new String(currentDataByte));
            System.out.println("------------------>>");
        });

        System.in.read();
    }

    @Test
    public void listenSubNode() throws Exception {
        // create PathChildrenCache
        PathChildrenCache childrenCache = new PathChildrenCache(client, "/lifei", true);

        // start watching
        childrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);

        // set callback method for listening
        childrenCache.getListenable().addListener((client, event) -> {
            // get the modified data
            byte[] bytes = event.getData().getData();
            System.out.println("Data of the node = " + new String(bytes));

            // get the modified child node
            System.out.println("node name = " + event.getData().getPath());

            PathChildrenCacheEvent.Type type = event.getType();
            System.out.println("event triggering type = " + type);

            System.out.println("------------------>>");
        });

        //3.block program
        System.in.read();
    }

    @Test
    public void treeCache() throws Exception {
        // 2. create node listener TreeCache
        TreeCache treeCache = new TreeCache(client, "/lifei");

        // start cache
        treeCache.start();

        // add listener callback method
        treeCache.getListenable().addListener((client, event) -> {
            // get modified data
            byte[] bytes = event.getData().getData();

            System.out.println("data inside the node = " + new String(bytes));

            // get modified sub nodes
            System.out.println("node name = " + event.getData().getPath());

            // get event type
            TreeCacheEvent.Type type = event.getType();
            System.out.println("event triggering type = " + type);
            System.out.println("--------------->>");
        });

        // block program
        System.in.read();
    }

    @After
    public void closeClient() {
        client.close();
    }
}
