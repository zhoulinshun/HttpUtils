package cn.miss.entity;

import java.util.List;
import java.util.Map;

/**
 * @Author MissNull
 * @Description:
 * @Date: Created in 2017/7/26.
 */
public class HttpEntity {
    private String url;
    private String method;
    private List<Map<String,String>> param;
    private Map<String,String> header;

    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }

    public List<Map<String, String>> getParam() {
        return param;
    }

    public void setParam(List<Map<String, String>> param) {
        this.param = param;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
