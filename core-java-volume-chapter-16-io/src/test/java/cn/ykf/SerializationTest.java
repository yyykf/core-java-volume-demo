package cn.ykf;

import cn.ykf.model.Person;
import cn.ykf.model.Phone;
import cn.ykf.model.Singleton;
import org.junit.Test;

import java.io.*;

/**
 * @author YuKaiFan <1092882580@qq.com>
 * @date 2020-10-10
 */
public class SerializationTest {

    @Test
    public void testSerialization() {
        Person p1 = new Person("0x01", "yukaifan", new Phone("", "black"));
        System.out.println(p1);

        final Person p2 = this.writeAndReadObj("person.dat", p1);
        System.out.println(p2);
    }

    @Test
    public void testSingleton() {
        final Singleton singleton = Singleton.newInstance();
        // 破坏单例
        final Singleton newSingleton = this.writeAndReadObj("singleton.dat", singleton);

        System.out.println(singleton == newSingleton);
    }

    /**
     * 把对象写入序列化到文件后反序列化回来
     *
     * @param fileName 待保存的文件名称
     * @param oldObj   待序列化对象
     * @param <T>      对象类型
     * @return 反序列化后的对象
     */
    @SuppressWarnings("unchecked")
    private <T> T writeAndReadObj(String fileName, T oldObj) {
        try (final ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(oldObj);
        } catch (IOException e) {
            e.printStackTrace();
        }

        T newObj = null;
        try (final ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            newObj = (T) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return newObj;
    }
}

