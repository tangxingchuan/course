package com.course.controller.admin;

import com.course.server.dto.ChapterDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.exception.ValidatorException;
import com.course.server.service.ChapterService;
import com.course.server.util.ValidatorUtil;
import org.apache.ibatis.annotations.Delete;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author TangKe（唐柯）
 * @date 2022/5/18 - 2:38 上午
 */
@RestController
@RequestMapping("/admin/chapter")
public class ChapterController {

    private static final Logger LOG = LoggerFactory.getLogger(ChapterController.class);
    public static final String BUSINESS_NAME = "大章";

    @Resource
    private ChapterService chapterService;


    /**
     * 列表查询
     */
    @PostMapping("/list")
    public ResponseDto list(@RequestBody  PageDto pageDto){

            LOG.info("pageDto:{}",pageDto);
            ResponseDto responseDto = new ResponseDto();
            chapterService.list(pageDto);
            responseDto.setContent(pageDto);
            return responseDto;
    }


    /**
     * 保存，id有值时更新，无值时新增
     */

    @PostMapping("/save")
    public ResponseDto list(@RequestBody ChapterDto  chapterDto){

        LOG.info("ChapterDto:{}",chapterDto);

            //保存校验
            ValidatorUtil.require(chapterDto.getName(),"名称");
            ValidatorUtil.require(chapterDto.getCourseId(),"课程ID");
            ValidatorUtil.length(chapterDto.getCourseId(),"课程ID",1,10);
            ResponseDto responseDto = new ResponseDto();
            chapterService.save(chapterDto);
            responseDto.setContent(chapterDto);
            return  responseDto;
    }


    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id){


            ResponseDto responseDto = new ResponseDto();
            chapterService.delete(id);

            return  responseDto;
    }


}
