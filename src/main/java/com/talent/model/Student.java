package com.talent.model;

import com.talent.annotation.SpecialTime;
import com.talent.annotation.TimeFiled;
import com.talent.enums.DateTimeEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: DownLoadImageDemo
 * @author: Mr.Guo
 * @description: 学生实体类
 * @create: 2019-04-18 14:25
 */
@Data
@TimeFiled(values = {"birthday", "startTime", "endTime"})
public class Student implements Serializable {

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
    @SpecialTime(DateTimeEnum.DATE_TIME)
    private Date birthday;

    /**
     * 家庭住址
     */
    private String address;

    /**
     * 入学时间
     */
    @SpecialTime(DateTimeEnum.DATE)
    private String startTime;

    /**
     * 毕业时间
     */
    @SpecialTime(DateTimeEnum.DATE)
    private String endTime;


}
