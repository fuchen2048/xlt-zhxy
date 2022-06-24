package com.xialuote.myzhyx.service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xialuote.myzhyx.pojo.Grade;

import java.util.List;

/**
* @author 伏辰
* @description 针对表【tb_grade】的数据库操作Service
* @createDate 2022-05-23 18:37:19
*/
public interface GradeService extends IService<Grade> {
	
	/**
	 * 年级管理--分页查询
	 * @param pageParam
	 * @param gradeName
	 * @return
	 */
	IPage<Grade> getGradeByOpr(Page<Grade> pageParam, String gradeName);
	
	/**
	 * 班级查询中 -- 年级查询
	 */
	List<Grade> getGrades();
}
