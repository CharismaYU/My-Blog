package com.site.blog.my.core.handle;

import com.site.blog.my.core.event.MyApplicationEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @Auther: yxn
 * @Date: 2019/9/10 22:02
 * @Description:
 */
@Component
public class MyEventHandle {

    /**
     * 参数一定要是ApplicationEvent，或者其子类
     *
     * 如果参数任意（比如Object）则所有该参数事件，或者其子事件都可以接收到
     * @param event
     */
    //@EventListener
    public void event(MyApplicationEvent event) {
        System.out.println("==接受到事件==" + event.getClass());
    }

    @EventListener
    public void event2(ContextStoppedEvent event) {
        System.out.println("应用停止事件");
    }
}
