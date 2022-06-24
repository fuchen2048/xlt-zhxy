package com.xialuote.myzhyx.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xialuote.myzhyx.pojo.Clazz;
import com.xialuote.myzhyx.pojo.Student;
import com.xialuote.myzhyx.service.StudentService;
import com.xialuote.myzhyx.util.MD5;
import com.xialuote.myzhyx.util.Result;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sms/studentController")
public class StudentController {
	
	@Autowired
	private StudentService studentService;
	
	@ApiOperation("添加或修改学生信息")
	@PostMapping("/addOrUpdateStudent")
	public Result addOrUpdateStudent(@ApiParam("要添加过修改的学生的JSON")@RequestBody Student student){
		
		Integer id = student.getId();
		if (id == null || 0 == id){
			student.setPassword(MD5.encrypt(student.getPassword()));
		}
		studentService.saveOrUpdate(student);
		
		return Result.ok();
		
	}
	
	//studentController/delStudentById
	@ApiOperation("删除学生信息")
	@DeleteMapping("/delStudentById")
	public Result delStudentById(@ApiParam("要删的学生id的JSON数组")@RequestBody List<Integer> ids){
		studentService.removeByIds(ids);
		return Result.ok();
	}
	
	//getStudentByOpr/1/3
	@ApiOperation("分页带条件查询学生信息")
	@GetMapping("/getStudentByOpr/{pageNo}/{pageSize}")
	public Result getStudentByOpr(@ApiParam("分页查询页码")@PathVariable("pageNo") Integer pageNo ,
	                              @ApiParam("分页查询显示条数")@PathVariable("pageSize") Integer pageSize,
	                              @ApiParam("分页查询条件")Student student){
		
		Page<Student> page = new Page<>(pageNo, pageSize);
		
		IPage<Student> pageResult = studentService.getStudentByOpr(page , student);
		
		return Result.ok(pageResult);
	}
}
