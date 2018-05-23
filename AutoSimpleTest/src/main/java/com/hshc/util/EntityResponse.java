package com.hshc.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by andywu on 2018/3/20.
 */
public class EntityResponse {

//    private static Log logger = LogFactory.getLog(EntityResponse.class);

    public static String printResponse(CloseableHttpResponse httpResponse)
    {
        String responseString = null;
        HttpEntity entity=httpResponse.getEntity();

        if (entity!=null)
            try {
                responseString = EntityUtils.toString(entity, "utf-8");
             } catch (IOException e) {
                e.printStackTrace();
            }
        return responseString;
    }

}
