package com.aroominn.aroom.entity;

public class RespEntity {

    private int code;
    private String message;
    private Object data;

    public RespEntity(RespCode respCode) {
        this.code = respCode.getCode();
        this.message = respCode.getMessage();
    }

    public RespEntity(RespCode respCode, Object data) {
        this(respCode);
        this.data = data;
    }

    public enum RespCode {

        SUCCESS(000, "请求成功"),
        LIKE(2001, "点赞成功"),
        UNLIKE(2011, "点赞成功"),
        REPORT(2002, "举报成功"),
        FOLLOW(2003, "关注成功"),
        BLACKLIST(2004, "拉黑成功"),
        FORWARD(2005, "转发成功"),
        UNFOLLOW(2006, "取关成功"),
        FAILED(-1, "请求失败"),
        REGISTERED(1001, "已注册"),
        FAILTOKEN(1002, "token验证失败"),
        FAILVALID(1003, "验证码错误"),
        IMERROR(3001,"注册失败"),
        WARN(001, "网络异常，请稍后重试");
        private int code;
        private String message;

        RespCode(int code, String message) {
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
