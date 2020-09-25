package cn.ykf.util;

import javafx.util.Pair;

/**
 * @author YuKaiFan <1092882580@qq.com>
 * @date 2020-09-25
 */
public class Arrays {

    public static void main(String[] args) {
        Pair[] pairs = {new Pair("", ""), new Pair("", "")};
        Object[] objects = pairs;
        java.util.Arrays.sort(objects);
        // compile error, except cast objects to Comparable[]
        compare((Comparable[]) objects);
    }

    public static void compare(Comparable[] objs) {
    }
}
