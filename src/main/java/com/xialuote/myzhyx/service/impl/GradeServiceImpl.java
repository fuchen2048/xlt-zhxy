package com.xialuote.myzhyx.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xialuote.myzhyx.mapper.GradeMapper;
import com.xialuote.myzhyx.pojo.Grade;
import com.xialuote.myzhyx.service.GradeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
* @author 伏辰
* @description 针对表【tb_grade】的数据库操作Service实现
* @createDate 2022-05-23 18:37:19
*/
@Service
@Transactional
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade>
    implements GradeService {
	
	@Override
	public IPage<Grade> getGradeByOpr(Page<Grade> pageParam, String gradeName) {
		QueryWrapper<Grade> queryWrapper = new QueryWrapper<>();
		if (!StringUtils.isEmpty(gradeName)){
			//模糊查询年级名称
			queryWrapper.like("name" , gradeName);
		}
		//对id升序排序
		queryWrapper.orderByDesc("id");
		//对id降序排序
		//queryWrapper.orderByDesc("id");
		Page<Grade> page = baseMapper.selectPage(pageParam, queryWrapper);
		return page;
	}
	
	@Override
	public List<Grade> getGrades() {
		List<Grade> grades = baseMapper.selectList(null);
		return grades;
	}
}




