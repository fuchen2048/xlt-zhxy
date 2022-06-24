package com.xialuote.myzhyx.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xialuote.myzhyx.pojo.Clazz;
import com.xialuote.myzhyx.service.ClazzService;
import com.xialuote.myzhyx.util.Result;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sms/clazzController")
public class ClazzController {
	
	@Autowired
	private ClazzService clazzService;
	
	@GetMapping("/getClazzs")
	public Result getClazzs(){
		List<Clazz> clazzs = clazzService.getClazzs();
		return  Result.ok(clazzs);
	}
	
	@ApiOperation("删除单个或多个班级成员")
	@DeleteMapping("/deleteClazz")
	public Result deleteClazz(@ApiParam("要删除的多个班级id的JSON数组")@RequestBody List<Integer> ids){
		clazzService.removeByIds(ids);
		return Result.ok();
	}
	
	@ApiOperation("添加或修改班级成员")
	@PostMapping("/saveOrUpdateClazz")
	public Result saveOrUpdateClazz(@ApiParam("JSON格式的班级信息")@RequestBody Clazz clazz){
		
		clazzService.saveOrUpdate(clazz);
		return Result.ok();
		
	}
	
	
	//getClazzsByOpr/1/3
	
	@ApiOperation("分页带条件查询班级信息")
	@GetMapping("/getClazzsByOpr/{pageNo}/{pageSize}")
	public Result getClazzsByOpr(@ApiParam("分页查询页码")@PathVariable("pageNo") Integer pageNo ,
	                             @ApiParam("分页查询显示条数")@PathVariable("pageSize") Integer pageSize,
	                             @ApiParam("分页查询条件")Clazz clazz){
		
		Page<Clazz> page = new Page<>(pageNo, pageSize);
		
		IPage<Clazz> pageResult = clazzService.getClazzByOpr(page , clazz);
		
		return Result.ok(pageResult);
	}

}
