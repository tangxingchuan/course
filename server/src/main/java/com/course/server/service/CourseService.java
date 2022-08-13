package com.course.server.service;


import com.course.server.domain.Course;
import com.course.server.domain.CourseContent;
import com.course.server.domain.CourseExample;
import com.course.server.dto.CourseContentDto;
import com.course.server.dto.CourseDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.SortDto;
import com.course.server.enums.CourseStatusEnum;
import com.course.server.mapper.CourseCategoryMapper;
import com.course.server.mapper.CourseContentMapper;
import com.course.server.mapper.CourseMapper;
import com.course.server.mapper.my.MyCourseMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
* @author TangKe（唐柯）
* @date 2022/5/19 - 4:11 下午
*/

@Service   //课程
public class CourseService {

    //日志
    private  static  final Logger LOG = LoggerFactory.getLogger(CourseService.class);

    @Resource
    private CourseMapper courseMapper;



    @Resource
    private MyCourseMapper myCourseMapper;


    @Resource
    private CourseCategoryService courseCategoryService;

    @Resource
    private CourseContentMapper courseContentMapper;



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
     * 按照提交时间，查询最近提交的三个课程
     * @param pageDto
     * @return
     */
       public List<CourseDto>  listNew(PageDto pageDto){

           PageHelper.startPage(pageDto.getPage(),pageDto.getSize());

           CourseExample example = new CourseExample();
           example.createCriteria().andStatusEqualTo(CourseStatusEnum.PUBLISH.getCode());
           example.setOrderByClause("created_at desc");
           List<Course> courseList = courseMapper.selectByExample(example);
           return CopyUtil.copyList(courseList,CourseDto.class);

       }




            /**
            * 保存,id有值时更新，无值时新增
            */
            @Transactional
            public void save(CourseDto  courseDto) {

            Course course = CopyUtil.copy(courseDto,Course.class);

            if (StringUtils.isEmpty(courseDto.getId())){
            this.insert(course);

            }else {
            this.update(course);
            }
            //批量保存课程分类 CourseCategoryService的saveBatch

                courseCategoryService.saveBatch(course.getId(),courseDto.getCategorys());


            }



            /**
            * 新增
            */
            private void insert(Course  course) {
                Date now = new Date();
                course.setCreatedAt(now);
                course.setUpdatedAt(now);
                course.setId(UuidUtil.getShortUuid());
            courseMapper.insert(course);

            }


            /**
            *更新
            */
            private void update(Course  course) {
                course.setUpdatedAt(new Date());
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


      /*
      *
      * 查找课程内容
      * */
      public CourseContentDto findContent(String id){
        CourseContent content= courseContentMapper.selectByPrimaryKey(id);

        if (content == null){
            return null;
        }

        return CopyUtil.copy(content,CourseContentDto.class);
    }


    /*
    * 保存课程内容，包含新增和修改
    *
    * */
       public int saveContent(CourseContentDto contentDto){

           CourseContent content = CopyUtil.copy(contentDto, CourseContent.class);

           int i = courseContentMapper.updateByPrimaryKeyWithBLOBs(content);
           if (i == 0){

               i = courseContentMapper.insert(content);
           }

           return i;
       }

    /**
      * 排序
      * @param sortDto
      */
     @Transactional
     public void sort(SortDto sortDto) {

         // 修改当前记录的排序值
               myCourseMapper.updateSort(sortDto);

          // 如果排序值变大
         if (sortDto.getNewSort() > sortDto.getOldSort()) {
             myCourseMapper.moveSortsForward(sortDto);


         }
           // 如果排序值变小
          if (sortDto.getNewSort() < sortDto.getOldSort()) {
             myCourseMapper.moveSortsBackward(sortDto);

          }

     }

}
