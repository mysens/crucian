package cn.mysens.crucian.redis;

import cn.mysens.crucian.config.RedisConfig;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.*;


/**
 * <br>
 *
 * @author 张成<br>
 * @date 2019年01月21日<br>
 */
public class RedisClient {

    private String nameSpace;

    private long timeout;

    private RedissonClient client;

    public RedisClient(RedisConfig config) {
        Config rsConfig = buildRsConfig(config);
        this.client = Redisson.create(rsConfig);
        this.nameSpace = config.getNameSpace();
        this.timeout = config.getDefaultTimeout();
    }

    /**
     * redisson config
     * @param config
     * @return
     */
    private static Config buildRsConfig(RedisConfig config){
        Config rsConfig = new Config();
        if(config.getClusterModeConfig()!=null) {
            //集群模式
            ClusterServersConfig serversConfig = rsConfig.useClusterServers()
                    .setScanInterval(config.getClusterModeConfig().getScanInterval())
                    .addNodeAddress(config.getClusterModeConfig().getNodeAddress())
                    .setMasterConnectionMinimumIdleSize(config.getClusterModeConfig().getMasterConnectionMinimumIdleSize())
                    .setMasterConnectionPoolSize(config.getClusterModeConfig().getMasterConnectionPoolSize())
                    .setSlaveConnectionMinimumIdleSize(config.getClusterModeConfig().getSlaveConnectionMinimumIdleSize())
                    .setSlaveConnectionPoolSize(config.getClusterModeConfig().getSlaveConnectionPoolSize())
                    .setIdleConnectionTimeout(config.getClusterModeConfig().getIdleConnectionTimeout());


            if(StringUtils.isNotBlank(config.getPassword())) {
                serversConfig.setPassword(config.getPassword());
            }

        }else if(config.getMasterSlaveModeConfig()!=null){
            //主从模式
            MasterSlaveServersConfig serversConfig = rsConfig.useMasterSlaveServers()
                    .setMasterAddress(config.getMasterSlaveModeConfig().getMasterAddress())
                    .addSlaveAddress(config.getMasterSlaveModeConfig().getSlaveAddress())
                    .setMasterConnectionMinimumIdleSize(config.getMasterSlaveModeConfig().getMasterConnectionMinimumIdleSize())
                    .setMasterConnectionPoolSize(config.getMasterSlaveModeConfig().getMasterConnectionPoolSize())
                    .setSlaveConnectionMinimumIdleSize(config.getMasterSlaveModeConfig().getSlaveConnectionMinimumIdleSize())
                    .setSlaveConnectionPoolSize(config.getMasterSlaveModeConfig().getSlaveConnectionPoolSize())
                    .setIdleConnectionTimeout(config.getMasterSlaveModeConfig().getIdleConnectionTimeout());

            if(StringUtils.isNotBlank(config.getPassword())) {
                serversConfig.setPassword(config.getPassword());
            }
        }else if(config.getReplicatedModeConfig()!=null){
            //复制模式
            ReplicatedServersConfig serversConfig = rsConfig.useReplicatedServers()
                    .setScanInterval(config.getReplicatedModeConfig().getScanInterval())
                    .addNodeAddress(config.getReplicatedModeConfig().getNodeAddress())
                    .setMasterConnectionMinimumIdleSize(config.getReplicatedModeConfig().getMasterConnectionMinimumIdleSize())
                    .setMasterConnectionPoolSize(config.getReplicatedModeConfig().getMasterConnectionPoolSize())
                    .setSlaveConnectionMinimumIdleSize(config.getReplicatedModeConfig().getSlaveConnectionMinimumIdleSize())
                    .setSlaveConnectionPoolSize(config.getReplicatedModeConfig().getSlaveConnectionPoolSize())
                    .setIdleConnectionTimeout(config.getReplicatedModeConfig().getIdleConnectionTimeout());
            if(StringUtils.isNotBlank(config.getPassword())) {
                serversConfig.setPassword(config.getPassword());
            }
        }else if(config.getSentinelModeConfig()!=null){
            //哨兵模式
            SentinelServersConfig serversConfig = rsConfig.useSentinelServers()
                    .setMasterName(config.getSentinelModeConfig().getMasterName())
                    .setScanInterval(config.getSentinelModeConfig().getScanInterval())
                    .addSentinelAddress(config.getSentinelModeConfig().getSentinelAddress())
                    .setMasterConnectionMinimumIdleSize(config.getSentinelModeConfig().getMasterConnectionMinimumIdleSize())
                    .setMasterConnectionPoolSize(config.getSentinelModeConfig().getMasterConnectionPoolSize())
                    .setSlaveConnectionMinimumIdleSize(config.getSentinelModeConfig().getSlaveConnectionMinimumIdleSize())
                    .setSlaveConnectionPoolSize(config.getSentinelModeConfig().getSlaveConnectionPoolSize())
                    .setIdleConnectionTimeout(config.getSentinelModeConfig().getIdleConnectionTimeout());

            if(StringUtils.isNotBlank(config.getPassword())) {
                serversConfig.setPassword(config.getPassword());
            }
        }else if(config.getSingleInstanceModeConfig()!=null){
            //单机模式
            SingleServerConfig serverConfig = rsConfig.useSingleServer()
                    .setAddress(config.getSingleInstanceModeConfig().getAddress())
                    .setConnectionMinimumIdleSize(config.getSingleInstanceModeConfig().getConnectionMinimumIdleSize())
                    .setConnectionPoolSize(config.getSingleInstanceModeConfig().getConnectionPoolSize())
                    .setIdleConnectionTimeout(config.getSingleInstanceModeConfig().getIdleConnectionTimeout());

            if(StringUtils.isNotBlank(config.getPassword())) {
                serverConfig.setPassword(config.getPassword());
            }
        }else{
            throw new RuntimeException("redis config not found!");
        }
        return rsConfig;
    }

    public String getNameSpace() {
        return nameSpace;
    }

    public long getTimeout() {
        return timeout;
    }

    public RedissonClient getClient() {
        return client;
    }

}
