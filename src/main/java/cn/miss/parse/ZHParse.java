package cn.miss.parse;

import cn.miss.entity.ZHAnswer;
import com.google.gson.Gson;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.net.URI;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author MissNull
 * @Description:
 * @Date: Created in 2017/7/27.
 */
public class ZHParse implements ParseString {
    private String request = "https://www.zhihu.com/api/v4/questions/";
    private String requestNumber = "62598434";
    private String sur = "&offset=0&limit=20";
    private String param = "answers?include=data%5B*%5D.is_normal%2Cis_collapsed%2Cannotation_action%2Cannotation_detail%2Ccollapse_reason%2Cis_sticky%2Ccollapsed_by%2Csuggest_edit%2Ccomment_count%2Ccan_comment%2Ccontent%2Ceditable_content%2Cvoteup_count%2Creshipment_settings%2Ccomment_permission%2Cmark_infos%2Ccreated_time%2Cupdated_time%2Creview_info%2Crelationship.is_authorized%2Cis_author%2Cvoting%2Cis_thanked%2Cis_nothelp%2Cupvoted_followees%3Bdata%5B*%5D.author.follower_count%2Cbadge%5B%3F(type%3Dbest_answerer)%5D.topics";
    private int i = 0;
    private String title;
    private Gson gson = new Gson();

    @Override
    public List<String> doParse(CloseableHttpResponse response, CookieStore cookieStore) {
        List<String> list = new ArrayList<>();
        HttpEntity str = response.getEntity();
        Header type = str.getContentType();
        System.out.println(type.getValue());
        try {
            if (type.getValue().contains("application/json")) {
                ZHAnswer zhAnswer = gson.fromJson(EntityUtils.toString(str), ZHAnswer.class);
                if (!zhAnswer.isEmpty()) {
                    title = zhAnswer.data.get(0).question.title;
                    if (!zhAnswer.paging.is_end) {
                        list.add(zhAnswer.paging.next);
                    }
                    zhAnswer.data.forEach(s -> list.addAll(getPicUrl(s.content)));
                }
            } else if (Pattern.matches("^image/.*", type.getValue())) {
                fileDownload(EntityUtils.toByteArray(str), type.getValue().substring(6));
            } else {
                System.out.println(EntityUtils.toString(str));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    private List<String> getPicUrl(String content) {
        Pattern pattern = Pattern.compile("https://pic[0-9]{1}.zhimg.com/[-a-zA-Z0-9]+_b\\.jpg");
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

    //图片文件下载
    private void fileDownload(byte[] bytes, String p) {
        File file = new File("D:\\知乎\\" + title + "\\");
        if (!file.exists()) {
            file.mkdirs();
        }
        File f = new File(file, i++ + "." + p);
        try {
            if (!f.exists())
                f.createNewFile();
            Files.write(f.toPath(), bytes);
            System.out.println("保存成功！");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("保存失败");
        }
    }


    @Override
    public void pretreatment(String url, HttpRequestBase client) {
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
//        if(url.startsWith(request))
//        else {
//            try {
//               Integer.parseInt(url);
//               requestNumber = url;
//               client.setURI(URI.create(request + requestNumber + "/" + param + sur));
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
        if (url.endsWith(".jpg")) {
            client.removeHeaders("Host");
        }
//            client.addHeader("Host", "");}
//        } else {
//            client.addHeader("Host", "www.zhihu.com");
//        }
    }
}
