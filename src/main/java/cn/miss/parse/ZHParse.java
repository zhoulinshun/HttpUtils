package cn.miss.parse;

import cn.miss.entity.ZHAnswer;
import com.google.gson.Gson;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
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
    private Gson gson = new Gson();

    @Override
    public List<String> doParse(HttpEntity str) {
        List<String> list = new ArrayList<>();
        Header type = str.getContentType();

        try {
            System.out.println(EntityUtils.toString(str));
            type.getName();
            if (type.getValue().equals("application/json")) {
                ZHAnswer zhAnswer = gson.fromJson(EntityUtils.toString(str), ZHAnswer.class);
                if (zhAnswer != null) {
                    if(!zhAnswer.paging.is_end){
                        list.add(zhAnswer.paging.next);
                    }
                    zhAnswer.data.forEach(s -> list.add(s.content));
                }
            } else if (Pattern.matches("^image/.*", type.getValue())) {
                fileDownload(EntityUtils.toByteArray(str), type.getValue().substring(5));
            } else {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void doParsePic(byte[] bytes) {

    }

    //图片文件下载
    private void fileDownload(byte[] bytes, String p) {
        File file = new File("D:\\知乎\\" + requestNumber + "\\");
        if (!file.exists()) {
            file.mkdirs();
        }
        File f = new File(file, i++ + "." + p);
        try {
            if (!f.exists())
                f.createNewFile();
            Files.write(f.toPath(), bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public String pretreatment(String url) {
        Pattern pattern = Pattern.compile("\\d+$");
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            requestNumber = matcher.group(0);
        }
        if (!url.startsWith(request))
            return request + requestNumber + "/" + param + sur;
        return url;
    }
}
