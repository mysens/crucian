package cn.mysens.crucian.redis;

import cn.mysens.crucian.lock.LockTemplate;
import cn.mysens.crucian.lock.Locker;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

/**
 * redis锁<br>
 *
 * @author 张成<br>
 * @date 2019年01月21日<br>
 */
public class RedisReentrantLock implements LockTemplate {

    /**
     * redis分隔符
     */
    private static final String SEPARATOR = "::";

    /**
     * 所有锁的根位置
     */
    private static final String ROOT_PATH = "LOCK_ROOT::";

    /**
     * 默认超时时间
     */
    private long timeout = -1L;

    /**
     * 锁前缀
     */
    private String keyPrefix;

    /**
     * redisson client
     */
    private RedissonClient client;

    public RedisReentrantLock(RedisClient redisClient) {
        this.keyPrefix = ROOT_PATH + redisClient.getNamespace() + SEPARATOR;
        this.timeout = redisClient.getTimeout();
        this.client = redisClient.getClient();
        if(client == null){
            throw new RuntimeException("redis client is not initialize!");
        }
    }

    @Override
    public Locker build(String key) {
        String realKey = this.keyPrefix + key;
        RLock rLock = client.getLock(realKey);
        if(timeout>0){
            return new RedisLocker(rLock, timeout, TimeUnit.MILLISECONDS);
        }
        return new RedisLocker(rLock);
    }

    @Override
    public Locker build(String key, long timeout, TimeUnit timeUnit) {
        String realKey = this.keyPrefix + key;
        RLock rLock = client.getLock(realKey);
        return new RedisLocker(rLock, timeout, timeUnit);
    }
}
