package cn.miss.test;


import cn.miss.client.builder.HttpClientBuilder;
import cn.miss.entity.HttpEntity;
import cn.miss.entity.ZHAnswer;
import cn.miss.parse.ZHParse;
import com.google.gson.Gson;
import org.junit.Test;

import java.io.*;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author MissNull
 * @Description:
 * @Date: Created in 2017/7/26.
 */
public class HttpTest {



    @Test
    public void test(){
        HttpClientBuilder builder = new HttpClientBuilder();
        HttpEntity httpEntity = new HttpEntity();
        HashMap<String, String> map = new HashMap<>();
        HashMap<String, String> cookies = new HashMap<>();
        // l_n_c=1; q_c1=6fe2bf809ee64b04bb2eb9c6aa308a43|1501150144000|1501150144000; _xsrf=8831062551d6f884de094a1f64c9d64c; r_cap_id="OTYxMzExZjEzYjY1NGFlYWI1YWFkZGM1YzFlZTk4ZTU=|1501150144|d80bf4f81fd976ab5a0290c877e3337340b37c24"; cap_id="MTViNmU4ZWZlMTE5NDVlYjllOWYyMjQxZjlkNGM1MWQ=|1501150144|17569bebee4fe488adfad60a5eee699da1390b81"; d_c0="AEDCSwb1IAyPTrwVI13YO8FRxiYS0QehvRk=|1501150145"; _zap=c8b3bef7-884f-4c38-8229-4651edb4fe2e; __utma=51854390.273912353.1501150162.1501150162.1501150162.1; __utmb=51854390.0.10.1501150162; __utmc=51854390; __utmz=51854390.1501150162.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); __utmv=51854390.000--|3=entry_date=20170727=1; z_c0=Mi4wQUVDQXpSTmV3UWtBUU1KTEJ2VWdEQmNBQUFCaEFsVk56a2loV1FBS0NUUHNmdkNRbGZqOVFXVWs1YUZjRFVTZ1R3|1501150158|049875bbc83aec0f6886dff82df5f3fb2ed837bf; _xsrf=8831062551d6f884de094a1f64c9d64c
        cookies.put("aliyungf_tc","AQAAAI09g0YOZAMAFX4pfRct197NqB2Y");
        cookies.put("l_n_c","1");
        cookies.put("q_c1","6fe2bf809ee64b04bb2eb9c6aa308a43|1501150144000|1501150144000");
        cookies.put("","");
        cookies.put("","");

        map.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        map.put("Accept-Encoding","gzip, deflate, br");
        map.put("Accept-Language","zh-CN,zh;q=0.8");
        map.put("Cache-Control","max-age=0");
        map.put("Connection","keep-alive");
        map.put("Host","www.zhihu.com");
//        map.put("Referer","https://www.zhihu.com/topic/19550228/top-answers");
        map.put("Upgrade-Insecure-Requests","1");
        map.put("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36");
        httpEntity.setUrl("https://www.zhihu.com/question/49044384");
        httpEntity.setMethod("GET");
        httpEntity.setHeader(map);
        httpEntity.setCookie(cookies);
        builder.start(httpEntity,new ZHParse());
    }

    @Test
    public void test2(){
        Pattern pattern = Pattern.compile("https://pic1.zhimg.com/[a-zA-Z0-9]*_{1}b.jpg");
        Matcher matcher = pattern.matcher("https://pic1.zhimg.com/e339a6c60cbade1fdb4fcb98e9261cf0_b.jpghttps://pic1.zhimg.com/e339a6c60cbade1fdb4fcb98e9261cf1_b.jpg");
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

    }

    @Test
    public void test14(){
        System.out.println(Pattern.matches("^image/.*", "image/jpeg"));
    }
}
