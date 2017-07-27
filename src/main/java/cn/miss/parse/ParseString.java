package cn.miss.parse;

import org.apache.http.HttpEntity;

import java.util.List;

/**
 * @Author MissNull
 * @Description:
 * @Date: Created in 2017/7/27.
 */
public interface ParseString {
    List<String> doParse(HttpEntity str);
    void doParsePic(byte[] bytes);
    String pretreatment(String url);
}
