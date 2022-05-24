package com.course.server.service;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.course.server.domain.Chapter;
import com.course.server.domain.ChapterExample;
import com.course.server.dto.ChapterDto;
import com.course.server.mapper.ChapterMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Array;
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

    public List<ChapterDto> list(){
        PageHelper.startPage(1,5);
        ChapterExample chapterExample = new ChapterExample();
         List<Chapter> chapterList = chapterMapper.selectByExample(chapterExample);
         List<ChapterDto> chapterDtoList = new ArrayList<>();

        for (int i = 0, l=chapterList.size();i < l ; i++) {
            Chapter chapter = chapterList.get(i);
            ChapterDto chapterDto = new ChapterDto();
            BeanUtils.copyProperties(chapter,chapterDto);
            chapterDtoList.add(chapterDto);
        }
        return chapterDtoList;
    }
}
