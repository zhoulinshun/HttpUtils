package cn.miss.test;


import cn.miss.client.builder.HttpClientExecutor;
import cn.miss.entity.HttpEntity;
import cn.miss.entity.ZHAnswer;
import cn.miss.parse.ZHParse;
import cn.miss.utils.Utils;
import com.google.gson.Gson;
import org.junit.Test;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author MissNull
 * @Description:
 * @Date: Created in 2017/7/26.
 */
public class HttpTest {



    public static void main(String[] args){
        HttpClientExecutor builder = new HttpClientExecutor();
        HttpEntity httpEntity = new HttpEntity();
        HashMap<String, String> map = new HashMap<>();
        HashMap<String, String> cookies = new HashMap<>();
        cookies.put("aliyungf_tc","AQAAAI09g0YOZAMAFX4pfRct197NqB2Y");
        cookies.put("l_n_c","1");
        cookies.put("q_c1","2eca2f819b7f4d21b7fdd0c180597d53|1500509970000|1500509970000");
        cookies.put("_zap","8afc7ea3-ce4a-487a-bb3c-9efbdf9bccd6");
        cookies.put("r_cap_id","ODk1Y2YyNzhmODkyNDNiNThmNjFmOTVjNzY3ZmQxNTQ=|1500942221|60c2ac18bca3b10020fc9af10a4a303ba862a8e4");
        cookies.put("cap_id","NWRlNThhZjdiNTI2NDc2YzhiYjExZGM0Y2U2N2Y4NTY=|1500942221|b7d84ca043a6edd344f879812a80030340d1ea1d");
        cookies.put("d_c0","AJBC0NzbHQyPTpWfN2ml51-wFl0T-W0ud-o=|1500942222");
        cookies.put("z_c0","Mi4wQUVDQXpSTmV3UWtBa0VMUTNOc2REQmNBQUFCaEFsVk5teHllV1FBTW1ZeUFkdVZiVVhfT0Y5RHVGRGFGRjVJVklB|1500942235|1da8f8fc129288a2f160e0c5b7296f6758a8bd39");
        cookies.put("_xsrf","e6fe94826905b1c446e6c2cb024d1377");
        cookies.put("__utma","51854390.838969751.1501136061.1501136061.1501136061.1");
        cookies.put("__utmz","51854390.1501136061.1.1.utmcsr=zhihu.com|utmccn=(referral)|utmcmd=referral|utmcct=/question/49044384");
        cookies.put("__utmv","51854390.100-1|2=registration_date=20160411=1^3=entry_date=20160411=1");
        cookies.put("_xsrf","e6fe94826905b1c446e6c2cb024d1377");

        map.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        map.put("Accept-Encoding","gzip, deflate, br");
        map.put("Accept-Language","zh-CN,zh;q=0.8");
        map.put("Cache-Control","max-age=0");
        map.put("Connection","keep-alive");
        map.put("Host","www.zhihu.com");
//        map.put("Referer","https://www.zhihu.com/topic/19550228/top-answers");
        map.put("Upgrade-Insecure-Requests","1");
        map.put("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36");
//        httpEntity.setUrl("https://www.zhihu.com/question/49044384");
//        httpEntity.setUrl("https://www.zhihu.com/question/23147606");
//        httpEntity.setUrl("https://www.zhihu.com/question/40273344");
//        httpEntity.setUrl("https://www.zhihu.com/question/39752484");
//        httpEntity.setUrl("https://www.zhihu.com/question/49364343");
//        httpEntity.setUrl("https://www.zhihu.com/question/20902967");
//        httpEntity.setUrl("https://www.zhihu.com/question/24463692");
        httpEntity.setUrl("https://www.zhihu.com/question/60080785");
        httpEntity.setMethod("GET");
        httpEntity.setHeader(map);
        httpEntity.setCookie(cookies);
        builder.start(httpEntity,new ZHParse());
    }

    @Test
    public void test2(){
        Pattern pattern = Pattern.compile("https://pic[0-9]{1}.zhimg.com/[-a-zA-Z0-9]+_b\\.jpg");
        Matcher matcher = pattern.matcher("https://pic1.zhimg.com/e-339a6c60cb-ade1fdb4fcb98e9261cf0_b.jpghttps://pic1.zhimg.com/e339a6c60cbade1fdb4fcb98e9261cf1_b.jpg");
        while (matcher.find()){
            System.out.println(matcher.group(0));
        }
    }
    @Test
    public void test3(){
        Pattern pattern = Pattern.compile("\\d+$");
        Matcher matcher = pattern.matcher("sadasdas12123sdsffs/123556566");
        if (matcher.find()) {
            System.out.println(matcher.group(0));
        }
    }

    @Test
    public void test4() throws Exception {
        Gson gson = new Gson();
        String path = getClass().getResource("/test.json").getPath();
        FileInputStream outputStream = new FileInputStream(new File(path));
        BufferedReader reader = new BufferedReader(new InputStreamReader(outputStream));
        StringBuffer stringBuffer = new StringBuffer();
        String len;
        while ((len = reader.readLine())!=null){
            stringBuffer.append(len);
        }
        reader.close();
        ZHAnswer zhAnswer = gson.fromJson(stringBuffer.toString(), ZHAnswer.class);
        zhAnswer.data.forEach(s-> System.out.println(s.content));
    }

    @Test
    public void test15(){
        Map<String, String> cookies = Utils.getCookies("q_c1=6fe2bf809ee64b04bb2eb9c6aa308a43|1501150144000|1501150144000; r_cap_id=\"OTYxMzExZjEzYjY1NGFlYWI1YWFkZGM1YzFlZTk4ZTU=|1501150144|d80bf4f81fd976ab5a0290c877e3337340b37c24\"; cap_id=\"MTViNmU4ZWZlMTE5NDVlYjllOWYyMjQxZjlkNGM1MWQ=|1501150144|17569bebee4fe488adfad60a5eee699da1390b81\"; d_c0=\"AEDCSwb1IAyPTrwVI13YO8FRxiYS0QehvRk=|1501150145\"; _zap=c8b3bef7-884f-4c38-8229-4651edb4fe2e; __utma=51854390.273912353.1501150162.1501150162.1501150162.1; __utmz=51854390.1501150162.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); __utmv=51854390.000--|3=entry_date=20170727=1; z_c0=Mi4wQUVDQXpSTmV3UWtBUU1KTEJ2VWdEQmNBQUFCaEFsVk56a2loV1FBS0NUUHNmdkNRbGZqOVFXVWs1YUZjRFVTZ1R3|1501150158|049875bbc83aec0f6886dff82df5f3fb2ed837bf");
        cookies.forEach((s,c)-> System.out.println(s+" "+c));
    }

    @Test
    public void test14(){
        System.out.println(Pattern.matches("^image/.*", "image/jpeg"));
    }
}
