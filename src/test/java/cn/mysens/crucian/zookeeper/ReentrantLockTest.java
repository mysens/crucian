package cn.mysens.crucian.zookeeper;

import cn.mysens.crucian.config.RedisConfig;
import cn.mysens.crucian.config.SingleInstanceModeConfig;
import cn.mysens.crucian.config.ZkConfig;
import cn.mysens.crucian.lock.LockTemplate;
import cn.mysens.crucian.lock.Locker;
import cn.mysens.crucian.redis.RedisClient;
import cn.mysens.crucian.redis.RedisReentrantLock;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

/**
 * <br>
 *
 * @author 张成<br>
 * @date 2019年01月22日<br>
 */
public class ReentrantLockTest {

    private LockTemplate lockTemplate;

    /**
     * zookeeper测试
     */
    @Test
    public void zkTest() {
        initZkLock();
        test();
    }

    /**
     * redis测试
     */
    @Test
    public void redisTest() {
        initRedisLock();
        test();
    }

    private void initZkLock() {
        //zk配置
        ZkConfig zkConfig = new ZkConfig();
        zkConfig.setNamespace("zk_test");
        zkConfig.setZkHosts("127.0.0.1:2181");
        //zk客户端
        ZkClient zkClient = new ZkClient(zkConfig);
        //锁模板
        lockTemplate = new ZkReentrantLock(zkClient);
    }

    private void initRedisLock() {
        //redis配置
        RedisConfig redisConfig = new RedisConfig();
        SingleInstanceModeConfig singleInstanceModeConfig = new SingleInstanceModeConfig();
        singleInstanceModeConfig.setAddress("redis://127.0.0.1:6379");
        redisConfig.setSingleInstanceModeConfig(singleInstanceModeConfig);
        redisConfig.setNamespace("redis_test");
        redisConfig.setDefaultTimeout(60 * 1000);
        //zk客户端
        RedisClient redisClient = new RedisClient(redisConfig);
        //锁模板
        lockTemplate = new RedisReentrantLock(redisClient);
    }

    private void test() {
        String key = "key";
        Locker locker = lockTemplate.build(key);
        int count = 5;
        CountDownLatch countDownLatch = new CountDownLatch(count);
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(count);
        for (int i = 0; i < count; i++) {
            fixedThreadPool.execute(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + " try to lock");
                    if (locker.tryLock(100, TimeUnit.MILLISECONDS)) {
                        System.out.println(Thread.currentThread().getName() + " success to lock");
                        System.out.println(Thread.currentThread().getName() + " 阻塞5000ms");
                        for (int j = 0; j < 5; j++) {
                            System.out.println(Thread.currentThread().getName() + " 倒计时： " + (5 - j));
                            Thread.sleep(1000);
                        }
                        locker.release();
                        System.out.println(Thread.currentThread().getName() + " success to release");
                    } else {
                        System.out.println(Thread.currentThread().getName() + " failed to lock");
                    }
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName() + " error to lock");
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }

            });
        }

        try {
            countDownLatch.await();
            System.out.println("执行成功");
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("抛出异常");
        }
    }

    @Test
    public void zkLock() throws InterruptedException {
        initZkLock();
        //锁操作
        String key = "id001";
        Locker locker = lockTemplate.build(key);
        if(locker.tryLock()){
            System.out.println("success to lock");
            assertTrue(locker.hasLock());
            if(locker.hasLock()){
                System.out.println("confirmed possessing lock");
                System.out.println("try to release lock after 5s");
                for(int i=0;i<5;i++){
                    System.out.println(5-i);
                    Thread.sleep(1000);
                }
                System.out.println("start to release lock");
                locker.release();
                System.out.println("success to release lock");
            }else{
                System.out.println("sorry, not possessing lock");
            }
        }else{
            System.out.println("failed to lock");
        }
    }

    @Test
    public void redisLock() throws InterruptedException {
        initRedisLock();
        //锁操作
        String key = "id001";
        Locker locker = lockTemplate.build(key);
        if(locker.tryLock()){
            System.out.println("success to lock");
            assertTrue(locker.hasLock());
            if(locker.hasLock()){
                System.out.println("confirmed possessing lock");
                System.out.println("try to release lock after 5s");
                for(int i=0;i<5;i++){
                    System.out.println(5-i);
                    Thread.sleep(1000);
                }
                System.out.println("start to release lock");
                locker.release();
                System.out.println("success to release lock");
            }else{
                System.out.println("sorry, not possessing lock");
            }
        }else{
            System.out.println("failed to lock");
        }
    }
}
