package com.course.business.controller.web;

import com.course.server.dto.*;
import com.course.server.enums.CourseStatusEnum;
import com.course.server.service.CourseCategoryService;
import com.course.server.service.CourseService;
import com.course.server.util.ValidatorUtil;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import sun.jvm.hotspot.debugger.Page;

import javax.annotation.Resource;
import java.util.List;

/**
* @author TangKe（唐柯）
* @date
*/
@RestController("webCourseController")
@RequestMapping("/web/course")
public class CourseController {

private static final Logger LOG = LoggerFactory.getLogger(CourseController.class);
public static final String BUSINESS_NAME = "课程";

    @Resource
    private CourseService courseService;


    /**
     * 新上好课查询
     * @return
     */
    @GetMapping("/list-new")
    public ResponseDto listNew(){

        PageDto pageDto = new PageDto();
        pageDto.setPage(1);
        pageDto.setSize(3);
        ResponseDto responseDto = new ResponseDto();
        List<CourseDto> courseDtos = courseService.listNew(pageDto);
        responseDto.setContent(courseDtos);
        return responseDto;
    }


    /**
     * 全部查询课程
     *
     * @return
     */
    @GetMapping("/list")
    public ResponseDto listCourse(CoursePageDto coursePageDto) {

        ResponseDto responseDto = new ResponseDto();
        coursePageDto.setStatus(CourseStatusEnum.PUBLISH.getCode());
        courseService.list(coursePageDto);
        responseDto.setContent(coursePageDto);
        return responseDto;
    }

}
