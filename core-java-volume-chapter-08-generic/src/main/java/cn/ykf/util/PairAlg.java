package cn.ykf.util;

import cn.ykf.model.Dog;
import cn.ykf.model.Pair;

/**
 * @author YuKaiFan <1092882580@qq.com>
 * @date 2020-09-22
 */
public class PairAlg {

    public static boolean hasNulls(Pair<?> pair) {
        return pair.getFirst() == null || pair.getSecond() == null;
    }

    public static void swap(Pair<?> pair) {
        // 没办法直接在这里交换属性
        // Object first = pair.getFirst();
        // setFirst (capture<?>) in Pair cannot be applied to (capture<?>)
        // pair.setFirst(pair.getSecond());

        // 所以需要借助泛型来进行捕获 ?
        swapHelper(pair);
    }

    public static void swap(Dog[] dogs, Pair<? super Dog> pair) {
        // 某些业务操作

        // 同理，无法直接交换
        swapHelper(pair);
    }

    public static <T> void swapHelper(Pair<T> pair) {
        T first = pair.getFirst();
        pair.setFirst(pair.getSecond());
        pair.setSecond(first);
    }

}
