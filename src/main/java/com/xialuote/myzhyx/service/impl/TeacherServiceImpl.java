package com.xialuote.myzhyx.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xialuote.myzhyx.mapper.TeacherMapper;
import com.xialuote.myzhyx.pojo.LoginForm;
import com.xialuote.myzhyx.pojo.Teacher;
import com.xialuote.myzhyx.service.TeacherService;
import com.xialuote.myzhyx.util.MD5;
import com.xialuote.myzhyx.util.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
* @author 伏辰
* @description 针对表【tb_teacher】的数据库操作Service实现
* @createDate 2022-05-23 18:37:55
*/
@Service
@Transactional
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher>
    implements TeacherService {
	
	@Override
	public Teacher login(LoginForm loginForm) {
		QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("name" , loginForm.getUsername());
		queryWrapper.eq("password" , MD5.encrypt(loginForm.getPassword()));
		Teacher teacher = baseMapper.selectOne(queryWrapper);
		return teacher;
	}
	
	@Override
	public Teacher getTeacherById(Long userId) {
		QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("id" , userId);
		Teacher teacher = baseMapper.selectOne(queryWrapper);
		return teacher;
	}
	
	@Override
	public IPage<Teacher> getTeachers(Page<Teacher> pageParam, Teacher teacher) {
		QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
		if (!StringUtils.isEmpty(teacher.getClazzName())) {
			queryWrapper.like("clazz_name"  , teacher.getClazzName());
		}
		if (!StringUtils.isEmpty(teacher.getName())) {
			queryWrapper.like("name" , teacher.getName());
		}
		queryWrapper.orderByDesc("id");
		Page<Teacher> page = baseMapper.selectPage(pageParam, queryWrapper);
		return page;
	}
}




