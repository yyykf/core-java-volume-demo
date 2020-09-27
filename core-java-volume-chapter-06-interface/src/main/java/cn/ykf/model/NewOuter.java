package cn.ykf.model;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author YuKaiFan <1092882580@qq.com>
 * @date 2020-09-27
 */
public class NewOuter {

    public void test(boolean flag) {
        // 局部内部类
        class TimerPrinter implements ActionListener {

            // 这里应该是会有一个生成的TimerPrinter(NewOuter, boolean)构造器，不过不知道class文件中为什么找不到，只有无参构造器

            // 多了一个final域用于备份flag
            //  final boolean val$flag;
            @Override
            public void actionPerformed(ActionEvent e) {
                // 2.局部内部类的这个方法可能会等待很久才被执行，可能外部类的test方法已经执行完毕了，flag也被释放了
                // 但是这里依旧可以使用flag变量，原因就是因为编译器在局部内部类生成了一个flag的副本
                if (flag) {
                    System.out.println("flag为true...");
                }
            }
        }

        // 6: invokespecial #3                  // Method NewOuter$1TimerPrinter."<init>":(LNewOuter;Z)V
        // 编译后，这里会调用局部内部类中一个生成的构造器 TimerPrinter(NewOuter, boolean)，把外部类和flag传递进去
        ActionListener listener = new TimerPrinter();
        // 1.test方法执行完这条语句就结束了，那么flag变量也随之被释放了
        new Timer(Integer.MAX_VALUE, listener).start();
    }
}
