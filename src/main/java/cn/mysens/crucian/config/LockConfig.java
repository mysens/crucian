package cn.mysens.crucian.config;

/**
 * 锁配置<br>
 *
 * @author 张成<br>
 * @date 2019年01月21日<br>
 */
public class LockConfig {

    /**
     * 命名空间
     */
    private String nameSpace;

    /**
     * 默认超时时间
     */
    private long defaultTimeout;

    public String getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    public long getDefaultTimeout() {
        return defaultTimeout;
    }

    public void setDefaultTimeout(long defaultTimeout) {
        this.defaultTimeout = defaultTimeout;
    }
}