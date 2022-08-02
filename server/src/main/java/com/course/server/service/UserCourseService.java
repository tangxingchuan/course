package com.course.server.service;

import com.course.server.domain.UserCourse;
import com.course.server.domain.UserCourseExample;
import com.course.server.dto.UserCourseDto;
import com.course.server.dto.PageDto;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.UserCourseMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserCourseService {

@Resource
private UserCourseMapper userCourseMapper;

/**
* 列表查询
*/
public void list(PageDto pageDto) {
PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
UserCourseExample userCourseExample = new UserCourseExample();
List<UserCourse> userCourseList = userCourseMapper.selectByExample(userCourseExample);
PageInfo<UserCourse> pageInfo = new PageInfo<>(userCourseList);
pageDto.setTotal(pageInfo.getTotal());
List<UserCourseDto> userCourseDtoList = CopyUtil.copyList(userCourseList, UserCourseDto.class);
    pageDto.setList(userCourseDtoList);
    }

    /**
    * 保存，id有值时更新，无值时新增
    */
    public void save(UserCourseDto userCourseDto) {
    UserCourse userCourse = CopyUtil.copy(userCourseDto, UserCourse.class);
    if (StringUtils.isEmpty(userCourseDto.getId())) {
    this.insert(userCourse);
    } else {
    this.update(userCourse);
    }
    }

    /**
    * 新增
    */
    private void insert(UserCourse userCourse) {
    userCourse.setId(UuidUtil.getShortUuid());
        UserCourse userDb = this.selectByLoginName(userCourse.getLoginName());
        if(userDb != null){

            throw new BusinessException(BusinessExceptionCode.MOBILE_CODE_ERROR);

        }




        userCourseMapper.insert(userCourse);
    }

    /**
    * 更新
    */
    private void update(UserCourse userCourse) {
    userCourseMapper.updateByPrimaryKey(userCourse);
    }

    /**
    * 删除
    */
    public void delete(String id) {
    userCourseMapper.deleteByPrimaryKey(id);
    }


    /**
     * 根据登录名查询用户信息
     * @param loginName
     * @return
     */
    public UserCourse selectByLoginName(String loginName){

        UserCourseExample user = new UserCourseExample();
        user.createCriteria().andLoginNameEqualTo(loginName);
        List<UserCourse> userCoursesList = userCourseMapper.selectByExample(user);
        if(CollectionUtils.isEmpty(userCoursesList)){

            return  null;
        }else {

            return userCoursesList.get(0);
        }


    }


    }


