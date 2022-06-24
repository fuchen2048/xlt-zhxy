package com.xialuote.myzhyx.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xialuote.myzhyx.pojo.Teacher;
import com.xialuote.myzhyx.service.TeacherService;
import com.xialuote.myzhyx.util.MD5;
import com.xialuote.myzhyx.util.Result;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sms/teacherController")
public class TeacherController {
	
	@Autowired
	private TeacherService teacherService;
	
	@ApiOperation("删除教师信息")
	@DeleteMapping("/deleteTeacher")
	public Result deleteTeacher(@ApiParam("需要删除的教师id的JSON信息")@RequestBody List<Integer> ids){
		teacherService.removeByIds(ids);
		return Result.ok();
	}
	
	//saveOrUpdateTeacher
	@ApiOperation("添加或修改教师信息")
	@PostMapping("/saveOrUpdateTeacher")
	public Result saveOrUpdateTeacher(@ApiParam("教师需要添加或修改的JSON信息")@RequestBody Teacher teacher){
		//新增信息密码需要需要加密
		if (teacher.getId() == null || teacher.getId() == 0){
			teacher.setPassword(MD5.encrypt(teacher.getPassword()));
		}
		teacherService.saveOrUpdate(teacher);
		return Result.ok();
	}
	
	//getTeachers/1/3
	@ApiOperation("分页查询教师信息")
	@GetMapping("/getTeachers/{pageNo}/{pageSize}")
	public Result getTeachers(@ApiParam("分页查询页码")@PathVariable("pageNo") Integer pageNo ,
	                          @ApiParam("分页查询每页条数")@PathVariable("pageSize") Integer pageSize ,
	                          @ApiParam("分页查询的条件") Teacher teacher){
		
		Page<Teacher> page = new Page<>(pageNo, pageSize);
		IPage<Teacher> pageResult = teacherService.getTeachers(page , teacher);
		return Result.ok(pageResult);
	}
}
