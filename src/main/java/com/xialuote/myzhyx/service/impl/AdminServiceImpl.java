package com.xialuote.myzhyx.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xialuote.myzhyx.mapper.AdminMapper;
import com.xialuote.myzhyx.pojo.Admin;
import com.xialuote.myzhyx.pojo.LoginForm;
import com.xialuote.myzhyx.pojo.Teacher;
import com.xialuote.myzhyx.service.AdminService;
import com.xialuote.myzhyx.util.MD5;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
* @author 伏辰
* @description 针对表【tb_admin】的数据库操作Service实现
* @createDate 2022-05-23 18:36:21
*/
@Service
@Transactional
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin>
    implements AdminService {
	
	@Override
	public Admin login(LoginForm loginForm) {
		QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("name" , loginForm.getUsername());
		queryWrapper.eq("password" , MD5.encrypt(loginForm.getPassword()));
		Admin admin = baseMapper.selectOne(queryWrapper);
		return admin;
	}
	
	@Override
	public Admin getAdminById(Long userId) {
		QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("id" , userId);
		Admin admin = baseMapper.selectOne(queryWrapper);
		return admin;
	}
	
	@Override
	public IPage<Admin> getAllAdmin(Page<Admin> pageParam, String adminName) {
		QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
		if (!StringUtils.isEmpty(adminName)) {
			queryWrapper.like("name" , adminName);
		}
		queryWrapper.orderByDesc("id");
		Page<Admin> page = baseMapper.selectPage(pageParam, queryWrapper);
		return page;
	}
}




