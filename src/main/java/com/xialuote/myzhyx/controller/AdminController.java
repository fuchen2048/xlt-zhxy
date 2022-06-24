package com.xialuote.myzhyx.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xialuote.myzhyx.pojo.Admin;
import com.xialuote.myzhyx.service.AdminService;
import com.xialuote.myzhyx.util.MD5;
import com.xialuote.myzhyx.util.Result;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sms/adminController")
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@ApiOperation("删除管理员信息")
	@DeleteMapping("/deleteAdmin")
	public Result deleteAdmin(@ApiParam("需要删除的多个id的JSON数组")@RequestBody List<Integer> ids){
		adminService.removeByIds(ids);
		return Result.ok();
	}
	
	@ApiOperation("添加或修改多个管理员信息")
	@PostMapping("/saveOrUpdateAdmin")
	public Result saveOrUpdateAdmin(@ApiParam("添加多个管理员信息的JSON信息")@RequestBody Admin admin){
		//新增信息密码需要需要加密
		if (admin.getId() == null || admin.getId() == 0){
			admin.setPassword(MD5.encrypt(admin.getPassword()));
		}
		adminService.saveOrUpdate(admin);
		return Result.ok();
	}
	
	//getAllAdmin/1/3
	@ApiOperation("分页查询管理员")
	@GetMapping("/getAllAdmin/{pageNo}/{pageSize}")
	public Result getAllAdmin(@ApiParam("分页查询页码") @PathVariable("pageNo") Integer pageNo ,
	                          @ApiParam("分页查询每页条数")@PathVariable("pageSize") Integer pageSize ,
	                          @ApiParam("分页查询条件") String adminName){
		Page<Admin> page = new Page<>(pageNo, pageSize);
		IPage<Admin> pageResult = adminService.getAllAdmin(page , adminName);
		return Result.ok(pageResult);
	}
}
