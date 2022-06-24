package com.xialuote.myzhyx.service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xialuote.myzhyx.pojo.LoginForm;
import com.xialuote.myzhyx.pojo.Teacher;

/**
* @author 伏辰
* @description 针对表【tb_teacher】的数据库操作Service
* @createDate 2022-05-23 18:37:55
*/
public interface TeacherService extends IService<Teacher> {
	
	/**
	 * 教师 登录
	 * @param loginForm
	 * @return
	 */
	Teacher login(LoginForm loginForm);
	
	/**
	 * 获取教师 id
	 * @return
	 */
	Teacher getTeacherById(Long userId);
	
	/**
	 * 教师管理--分页查询
	 * @param pageParam
	 * @param teacher
	 * @return
	 */
	IPage<Teacher> getTeachers(Page<Teacher> pageParam, Teacher teacher);
}
