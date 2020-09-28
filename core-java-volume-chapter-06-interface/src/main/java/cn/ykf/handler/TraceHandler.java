package cn.ykf.handler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author YuKaiFan <1092882580@qq.com>
 * @date 2020-09-28
 */
public class TraceHandler implements InvocationHandler {

    /**
     * 被代理的实际对象
     */
    private Object target;

    public TraceHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 打印被代理对象以及调用的方法名
        System.out.print(target + "." + method.getName() + "(");
        // 方法参数
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                if (i >= 1) {
                    System.out.print(", ");
                }
                System.out.print(args[i]);
            }
        }
        System.out.println(")");

        // 调用方法
        return method.invoke(target, args);
    }
}
