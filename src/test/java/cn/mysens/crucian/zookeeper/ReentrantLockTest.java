package cn.mysens.crucian.zookeeper;

import cn.mysens.crucian.config.RedisConfig;
import cn.mysens.crucian.config.SingleInstanceModeConfig;
import cn.mysens.crucian.config.ZkConfig;
import cn.mysens.crucian.lock.LockTemplate;
import cn.mysens.crucian.lock.Locker;
import cn.mysens.crucian.redis.RedisClient;
import cn.mysens.crucian.redis.RedisReentrantLock;
import net.bytebuddy.implementation.bytecode.Throw;
import org.junit.Assert;
import org.junit.Test;
import org.redisson.config.SingleServerConfig;

import static org.junit.Assert.*;

/**
 * <br>
 *
 * @author 张成<br>
 * @date 2019年01月22日<br>
 */
public class ReentrantLockTest {

    @Test
    public void zkLock() throws InterruptedException {
        //zk配置
        ZkConfig zkConfig = new ZkConfig();
        zkConfig.setNameSpace("zk_test");
        zkConfig.setDefaultTimeout(30);
        zkConfig.setZkHosts("127.0.0.1:2181");
        //zk客户端
        ZkClient zkClient = new ZkClient(zkConfig);
        //锁模板
        LockTemplate lockTemplate = new ZkReentrantLock(zkClient);
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
                locker.release(key);
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
        //redis配置
        RedisConfig redisConfig = new RedisConfig();
        SingleInstanceModeConfig singleInstanceModeConfig = new SingleInstanceModeConfig();
        singleInstanceModeConfig.setAddress("redis://127.0.0.1:6379");
        redisConfig.setSingleInstanceModeConfig(singleInstanceModeConfig);
        redisConfig.setNameSpace("redis_test");
        redisConfig.setDefaultTimeout(30);
        //zk客户端
        RedisClient redisClient = new RedisClient(redisConfig);
        //锁模板
        LockTemplate lockTemplate = new RedisReentrantLock(redisClient);
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
                locker.release(key);
                System.out.println("success to release lock");
            }else{
                System.out.println("sorry, not possessing lock");
            }
        }else{
            System.out.println("failed to lock");
        }
    }
}
