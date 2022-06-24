package com.xialuote.myzhyx.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xialuote.myzhyx.pojo.Student;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 伏辰
* @description 针对表【tb_student】的数据库操作Mapper
* @createDate 2022-05-23 18:37:30
* @Entity .pojo.Student
*/
@Mapper
public interface StudentMapper extends BaseMapper<Student> {

}




