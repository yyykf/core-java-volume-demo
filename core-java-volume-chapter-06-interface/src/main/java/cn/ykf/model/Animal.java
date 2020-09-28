package cn.ykf.model;

import cn.ykf.service.MyInterface;

/**
 * @author YuKaiFan <1092882580@qq.com>
 * @date 2020-09-25
 */
public class Animal {
    private String name;

    public Animal(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "age='" + name + '\'' +
                '}';
    }

    /**
     * @see MyInterface#defaultMethod() 同名同参数的默认方法
     */
    public void defaultMethod() {
        System.out.println("Animal的默认方法");
    }
}
