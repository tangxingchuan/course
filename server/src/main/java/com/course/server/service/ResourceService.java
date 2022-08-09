package com.course.server.service;

import com.alibaba.fastjson.JSON;
import com.course.server.domain.Resource;
import com.course.server.domain.ResourceExample;
import com.course.server.dto.ResourceDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.ResourceMapper;
import com.course.server.mapper.UserCourseMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.annotations.JsonAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;


import java.util.ArrayList;
import java.util.List;

@Service
public class ResourceService {




    private static final Logger LOG = LoggerFactory.getLogger(ResourceMapper.class);


@javax.annotation.Resource
private ResourceMapper resourceMapper;

/**
* 列表查询
*/
public void list(PageDto pageDto) {
PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
ResourceExample resourceExample = new ResourceExample();
List<Resource> resourceList = resourceMapper.selectByExample(resourceExample);
PageInfo<Resource> pageInfo = new PageInfo<>(resourceList);
pageDto.setTotal(pageInfo.getTotal());
List<ResourceDto> resourceDtoList = CopyUtil.copyList(resourceList, ResourceDto.class);
    pageDto.setList(resourceDtoList);
    }

    /**
    * 保存，id有值时更新，无值时新增
    */
    public void save(ResourceDto resourceDto) {
    Resource resource = CopyUtil.copy(resourceDto, Resource.class);
    if (StringUtils.isEmpty(resourceDto.getId())) {
    this.insert(resource);
    } else {
    this.update(resource);
    }
    }

    /**
    * 新增，这里的id是我在json中定义好的，不是自动生成的短id
    */
    private void insert(Resource resource) {
    resourceMapper.insert(resource);
    }

    /**
    * 更新
    */
    private void update(Resource resource) {
    resourceMapper.updateByPrimaryKey(resource);
    }

    /**
    * 删除
    */
    public void delete(String id) {
    resourceMapper.deleteByPrimaryKey(id);
    }


    /**
     * 保存资源树
     * @param json
     */
    public void saveJson(String json){

          List <ResourceDto> jsonLis =JSON.parseArray(json,ResourceDto.class);

           ArrayList<ResourceDto> list = new ArrayList<>();

            if (!CollectionUtils.isEmpty(jsonLis)){

                for (ResourceDto d : jsonLis){

                    d.setParent("");
                    add(list,d);

                }

            }

            LOG.info("共{}条",list.size());

            resourceMapper.deleteByExample(null);
        for (int i = 0; i < list.size(); i++) {

            this.insert(CopyUtil.copy(list.get(i),Resource.class));
        }
    }


    /**
     * 递归，将树型结构的节点全部取出来，放到list
     * @param list
     * @param dto
     */
    public void add(List<ResourceDto> list,ResourceDto dto){

        list.add(dto);

        if (!CollectionUtils.isEmpty(dto.getChildren())){

            for (ResourceDto d:dto.getChildren()){

                d.setParent(dto.getId());
                add(list,dto);
            }
        }

    }



    }



