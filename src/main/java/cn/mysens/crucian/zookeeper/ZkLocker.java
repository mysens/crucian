package cn.mysens.crucian.zookeeper;

import cn.mysens.crucian.lock.Locker;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * <br>
 *
 * @author 张成<br>
 * @date 2019年01月21日<br>
 */
public class ZkLocker implements Locker {

    private static final Logger log = LoggerFactory.getLogger(ZkLocker.class);

    private InterProcessLock locker;

    ZkLocker(InterProcessLock interProcessLock) {
        this.locker = interProcessLock;
    }

    @Override
    public boolean tryLock() throws InterruptedException {
        try {
            return locker.acquire(0, null);
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
            return locker.acquire(waitTime, timeUnit);
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
            locker.release();
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public boolean hasLock() {
        try {
            return locker.isAcquiredInThisProcess();
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
