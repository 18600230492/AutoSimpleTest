package com.hshc.bean;

/**
 * 返回信息
 * Created by andywu on 2018/3/19.
 */

public class MessResponse {
   // public String status;
    public int statusCode;
//    public String contentType;
    public String body;
//    public String url;
//    public String method;


    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
