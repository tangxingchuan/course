package com.course.business.controller.web;

import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.dto.SmsDto;
import com.course.server.service.SmsService;
import com.course.server.util.SmsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
* @author TangKe（唐柯）
* @date
*/
@RestController("webSmsController")
@RequestMapping("/web/sms")
public class SmsController {



     @Resource
     private RedisTemplate redisTemplate;

private static final Logger LOG = LoggerFactory.getLogger(SmsController.class);
public static final String BUSINESS_NAME = "短信验证码";

@Resource
private SmsService smsService;


    /**
* 发送短信接口
*/
@RequestMapping(value="/send" , method = RequestMethod.POST)
public ResponseDto send(@RequestBody SmsDto smsDto){

     LOG.info("请求发送短信开始:{}",smsDto);
     ResponseDto responseDto = new ResponseDto();



//根据验证码token去获取redis中的验证码
     String imageCode = (String) redisTemplate.opsForValue().get(smsDto.getCode());

     LOG.info("从redis中获取到的验证码：{}", imageCode);
     if (StringUtils.isEmpty(imageCode)) {
          responseDto.setSuccess(false);
          responseDto.setMessage("验证码已过期");
          LOG.info("用户登录失败，验证码已过期");
          return responseDto;
     }
     if (!imageCode.toLowerCase().equals(smsDto.getCode().toLowerCase())) {
          responseDto.setSuccess(false);
          responseDto.setMessage("验证码不对");
          LOG.info("用户登录失败，验证码不对");
          return responseDto;
     } else {

          //移除redis中的验证码
          redisTemplate.delete(smsDto.getCode());
     }

     try {
          String phone = SmsUtil.sendSms2(smsDto.getMobile());
          System.out.println(phone);
     } catch (Exception e) {
          e.printStackTrace();
     }
     LOG.info("请求发送短信结束:{}",responseDto);
     return responseDto;
}


}
