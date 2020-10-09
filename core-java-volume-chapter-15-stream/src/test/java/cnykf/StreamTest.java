package cnykf;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Stream;

/**
 * @author YuKaiFan <1092882580@qq.com>
 * @date 2020-09-30
 */
public class StreamTest {

    /**
     * Returns an infinite sequential unordered stream
     * 无限，无序
     */
    @Test
    public void testGenerate() {
        // 生成一个无限随机数流，如果不加limit，那么会无限生成元素并打印
        Stream.generate(Math::random).limit(10).forEach(System.out::println);
    }

    /**
     * Returns an infinite sequential ordered stream
     * 无限，有序，有某种规律
     */
    @Test
    public void testIterate() {
        // 将一元函数反复应用到之前的结果上
        // 第一个元素是种子0，第二个元素是 f(0)，第三个元素是 f( f(0) )
        Stream.iterate(BigDecimal.ZERO, n -> n.add(BigDecimal.ONE)).limit(10).forEach(System.out::println);
    }

    @Test
    public void testMap() {
        // 对流中的每个元素进行大写转换映射到一个新的流
        Stream.of("aaa", "bbb", "ccc", "ddd").map(String::toUpperCase).forEach(System.out::println);
    }

    @Test
    public void testFlatMap() {
        // 如果使用map，那么这时候流中的每个元素都是一个Stream流
        Stream.of("aaa", "bbb", "ccc", "ddd").map(this::letters).forEach(stringStream -> stringStream.forEach(System.out::println));

        System.out.println("--------------------");

        // 使用flatMap可以将map结果（也是流）进行连接，相当于把每个子流的元素进行平铺
        Stream.of("aaa", "bbb", "ccc", "ddd").flatMap(this::letters).forEach(System.out::println);
    }

    @Test
    public void testConcat() {
        List<String> list = Arrays.asList("aaa", "bbb");
        Stream<String> l1 = this.letters(list.get(0));
        Stream<String> l2 = this.letters(list.get(1));

        // 连接两个子流，类似上面的flatMap
        // 第一个流不能是无限流，否则第二个流没有机会被处理
        Stream.concat(l1, l2).skip(1).forEach(System.out::println);
    }

    @Test
    public void testPeek() {
        // 在每次获取一个元素时，都会调用peek中的函数，这里不加count，没有对流中的元素进行操作，所以不会打印，也就是惰性处理
        Stream.generate(Math::random).peek(System.out::println).limit(10);
        System.out.println("------------------------");
        // 这里使用了count这个终止操作，获取了流中的元素，所以打印
        Stream.generate(Math::random).peek(System.out::println).limit(10).count();

    }

    @Test
    public void testTerminalOperation() {
        // 最大值
        Optional<Double> max = Stream.generate(Math::random).limit(20).max(Comparator.naturalOrder());
        System.out.println(max.orElse(Double.NaN));
        // 获取流中任意一个元素
        Optional<String> any = Stream.of("aaa", "bbb", "cc", "dddd", "eee").parallel().filter(s -> s.length() > 2).findAny();
        System.out.println(any.orElse(null));
        // 是否匹配
        boolean existed = Stream.of("abcd", "efg", "hij", "aaa").anyMatch(s -> s.startsWith("a"));
        System.out.println(existed);
    }

    /**
     * 将字符串分解为单个字符
     *
     * @param s 字符串
     * @return 含有字符串的所有字符的Stream流
     */
    private Stream<String> letters(String s) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            result.add(s.substring(i, i + 1));
        }
        return result.stream();
    }
}
