package cn.ykf;

import org.junit.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

/**
 * @author YuKaiFan <1092882580@qq.com>
 * @date 2020-09-28
 */
public class ExceptionTest {

    @Test
    public void test() {
        Throwable t = new Throwable();
        StringWriter out = new StringWriter();
        t.printStackTrace(new PrintWriter(out));
        // System.out.println(out);

        Map<Thread, StackTraceElement[]> map = Thread.getAllStackTraces();
        for (Map.Entry<Thread, StackTraceElement[]> threadEntry : map.entrySet()) {
            for (StackTraceElement stackTraceElement : threadEntry.getValue()) {
                System.out.println(stackTraceElement);
            }

            System.out.println("------");
        }
    }
}
