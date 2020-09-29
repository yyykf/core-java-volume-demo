package cn.ykf;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * @author YuKaiFan <1092882580@qq.com>
 * @date 2020-09-29
 */
public class CollectionTest {

    @Test
    public void testListIterator() {
        List<String> list = new LinkedList<>();
        list.add("1");
        list.add("2");
        list.add("3");

        ListIterator iterator = list.listIterator();
        // 等价于直接调用LinkedList的addFirst
        iterator.add("0");

        while (iterator.hasNext()) {
            // 调整迭代器到最后位置
            iterator.next();
        }

        // 往前遍历
        while (iterator.hasPrevious()) {
            String previous = (String) iterator.previous();

            System.out.println("当前元素：" + previous);

            if ("1".equals(previous) || "2".equals(previous)) {
                iterator.remove();
            }

            if ("0".equals(previous)) {
                // 把第一个元素设置为 first
                // set方法默认更改迭代器刚返回的元素
                iterator.set("first");
            }
        }

        for (String s : list) {
            System.out.println(s);
        }
    }

    @Test
    public void testLinkedList() {
        List<String> aList = new LinkedList<>();
        aList.add("a");
        aList.add("c");
        aList.add("e");

        List<String> bList = new LinkedList<>();
        bList.add("b");
        bList.add("d");
        bList.add("f");
        bList.add("g");

        ListIterator<String> aIte = aList.listIterator();
        ListIterator<String> bIte = bList.listIterator();

        while (bIte.hasNext()) {
            if (aIte.hasNext()) {
                aIte.next();
            }
            aIte.add(bIte.next());
        }
        System.out.println(aList);

        // 重置迭代器
        bIte = bList.listIterator();
        while (bIte.hasNext()) {
            bIte.next();
            if (bIte.hasNext()) {
                bIte.next();
                bIte.remove();
            }
        }
        System.out.println(bList);

        aList.removeAll(bList);
        System.out.println(aList);
    }
}
