package com.course.server.mapper.my;

import com.course.server.dto.SortDto;
import org.apache.ibatis.annotations.Param;

/**
 * @author TangKe（唐柯）
 * @date 2022/6/4 - 11:07 下午
 */
public interface MyCourseMapper {

    int updateTime(@Param("courseId") String courseId);

    int updateSort(SortDto sortDto);

    int moveSortsBackward(SortDto sortDto);

    int moveSortsForward(SortDto sortDto);

}
