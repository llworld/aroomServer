package com.aroominn.aroom.utils.ali;

import com.aliyuncs.utils.Base64Helper;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class ALiUtils {

    public void getSigna() {


        try {
            Map<String, String> param = new HashMap<String, String>();
            param.put("AccessKeyId", "testid");
            param.put("Action", "SingleSendSms");
            param.put("ParamString", "{\"name\":\"d\",\"name1\":\"d\"}");
            param.put("RecNum", "13098765432");
            param.put("RegionId", "cn-hangzhou");
            param.put("SignName", "标签测试");
            param.put("SignatureMethod", "HMAC-SHA1");
            param.put("SignatureNonce", "9e030f6b-03a2-40f0-a6ba-157d44532fd0");
            param.put("SignatureVersion", "1.0");
            param.put("TemplateCode", "SMS_1650053");
            param.put("Timestamp", "2016-10-20T05:37:52Z");
            param.put("Version", "2016-09-27");
            param.put("Format", "XML");
            //构造规范化请求字符串Canonicalized Query String
            StringBuffer cqs = new StringBuffer();
//         按照参数名称的字典顺序对请求中所有的请求参数进行排序。(不包括签名字符串参数)
            String[] keyArray = (String[]) param.keySet().toArray(new String[]{});
            Arrays.sort(keyArray);
//         对每个请求参数的名称和值进行编码。名称和值要使用 UTF-8 字符集进行 URL编码
//         一般支持 URL 编码的库（比如 Java 中的 java.net.URLEncoder）
//         都是按照“application/x-www-form-urlencoded”的MIME类型的规则进行编码的。实现时可以直接使用这类方式进行编码，
//         把编码后的字符串中加号（+）替换成%20、星号（*）替换成%2A、%7E 替换回波浪号（~）

//         对编码后的参数名称和值使用英文等号（=）进行连接。
//         再把英文等号连接得到的字符串按参数名称的字典顺序依次使用&符号连接，即得到规范化请求字符串。
            for (int i = 0; i < keyArray.length; i++) {
                cqs.append(percentEcoding(keyArray[i])).append("=")
                        .append(percentEcoding(param.get(keyArray[i])));
                if (i < keyArray.length - 1) {
                    cqs.append("&");
                }
            }
//       使用上一步构造的规范化字符串按照下面的规则构造用于计算签名的字符串:
            StringBuffer StringToSign = new StringBuffer();
            StringToSign.append("POST").append("&").append(percentEcoding("/")).append("&").append(percentEcoding(cqs.toString()));
            //打印StringToSign
            System.out.println("\n" + StringToSign.toString());

//       按照 RFC2104 的定义，使用上面的用于签名的字符串计算签名 HMAC 值。
//       注意：计算签名时使用的 Key 就是用户持有的 Access Key Secret 并加上一个“&”字符(ASCII:38)，使用的哈希算法是 SHA1。
            String signKey = "testsecret&";
            String dig = "HmacSHA1";
            Mac mac = Mac.getInstance(dig);
            mac.init(new SecretKeySpec(signKey.getBytes("utf-8"), dig));
            byte[] doFinal = mac.doFinal(StringToSign.toString().getBytes("utf-8"));
//     按照 Base64 编码规则把上面的 HMAC 值编码成字符串，即得到签名值（Signature）。
            String Signature = Base64Helper.encode(doFinal);//阿里云的SDK提供的工具类
            //打印Signature
            System.out.println("\n " + Signature);

//     注意：得到的签名值在作为最后的请求参数值提交给 DirectMail 服务器的时候，要和其他参数一样，按照 RFC3986 的规则进行 URL 编码）。
            System.out.println("\n ecode " + percentEcoding(Signature));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
    }


    public String percentEcoding(String value) throws UnsupportedEncodingException {
        return value != null ? URLEncoder.encode(value, "utf-8").replace("+", "%20").replace("*", "%2A")
                .replace("%7E", "~") : null;
    }

}
