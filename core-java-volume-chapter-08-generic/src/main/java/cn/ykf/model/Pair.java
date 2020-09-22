package cn.ykf.model;

import java.util.function.Supplier;

/**
 * @author YuKaiFan<yukf @ pvc123.com>
 * @date 2020-09-21
 */
public class Pair<T> {

    // 原始类型(raw type)为Object，因为无限定符
    private T first;
    private T second;

    public Pair() {
    }

    public Pair(T first, T second) {
        this.first = first;
        this.second = second;
    }

    public static <T> Pair<T> of(Supplier<T> supplier) {
        return new Pair<>(supplier.get(), supplier.get());
    }

    public static <T> Pair<T> of(Class<T> clazz) {
        try {
            return new Pair<>(clazz.newInstance(), clazz.newInstance());
        } catch (InstantiationException | IllegalAccessException ignore) {
            return null;
        }
    }

    public T getFirst() {
        return first;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public T getSecond() {
        return second;
    }

    public void setSecond(T second) {
        this.second = second;
    }


    /*
    https://blog.csdn.net/hao_yan_bing/article/details/89447792
     这样是编译不通过的，因为编译前，说是重载吧，一个参数T，一个参数Object，编译后类型擦除后方法签名完全一样，构不成重载
     说是重写吧，编译前方法签名又不一样，构不成重写
     所以参数类型不能为泛型类型参数，只能为Object
    public boolean equals(T o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pair<?> pair = (Pair<?>) o;

        if (first != null ? !first.equals(pair.first) : pair.first != null) return false;
        return second != null ? second.equals(pair.second) : pair.second == null;
    }*/
}
