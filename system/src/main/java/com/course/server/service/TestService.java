package com.course.server.service;

import com.course.server.Dao.Test;
import com.course.server.mapper.TestMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author TangKe（唐柯）
 * @date 2022/5/19 - 4:11 下午
 */

@Service
public class TestService {

    @Resource
    private TestMapper testMapper;

    public List<Test> list(){
        return testMapper.list() ;
    }
}
