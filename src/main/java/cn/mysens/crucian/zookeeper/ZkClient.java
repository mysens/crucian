package cn.mysens.crucian.zookeeper;

import cn.mysens.crucian.config.ZkConfig;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * <br>
 *
 * @author 张成<br>
 * @date 2019年01月21日<br>
 */
public class ZkClient {

    /**
     * 命名路径
     */
    private String namespace;

    /**
     * Zookeeper client
     */
    private CuratorFramework client;

    public ZkClient(ZkConfig config) {
        this.namespace = config.getNamespace();
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        client = CuratorFrameworkFactory.newClient(config.getZkHosts(), retryPolicy);
        client.start();
    }

    public String getNamespace() {
        return namespace;
    }

    public CuratorFramework getClient() {
        return client;
    }
}
