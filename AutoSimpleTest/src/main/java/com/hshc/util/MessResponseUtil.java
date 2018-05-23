package com.hshc.util;

import com.hshc.bean.MessResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by andywu on 2018/3/20.
 */
public class MessResponseUtil {

    //private static Log logger= LogFactory.getLog(MessResponseUtil.class);

    public MessResponse setMessResponse(CloseableHttpResponse httpResponse){
        MessResponse messResponse=new MessResponse();

        HttpEntity entity=httpResponse.getEntity();
        if (entity!=null){
            try {
                String responseString=EntityUtils.toString(entity,"utf-8");
                String rs=responseString.replace("\r\n","");

               // messResponse.setStatus(httpResponse.getStatusLine().getReasonPhrase());
                messResponse.setStatusCode(httpResponse.getStatusLine().getStatusCode());
                messResponse.setBody(rs);

//                logger.info("\n" +"***************************返回开始**********************************" + "\n" +
//                        httpResponse.getStatusLine().getReasonPhrase() + "\n" +
//                        Integer.toString(httpResponse.getStatusLine().getStatusCode()) + "\n" +
//                        "Context" + rs + "\n" +
//                        "***************************返回结束**********************************");
               // System.out.println("Status="+httpResponse.getStatusLine().getReasonPhrase());
                System.out.println("Code="+Integer.toString(httpResponse.getStatusLine().getStatusCode()));
                System.out.println("rs="+rs);
                //打印头信息
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return messResponse;
    }
}
