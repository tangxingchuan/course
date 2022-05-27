package com.course.controller.admin;

import com.course.server.dto.${Domain}Dto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.exception.ValidatorException;
import com.course.server.service.${Domain}Service;
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
@RequestMapping("/admin/${domain}")
public class ${Domain}Controller {

private static final Logger LOG = LoggerFactory.getLogger(${Domain}Controller.class);
public static final String BUSINESS_NAME = "大章";

@Resource
private ${Domain}Service ${domain}Service;


/**
* 列表查询
*/
@PostMapping("/list")
public ResponseDto list(@RequestBody  PageDto pageDto){

LOG.info("pageDto:{}",pageDto);
ResponseDto responseDto = new ResponseDto();
${domain}Service.list(pageDto);
responseDto.setContent(pageDto);
return responseDto;
}


/**
* 保存，id有值时更新，无值时新增
*/

@PostMapping("/save")
public ResponseDto list(@RequestBody ${Domain}Dto  ${domain}Dto){

LOG.info("${Domain}Dto:{}",${domain}Dto);

//保存校验
ResponseDto responseDto = new ResponseDto();
${domain}Service.save(${domain}Dto);
responseDto.setContent(${domain}Dto);
return  responseDto;
}


/**
* 删除
*/
@DeleteMapping("/delete/{id}")
public ResponseDto delete(@PathVariable String id){


ResponseDto responseDto = new ResponseDto();
${domain}Service.delete(id);

return  responseDto;
}


}
