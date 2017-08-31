package cn.miss.parse;

import cn.miss.entity.HttpEntity;
import cn.miss.utils.Utils;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author MissNull
 * @Description:
 * @Date: Created in 2017/8/2.
 */
public class ACGParse extends ParseString {
    private String title;
    private Date date;
    private int number = 1;
    private boolean isFirst = true;
    private String url = "http://pm.531mk.com/lifanacgup/lifanacg";
    private final String defaultURL = "http://www.baidu.com";
    private String nextUrl;
    private String outPath;
    private String truePath;
    private SimpleDateFormat format;

    @Override
    public List<String> doParse(CloseableHttpResponse response, CookieStore cookieStore) {
        List<String> list = new ArrayList<>(100);
        if (isFirst) {
            list.addAll(getURL());
            isFirst = false;
        } else {
            String value = response.getEntity().getContentType().getValue();
            append(value);
            org.apache.http.HttpEntity entity = response.getEntity();
            try {
                if (value.contains("image")) {
                    byte[] bytes = EntityUtils.toByteArray(entity);
                    fileSave(bytes);
                    list.add(nextUrl);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return list;
    }

    private void fileSave(byte[] bytes) {
        String filename = getNumber(number-1) + ".jpg";
        Utils.fileSave(bytes, filename, truePath);
        append(filename + "：保存成功");
    }

    @Override
    public void pretreatment(String url, HttpRequestBase client) {
        if (isFirst) {
            number = 1;
            client.setURI(URI.create(defaultURL));
        } else {
            if (!url.equals(title)) {
                title = url;
                number = 1;
            }
            truePath = outPath + "\\" + url;
            client.setURI(URI.create(this.url + "/" + url + "/" + getNumber(number++) + ".jpg"));
            nextUrl = url;
        }
    }

    /**
     * 批量获取连接  只在第一次时调用
     *
     * @return
     */
    private List<String> getURL() {
        List<String> list = new ArrayList<>(2000);
        Date now = new Date();
        while (true) {
            if (now.compareTo(date) > 0) {
                for (int i = 1; i < 10; i++) {
                    list.add(format.format(date) + "/" + i);
                }
                date.setDate(date.getDate() + 1);
            } else
                break;
        }
        return list;
    }

    /**
     * 获取字符串形式的编号
     *
     * @param i
     * @return
     */
    private String getNumber(int i) {
        if ((i + "").length() == 1) {
            return "00" + i;
        } else if ((i + "").length() == 2) {
            return "0" + i;
        } else return i + "";
    }

    @Override
    public void init(HttpEntity entity) {
        String url = entity.getUrl();
        outPath = entity.getOutPath();
        String substring = url.substring(url.lastIndexOf("/") + 1);
        title = substring + "/1";
        format = new SimpleDateFormat("yyyyMMdd");
        try {
            date = format.parse(substring);
        } catch (ParseException e) {
            e.printStackTrace();
            date = new Date();
        }
    }
}
