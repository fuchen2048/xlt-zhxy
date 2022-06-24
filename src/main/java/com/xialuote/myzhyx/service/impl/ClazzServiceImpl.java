package com.xialuote.myzhyx.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xialuote.myzhyx.mapper.ClazzMapper;
import com.xialuote.myzhyx.pojo.Clazz;
import com.xialuote.myzhyx.pojo.Grade;
import com.xialuote.myzhyx.service.ClazzService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
* @author 伏辰
* @description 针对表【tb_clazz】的数据库操作Service实现
* @createDate 2022-05-23 18:37:06
*/
@Service
@Transactional
public class ClazzServiceImpl extends ServiceImpl<ClazzMapper, Clazz>
    implements ClazzService {
	
	@Override
	public IPage<Clazz> getClazzByOpr(Page<Clazz> pageParam, Clazz clazz) {
		QueryWrapper<Clazz> queryWrapper = new QueryWrapper<>();
		if (!StringUtils.isEmpty(clazz.getName())){
			//模糊查询年级名称
			queryWrapper.like("name" , clazz.getName());
		}
		//对id升序排序
		queryWrapper.orderByDesc("id");
		//对id降序排序
		//queryWrapper.orderByDesc("id");
		Page<Clazz> page = baseMapper.selectPage(pageParam, queryWrapper);
		return page;
	}
	
	@Override
	public List<Clazz> getClazzs() {
		List<Clazz> clazzs = baseMapper.selectList(null);
		return clazzs;
	}
}




