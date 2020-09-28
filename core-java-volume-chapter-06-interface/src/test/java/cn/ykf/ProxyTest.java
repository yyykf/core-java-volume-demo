package cn.ykf;

import cn.ykf.handler.TraceHandler;
import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Random;

/**
 * @author YuKaiFan <1092882580@qq.com>
 * @date 2020-09-28
 */
public class ProxyTest {

    /**
     * <pre>
     *     500.compareTo(750)
     *     750.compareTo(750)
     *     750.toString()
     *     750
     * </pre>
     * 虽然指定只代理Comparable接口的方法，但是默认也会对Object的 toString()、hashCode()以及equals()进行代理
     */
    @Test
    public void testProxy() {
        Object[] integers = new Object[1000];

        for (int i = 0; i < 1000; i++) {
            Integer value = i + 1;
            // 调用处理器
            InvocationHandler handler = new TraceHandler(value);
            // 只对Comparable接口进行代理，生成代理对象
            Object proxy = Proxy.newProxyInstance(value.getClass().getClassLoader(), new Class[]{Comparable.class}, handler);
            // 放入数组
            integers[i] = proxy;
        }

        // 随机生成key
        int key = new Random().nextInt(integers.length) + 1;

        // 排序，会调用到代理对象的 compareTp() 方法
        int result = Arrays.binarySearch(integers, key);

        if (result >= 0) {
            System.out.println(integers[result]);
        }
    }

    /**
     * 如果使用同一个类加载器以及相同的一组接口，那么生成的对象都是同一个代理类，只是不同的实列而已
     */
    @Test
    public void testSameClassLoader() {
        ClassLoader classLoader = this.getClass().getClassLoader();
        Class[] interfaces = {Comparable.class};

        Object o1 = Proxy.newProxyInstance(classLoader, interfaces, new TraceHandler(1));
        Object o2 = Proxy.newProxyInstance(classLoader, interfaces, new TraceHandler(2));

        // 同一个代理类
        System.out.println(o1.getClass() == o2.getClass());
        System.out.println(Proxy.getProxyClass(classLoader, Comparable.class));
    }
}
