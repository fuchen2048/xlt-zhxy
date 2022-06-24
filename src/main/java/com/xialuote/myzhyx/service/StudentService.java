package com.xialuote.myzhyx.service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xialuote.myzhyx.pojo.Clazz;
import com.xialuote.myzhyx.pojo.LoginForm;
import com.xialuote.myzhyx.pojo.Student;

/**
* @author 伏辰
* @description 针对表【tb_student】的数据库操作Service
* @createDate 2022-05-23 18:37:30
*/
public interface StudentService extends IService<Student> {
	/**
	 * 学生 登录
	 * @param loginForm
	 * @return
	 */
	Student login(LoginForm loginForm);
	
	/**
	 * 获取学生 id
	 * @return
	 */
	Student getStudentById(Long userId);
	
	/**
	 * 学生管理--分页查询
	 * @param pageParam
	 * @param student
	 * @return
	 */
	IPage<Student> getStudentByOpr(Page<Student> pageParam, Student student);
}
