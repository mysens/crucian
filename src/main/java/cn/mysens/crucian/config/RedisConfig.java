package cn.mysens.crucian.config;

/**
 * <br>
 *
 * @author 张成<br>
 * @date 2019年01月21日<br>
 */
public class RedisConfig extends LockConfig{

    /**
     * 密码
     */
    private String password;

    /**
     * 默认超时时间
     */
    private long defaultTimeout;

    private ClusterModeConfig clusterModeConfig;

    private ReplicatedModeConfig replicatedModeConfig;

    private MasterSlaveModeConfig masterSlaveModeConfig;

    private SingleInstanceModeConfig singleInstanceModeConfig;

    private SentinelModeConfig sentinelModeConfig;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getDefaultTimeout() {
        return defaultTimeout;
    }

    public void setDefaultTimeout(long defaultTimeout) {
        this.defaultTimeout = defaultTimeout;
    }

    public ClusterModeConfig getClusterModeConfig() {
        return clusterModeConfig;
    }

    public void setClusterModeConfig(ClusterModeConfig clusterModeConfig) {
        this.clusterModeConfig = clusterModeConfig;
    }

    public ReplicatedModeConfig getReplicatedModeConfig() {
        return replicatedModeConfig;
    }

    public void setReplicatedModeConfig(ReplicatedModeConfig replicatedModeConfig) {
        this.replicatedModeConfig = replicatedModeConfig;
    }

    public MasterSlaveModeConfig getMasterSlaveModeConfig() {
        return masterSlaveModeConfig;
    }

    public void setMasterSlaveModeConfig(MasterSlaveModeConfig masterSlaveModeConfig) {
        this.masterSlaveModeConfig = masterSlaveModeConfig;
    }

    public SingleInstanceModeConfig getSingleInstanceModeConfig() {
        return singleInstanceModeConfig;
    }

    public void setSingleInstanceModeConfig(SingleInstanceModeConfig singleInstanceModeConfig) {
        this.singleInstanceModeConfig = singleInstanceModeConfig;
    }

    public SentinelModeConfig getSentinelModeConfig() {
        return sentinelModeConfig;
    }

    public void setSentinelModeConfig(SentinelModeConfig sentinelModeConfig) {
        this.sentinelModeConfig = sentinelModeConfig;
    }
}
