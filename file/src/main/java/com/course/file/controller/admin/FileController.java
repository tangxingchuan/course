package com.course.business.controller.admin;

import com.course.server.dto.FileDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.exception.ValidatorException;
import com.course.server.service.FileService;
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
@RequestMapping("/admin/file")
public class FileController {

private static final Logger LOG = LoggerFactory.getLogger(FileController.class);
public static final String BUSINESS_NAME = "文件";

@Resource
private FileService fileService;


/**
* 列表查询
*/
@PostMapping("/list")
public ResponseDto list(@RequestBody  PageDto pageDto){

LOG.info("pageDto:{}",pageDto);
ResponseDto responseDto = new ResponseDto();
fileService.list(pageDto);
responseDto.setContent(pageDto);
return responseDto;
}


/**
* 保存，id有值时更新，无值时新增
*/

@PostMapping("/save")
public ResponseDto list(@RequestBody FileDto  fileDto){

LOG.info("FileDto:{}",fileDto);

//保存校验
            ValidatorUtil.require(fileDto.getPath(), "相对路径");
            ValidatorUtil.length(fileDto.getPath(), "相对路径", 1, 100);
            ValidatorUtil.length(fileDto.getName(), "文件名", 1, 100);
            ValidatorUtil.length(fileDto.getSuffix(), "后缀", 1, 10);
ResponseDto responseDto = new ResponseDto();
fileService.save(fileDto);
responseDto.setContent(fileDto);
return responseDto;
}


/**
* 删除
*/
@DeleteMapping("/delete/{id}")
public ResponseDto delete(@PathVariable String id){


ResponseDto responseDto = new ResponseDto();
fileService.delete(id);

return  responseDto;
}


}
