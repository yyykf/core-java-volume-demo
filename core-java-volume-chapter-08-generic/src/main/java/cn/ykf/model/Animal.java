package cn.ykf.model;

/**
 * @author YuKaiFan <1092882580@qq.com>
 * @date 2020-09-22
 */
public class Animal implements Comparable {

    // 父类和子类实现接口时不指定泛型，合法
    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
