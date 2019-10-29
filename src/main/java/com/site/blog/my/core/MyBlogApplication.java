package com.site.blog.my.core;

import com.site.blog.my.core.event.MyApplicationEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author yxn* @qq交流群 796794009
 * @email 2449207463@qq.comprofile
 * @link http://13blog.site
 * <p>
 * 1、自定义事件，一般是继承ApplicationEvent抽象类
 * 2、定义事件监听器，一般实现ApplicationListener接口
 * 3、启动的时候，需要把监听器加入到Spring容器中，即可以使用注解@Component
 * 4、发布事件，使用ApplicationContext.publishEvent发布事件
 * <p>
 * 配置监听器
 * 1、SpringApplication.addListeners 添加监听器
 * 2、把监听器纳入到spring容器中管理，即可以使用注解@Component
 * 3、使用context.listener.classes配置项配置 （application.properties)
 * 4、使用@EventListener注解，在方法上面加入@EventListener注解，且该类需要纳入到spring容器中管理
 */
//@MapperScan("com.site.blog.my.core.dao")
@SpringBootApplication
public class MyBlogApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        //SpringApplication.run(MyBlogApplication.class, args);
        SpringApplication app = new SpringApplication(MyBlogApplication.class);
        //app.addListeners(new MyApplicationListener()); // 可以通过注解方式@Component
        //DelegatingApplicationListener
        ConfigurableApplicationContext context = app.run(args);
        context.publishEvent(new MyApplicationEvent(new Object()));
        //context.stop();
        //context.close();
    }

    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(MyBlogApplication.class);
    }
}
