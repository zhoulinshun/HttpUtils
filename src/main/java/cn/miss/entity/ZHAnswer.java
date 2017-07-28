package cn.miss.entity;

import java.util.List;

/**
 * @Author MissNull
 * @Description:
 * @Date: Created in 2017/7/27.
 */
public class ZHAnswer {
    public Paging paging;
    public List<Data> data;

    public boolean isEmpty() {
        return paging == null || data == null;
    }

    public static class Paging {
        public String next;
        public int totals;
        public boolean is_end;
    }

    public static class Data {
        public String content;
        public Question question;
    }
    public static class Question{
        public String title;
    }
}

