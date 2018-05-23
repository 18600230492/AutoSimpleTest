package com.hshc.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sun.corba.se.impl.protocol.giopmsgheaders.RequestMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * Created by andywu on 2018/3/19.
 */
public class HttpUtils {

    private static Log logger= LogFactory.getLog(HttpUtils.class);
    Charset charSet=Consts.UTF_8;

    public static HttpUtils getInstance(){

        return new HttpUtils();
    }

    //处理登陆保存cookie
    public CloseableHttpResponse postLogin(String url, Map<String, String> params,CloseableHttpClient httpclient,CookieStore cookieStore){
        UrlEncodedFormEntity entitys = getFormEntity(params);
        HttpPost httppost=new HttpPost(url);

//        if(cookieStore!=null){
//            httppost.setHeader("Cookie", "JSESSIONID=" + cookieStore.getCookies().get(0).getValue().trim());
////            System.out.println("cookieStore,不为空");
////            System.out.println("Cookie: JSESSIONID="+cookieStore.toString());
//        }
        httppost.setEntity(entitys);
        return HttpRequestMessages(httppost, httpclient);
    }

    public CloseableHttpResponse HttpRequestMessages(HttpUriRequest httpRequest ,CloseableHttpClient httpclient){

        CloseableHttpResponse response = null;
        try {


            logger.info("\n"+"***************************请求信息**********************************" + "\n"
                    + httpRequest.getRequestLine().toString() + "\n" +
                    "***************************请求结束**********************************"
            );

            response = httpclient.execute(httpRequest);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return  response;
    }

    //处理post请求，得到response内容
    public String post(String url, Map<String,String> params, CloseableHttpClient httpclient, CookieStore cookieStore){

        UrlEncodedFormEntity entitys=getFormEntity(params);//调用“参数进行封装方法”
        HttpPost httppost=new HttpPost(url);

        if (cookieStore!=null){
//            httppost.setHeader("Cookie","JSESSIONID="+cookieStore.getCookies().get(0).getValue().trim());
            System.out.println("Cookie: JSESSIONID="+cookieStore.getCookies().get(0).getValue().trim());
            System.out.println("Cookie: JSESSIONID="+cookieStore.toString());
        }
        httppost.setEntity(entitys);
        return RquestMessages(httppost,httpclient);//调用返回response方法
    }

    //处理put请求，得到response内容
    public String put(String url, Map<String,String> params, CloseableHttpClient httpclient, CookieStore cookieStore){
        UrlEncodedFormEntity entitys=getFormEntity(params);//调用“参数进行封装方法”
        HttpPut httpput=new HttpPut(url);
//        if(cookieStore!=null){
//            httpput.setHeader("Cookie", "JSESSIONID="+cookieStore.getCookies().get(0).getValue().trim());
//        }
        httpput.setEntity(entitys);
        return RquestMessages(httpput,httpclient);
    }

    //处理get请求，得到response内容
    public String get(String url, CloseableHttpClient httpclient, CookieStore cookieStore){

        HttpGet httpget=new HttpGet(url);
        return RquestMessages(httpget,httpclient);
    }

    //处理delete请求，得到response内容
    public String delete(String url, CloseableHttpClient httpclient, CookieStore cookieStore){

        HttpDelete httpdelete=new HttpDelete(url);
        return RquestMessages(httpdelete,httpclient);
    }


    //执行请求，返回response
    public String RquestMessages(HttpUriRequest httpRequest, CloseableHttpClient httpclient) {
        CloseableHttpResponse response=null;
        HttpEntity entity=null;
        //String result=null;
        try {
            logger.info("\n"+"***************************请求信息**********************************" + "\n"
                    + httpRequest.getRequestLine().toString() + "\n" +
                    "***************************请求结束**********************************");
            response=httpclient.execute(httpRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("返回的response="+response);
        return EntityResponse.printResponse(response);
    }

    //将参数进行封装
    public UrlEncodedFormEntity getFormEntity(Map<String, String> params) {
        List<BasicNameValuePair> formparams=new ArrayList<>();
        for (Map.Entry<String,String> entry:params.entrySet())
            formparams.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
        UrlEncodedFormEntity entitys=new UrlEncodedFormEntity(formparams,charSet);
        System.out.println("entitys的值"+entitys);
        return entitys;
    }
}
