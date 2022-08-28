package com.course.server.service;


import com.course.server.domain.Course;
import com.course.server.domain.CourseContent;
import com.course.server.domain.CourseExample;
import com.course.server.dto.*;
import com.course.server.enums.CourseStatusEnum;
import com.course.server.mapper.*;
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

    /*课程分类的业务层*/
    @Resource
    private CourseCategoryService courseCategoryService;

    @Resource
    private CourseContentMapper courseContentMapper;


    /*讲师的业务层*/
    @Resource
    private TeacherService teacherService;

    /*大章的业务层*/
    @Resource
    private ChapterService chapterService;

    /*小节的业务层*/
    @Resource
    private SectionService sectionService;





    /**
     * 列表查询
     */
    public void list(CoursePageDto coursePageDto) {
        PageHelper.startPage(coursePageDto.getPage(), coursePageDto.getSize());
        List<CourseDto> courseDtoList = myCourseMapper.list(coursePageDto);
        PageInfo<CourseDto> pageInfo = new PageInfo<>(courseDtoList);
        coursePageDto.setTotal(pageInfo.getTotal());
        coursePageDto.setList(pageInfo.getList());


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


    /**
     * 查找某一课程，供web模块用，只能查已发布的
     * @param id
     * @return
     */
    public CourseDto findCourse(String id) {
        Course course = courseMapper.selectByPrimaryKey(id);
        if (course == null || !CourseStatusEnum.PUBLISH.getCode().equals(course.getStatus())) {
            return null;
        }
        CourseDto courseDto = CopyUtil.copy(course, CourseDto.class);

        // 查询内容
        CourseContent content = courseContentMapper.selectByPrimaryKey(id);
        if (content != null) {
            courseDto.setContent(content.getContent());
        }

        // 查找讲师信息
        TeacherDto teacherDto = teacherService.findId(courseDto.getTeacherId());
        courseDto.setTeacher(teacherDto);

        // 查找章信息
        List<ChapterDto> chapterDtoList = chapterService.listByCourse(id);
        courseDto.setChapters(chapterDtoList);

        // 查找节信息
        List<SectionDto> sectionDtoList = sectionService.listByCourse(id);
        courseDto.setSections(sectionDtoList);

        return courseDto;
    }

}
