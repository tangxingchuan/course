package com.course.controller;

import com.course.server.Dao.Test;
import com.course.server.service.TestService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author TangKe（唐柯）
 * @date 2022/5/18 - 2:38 上午
 */
@RestController
public class TestController {

    @Resource
    private TestService testService;

    @RequestMapping("/test")
    public List<Test> test(){

        return testService.list();
    }




}
