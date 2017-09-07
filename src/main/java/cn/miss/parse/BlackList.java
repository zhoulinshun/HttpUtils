package cn.miss.parse;

import cn.miss.ano.ParseImpl;
import cn.miss.entity.HttpEntity;
import cn.miss.entity.Person;
import cn.miss.utils.CurrencyUtils;
import cn.miss.utils.Utils;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author MissNull
 * @Description:
 * @Date: Created in 2017/8/7.
 */
@ParseImpl("信用黑名单")
public class BlackList extends ParseString {
    private String url = "https://www.xinyongheimingdan.cc/blacklist-";
    private String uuu = "https://www.xinyongheimingdan.cc/";
    private int i = 1;
    private Connection connection = Utils.getConnection();
    private boolean flag = false;//是否是具体信息

//    private List<Person> personList = new ArrayList<>(2000);

    @Override
    public List<String> doParse(CloseableHttpResponse response) {
        org.apache.http.HttpEntity entity = response.getEntity();
        List<String> list = new ArrayList<>();
        try {
            String s = EntityUtils.toString(entity);
            System.out.println(s);
            if (!flag) {
                String value = CurrencyUtils.getReg("(?:class=\"next\" href=\")(s\\?p=[0-9]+)(?:\">)", s, 1);
                if (!value.equals("")) {
                    list.add(uuu + value);
                }
                list.addAll(CurrencyUtils.getRegs("(?:\\(')([a-zA-Z0-9]{10})(?:'\\))", s, 1).
                        stream().map(m -> url + m + ".html").collect(Collectors.toList()));
            } else {
                Person personInfo = getPersonInfo(s);
                PreparedStatement pre = connection.prepareStatement("INSERT INTO sx_person (name, card, phone, zfb, wx) VALUES (?,?,?,?,?)");
                pre.setString(1, personInfo.getName());
                pre.setString(2, personInfo.getCard());
                pre.setString(3, personInfo.getPhone());
                pre.setString(4, personInfo.getZfb());
                pre.setString(5, personInfo.getWx());
                if (pre.execute()) {
                    append("成功插入一条数据" + personInfo);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Person getPersonInfo(String s) {
        String name = CurrencyUtils.getReg("(?:姓名：)([\\u4e00-\\u9fa5]+)", s, 1);
        String card = CurrencyUtils.getReg("(?:身份证号码：)([0-9xX]+)", s, 1);
        String phone = CurrencyUtils.getReg("(?:手机号码：)([0-9]{11})", s, 1);
        String zfb = CurrencyUtils.getReg("(?:支付宝：)([a-zA-Z0-9]+)", s, 1);
        String wx = CurrencyUtils.getReg("(?:微信：)([a-zA-Z0-9]+)", s, 1);
        String money = CurrencyUtils.getReg("(?:逾期本息：<i>)([0-9]+\\.[0-9]{2}元)(?:</i>)", s, 1);
        String dataLength = CurrencyUtils.getReg("(?:状态：<i>)(逾期[0-9]+天)", s, 1);
        return new Person(name, card, phone, zfb, wx, money, dataLength);
    }


    @Override
    public void pretreatment(String url, HttpRequestBase client, CookieStore cookieStore) {
        flag = url.contains("blacklist");
        client.setURI(URI.create(url));
        cookieStore.getCookies().forEach(c -> ((BasicClientCookie) c).setDomain(" https://www.xinyongheimingdan.cc/"));
        System.out.println(cookieStore.getCookies());
    }

    @Override
    public void start(HttpEntity entity, HttpGet httpGet) {
        System.out.println(entity.getCookie());
        System.out.println(entity.getHeader());
    }

    @Override
    public void end() {

    }
}
