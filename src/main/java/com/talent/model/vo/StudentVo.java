package com.talent.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: DownLoadImageDemo
 * @author: Mr.Guo
 * @description: 页面展示的学生字段
 * @create: 2019-04-18 14:41
 */
@Data
public class StudentVo implements Serializable {

    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private String sex;

    /**
     * 出生日期
     */
    private String birthday;

    /**
     * 家庭住址
     */
    private String address;

    /**
     * 入学时间
     */
    private Date startTime;

    /**
     * 毕业时间
     */
    private Date endTime;
}
