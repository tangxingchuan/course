package com.course.business.controller.admin;

import com.course.server.dto.*;
import com.course.server.service.UserCourseService;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
* @author TangKe（唐柯）
* @date
*/
@RestController
@RequestMapping("/admin/userCourse")
public class UserCourseController {

private static final Logger LOG = LoggerFactory.getLogger(UserCourseController.class);
public static final String BUSINESS_NAME = "用户";

@Resource
private UserCourseService userCourseService;


/**
* 列表查询
*/
@PostMapping("/list")
public ResponseDto<PageDto> list(@RequestBody  PageDto pageDto){

LOG.info("pageDto:{}",pageDto);
ResponseDto<PageDto> responseDto = new ResponseDto<PageDto>();
userCourseService.list(pageDto);
responseDto.setContent(pageDto);
return responseDto;
}


/**
* 保存，id有值时更新，无值时新增
*/

@PostMapping("/save")
public ResponseDto<UserCourseDto> save(@RequestBody UserCourseDto  userCourseDto){

    userCourseDto.setPassword(DigestUtils.md5DigestAsHex(userCourseDto.getPassword().getBytes()));


    LOG.info("UserCourseDto:{}",userCourseDto);

//保存校验
            ValidatorUtil.require(userCourseDto.getLoginName(), "登录名");
            ValidatorUtil.length(userCourseDto.getLoginName(), "登录名", 1, 60);
            ValidatorUtil.length(userCourseDto.getName(), "昵称", 1, 50);
            ValidatorUtil.require(userCourseDto.getPassword(), "密码");
ResponseDto<UserCourseDto> responseDto = new ResponseDto<UserCourseDto>();
userCourseService.save(userCourseDto);
responseDto.setContent(userCourseDto);
return responseDto;
}


/**
* 删除
*/
@DeleteMapping("/delete/{id}")
public ResponseDto delete(@PathVariable String id){


ResponseDto responseDto = new ResponseDto();
userCourseService.delete(id);

return  responseDto;
}


    /**
     * 保存密码
     */

    @PostMapping("/save-password")
    public ResponseDto<UserCourseDto> savePassword(@RequestBody UserCourseDto  userCourseDto){

        LOG.info("UserCourseDto:{}",userCourseDto);
        userCourseDto.setPassword(DigestUtils.md5DigestAsHex(userCourseDto.getPassword().getBytes()));
        ResponseDto<UserCourseDto> responseDto = new ResponseDto<>();
        userCourseService.savePassword(userCourseDto);
        responseDto.setContent(userCourseDto);
        return responseDto;
    }


    /**
     * 重置密码
     */

    @PostMapping("/login")
    public ResponseDto login(@RequestBody UserCourseDto  userCourseDto, HttpServletRequest request){

        userCourseDto.setPassword(DigestUtils.md5DigestAsHex(userCourseDto.getPassword().getBytes()));
        ResponseDto responseDto = new ResponseDto();
        LoginUserCourseDto loginUserDto = userCourseService.login(userCourseDto);
        request.getSession().setAttribute(constant.LOGIN_USER,loginUserDto);
        responseDto.setContent(loginUserDto);
        return responseDto;
    }



    /**
     * 退出登录
     */

    @GetMapping("/loginOut")
    public ResponseDto loginOut( HttpServletRequest request){

        ResponseDto responseDto = new ResponseDto();
        request.getSession().removeAttribute(constant.LOGIN_USER);
        return responseDto;


    }

}
