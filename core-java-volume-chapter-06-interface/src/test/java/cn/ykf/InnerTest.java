package cn.ykf;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author YuKaiFan <1092882580@qq.com>
 * @date 2020-09-27
 */
public class InnerTest {

    @Test
    public void testDoubleBraceInitialization() {
        // 双括号初始化，不知道什么骚操作
        this.fun(new ArrayList<String>() {
            // 这一对是构造匿名子类

            {
                // 这一对括号则是初始化块
                add("aa");
                add("bb");
                // class cn.ykf.InnerTest$1，这是InnerTest的一个匿名子类
                System.out.println(this.getClass());
                // 获取外围类 class cn.ykf.InnerTest
                System.out.println(this.getClass().getEnclosingClass());
            }
        });
    }

    private void fun(List<String> list) {
        System.out.println(list);
    }
}
