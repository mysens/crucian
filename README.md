# crucian
分布式锁管理工具
分布式部署环境下，通过锁机制来让多客户端互斥的对共享资源进行访问。
本工具依赖zk或redis环境

##说明
1. 基于curator-recipes完成zookeeper分布式锁
1. 基于redisson完成redis分布式锁
1. 使用方法请参考单元测试ReentrantLockTest