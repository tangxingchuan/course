package com.course.server.dto;

/**
 * @author TangKe（唐柯）
 * @date 2022/8/15 - 10:33 下午
 */
public class CoursePageDto extends PageDto {


    //状态，分页
    private String status;

    private String categoryId;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "CoursePageDto{" +
                "status='" + status + '\'' +
                ", categoryId='" + categoryId + '\'' +
                ", page=" + page +
                ", size=" + size +
                ", total=" + total +
                ", list=" + list +
                '}';
    }
}
