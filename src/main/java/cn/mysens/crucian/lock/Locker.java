package cn.mysens.crucian.lock;

import java.util.concurrent.TimeUnit;

/**
 * 分布式锁<br>
 *
 * @author 张成<br>
 * @date 2019年01月21日<br>
 */
public interface Locker {

    /**
     * 尝试获取锁
     * @return
     */
    boolean tryLock() throws InterruptedException;

    /**
     * 尝试获取锁
     * @param waitTime 等待时间
     * @param timeUnit 时间单位
     * @return
     * @throws InterruptedException
     */
    boolean tryLock(long waitTime, TimeUnit timeUnit) throws InterruptedException;

    /**
     * 释放锁
     */
    void release();

    /**
     * 是否持有锁
     * @return
     */
    boolean hasLock();

}
