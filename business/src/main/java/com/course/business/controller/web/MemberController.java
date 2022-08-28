package com.course.business.controller.web;

import com.alibaba.fastjson.JSON;
import com.course.server.dto.*;
import com.course.server.service.MemberService;
import com.course.server.util.UuidUtil;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
* @author TangKe（唐柯）
* @date
*/
@RestController("webMemberController")
@RequestMapping("/web/member")
public class MemberController {

private static final Logger LOG = LoggerFactory.getLogger(MemberController.class);
public static final String BUSINESS_NAME = "会员";

@Resource
private MemberService memberService;


@Resource(name = "redisTemplate")
private RedisTemplate redisTemplate;

/**
* 列表查询
*/
@PostMapping("/list")
public ResponseDto list(@RequestBody  PageDto pageDto){

LOG.info("pageDto:{}",pageDto);
ResponseDto responseDto = new ResponseDto();
memberService.list(pageDto);
responseDto.setContent(pageDto);
return responseDto;
}


/**
* 保存，id有值时更新，无值时新增
*/

@PostMapping("/register")
public ResponseDto register(@RequestBody MemberDto  memberDto){

LOG.info("MemberDto:{}",memberDto);

//保存校验
            ValidatorUtil.require(memberDto.getMobile(), "手机号");
            ValidatorUtil.length(memberDto.getMobile(), "手机号", 1, 11);
            ValidatorUtil.require(memberDto.getPassword(), "密码");
            ValidatorUtil.length(memberDto.getName(), "昵称", 1, 50);
            ValidatorUtil.length(memberDto.getPhoto(), "头像url", 1, 200);

            //密码加密
           memberDto.setPassword(DigestUtils.md5DigestAsHex(memberDto.getPassword().getBytes()));

ResponseDto responseDto = new ResponseDto();
memberService.save(memberDto);
responseDto.setContent(memberDto);
return responseDto;
}


/**
* 删除
*/
@DeleteMapping("/delete/{id}")
public ResponseDto delete(@PathVariable String id){


ResponseDto responseDto = new ResponseDto();
memberService.delete(id);

return  responseDto;
}

    /**
     * 登录
     */

    @PostMapping("/login")
    public ResponseDto login(@RequestBody MemberDto memberDto, HttpServletRequest request){


        LOG.info("用户登录开始");
        memberDto.setPassword(DigestUtils.md5DigestAsHex(memberDto.getPassword().getBytes()));
        ResponseDto responseDto = new ResponseDto();

        //根据验证码token去获取redis中的验证码
        String imageCode = (String) redisTemplate.opsForValue().get(memberDto.getImageCodeToken());
        LOG.info("从redis中获取到的验证码：{}", imageCode);
        if (StringUtils.isEmpty(imageCode)) {
            responseDto.setSuccess(false);
            responseDto.setMessage("验证码已过期");
            LOG.info("用户登录失败，验证码已过期");
            return responseDto;
        }
        if (!imageCode.toLowerCase().equals(memberDto.getImageCode().toLowerCase())) {
            responseDto.setSuccess(false);
            responseDto.setMessage("验证码不对");
            LOG.info("用户登录失败，验证码不对");
            return responseDto;
        } else {

            //移除redis中的验证码
            redisTemplate.delete(memberDto.getImageCodeToken());
        }

        LoginMemberDto loginUserDto = memberService.login(memberDto);
        String token = UuidUtil.getShortUuid();
        loginUserDto.setToken(token);

        redisTemplate.opsForValue().set(token, JSON.toJSONString(loginUserDto),3600, TimeUnit.SECONDS);
        request.getSession().setAttribute(constant.LOGIN_USER,loginUserDto);
        responseDto.setContent(loginUserDto);
        return responseDto;
    }


    /**
     * 退出登录
     */

    @GetMapping("/loginOut/{token}")
    public ResponseDto loginOut(@PathVariable String token){

        ResponseDto responseDto = new ResponseDto();

        //request.getSession().removeAttribute(constant.LOGIN_USER);

        //移除redis中的验证码
        redisTemplate.delete(token);

        LOG.info("从redis中时删除token:{}",token);
        return responseDto;


    }


}
