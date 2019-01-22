package cn.mysens.crucian.config;

/**
 * <br>
 *
 * @author 张成<br>
 * @date 2019年01月22日<br>
 */
public class ReplicatedModeConfig {

    private int scanInterval = 2000;

    private String[] nodeAddress;

    private int slaveConnectionMinimumIdleSize = 32;

    private int slaveConnectionPoolSize = 64;

    private int masterConnectionMinimumIdleSize = 32;

    private int masterConnectionPoolSize = 64;

    private int idleConnectionTimeout = 10000;

    public int getScanInterval() {
        return scanInterval;
    }

    public void setScanInterval(int scanInterval) {
        this.scanInterval = scanInterval;
    }

    public String[] getNodeAddress() {
        return nodeAddress;
    }

    public void setNodeAddress(String[] nodeAddress) {
        this.nodeAddress = nodeAddress;
    }

    public int getSlaveConnectionMinimumIdleSize() {
        return slaveConnectionMinimumIdleSize;
    }

    public void setSlaveConnectionMinimumIdleSize(int slaveConnectionMinimumIdleSize) {
        this.slaveConnectionMinimumIdleSize = slaveConnectionMinimumIdleSize;
    }

    public int getSlaveConnectionPoolSize() {
        return slaveConnectionPoolSize;
    }

    public void setSlaveConnectionPoolSize(int slaveConnectionPoolSize) {
        this.slaveConnectionPoolSize = slaveConnectionPoolSize;
    }

    public int getMasterConnectionMinimumIdleSize() {
        return masterConnectionMinimumIdleSize;
    }

    public void setMasterConnectionMinimumIdleSize(int masterConnectionMinimumIdleSize) {
        this.masterConnectionMinimumIdleSize = masterConnectionMinimumIdleSize;
    }

    public int getMasterConnectionPoolSize() {
        return masterConnectionPoolSize;
    }

    public void setMasterConnectionPoolSize(int masterConnectionPoolSize) {
        this.masterConnectionPoolSize = masterConnectionPoolSize;
    }

    public int getIdleConnectionTimeout() {
        return idleConnectionTimeout;
    }

    public void setIdleConnectionTimeout(int idleConnectionTimeout) {
        this.idleConnectionTimeout = idleConnectionTimeout;
    }

}
