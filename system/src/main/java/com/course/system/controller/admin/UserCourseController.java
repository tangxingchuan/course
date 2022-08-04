package com.course.business.controller.admin;

import com.course.server.dto.UserCourseDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.exception.ValidatorException;
import com.course.server.service.UserCourseService;
import com.course.server.util.ValidatorUtil;
import org.apache.ibatis.annotations.Delete;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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
public ResponseDto list(@RequestBody  PageDto pageDto){

LOG.info("pageDto:{}",pageDto);
ResponseDto responseDto = new ResponseDto();
userCourseService.list(pageDto);
responseDto.setContent(pageDto);
return responseDto;
}


/**
* 保存，id有值时更新，无值时新增
*/

@PostMapping("/save")
public ResponseDto save(@RequestBody UserCourseDto  userCourseDto){

    userCourseDto.setPassword(DigestUtils.md5DigestAsHex(userCourseDto.getPassword().getBytes()));


    LOG.info("UserCourseDto:{}",userCourseDto);

//保存校验
            ValidatorUtil.require(userCourseDto.getLoginName(), "登录名");
            ValidatorUtil.length(userCourseDto.getLoginName(), "登录名", 1, 60);
            ValidatorUtil.length(userCourseDto.getName(), "昵称", 1, 50);
            ValidatorUtil.require(userCourseDto.getPassword(), "密码");
ResponseDto responseDto = new ResponseDto();
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
    public ResponseDto savePassword(@RequestBody UserCourseDto  userCourseDto){

        userCourseDto.setPassword(DigestUtils.md5DigestAsHex(userCourseDto.getPassword().getBytes()));


        LOG.info("UserCourseDto:{}",userCourseDto);
        ResponseDto responseDto = new ResponseDto();
        userCourseService.savePassword(userCourseDto);
        responseDto.setContent(userCourseDto);
        return responseDto;
    }


}
