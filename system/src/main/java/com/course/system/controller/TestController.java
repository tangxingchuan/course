package com.course.system.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author TangKe（唐柯）
 * @date 2022/5/18 - 2:38 上午
 */
@RestController
public class TestController {


    @RequestMapping("/test")
    public String test(){

        return "success";
    }




}
