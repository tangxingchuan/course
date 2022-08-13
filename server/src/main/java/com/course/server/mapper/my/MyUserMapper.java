package com.course.server.mapper.my;

import com.course.server.dto.ResourceDto;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

/**
 * @author TangKe（唐柯）
 * @date 2022/8/13 - 12:27 下午
 */
public interface MyUserMapper {


    List<ResourceDto> findResources(@Param("userId") String userId);
}
