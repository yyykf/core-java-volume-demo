package cn.ykf;

import cn.ykf.model.*;
import org.junit.Test;

import java.io.Serializable;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author YuKaiFan <1092882580@qq.com>
 * @date 2020-09-22
 */
public class ClassTest {

    @Test
    public void testCast() {
        Dog dog = new Dog();
        // 向上转型
        Animal cast = Animal.class.cast(dog);
        // class cn.ykf.model.Dog
        System.out.println(cast.getClass());
    }

    @Test
    public void testNewInstance() {
        Pair<Dog> instance = instance(Dog.class);
        Pair<Animal> instance1 = instance(Animal.class);
    }

    private <T> Pair<T> instance(Class<T> clazz) {
        try {
            return new Pair<>(clazz.newInstance(), clazz.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Test
    public void testReflect() {
        printClass(Pair.class);
    }

    private void printClass(Class<?> clazz) {
        System.out.println(clazz);
        // printTypes()
        for (TypeVariable<? extends Class<?>> typeParameter : clazz.getTypeParameters()) {
            System.out.println(typeParameter);
        }
    }

    @Test
    public void testClassApi() {
        // []，获取这个类的类型变量数组
        System.out.println(Arrays.toString(SubArrayList.class.getTypeParameters()));
        // cn.ykf.model.ArrayList<java.lang.String>，获取父类的泛型类型，如果Object或不是类则返回null
        System.out.println(SubArrayList.class.getGenericSuperclass());
        // [java.util.List<java.lang.String>, java.lang.Comparable<java.lang.String>]，获取父接口的泛型类型数组，按声明顺序
        System.out.println(Arrays.toString(NewList.class.getGenericInterfaces()));
    }

    @Test
    public void testMethodApi() throws NoSuchMethodException {
        Method show = ClassTest.class.getDeclaredMethod("show", List.class, Class.class, int.class, Serializable[].class);
        // 返回值可以为 TypeVariable 类型变量，比如T
        // 也可以为 ParameterizedType 参数化类型，比如 List<T>、List<String>
        // 还可以为GenericArrayType 泛型数组，如 T[]
        // 还可以为普通类型 Class
        // 就是不可能为 WildcardType 通配符

        // 如果是泛型方法，则为类型变量数组
        TypeVariable<Method>[] typeParameters = show.getTypeParameters();
        for (TypeVariable<Method> typeParameter : typeParameters) {
            // TypeVariable.getBounds() 获取extends限定数组
            System.out.println("generic type: " + typeParameter + ", bounds: " + Arrays.toString(typeParameter.getBounds()));

            // java.lang.Comparable<? super T>
            Type[] actualTypeArguments = ((ParameterizedType) typeParameter.getBounds()[1]).getActualTypeArguments();
            // ? super T，通配符表达式
            System.out.println(actualTypeArguments[0]);
            // true
            System.out.println(actualTypeArguments[0] instanceof WildcardType);
            // Object，因为泛型擦除
            System.out.println(((WildcardType) actualTypeArguments[0]).getUpperBounds()[0]);
        }

        // 泛型返回类型
        System.out.println(show.getGenericReturnType());

        // 泛型参数
        Type[] genericParameterTypes = show.getGenericParameterTypes();
        System.out.println(Arrays.toString(genericParameterTypes));
        // T[]，泛型数组，组件类型T
        System.out.println(((GenericArrayType) genericParameterTypes[genericParameterTypes.length - 1]).getGenericComponentType());
    }

    private <T extends Serializable & Comparable<? super T>> T[] show(List<String> list, Class<T> tClass, int i, T[] t) {
        return null;
    }

    @Test
    public void testParameterizedType() throws NoSuchMethodException {
        Method apply = getClass().getDeclaredMethod("apply", Map.Entry.class);
        Type genericParameterType = apply.getGenericParameterTypes()[0];
        // Map
        System.out.println(((ParameterizedType) genericParameterType).getOwnerType());
    }

    private <K, V> void apply(Map.Entry<K, V> entry) {
    }
}
