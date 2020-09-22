package cn.ykf.model;

/**
 * @author YuKaiFan <1092882580@qq.com>
 * @date 2020-09-22
 */
public class Dog extends Animal implements Comparable {

    /*
        但是如果父类 implements Comparable<Animal> ，而子类 implements Comparable<Dog>
        这样就非法了，因为实现接口后，肯定会生成一个桥方法
        public class Animal implements java.lang.Comparable<Animal> {
          public Animal();
          public int compareTo(Animal);
          // 生成的桥方法 compareTo(Object o) { compareTo((Dog)o); }
          public int compareTo(java.lang.Object);
        }

        这样一来，桥方法就会冲突了，毕竟桥方法也不知道该调用父类的compareTo还是子类的compareTo
     */

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
