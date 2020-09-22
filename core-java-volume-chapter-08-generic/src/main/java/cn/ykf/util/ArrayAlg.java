package cn.ykf.util;

import java.io.Serializable;

/**
 * @author YuKaiFan<yukf @ pvc123.com>
 * @date 2020-09-21
 */
public class ArrayAlg {

    public static <T> T getMiddle(T... args) {
        return args[args.length / 2];
    }

    /**
     * 多重限定，最多只能有一个类限定，且需要在第一位
     *
     * @param a
     * @param <T>
     * @return
     */
    public static <T extends Number & Comparable & Serializable> T min(T[] a) {
        if (a == null || a.length == 0) {
            return null;
        }

        T smallest = a[0];
        // 类型擦除（erased）：原始类型默认是用第一个限定类型变量来替换，如果没有就是Object
        // 这里有，为Number，所以会用Number替换，但是调用了compareTo，所以编译器会强制转换，可以看class文件
        // if (((Comparable)smallest).compareTo(a[i]) > 0) {
        // 为了提高效率，标记（tagging）接口应该放在限定类型变量的最后面
        // 不要 <T extends Serializable & Comparable>
        for (int i = 1; i < a.length; i++) {
            if (smallest.compareTo(a[i]) > 0) {
                smallest = a[i];
            }
        }

        return smallest;
    }
}
