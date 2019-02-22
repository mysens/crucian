package cn.mysens.crucian.redis;

import cn.mysens.crucian.lock.Locker;
import org.redisson.api.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * <br>
 *
 * @author 张成<br>
 * @date 2019年01月21日<br>
 */
public class RedisLocker implements Locker {

    private static final Logger log = LoggerFactory.getLogger(RedisLocker.class);

    private RLock locker;

    private long timeout = -1;

    private TimeUnit timeUnit = TimeUnit.MILLISECONDS;

    public RedisLocker(RLock rLock) {
        this.locker = rLock;
    }

    public RedisLocker(RLock locker, long timeout, TimeUnit timeUnit) {
        this.locker = locker;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
    }

    @Override
    public boolean tryLock() throws InterruptedException {
        try {
            if(this.timeout > 0){
                return locker.tryLock(100, timeout, timeUnit);
            }
            return locker.tryLock(100,-1, timeUnit);
        } catch (InterruptedException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new RuntimeException(e.getMessage(),e);
        }
    }

    @Override
    public boolean tryLock(long waitTime, TimeUnit timeUnit) throws InterruptedException {
        try {
            return locker.tryLock(100, waitTime, timeUnit);
        } catch (InterruptedException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new RuntimeException(e.getMessage(),e);
        }
    }

    @Override
    public void release(String key) {
        try {
            locker.unlock();
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public boolean hasLock() {
        try {
            return locker.isHeldByCurrentThread();
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
