package cn.ykf.service;

/**
 * @author YuKaiFan <1092882580@qq.com>
 * @date 2020-09-25
 */
public interface MyInterface {

    static void staticMethod() {
        System.out.println("jdk8 支持接口中定义静态方法");
    }

    default void defaultMethod() {
        System.out.println("jdk8 支持接口中定义默认方法");
    }
}
