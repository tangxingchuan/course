package com.course.server.util;


import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.tea.*;
import com.google.gson.Gson;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

public class SmsUtil {


    @Resource
   static RedisTemplate redisTemplate;

    public static String getCode() {

        String code = String.valueOf((int) (((Math.random() * 9) + 1) * 100000));
        //将验证码的session放到redis中，共享
        redisTemplate.opsForValue().set(TimeUnit.SECONDS, code, 300);

        return code + "";
    }



    public static com.aliyun.dysmsapi20170525.Client createClient(String accessKeyId, String accessKeySecret) throws Exception {


        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config()
                // 您的 AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 您的 AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new com.aliyun.dysmsapi20170525.Client(config);
    }

    public static String sendSms2(String phone) throws Exception {
        String code = SmsUtil.getCode();

        //这里的accessKeyId需要我们填入刚刚添加的AccessKey的账号，后面那个参数为密码
        Client client = SmsUtil.createClient("LTAI5tSJB9HEvafUvgKcL42S", "C7mZW9adaOf1L3eRM51qZRq11REhj1");
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                //短信的签名,改成自己申请的签名
                .setSignName("阿里云短信测试")
                //短信的模板码,改成自己申请的模板码
                .setTemplateCode("SMS_154950909")
                //收短信的手机号，接收前台传来的手机号。
                .setPhoneNumbers(phone)
                //code后面的值为验证码，code的值只支持4-6位纯数字
                .setTemplateParam("{\"code\":" + code + "}");
        SendSmsResponse res = client.sendSms(sendSmsRequest);

        //返回响应的值
        return new Gson().toJson(res.body);
    }
}