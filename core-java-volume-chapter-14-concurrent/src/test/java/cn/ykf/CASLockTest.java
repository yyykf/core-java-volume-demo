package cn.ykf;

import cn.ykf.lock.CASLock;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author YuKaiFan <1092882580@qq.com>
 * @date 2020-10-14
 */
public class CASLockTest {

    @Test
    public void test() throws InterruptedException {
        final CASLock lock = new CASLock();

        for (int i = 0; i < 5; i++) {

            new Thread(() -> {
                try {
                    lock.tryLock();
                    // 加锁后休眠1秒
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }).start();
        }

        TimeUnit.SECONDS.sleep(5);
    }
}
