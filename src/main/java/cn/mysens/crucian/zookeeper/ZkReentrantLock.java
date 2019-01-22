package cn.mysens.crucian.zookeeper;

import cn.mysens.crucian.lock.Locker;
import cn.mysens.crucian.lock.LockTemplate;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * zookeeper分布式锁<br>
 *
 * @author 张成<br>
 * @date 2019年01月21日<br>
 */
public class ZkReentrantLock implements LockTemplate {

    private static final Logger log = LoggerFactory.getLogger(ZkReentrantLock.class);

    /**
     * zk分隔符
     */
    private static final String SEPARATOR = "/";

    /**
     * 所有锁的根位置
     */
    private static final String ROOT_PATH = "/LOCK_ROOT/";

    /**
     * 默认超时时间
     */
    private long timeout = -1L;

    /**
     * 锁前缀
     */
    private String keyPrefix;

    /**
     * Zookeeper client
     */
    private CuratorFramework client;


    public ZkReentrantLock(ZkClient zkClient){
        init(zkClient);
    }

    private void init(ZkClient zkClient){
        this.keyPrefix = ROOT_PATH + zkClient.getNameSpace() + SEPARATOR;
        this.timeout = zkClient.getTimeout();
        this.client = zkClient.getClient();
        if(client == null){
            throw new RuntimeException("zk client is not initialize!");
        }
    }

    @Override
    public Locker build(String key) {
        String realKey = this.keyPrefix + key;
        InterProcessMutex interProcessMutex = new InterProcessMutex(client, realKey);
        if(timeout>0){
            return new ZkLocker(interProcessMutex, timeout, TimeUnit.SECONDS);
        }
        return new ZkLocker(interProcessMutex);
    }

    @Override
    public Locker build(String key, long timeout, TimeUnit timeUnit) {
        String realKey = this.keyPrefix + key;
        InterProcessMutex interProcessMutex = new InterProcessMutex(client, realKey);
        return new ZkLocker(interProcessMutex, timeout, timeUnit);
    }

}
