package cn.ykf.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @author YuKaiFan <1092882580@qq.com>
 * @date 2020-10-09
 */
public class Person implements Serializable {

    private static final long serialVersionUID = -8559913068799529968L;

    private String id;

    private String name;

    /** transient，未实现 Serializable，不会参与自动序列化/反序列化 */
    private transient Phone phone;

    public Person() {
    }

    public Person(String id, String name) {
        this(id, name, null);
    }

    public Person(String id, String name, Phone phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    /**
     * 如果不提供自定义实现，那么由于 phone 是 transient ，所以不会参与到自动的序列化和反序列化，
     * 需要提供自定义序列化来覆盖自动序列化
     *
     * @param out
     * @throws IOException
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        // 先序列化对象描述符和String域
        // 注意phone必须为transient，否则由于未实现Serializable，在这里序列化的时候会抛异常
        out.defaultWriteObject();
        // 依次序列化phone的字段
        out.writeObject(phone.getPhoneName());
        out.writeObject(phone.getPhoneColor());
    }

    /**
     * 覆盖默认的反序列化操作
     *
     * @param in
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        // 读对象描述符和String域
        in.defaultReadObject();
        // 依次读出phone的字段
        final String phoneName = (String) in.readObject();
        final String phoneColor = (String) in.readObject();
        // 不使用默认的操作，可以定制想要的实现
        phone = new Phone(phoneName.isEmpty() ? "UNKNOWN" : phoneName, phoneColor.isEmpty() ? "UNKNOWN" : phoneColor);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", phone=" + phone +
                '}';
    }
}
