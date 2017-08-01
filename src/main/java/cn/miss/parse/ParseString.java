package cn.miss.parse;

import cn.miss.utils.CallBack;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @Author MissNull
 * @Description:
 * @Date: Created in 2017/7/27.
 */
public abstract class ParseString {

    protected CallBack callBack;

    public ParseString(CallBack callBack){
        this.callBack = callBack;
    }
    public ParseString(){

    }
    public void append(String msg){
        Optional.ofNullable(callBack).ifPresent((s)->s.append(msg));
    }

    public abstract List<String> doParse(CloseableHttpResponse response, CookieStore cookieStore);

    public abstract void pretreatment(String url, HttpRequestBase client);

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public CallBack getCallBack() {
        return callBack;
    }
}
