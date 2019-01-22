package cn.mysens.crucian.lock;

import java.util.concurrent.TimeUnit;

/**
 * 分布式锁模板<br>
 *
 * @author 张成<br>
 * @date 2019年01月21日<br>
 */
public interface LockTemplate {

    /**
     * 创建锁对象
     * @param key
     * @return
     */
    Locker build(String key);

    /**
     * 创建锁对象
     * @param key
     * @param timeout
     * @return
     */
    Locker build(String key, long timeout, TimeUnit timeUnit);



}
