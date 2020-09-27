package cn.ykf.model;

/**
 * @author YuKaiFan <1092882580@qq.com>
 * @date 2020-09-27
 */
public class Boy {

    private String name;

    public Boy() {
    }

    public Boy(String name) {
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
        return "Boy{" +
                "name='" + name + '\'' +
                '}';
    }
}
