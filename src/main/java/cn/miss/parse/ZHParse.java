package cn.miss.parse;

import cn.miss.ano.ParseImpl;
import cn.miss.entity.ZHAnswer;
import cn.miss.utils.CallBack;
import cn.miss.utils.CurrencyUtils;
import com.google.gson.Gson;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.util.EntityUtils;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author MissNull
 * @Description: 知乎
 * @Date: Created in 2017/7/27.
 */
@ParseImpl("知乎")
public class ZHParse extends ParseString {
    private String request = "https://www.zhihu.com/api/v4/questions/";
    private String requestNumber = "62598434";
    private String sur = "&offset=0&limit=20";
    private String param = "answers?include=data%5B*%5D.is_normal%2Cis_collapsed%2Cannotation_action%2Cannotation_detail%2Ccollapse_reason%2Cis_sticky%2Ccollapsed_by%2Csuggest_edit%2Ccomment_count%2Ccan_comment%2Ccontent%2Ceditable_content%2Cvoteup_count%2Creshipment_settings%2Ccomment_permission%2Cmark_infos%2Ccreated_time%2Cupdated_time%2Creview_info%2Crelationship.is_authorized%2Cis_author%2Cvoting%2Cis_thanked%2Cis_nothelp%2Cupvoted_followees%3Bdata%5B*%5D.author.follower_count%2Cbadge%5B%3F(type%3Dbest_answerer)%5D.topics";
    private int i = 0;
    private String title;
    private Gson gson = new Gson();
    private String outPath;

    public ZHParse(CallBack callBack) {
        super(callBack);
    }

    public ZHParse() {
        super();
    }

    @Override
    public List<String> doParse(CloseableHttpResponse response) {
        List<String> list = new ArrayList<>();
        HttpEntity str = response.getEntity();
        Header type = str.getContentType();
        append(type.getValue());
        try {
            if (type.getValue().contains("application/json")) {
                String s1 = EntityUtils.toString(str);
                System.out.println(s1);
                ZHAnswer zhAnswer = gson.fromJson(s1, ZHAnswer.class);
                if (!zhAnswer.isEmpty()) {
                    title = zhAnswer.data.get(0).question.title;
                    if (!zhAnswer.paging.is_end) {
                        list.add(zhAnswer.paging.next);
                    }
                    zhAnswer.data.forEach(s -> list.addAll(getPicUrl(s.content)));
                }
            } else if (Pattern.matches("^image/.*", type.getValue())) {
                fileSave(EntityUtils.toByteArray(str), type.getValue().substring(6));
            } else {
                System.out.println(EntityUtils.toString(str));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    private List<String> getPicUrl(String content) {
        Pattern pattern = Pattern.compile("https://pic[0-9].zhimg.com/[-a-zA-Z0-9]+_b\\.jpg");
        Matcher matcher = pattern.matcher(content);
        List<String> list = new ArrayList<>();
        while (matcher.find()) {
            String group = matcher.group(0);
            if (!list.contains(group)) {
                list.add(group);
            }
        }
        return list;
    }

    //文件保存
    private void fileSave(byte[] bytes, String p) {
        String path = outPath + "\\知乎\\" + title + "\\";
        String name = i++ + "." + p;
        append(CurrencyUtils.fileSave(bytes, name, path));
    }


    @Override
    public void pretreatment(String url, HttpRequestBase client, CookieStore cookieStore) {
        if (url.startsWith("http:")) {
            url = "https" + url.substring(4);
        }
        if (url.startsWith("https://www.zhihu.com/question/")) {
            Pattern pattern = Pattern.compile("\\d+$");
            Matcher matcher = pattern.matcher(url);
            if (matcher.find()) {
                requestNumber = matcher.group(0);
            }
            client.setURI(URI.create(request + requestNumber + "/" + param + sur));
        } else {
            client.setURI(URI.create(url));
        }
        if (url.endsWith(".jpg")) {
            client.removeHeaders("Host");
        }
    }

    @Override
    public void start(cn.miss.entity.HttpEntity entity, HttpGet httpGet) {
        outPath = entity.getOutPath();
    }
}
