package cn.ykf.model;

import cn.ykf.service.MyInterface;

/**
 * @author YuKaiFan <1092882580@qq.com>
 * @date 2020-09-25
 */
public class Animal {

    /**
     * @see MyInterface#defaultMethod() 同名同参数的默认方法
     */
    public void defaultMethod() {
        System.out.println("Animal的默认方法");
    }
}
