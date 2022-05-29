package com.course.business.controller.admin;

import com.course.server.dto.SectionDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.exception.ValidatorException;
import com.course.server.service.SectionService;
import com.course.server.util.ValidatorUtil;
import org.apache.ibatis.annotations.Delete;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
* @author TangKe（唐柯）
* @date
*/
@RestController
@RequestMapping("/admin/section")
public class SectionController {

private static final Logger LOG = LoggerFactory.getLogger(SectionController.class);
public static final String BUSINESS_NAME = "小节";

@Resource
private SectionService sectionService;


/**
* 列表查询
*/
@PostMapping("/list")
public ResponseDto list(@RequestBody  PageDto pageDto){

LOG.info("pageDto:{}",pageDto);
ResponseDto responseDto = new ResponseDto();
sectionService.list(pageDto);
responseDto.setContent(pageDto);
return responseDto;
}


/**
* 保存，id有值时更新，无值时新增
*/

@PostMapping("/save")
public ResponseDto list(@RequestBody SectionDto  sectionDto){

LOG.info("SectionDto:{}",sectionDto);

//保存校验
ResponseDto responseDto = new ResponseDto();
sectionService.save(sectionDto);
responseDto.setContent(sectionDto);
return  responseDto;
}


/**
* 删除
*/
@DeleteMapping("/delete/{id}")
public ResponseDto delete(@PathVariable String id){


ResponseDto responseDto = new ResponseDto();
sectionService.delete(id);

return  responseDto;
}


}