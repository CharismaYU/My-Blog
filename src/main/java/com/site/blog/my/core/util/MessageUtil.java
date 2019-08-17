package com.site.blog.my.core.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Locale;

/**
 * @Auther: yxn
 * @Date: 2019/8/17 11:07
 * @Description:
 */
@Component("MessageUtil")
@Lazy(false)
public class MessageUtil implements ApplicationContextAware {

    /**
     * applicationContext
     */
    private static ApplicationContext applicationContext;

    /**
     * 不可实例化
     */
    private MessageUtil() {
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        MessageUtil.applicationContext = applicationContext;
    }

    public static Locale getLocale(String lang) {
        Locale locale = Locale.CHINA;
        switch (lang) {
            case "en_US":
                locale = new Locale("en", "US");
                break;
            case "zh_CN":
                locale = Locale.CHINA;
                break;
            default:
                locale = Locale.CHINA;
                break;
        }
        return locale;
    }

    public static String getMessage(String lang, String key) {
        Locale locale = getLocale(lang);
        return getMessage(locale, key);
    }

    /**
     * 获取配置属性(指定语言环境)
     *
     * @param key
     * @param args
     * @param defaultValue
     * @param locale
     * @return
     * @throws BeansException
     */
    public static String getMessage(Locale locale, String key, Object[] args, String defaultValue) throws BeansException {
        Assert.hasText(key, "Argument:[key] must not be null or empty.");
        Assert.notNull(locale, "locale must not be null or empty.");
        if (applicationContext == null) {
            return null;
        }
        return applicationContext.getMessage(key, args, defaultValue, locale);
    }

    /**
     * 获取配置属性(指定语言环境)
     *
     * @param locale
     * @param key
     * @return
     */
    public static String getMessage(Locale locale, String key) {
        return getMessage(locale, key, null, null);
    }

    /**
     * 获取配置属性(指定语言环境)
     *
     * @param locale
     * @param key
     * @return
     */
    public static String getMessage(Locale locale, String key, String defaultValue) {
        return getMessage(locale, key, null, defaultValue);
    }

    public static String getDefaultMessage(String name) {
        //String newName = StringUtil.addUnderscores(name);
        String newName = name;

        String[] array = newName.split("_");
        StringBuilder buf = new StringBuilder();
        for (String str : array) {
            if (buf.length() > 0) {
                buf.append(" ");
            }
            buf.append(StringUtils.capitalize(str));
        }
        return buf.toString();
    }

}