package com.hshc.testCore;

import com.hshc.util.xmlUtil;
import org.testng.annotations.Test;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by andywu on 2018/3/22.
 */
public class TestReadXML {

    @Test
    public void  testReadXml(){
        HashMap<String,String> locatorMap=xmlUtil.readXMLDocument("GetCustomInfo");
        for(Map.Entry<String,String> entry:locatorMap.entrySet()){
            System.out.println("map"+entry.getKey()+","+entry.getValue());
        }
    }
}
