package com.course.server.mapper.my;

import org.apache.ibatis.annotations.Param;

/**
 * @author TangKe（唐柯）
 * @date 2022/6/4 - 11:07 下午
 */
public interface MyCourseMapper {

    int updateTime(@Param("courseId") String courseId);

}
