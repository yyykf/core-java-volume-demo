package cn.ykf.model;

/**
 * 不实现 Serializable
 *
 * @author YuKaiFan <1092882580@qq.com>
 * @date 2020-10-10
 */
public class Phone {

    private String phoneName;
    private String phoneColor;

    public Phone() {
    }

    public Phone(String phoneName, String phoneColor) {
        this.phoneName = phoneName;
        this.phoneColor = phoneColor;
    }

    public String getPhoneName() {
        return phoneName;
    }

    public void setPhoneName(String phoneName) {
        this.phoneName = phoneName;
    }

    public String getPhoneColor() {
        return phoneColor;
    }

    public void setPhoneColor(String phoneColor) {
        this.phoneColor = phoneColor;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "phoneName='" + phoneName + '\'' +
                ", phoneColor='" + phoneColor + '\'' +
                '}';
    }
}
