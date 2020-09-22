package cn.ykf.util;

/**
 * @author YuKaiFan <1092882580@qq.com>
 * @date 2020-09-22
 */
public abstract class Block {


    /**
     * 可以把checked exception 封装为 unchecked exception
     */
    @SuppressWarnings("unchecked")
    public static <T extends Throwable> void throwAs(Throwable e) throws T {
        // 编译后这里其实就是 throw e; 只是一个强转的假象，不会真的执行 throw (RuntimeException) e
        throw (T) e;
    }

    public abstract void doSomething() throws Exception;

    public Thread toThread() {
        return new Thread(() -> {
            try {
                doSomething();
            } catch (Throwable t) {
                // 使编译器认为这个异常是一个RuntimeException，直接在线程的run()中就抛出了
                Block.<RuntimeException>throwAs(t);
            }
        });
    }

    // 泛型可以用在 throws 中
    public static <T extends Throwable> void doWork(T t) throws T {
        try {
            // do something
        } catch (Throwable cause) {
            t.initCause(cause);
            throw t;
        }
    }

  /*  public static <T extends Throwable> void doWork(Class<T> clazz) {
        try {
            // do something
        }catch (T t){
            // 不能在catch子句中使用类型变量
        }
    }*/
}
