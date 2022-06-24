package com.xialuote.myzhyx.service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xialuote.myzhyx.pojo.Admin;
import com.xialuote.myzhyx.pojo.LoginForm;

import javax.management.Query;

/**
* @author 伏辰
* @description 针对表【tb_admin】的数据库操作Service
* @createDate 2022-05-23 18:36:21
*/
public interface AdminService extends IService<Admin> {
	
	/**
	 * 管理员 登录
	 * @param loginForm
	 * @return
	 */
	Admin login(LoginForm loginForm);
	
	/**
	 * 获取管理员 id
 	 * @return
	 */
	Admin getAdminById(Long userId);
	
	/**
	 * 管理员--查询所有管理员
	 * @param pageParam
	 * @param adminName
	 * @return
	 */
	IPage<Admin> getAllAdmin(Page<Admin> pageParam, String adminName);
}
