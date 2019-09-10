package com.site.blog.my.core.event;


import org.springframework.context.ApplicationEvent;

/**
 * @Auther: yxn
 * @Date: 2019/9/10 21:23
 * @Description: 定义事件
 */
public class MyApplicationEvent extends ApplicationEvent {

    private static final long serialVersionUID = 1L;

    public MyApplicationEvent(Object source) {
        super(source);
    }
}
