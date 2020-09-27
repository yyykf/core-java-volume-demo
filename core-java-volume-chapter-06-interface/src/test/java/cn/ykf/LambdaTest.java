package cn.ykf;

import cn.ykf.model.Boy;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;

import static java.util.Comparator.*;

/**
 * @author YuKaiFan <1092882580@qq.com>
 * @date 2020-09-27
 */
public class LambdaTest {

    @Test
    public void test() {
        Boy[] boys = new Boy[]{new Boy("zd"), new Boy("dy"), new Boy("abc"), new Boy("oioio"), new Boy()};
        System.out.println(Arrays.toString(boys));

        // naturalOrder 按自然顺序比较，遇null抛异常
        // 所以使用了 nullsFirst进行包装，认为null < non-null，如果被包装的比较器为null，那么就认为所有的non-null 都相等
        Arrays.sort(boys, Comparator.comparing(Boy::getName, nullsFirst(naturalOrder())));
        // [Boy{name='null'}, Boy{name='abc'}, Boy{name='dy'}, Boy{name='oioio'}]
        System.out.println(Arrays.toString(boys));

        // nullLast认为non-null > null，这里是对reverseOrder()进行包装
        // [Boy{name='zd'}, Boy{name='oioio'}, Boy{name='dy'}, Boy{name='abc'}, Boy{name='null'}]
        Arrays.sort(boys, Comparator.comparing(Boy::getName, nullsLast(reverseOrder())));
        System.out.println(Arrays.toString(boys));
    }
}
