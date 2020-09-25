package cn.ykf.service;

/**
 * @author YuKaiFan <1092882580@qq.com>
 * @date 2020-09-25
 */
public interface Person {

    default void getName() {
        System.out.println("Person接口的getName()方法...");
    }
}
