package cn.ykf.service.impl;

import cn.ykf.service.MyInterface;

/**
 * @author YuKaiFan <1092882580@qq.com>
 * @date 2020-09-25
 */
public class MyInterfaceImpl implements MyInterface {

    private void test() {
        // 可以不覆盖default方法
        this.defaultMethod();
        // 直接调用static方法
        MyInterface.staticMethod();
    }

}
