package com.xialuote.myzhyx.service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xialuote.myzhyx.pojo.Clazz;

import java.util.List;

/**
* @author 伏辰
* @description 针对表【tb_clazz】的数据库操作Service
* @createDate 2022-05-23 18:37:06
*/
public interface ClazzService extends IService<Clazz> {
	/**
	 * 班级管理--分页查询
	 * @param pageParam
	 * @param clazz
	 * @return
	 */
	IPage<Clazz> getClazzByOpr(Page<Clazz> pageParam, Clazz clazz);
	
	/**
	 * 查询所有班级
	 * @return
	 */
	List<Clazz> getClazzs();
}
