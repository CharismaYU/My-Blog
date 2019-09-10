package com.site.blog.my.core.listen;

import com.site.blog.my.core.event.MyApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @Auther: yxn
 * @Date: 2019/9/10 21:28
 * @Description:
 */
//@Component
public class MyApplicationListener implements ApplicationListener<MyApplicationEvent> {

    @Override
    public void onApplicationEvent(MyApplicationEvent event) {
        System.out.println("=========接收到事件===========" + event.getClass());
    }
}
