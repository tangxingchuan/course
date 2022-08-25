package com.course.business.controller.web;

import com.course.server.dto.MemberDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.MemberService;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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


}