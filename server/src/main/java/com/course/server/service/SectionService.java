package com.course.server.service;


import com.course.server.domain.Section;
import com.course.server.domain.SectionExample;
import com.course.server.dto.SectionDto;
import com.course.server.dto.PageDto;
import com.course.server.enums.SectionChargeEnum;
import com.course.server.mapper.SectionMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
* @author TangKe（唐柯）
* @date 2022/5/19 - 4:11 下午
*/

@Service
public class SectionService {

@Resource
private SectionMapper sectionMapper;


/**
* 列表查询
*/
public void list(PageDto pageDto) {
PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
SectionExample sectionExample = new SectionExample();
SectionExample.Criteria criteria = sectionExample.createCriteria();

List<Section> sectionList = sectionMapper.selectByExample(sectionExample);
    PageInfo<Section> pageInfo = new PageInfo<>(sectionList);
        pageDto.setTotal(pageInfo.getTotal());

        List<SectionDto> sectionDtoList = CopyUtil.copyList(sectionList, SectionDto.class);
            pageDto.setList(sectionDtoList);


            }

            /**
            * 保存
            */
            public void save(SectionDto  sectionDto) {

            Section section = CopyUtil.copy(sectionDto,Section.class);

            if (StringUtils.isEmpty(sectionDto.getId())){
            this.insert(section);

            }else {
            this.update(section);
            }

            }



            /**
            * 新增
            */
            private void insert(Section  section) {
                Date now =new Date();
                section.setCreatedAt(now);
                section.setUpdatedAt(now);
                section.setCharge(SectionChargeEnum.CHARGE.getCode());
                section.setId(UuidUtil.getShortUuid());
                sectionMapper.insert(section);

            }

            /**
            *修改
            */
            private void update(Section  section) {
            sectionMapper.updateByPrimaryKey(section);

            }

            /**
            *根据id删除
            */
            public void delete(String id) {
            sectionMapper.deleteByPrimaryKey(id);

            }


            }