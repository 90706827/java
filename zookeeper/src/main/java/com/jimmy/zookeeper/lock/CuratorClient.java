package com.jimmy.zookeeper.lock;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.CuratorCache;
import org.apache.curator.framework.recipes.cache.CuratorCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @author jimmy
 */
public class CuratorClient {
    private static final Logger logger = LoggerFactory.getLogger(CuratorClient.class);

    public static void main(String[] args) {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1:2181")
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(5000)
                .retryPolicy(retryPolicy)
                .namespace("zoo")
                .build();
        client.start();

        try {
            /**
             * CreateMode.EPHEMERAL - 临时
             * CreateMode.EPHEMERAL_SEQUENTIAL - 临时有序
             * CreateMode.PERSISTENT  - 持久
             * CreateMode.PERSISTENT_SEQUENTIAL - 持久有序
             */
            String path = "/zhangsan1";
            Stat exist = client.checkExists().forPath(path);
//            client.create()
//                    .creatingParentContainersIfNeeded() //自动递归创建父节点
//                    .withMode(CreateMode.PERSISTENT)
//                    .forPath("path","19999999".getBytes(StandardCharsets.UTF_8));

//           logger.info(client.getData().forPath("/zhangsan1"));
//            client.delete().forPath("/zhangsan1");
//            https://juejin.cn/post/7022614644489453582
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static CuratorFramework getClient() {
        return CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1:2181")
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .connectionTimeoutMs(15 * 1000)
                .sessionTimeoutMs(60 * 1000)
                .namespace("arch")
                .build();
    }

    public static void pathChildrenCache() throws Exception {
        final String path = "/pathChildrenCache";
        final CuratorFramework client = getClient();
        client.start();

        CuratorCacheListener listener = CuratorCacheListener.builder()
                .forPathChildrenCache(File.separator, client, (c, event) -> {
                    // child node listener
                    switch (event.getType()) {
                        case CHILD_ADDED:
                            logger.info("CHILD_ADDED:" + event.getData().getPath());
                            break;
                        case CHILD_REMOVED:
                            logger.info("CHILD_REMOVED:" + event.getData().getPath());
                            break;
                        case CHILD_UPDATED:
                            logger.info("CHILD_UPDATED:" + event.getData().getPath());
                            break;
                        case CONNECTION_LOST:
                            logger.info("CONNECTION_LOST:" + event.getData().getPath());
                            break;
                        case CONNECTION_RECONNECTED:
                            logger.info("CONNECTION_RECONNECTED:" + event.getData().getPath());
                            break;
                        case CONNECTION_SUSPENDED:
                            logger.info("CONNECTION_SUSPENDED:" + event.getData().getPath());
                            break;
                        case INITIALIZED:
                            logger.info("INITIALIZED:" + event.getData().getPath());
                            break;
                        default:
                            break;
                    }
                })
                .forNodeCache(() -> {
                    logger.info("do something when node changed");
                }).forTreeCache(client, (c, e) -> {
                    logger.info("status changed:{}", e.getType());
                    switch (e.getType()) {
                        case INITIALIZED:
                            logger.info("init ");
                            break;
                        case NODE_ADDED:
                            logger.info("add ");
                            break;
                        case NODE_UPDATED:
                            logger.info("update ");
                            break;
                        case NODE_REMOVED:
                            logger.info("remove ");
                            break;
                        default:
                            break;
                    }
                }).build();

        final CuratorCache curatorCache = CuratorCache.builder(client, path).build();
        curatorCache.listenable().addListener(listener);
        curatorCache.start();

//                client.create().withMode(CreateMode.PERSISTENT).forPath(path);
        Thread.sleep(1000);

        client.create().withMode(CreateMode.PERSISTENT).forPath(path + "/c1");
        Thread.sleep(1000);

        client.delete().forPath(path + "/c1");
        Thread.sleep(1000);

        client.delete().forPath(path); //监听节点本身的变化不会通知
        Thread.sleep(1000);

        client.close();

    }


}
