package com.hshc.testCore;

import com.hshc.bean.MessResponse;
import com.hshc.util.*;
import com.jayway.jsonpath.JsonPath;
import org.apache.commons.collections.bag.SynchronizedSortedBag;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

import static com.hshc.util.EntityResponse.printResponse;

/**
 * Created by andywu on 2018/3/19.
 */
public class TestRun {

    private static CloseableHttpClient httpClient;
    private static CookieStore cookieStore;
    //static CookieUtil cookieUtil=new CookieUtil() ;
    HttpUtils httpUtils= HttpUtils.getInstance();
    static {
        System.out.println("静态代码块开始执行");
        cookieStore=new BasicCookieStore();
        //将CookieStore设置到httpClient中
        httpClient= HttpClients.custom().setDefaultCookieStore(cookieStore).build();
    }

    @BeforeClass
    public void test(){
        System.out.println("程序开始运行");
    }

    @Test
    public void LoginTest(){
        String url="http://www.hibug.cn/api/ebugs/member/login.do";
        HashMap<String,String> params=xmlUtil.readXMLDocument("LoginParams");
        //处理登陆操作
        CloseableHttpResponse httpResponse=httpUtils.postLogin(url,params,httpClient,cookieStore);
        System.out.println("httpResponse的值===============");
        System.out.println(httpResponse);

//        System.out.println("---------Post返回值----------");
//        System.out.println(postresponse);
//        int result = JsonPath.parse(postresponse).read("$.result");
//        String message=JsonPath.parse(postresponse).read("$.message");
//        Assert.assertEquals(1,result);
//        Assert.assertEquals("登录成功",message);
        cookieStore=CookieUtil.setCookieStore(httpResponse);
    }

    @Test
    public void girlAdd() throws Exception {
//        String url="http://localhost:8080/girlAdd";
//        Map<String, String> params=new HashMap<>();
//        params.put("age", "50");
//        params.put("cupSize", "C");
//        params.put("money", "99");

        String url=Contants.url+"girlAdd";
        HashMap<String,String> params=xmlUtil.readXMLDocument("AddParams");

//        String url=Contants.urlp+"/itfer/addr/add.do";
//        HashMap<String,String> params=xmlUtil.readXMLDocument("add");

//        CloseableHttpResponse httpResponse=httpUtils.post(url,params,httpClient,cookieStore);
//        String postresponse= EntityResponse.printResponse(httpResponse);

        //之间返回字符串
        String postresponse=httpUtils.post(url,params,httpClient,cookieStore);

        System.out.println("---------Post返回值----------");
        System.out.println(postresponse);
//        System.out.println("解析后："+ JsonPath.parse(postresponse).read("$..code")); //查找所有，返回的是一个list集合
        System.out.println("解析后："+ JsonPath.parse(postresponse).read("$.code"));
        System.out.println("解析后："+ JsonPath.parse(postresponse).read("$.msg"));
        System.out.println("解析后："+ JsonPath.parse(postresponse).read("$.data.cupSize"));
        List<Object> list = JsonPath.parse(postresponse).read("$..code");
        Assert.assertEquals(0, list.get(0));
        int code = JsonPath.parse(postresponse).read("$.code");
        String msg=JsonPath.parse(postresponse).read("$.msg");
//        Assert.assertEquals(200,code);
        Assert.assertEquals("成功",msg);

//        MessResponse messResponse=new MessResponseUtil().setMessResponse(httpResponse);
//        Assert.assertEquals(200,messResponse.getStatusCode());
    }

    @Test
    public void girlGetOne(){
        String url=Contants.url+"girls/3";

        //之间返回字符串
        String postresponse=httpUtils.get(url,httpClient,cookieStore);

        System.out.println("---------Get返回值----------");
        System.out.println(postresponse);
        System.out.println("解析后："+JsonPath.parse(postresponse).read("$.id"));
        Assert.assertEquals(3,JsonPath.parse(postresponse).read("$.id"));
    }

    @Test
    public void girlPutOne(){
        String url=Contants.url+"girls/3";
        HashMap<String,String> params=xmlUtil.readXMLDocument("PutOneParams");
        String postresponse=httpUtils.put(url,params,httpClient,cookieStore);
        System.out.println("---------Put返回值----------");
        System.out.println(postresponse);
        System.out.println("解析后："+JsonPath.parse(postresponse).read("$.money"));
        Assert.assertEquals(192,JsonPath.parse(postresponse).read("$.money"));
    }

    @Test
    public void girlDeleteOne(){
        String url=Contants.url+"girls/9";
        String postresponse=httpUtils.delete(url,httpClient,cookieStore);
        System.out.println("---------Delete返回值----------");
        System.out.println(postresponse);
        System.out.println("解析后："+JsonPath.parse(postresponse).read("$.code"));
        System.out.println("解析后："+JsonPath.parse(postresponse).read("$.msg"));
        Assert.assertEquals(0,JsonPath.parse(postresponse).read("$.code"));
        Assert.assertEquals("成功",JsonPath.parse(postresponse).read("$.msg"));
    }

    @AfterSuite
    public void closeClient(){
        System.out.println("测试结束，准备关闭资源");
        //关闭并释放流资源
        try {
            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
