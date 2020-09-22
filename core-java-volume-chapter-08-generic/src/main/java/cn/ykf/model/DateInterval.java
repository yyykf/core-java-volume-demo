package cn.ykf.model;

import java.time.LocalDate;

/**
 * @author YuKaiFan<yukf @ pvc123.com>
 * @date 2020-09-21
 */
public class DateInterval extends Pair<LocalDate> {

    public DateInterval(LocalDate first, LocalDate second) {
        super(first, second);
    }

    /**
     * <pre>
     *     public class DateInterval extends Pair<java.time.LocalDate> {
     *          public DateInterval(java.time.LocalDate, java.time.LocalDate);
     *          public void setSecond(java.time.LocalDate);
     *          // 生成的桥方法（bridge method），最终会调用上面的方法
     *          public void setSecond(java.lang.Object);
     *
     *          public java.time.LocalDate getSecond();
     *          // 这个生成的桥方法，和原本的方法的方法签名是一样的，都是 getSecond()
     *          // 如果是我们自己这样写的话，是不能通过编译的，只能由编译器自己生成
     *          // 因为VM是根据 “返回值 + 参数类型” 来确定方法的，所以不影响
     *          public java.lang.Object getSecond();
     *     }
     *
     *     // 返回值 LocalDate，非synthetic
     *     public java.time.LocalDate getSecond();
     *          descriptor: ()Ljava/time/LocalDate;
     *          flags: ACC_PUBLIC
     *
     *     // 返回值 Object，synthetic 方法
     *     public java.lang.Object getSecond();
     *          descriptor: ()Ljava/lang/Object;
     *          flags: ACC_PUBLIC, ACC_BRIDGE, ACC_SYNTHETIC
     * </pre>
     */
    @Override
    public void setSecond(LocalDate second) {
        if (second.compareTo(getFirst()) > 0) {
            super.setSecond(second);
        }
    }

    @Override
    public LocalDate getSecond() {
        return super.getSecond();
    }
}
