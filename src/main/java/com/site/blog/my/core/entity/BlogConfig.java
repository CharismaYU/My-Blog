package com.site.blog.my.core.entity;

public class BlogConfig extends AbstractAuditable {
    /**
     * 配置项的名称
     */
    private String configName;
    /**
     * 配置项的值
     */
    private String configValue;

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName == null ? null : configName.trim();
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue == null ? null : configValue.trim();
    }
}