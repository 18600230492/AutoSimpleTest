package com.hshc.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by andywu on 2018/3/22.
 */
public class xmlUtil {

    public static HashMap<String,String> readXMLDocument(String beanName){

        String path=Contants.path;
        HashMap<String,String> locatorMap=new HashMap<>();
        File file=new File(path);
        //下面可以加一个判断file文件不存在的异常处理
        if (!file.exists()){
            try {
                throw new IOException("Can't find"+ path);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        SAXReader saxReader = new SAXReader();
        Document document;

        try {
            document=saxReader.read(file);
            for (Iterator iter=document.getRootElement().elementIterator();iter.hasNext();){
                Element element= (Element) iter.next();

                if (element.attributeValue("beanName").equalsIgnoreCase(beanName)){

                    System.out.println("=====3"+element.attributeValue("beanName"));

                    for (Iterator iter1=element.elementIterator();iter1.hasNext();){
                        Element e2= (Element) iter1.next();
                        String elementName=e2.attributeValue("name").toString();
                        String elementValue=e2.attributeValue("value").toString();
                        System.out.println(element+","+elementValue);
                        locatorMap.put(elementName,elementValue);
                    }
                    return locatorMap;
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return locatorMap;
    }
}
