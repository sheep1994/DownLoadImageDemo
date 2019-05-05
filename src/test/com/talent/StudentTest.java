package com.talent;

import com.alibaba.fastjson.JSON;
import com.talent.logic.convert.DtoVoConvert;
import com.talent.model.Student;
import com.talent.model.vo.StudentVo;
import org.junit.Test;

import java.util.Date;

/**
 * @program: DownLoadImageDemo
 * @author: Mr.Guo
 * @description: 测试类
 * @create: 2019-04-19 23:24
 */
public class StudentTest {

    @Test
    public void test1() {
        Student student = new Student();
        student.setId(1);
        student.setName("张三");
        student.setBirthday(new Date());
        student.setAddress("张江");
        student.setSex("男");
        student.setStartTime("2018/10/10");
        student.setEndTime("2019/10/10");

        StudentVo result = DtoVoConvert.convert(student, StudentVo.class);

        System.out.println(JSON.toJSONString(result));
    }
}
