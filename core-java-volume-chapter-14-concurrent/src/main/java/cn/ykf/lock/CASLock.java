package cn.ykf.lock;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 乐观锁
 *
 * @author YuKaiFan <1092882580@qq.com>
 * @date 2020-10-14
 */
public class CASLock {

    /** 通过AtomicInteger来实现 */
    private AtomicInteger lock = new AtomicInteger();

    /** 当前获取到锁的线程 */
    private Thread currentThread = null;

    public void tryLock() throws Exception {
        // CAS更新，更新为1则代表加锁
        final boolean isLock = lock.compareAndSet(0, 1);
        if (!isLock) {
            throw new Exception("tryLock failed...");
        }

        // 保存当前加锁线程
        currentThread = Thread.currentThread();
        System.out.println(currentThread + " tryLock...");
    }

    public void unlock() {
        final int value = lock.get();
        // 0代表未加锁
        if (value == 0) {
            return;
        }

        if (currentThread == Thread.currentThread()) {
            // 这里只有上锁的线程会进来，所以只需要更新一次
            lock.compareAndSet(1, 0);
            System.out.println(currentThread + " unlock...");
        }
    }
}
