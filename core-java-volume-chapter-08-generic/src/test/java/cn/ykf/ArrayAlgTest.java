package cn.ykf;

import cn.ykf.model.Pair;
import cn.ykf.util.ArrayAlg;
import org.junit.Test;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntFunction;

/**
 * @author YuKaiFan<yukf @ pvc123.com>
 * @date 2020-09-21
 */
public class ArrayAlgTest {

    @Test
    public void test() {
        // 调用泛型方法时，类型参数写在方法名前，点号后
        System.out.println(ArrayAlg.<String>getMiddle("1", "2,", "3"));
        // 可省略
        System.out.println(ArrayAlg.getMiddle("1", "2,", "3"));
        // 多个参数类型不一致，向上找父类或父接口，Object，Serializable、Comparable
        Serializable result = ArrayAlg.getMiddle("1", "2,", 2.0);
        System.out.println(result);
        Serializable result1 = ArrayAlg.getMiddle("Hello", 0, null);
        System.out.println(result1);
    }

    @Test
    public void testCast() {
        Pair<String> pair = new Pair<>("a", "b");
        // 泛型域，类型也被擦除，编译强转String
        // String first = pair.first;
        // 泛型方法返回类型擦除，返回值为Object，然后强转String
        String middle = ArrayAlg.getMiddle("A", "HB");
    }

    @Test
    public void testGenericArray() {
        // 所以泛型数组可以声明，但是只能通过通配类型来进行强转
        Pair<String>[] genericArray = (Pair<String>[]) new Pair<?>[5];
        // 编译不通过 Generic array creation
        // Pair<String>[] genericArray = new Pair<String>[10];

        // 因为如果可以编译通过的话，那么genericArray可以强转为 Object[]
        // 数组会记住它原本的存储类型是 Pair
        Object[] array = genericArray;
        // 这里编译器会出现警告，说可能出现 ArrayStoreException
        // Storing element of type 'java.lang.Integer' to array of 'cn.ykf.model.Pair' elements may produce 'ArrayStoreException'
        array[1] = 1;
        // 由于类型擦除，这里编译能通过数组存储检查，但是会出现 ClassCastException
        array[1] = new Pair<Integer>(1, 2);
        // java.lang.ClassCastException: java.lang.Integer cannot be cast to java.lang.String
        // 因为原本的泛型是 String，现在放了 Integer
        // 所以不允许创建泛型数组
        System.out.println(genericArray[1].getFirst());
    }

    @Test
    public void testAnotherGenericList() {
        this.anotherGenericList(new ArrayList<>());
    }

    // 实际是不安全的泛型数组操作
    @SafeVarargs
    private final void anotherGenericList(List<String>... lists) {
        Object[] array = lists;
        List<Integer> integers = Arrays.asList(11, 22);
        // 编译不会报错，因为数组存储只会检查擦除后的类型，所以相当于 array[0] = new List();
        array[0] = integers;
        // 运行时 ClassCastException
        System.out.println(lists[0].get(0));
    }

    @Test
    public void testInstance() {
        // 不能直接通过泛型变量 T 来创建对象 T.class不合法的
        Pair<String> p1 = Pair.of(String.class);
        Pair<String> p2 = Pair.of(String::new);
    }

    @Test
    public void initGenericArray() {
        // String[] minmax = this.minmax(new String[]{"1", "2"});
        // 指定泛型数组类型
        String[] minmax = this.minmax(String[]::new, "1", "2");
        for (String s : minmax) {
            System.out.println(s);
        }
    }

    private <T extends Comparable> T[] minmax(T[] a) {
        // 如果允许直接构造泛型数组
        // 那么可以这样 Pair<String>[] array = new Pair<String>[10];
        // Object[] a = array;  父类型数组引用可以接收子类型数组引用
        // a[0] = 1;
        // 这样就会在运行时发生类型转换异常，违背了泛型的目的，把错误拦截在编译前

        // Type parameter 'T' cannot be instantiated directly
        // T[] mm = new T[2];

        // 运行报错  [Ljava.lang.Object; cannot be cast to [Ljava.lang.Comparable;
        Object[] mm = new Object[2];
        return (T[]) mm;
    }

    // 可以做以下更改， IntFunction 可用于创建泛型数组，可以参考 Stream.toArray()
    private <T extends Comparable> T[] minmax(IntFunction<T[]> function, T... a) {
        // 数组构造器
        T[] mm = function.apply(2);
        System.arraycopy(a, 0, mm, 0, a.length);

        // 或者使用反射
        T[] mm1 = (T[]) Array.newInstance(a.getClass().getComponentType(), a.length);

        return mm;
    }
}

