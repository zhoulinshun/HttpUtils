package cn.miss.parse;

import cn.miss.utils.Utils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author MissNull
 * @Description: 煎蛋网
 * @Date: Created in 2017/8/1.
 */
public class JDParse extends ParseString {
    private int i = 0;
    private String title = null;
    private int maxPage = 0;
    private Integer current = 0;
    private boolean isFirst = true;
    private String suff = "http://jandan.net/";
    private String pre = "jpg";
    private String outPath;

    @Override
    public List<String> doParse(CloseableHttpResponse response, CookieStore cookieStore) {
        List<String> list = new ArrayList<>(32);
        HttpEntity entity = response.getEntity();
        Header type = entity.getContentType();
        append(type.getValue());
        try {
            if (Pattern.matches("^image/.*", type.getValue())) {
                fileSave(EntityUtils.toByteArray(entity));
            } else if (type.getValue().contains("text/html")) {
                String html = EntityUtils.toString(entity);
                if (title == null)
                    getTitleAndMaxPage(html);
                if (current != 0)
                    list.add(suff + "/page-" + current--);
                list.addAll(getURL(html));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    private List<String> getURL(String html) {
        List<String> url = new ArrayList<>(32);
        Pattern compile = Pattern.compile("//wx[0-9]\\.sinaimg\\.cn/large/[0-9A-Za-z]+\\.[a-zA-Z]+");
        Matcher matcher = compile.matcher(html);
        while (matcher.find()) {
            url.add("https:" + matcher.group(0));
        }
        return url;
    }

    private void getTitleAndMaxPage(String html) {
        Pattern compile = Pattern.compile("<h1 class=\"title\">.+</h1>");
        Matcher matcher = compile.matcher(html);
        if (matcher.find()) {
            String substring = matcher.group(0).substring(18);
            int i = substring.indexOf("<");
            title = substring.substring(0, i);
        }
        compile = Pattern.compile("<a href=\"http://jandan.net/[a-z]+/page-[0-9]+#comments\">\\s+\\d+\\s+</a>");
        matcher = compile.matcher(html);
        if (matcher.find()) {
            String substring = matcher.group(0).substring(51);
            int i = substring.indexOf("<");
            maxPage = Integer.parseInt(substring.substring(0, i).trim());
            current = maxPage - 1;
        }
    }

    private void fileSave(byte[] bytes) {
        String path = outPath + "\\煎蛋\\" + title + "\\";
        String name = i++ + "." + pre;
        append(Utils.fileSave(bytes, name, path));
    }

    @Override
    public void pretreatment(String url, HttpRequestBase client) {
        int i = url.lastIndexOf(".");
        pre = url.substring(i + 1);
        if (isFirst) {
            suff = url;
            isFirst = false;
        }
        client.setURI(URI.create(url));
    }

    @Override
    public void init(cn.miss.entity.HttpEntity entity) {
        outPath = entity.getOutPath();
    }
}
