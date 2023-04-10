package com.lifei.distributedlock;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.utils.CloseableUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.io.UnsupportedEncodingException;

@Slf4j
@Data
public class ZKClient {
    private CuratorFramework client;

    private static final String ZK_ADDRESS = "127.0.0.1:2181";

    public static ZKClient instance = null;

    static {
        instance = new ZKClient();
        instance.init();
    }

    private ZKClient() {

    }

    public void init() {
        if (null != client) {
            return;
        }

        // create client
        client = ClientFactory.createSimple(ZK_ADDRESS);
        client.start();
    }

    public void destroy() {
        CloseableUtils.closeQuietly(client);
    }

    public void createNode(String zkPath, String data) {
        try {
            // create a ZNode whose data is the payload
            byte[] payload = "to set content".getBytes("UTF-8");
            if (data != null){
                payload = data.getBytes("UTF-8");
            }
            client.create()
                    .creatingParentsIfNeeded()
                    .withMode(CreateMode.PERSISTENT)
                    .forPath(zkPath, payload);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteNode(String zkPath) {
        try {
            if (!isNodeExist(zkPath)) {
                return;
            }
            client.delete().forPath(zkPath);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isNodeExist(String zkPath) {
        try {
            Stat stat = client.checkExists().forPath(zkPath);
            if (null == stat) {
                System.out.println("Node not exist: " + zkPath);
                return false;
            } else {
                System.out.println("Node exist and stat is " + stat);
                return true;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String createEphemeralSeqNode(String srcPath) {
        try {
            String path = client.create()
                    .creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                    .forPath(srcPath);
            return path;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
