package com.course.server.service;


import com.course.server.domain.Chapter;
import com.course.server.domain.ChapterExample;
import com.course.server.dto.ChapterDto;
import com.course.server.dto.ChapterPageDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.ChapterMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author TangKe（唐柯）
 * @date 2022/5/19 - 4:11 下午
 */

@Service
public class ChapterService {

    @Resource
    private ChapterMapper chapterMapper;


    /**
     * 列表查询
     */
    public void list(ChapterPageDto chapterPageDto) {
        PageHelper.startPage(chapterPageDto.getPage(), chapterPageDto.getSize());
        ChapterExample chapterExample = new ChapterExample();
        ChapterExample.Criteria criteria = chapterExample.createCriteria();
        if (!StringUtils.isEmpty(chapterPageDto.getCourseId())) {
            criteria.andCourseIdEqualTo(chapterPageDto.getCourseId());
        }
        List<Chapter> chapterList = chapterMapper.selectByExample(chapterExample);
        PageInfo<Chapter> pageInfo = new PageInfo<>(chapterList);
        chapterPageDto.setTotal(pageInfo.getTotal());
        List<ChapterDto> chapterDtoList = CopyUtil.copyList(chapterList, ChapterDto.class);
        chapterPageDto.setList(chapterDtoList);



    }

    /**
     * 保存
     */
    public void save(ChapterDto  chapterDto) {

        Chapter chapter = CopyUtil.copy(chapterDto,Chapter.class);

        if (StringUtils.isEmpty(chapterDto.getId())){
            this.insert(chapter);

        }else {
            this.update(chapter);
        }

    }



    /**
     * 新增
     */
    private void insert(Chapter  chapter) {
        chapter.setId(UuidUtil.getShortUuid());
        chapterMapper.insert(chapter);

    }


    /**
     *修改
     */
    private void update(Chapter  chapter) {
        chapterMapper.updateByPrimaryKey(chapter);

    }

    /**
     *根据id删除
     */
    public void delete(String id) {
        chapterMapper.deleteByPrimaryKey(id);

    }


}