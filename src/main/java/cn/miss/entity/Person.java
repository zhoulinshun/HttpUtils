package cn.miss.entity;

/**
 * @Author MissNull
 * @Description:
 * @Date: Created in 2017/8/31.
 */

public class Person {
    private String name;
    private String card;
    private String phone;
    private String zfb;
    private String wx;
    private String money;
    private String dataLength;

    public Person(String name, String card, String phone, String zfb, String wx, String money, String dataLength) {
        this.name = name;
        this.card = card;
        this.phone = phone;
        this.zfb = zfb;
        this.wx = wx;
        this.money = money;
        this.dataLength = dataLength;
    }

    public Person() {
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", card='" + card + '\'' +
                ", phone='" + phone + '\'' +
                ", zfb='" + zfb + '\'' +
                ", wx='" + wx + '\'' +
                ", money='" + money + '\'' +
                ", dataLength='" + dataLength + '\'' +
                '}';
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getDataLength() {
        return dataLength;
    }

    public void setDataLength(String dataLength) {
        this.dataLength = dataLength;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getZfb() {
        return zfb;
    }

    public void setZfb(String zfb) {
        this.zfb = zfb;
    }

    public String getWx() {
        return wx;
    }

    public void setWx(String wx) {
        this.wx = wx;
    }
}
