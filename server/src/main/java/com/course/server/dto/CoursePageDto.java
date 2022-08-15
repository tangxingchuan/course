package com.course.server.dto;

/**
 * @author TangKe（唐柯）
 * @date 2022/8/15 - 10:33 下午
 */
public class CoursePageDto extends PageDto {


    //状态，分页
    private String status;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CoursePageDto{" +
                "status='" + status + '\'' +
                ", page=" + page +
                ", size=" + size +
                ", total=" + total +
                ", list=" + list +
                '}';
    }
}
