package cn.ykf.reflect;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * @author YuKaiFan <1092882580@qq.com>
 * @date 2020-09-23
 */
public class CopyOfDemo {

    public static void main(String[] args) {
        int[] a = {1, 2, 3};
        a = (int[]) goodCopyOf(a, 10);
        System.out.println(Arrays.toString(a));

        String[] b = {"1", "2", "3"};
        b = (String[]) goodCopyOf(b, 10);
        System.out.println(b);

        // 转换异常  [Ljava.lang.Object; cannot be cast to [Ljava.lang.String;  Object[] 无法转为 String[]
        b = (String[]) badCopyOf(b, 10);
    }

    private static Object[] badCopyOf(Object[] a, int newLength) {
        // 无效，因为new的时候数组已经记住了元素类型为Object，到时候不能转为某个子类
        Object[] objects = new Object[newLength];
        System.arraycopy(a, 0, objects, 0, Math.min(a.length, newLength));
        return objects;
    }

    // 必须声明为Object，而不是Object[] , int[] 可以转为 Object，但是不能转为 Object[]
    private static Object goodCopyOf(Object a, int newLength) {
        Class<?> clazz = a.getClass();
        if (!clazz.isArray()) {
            return null;
        }

        int length = Array.getLength(a);
        Class<?> componentType = clazz.getComponentType();
        Object newArray = Array.newInstance(componentType, newLength);

        System.arraycopy(a, 0, newArray, 0, Math.min(length, newLength));

        return newArray;
    }
}
