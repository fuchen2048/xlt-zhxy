package com.xialuote.myzhyx.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xialuote.myzhyx.pojo.Grade;
import com.xialuote.myzhyx.service.GradeService;
import com.xialuote.myzhyx.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Api(tags = "年级控制器")
@RestController
@RequestMapping("/sms/gradeController")
public class GradeController {
	
	@Autowired
	private GradeService gradeService;
	
	@ApiOperation("获取全部年级")
	@GetMapping("/getGrades")
	public Result getGrades(){
		//调用服务层查询所有年级
		List<Grade> grades = gradeService.getGrades();
		return Result.ok(grades);
	}
	
	@ApiOperation("删除Grade信息")
	@DeleteMapping("/deleteGrade")
	public Result deleteGrade(@ApiParam("要删除的所有的Grade的id的JSON集合")@RequestBody List<Integer> ids){
		//调用服务层完成删除的请求
		gradeService.removeByIds(ids);
		return Result.ok();
	}
	
	@ApiOperation("添加或修改Grade信息，有属性是修改，没有则添加")
	@PostMapping("/saveOrUpdateGrade")
	public Result saveOrUpdateGrade(@ApiParam("JSON的Grade对象")@RequestBody Grade grade){
		//调用服务层方法完成增加或修改的操作
		gradeService.saveOrUpdate(grade);
		return Result.ok();
	}
	
	@ApiOperation("根据年级名称带分页的模糊查询")
	@GetMapping("/getGrades/{pageNo}/{pageSize}")
	public Result getGrades(@ApiParam("分页查询的页码数")@PathVariable("pageNo") Integer pageNo ,
	                        @ApiParam("分页查询的显示条数")@PathVariable("pageSize") Integer pageSize ,
	                        @ApiParam("分页模糊查询") String gradeName){
		//分页带条件查询查询
		Page<Grade> page = new Page<>(pageNo , pageSize);
		//通过服务层
		IPage<Grade> pageResult = gradeService.getGradeByOpr(page , gradeName);
		//封装Result对象并返回
		return Result.ok(pageResult);
	}
}
