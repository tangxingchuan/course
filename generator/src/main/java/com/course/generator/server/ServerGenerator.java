package com.course.generator.server;

import com.course.generator.util.FreemarkerUtil;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author TangKe（唐柯）
 * @date 2022/5/28 - 6:10 上午
 */
public class ServerGenerator {

    static String toServicePath = "server/src/main/java/com/course/server/service/";

    public static void main(String[] args) throws IOException, TemplateException {

        String Domain = "Section";
        String domain = "section";
        Map<String,Object> map =new HashMap<>();
        map.put("Domain",Domain);
        map.put("domain",domain);

        //替换ftlName，就可以生成不同的service层
        FreemarkerUtil.initConfig("service.ftl");
        FreemarkerUtil.generator(toServicePath+Domain+"Section.java",map);
    }
}
