package cn.ykf.model;

import java.io.Serializable;

/**
 * @author YuKaiFan <1092882580@qq.com>
 * @date 2020-10-10
 */
public class Singleton implements Serializable {

    private static final long serialVersionUID = -3947415207985102226L;
    private static final Singleton SINGLETON = new Singleton();

    private Singleton() {
    }

    public static Singleton newInstance() {
        return SINGLETON;
    }

    /**
     * 在对象序列化后被调用，会成为 readObject 的返回值
     * 防止单例被破坏
     *
     * @return
     */
    protected Object readResolve() {
        return SINGLETON;
    }
}
