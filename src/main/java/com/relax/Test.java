package com.relax;

import org.apache.http.protocol.HTTP;
import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) throws Exception {

        String timestamp = String.valueOf(System.currentTimeMillis());
        String tmp=String.valueOf(16551)+timestamp+"209903b0-5f92-4759-9ea9-8723124f8c9e";
        BASE64Encoder base64Encoder = new BASE64Encoder();
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        String signature = base64Encoder.encode(md5.digest(tmp.getBytes("utf-8"))).toUpperCase();
        Map head=new HashMap();
        head.put(HTTP.CONTENT_TYPE,"application/json");
        head.put("timestamp",timestamp);
        head.put("signature",signature);

        System.out.println("timestamp:"+timestamp);
        System.out.println("signature:"+signature);
        Map param = new HashMap();
        param.put("supplierID",16551);

        Map requestor = new HashMap();
        requestor.put("invoker","ZS");
        requestor.put("operatorName","ZS");
        requestor.put("opClientIP","127.0.0.1");
        requestor.put("userId",1);
        requestor.put("langurageType","CN");

        param.put("requestor",requestor);


        String post = HttpUtil.post("http://m.fat.ctripqa.com/restapi/soa2/13353/getdltcountrylist", head, param);
        System.out.println(post);
    }
}
