package com.course.server.service;


import com.course.server.domain.Course;
import com.course.server.domain.CourseExample;
import com.course.server.dto.CourseDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.CourseCategoryMapper;
import com.course.server.mapper.CourseMapper;
import com.course.server.mapper.my.MyCourseMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
* @author TangKe（唐柯）
* @date 2022/5/19 - 4:11 下午
*/

@Service
public class CourseService {

    //日志
    private  static  final Logger LOG = LoggerFactory.getLogger(CourseService.class);


    @Resource
    private CourseMapper courseMapper;



    @Resource
    private MyCourseMapper myCourseMapper;


    @Resource
    private CourseCategoryService courseCategoryService;


/**
* 列表查询
*/
public void list(PageDto pageDto) {
PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
CourseExample courseExample = new CourseExample();
CourseExample.Criteria criteria = courseExample.createCriteria();

List<Course> courseList = courseMapper.selectByExample(courseExample);
    PageInfo<Course> pageInfo = new PageInfo<>(courseList);
        pageDto.setTotal(pageInfo.getTotal());

        List<CourseDto> courseDtoList = CopyUtil.copyList(courseList, CourseDto.class);
            pageDto.setList(courseDtoList);


            }

            /**
            * 保存
            */
            public void save(CourseDto  courseDto) {

            Course course = CopyUtil.copy(courseDto,Course.class);

            if (StringUtils.isEmpty(courseDto.getId())){
            this.insert(course);

            }else {
            this.update(course);
            }
            //批量保存课程分类
                courseCategoryService.saveBatch(courseDto.getId(),courseDto.getCategorys());


            }



            /**
            * 新增
            */
            private void insert(Course  course) {
            course.setId(UuidUtil.getShortUuid());
            courseMapper.insert(course);

            }


            /**
            *修改
            */
            private void update(Course  course) {
            courseMapper.updateByPrimaryKey(course);

            }

            /**
            *根据id删除
            */
            public void delete(String id) {
            courseMapper.deleteByPrimaryKey(id);

            }

    /**
     * 更新课程时长
     * @param courseId
     */
           public void updateTime(String courseId){

                LOG.info("更新课程时长：{}",courseId);
                myCourseMapper.updateTime(courseId);
            }


            }