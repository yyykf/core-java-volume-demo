package cn.ykf;

import cn.ykf.model.Dog;
import cn.ykf.model.Student;
import org.junit.Test;

/**
 * @author YuKaiFan <1092882580@qq.com>
 * @date 2020-09-25
 */
public class DefaultMethodTest {

    @Test
    public void testDefaultMethod() {
        Dog dog = new Dog();
        // Animal父类和MyInterface接口都有 defaultMethod() 方法，MyInterface接口的方法为默认方法
        // 此时将以父类优先，忽略接口的默认方法
        dog.defaultMethod();
    }


    @Test
    public void testStudent() {
        Student student = new Student();
        student.getName();
    }
}
