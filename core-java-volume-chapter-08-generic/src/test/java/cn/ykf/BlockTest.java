package cn.ykf;

import cn.ykf.util.Block;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @author YuKaiFan <1092882580@qq.com>
 * @date 2020-09-22
 */
public class BlockTest {

    @Test
    public void test() throws InterruptedException {
        // 但是如果通过泛型， 见 Block.throwAs()，可以让编译器以为这个异常不是 unchecked exception，所以编译可以通过
        new Block() {
            @Override
            public void doSomething() throws Exception {
                Scanner in = new Scanner(new File("not existed file"), "UTF-8");
                while (in.hasNext()) {
                    System.out.println(in.next());
                }
            }
        }.toThread().start();

        // 正常情况下，Thread的run()方法不允许直接抛出一个unchecked exception，所以只能包装成一个unchecked exception后才能抛出
        new Thread(() -> {
            try {
                Scanner in = new Scanner(new File("not existed file"), "UTF-8");
                while (in.hasNext()) {
                    System.out.println(in.next());
                }
            } catch (FileNotFoundException e) {
                RuntimeException runtimeException = new RuntimeException();
                runtimeException.initCause(e);
                throw runtimeException;
            }
        }).start();

        TimeUnit.SECONDS.sleep(1);
    }
}
