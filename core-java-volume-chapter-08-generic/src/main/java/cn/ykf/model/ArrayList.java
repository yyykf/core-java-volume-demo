package cn.ykf.model;

/**
 * @author YuKaiFan <1092882580@qq.com>
 * @date 2020-09-21
 */
public class ArrayList<E> {

    // private Object[] elements;
    private E[] elements;

    public ArrayList() {
        elements = (E[]) new Object[10];
    }

    @SuppressWarnings("unchecked")
    public E get(int n) {
        // from Object cast to E
        return (E) elements[n];
    }

    public void set(int n, E e) {
        // don't need to cast to E
        elements[n] = e;
    }


}
