package cn.ykf;

import cn.ykf.model.Animal;
import cn.ykf.model.Dog;
import cn.ykf.model.Student;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author YuKaiFan <1092882580@qq.com>
 * @date 2020-09-25
 */
public class DefaultMethodTest {

    @Test
    public void testDefaultMethod() {
        Dog dog = new Dog("");
        // Animal父类和MyInterface接口都有 defaultMethod() 方法，MyInterface接口的方法为默认方法
        // 此时将以父类优先，忽略接口的默认方法
        dog.defaultMethod();
    }


    @Test
    public void testStudent() {
        Student student = new Student();
        student.getName();
    }

    @Test
    public void testArrayClone() {
        Animal[] animals = {new Animal("a")};
        System.out.println(Arrays.toString(animals));

        // 数组元素并没有实现 Cloneable 接口，但是可以clone成功
        // 由于数组类是在运行时动态生成，查看class文件只能看到调用了这个数组类的clone方法，参数为animal
        // 好奇jvm做了什么操作
        Animal[] clone = animals.clone();
        System.out.println(Arrays.toString(clone));

        clone[0].setName("dadsa");
        System.out.println(Arrays.toString(animals));

        List<String> names = new ArrayList<>();
        Animal[] animals1 = names.stream().toArray(Animal[]::new);
    }
}
