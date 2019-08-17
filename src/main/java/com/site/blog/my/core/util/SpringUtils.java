package com.site.blog.my.core.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Utils - Spring
 */
@Component("springUtils")
@Lazy(false)
public final class SpringUtils implements ApplicationContextAware, DisposableBean {

	/** applicationContext */
	private static ApplicationContext applicationContext;

	private static AtomicBoolean started = new AtomicBoolean(true);

	/**
	 * 不可实例化
	 */
	private SpringUtils() {
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		SpringUtils.applicationContext = applicationContext;
	}

	public boolean isStarted() {
		return started.get();
	}

	@Override
	public void destroy() throws Exception {
		applicationContext = null;
		started.set(false);
	}

	/**
	 * 获取applicationContext
	 *
	 * @return applicationContext
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * 获取配置属性
	 *
	 * @param key
	 * @return Object 一个以所给名字注册的bean的实例
	 * @throws org.springframework.beans.BeansException
	 */
	public static String getConfig(String key) throws BeansException {
		Assert.hasText(key, "Argument:[key] must not be null or empty.");
		if (applicationContext == null) {
			return null;
		}

		ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) applicationContext;

		ConfigurableEnvironment configurableEnvironment = configurableApplicationContext.getEnvironment();
		return configurableEnvironment.getProperty(key);
	}

	/**
	 * 获取配置属性
	 *
	 * @param name
	 * @return Object 一个以所给名字注册的bean的实例
	 * @throws org.springframework.beans.BeansException
	 */
	public static String getProperty(String key, String defaultValue) throws BeansException {
		Assert.hasText(key, "Argument:[key] must not be null or empty.");
		if (applicationContext == null) {
			return null;
		}

		ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) applicationContext;

		ConfigurableEnvironment configurableEnvironment = configurableApplicationContext.getEnvironment();
		return configurableEnvironment.getProperty(key, defaultValue);
	}

	/**
	 * 获取配置属性
	 *
	 * @param key
	 * @param targetType
	 * @return Object 一个以所给名字注册的bean的实例
	 * @throws org.springframework.beans.BeansException
	 */
	public static <T> T getProperty(String key, Class<T> targetType) throws BeansException {
		Assert.hasText(key, "Argument:[name] must not be null or empty.");
		if (applicationContext == null) {
			return null;
		}

		ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) applicationContext;

		ConfigurableEnvironment configurableEnvironment = configurableApplicationContext.getEnvironment();
		return configurableEnvironment.getProperty(key, targetType);
	}

	/**
	 * 获取配置属性
	 *
	 * @param key
	 * @param targetType
	 * @param defaultValue
	 * @return Object 一个以所给名字注册的bean的实例
	 * @throws org.springframework.beans.BeansException
	 */
	public static <T> T getProperty(String key, Class<T> targetType, T defaultValue) throws BeansException {
		Assert.hasText(key, "Argument:[name] must not be null or empty.");
		if (applicationContext == null) {
			return null;
		}

		ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) applicationContext;

		ConfigurableEnvironment configurableEnvironment = configurableApplicationContext.getEnvironment();
		return configurableEnvironment.getProperty(key, targetType, defaultValue);
	}

	/**
	 * 获取对象
	 *
	 * @param name
	 * @return Object 一个以所给名字注册的bean的实例
	 * @throws org.springframework.beans.BeansException
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) throws BeansException {
		Assert.hasText(name, "Argument:[name] must not be null or empty.");
		if (applicationContext == null) {
			return null;
		}

		return (T) applicationContext.getBean(name);
	}

	/**
	 * 获取对象
	 *
	 * @param type
	 * @return Object 一个以所给名字注册的bean的实例
	 * @throws org.springframework.beans.BeansException
	 */
	public static <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
		Assert.notNull(type, "Argument:[type] must not be null or empty.");
		if (applicationContext == null) {
			return null;
		}

		return applicationContext.getBeansOfType(type);
	}

	/**
	 * 获取类型为requiredType的对象
	 *
	 * @param clz
	 * @return
	 * @throws org.springframework.beans.BeansException
	 */
	public static <T> T getBean(String name, Class<T> clz) throws BeansException {
		Assert.hasText(name, "Argument:[name] must not be null or empty.");
		Assert.notNull(clz, "Argument:[clz] must not be null.");
		if (applicationContext == null) {
			return null;
		}

		T result = applicationContext.getBean(name, clz);
		return result;
	}

	/**
	 * 获取类型为requiredType的对象
	 *
	 * @param clz
	 * @return
	 * @throws org.springframework.beans.BeansException
	 */
	public static <T> T getBean(Class<T> clz) throws BeansException {
		Assert.notNull(clz, "Argument:[clz] must not be null.");
		if (applicationContext == null) {
			return null;
		}
		T result = applicationContext.getBean(clz);
		return result;
	}

	/**
	 * 如果BeanFactory包含一个与所给名称匹配的bean定义，则返回true
	 *
	 * @param name
	 * @return boolean
	 */
	public static boolean containsBean(String name) {
		Assert.hasText(name, "Argument:[name] must not be null or empty.");
		if (applicationContext == null) {
			return false;
		}

		return applicationContext.containsBean(name);
	}

	/**
	 * 判断以给定名字注册的bean定义是一个singleton还是一个prototype。 如果与给定名字相应的bean定义没有被找到，将会抛出一个异常（NoSuchBeanDefinitionException）
	 *
	 * @param name
	 * @return boolean
	 * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException
	 */
	public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
		Assert.hasText(name, "Argument:[name] must not be null or empty.");
		if (applicationContext == null) {
			return false;
		}

		return applicationContext.isSingleton(name);
	}

	/**
	 * @param name
	 * @return Class 注册对象的类型
	 * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException
	 */
	public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
		Assert.hasText(name, "Argument:[name] must not be null or empty.");
		if (applicationContext == null) {
			return null;
		}

		return applicationContext.getType(name);
	}

	/**
	 * 如果给定的bean名字在bean定义中有别名，则返回这些别名
	 *
	 * @param name
	 * @return
	 * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException
	 */
	public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
		Assert.hasText(name, "Argument:[name] must not be null or empty.");
		if (applicationContext == null) {
			return null;
		}

		return applicationContext.getAliases(name);
	}

}
