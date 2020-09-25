package cn.ykf.model;

import cn.ykf.service.Named;
import cn.ykf.service.Person;

/**
 * @author YuKaiFan <1092882580@qq.com>
 * @date 2020-09-25
 */
// cn.ykf.model.Student inherits unrelated defaults for getName() from types cn.ykf.service.Named and cn.ykf.service.Person
// 出现冲突，要求必须覆盖方法来解决冲突
public class Student implements Named, Person {
    @Override
    public void getName() {
        // 这里选择调用Person的方法
        Person.super.getName();
    }
}
