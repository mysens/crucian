package cn.mysens.crucian.config;


/**
 * <br>
 *
 * @author 张成<br>
 * @date 2019年01月21日<br>
 */
public class ZkConfig extends LockConfig {

    /**
     * zookeeper配置地址
     */
    private String zkHosts;

    public String getZkHosts() {
        return zkHosts;
    }

    public void setZkHosts(String zkHosts) {
        this.zkHosts = zkHosts;
    }
}
