package com.xialuote.myzhyx.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xialuote.myzhyx.pojo.Admin;
import com.xialuote.myzhyx.pojo.LoginForm;
import com.xialuote.myzhyx.pojo.Student;
import com.xialuote.myzhyx.pojo.Teacher;
import com.xialuote.myzhyx.service.AdminService;
import com.xialuote.myzhyx.service.StudentService;
import com.xialuote.myzhyx.service.TeacherService;
import com.xialuote.myzhyx.util.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("/sms/system")
public class SystemController {
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private TeacherService teacherService;
	
	/**
	 * 修改密码
	 * @param token
	 * @param oldPwd
	 * @param newPwd
	 * @return
	 */
	@ApiOperation("修改密码")
	@PostMapping("/updatePwd/{oldPwd}/{newPwd}")
	public Result updatePwd(@ApiParam("token")@RequestHeader("token") String token ,
	                        @ApiParam("原密码")@PathVariable("oldPwd") String oldPwd ,
	                        @ApiParam("新密码")@PathVariable("newPwd") String newPwd){
		if (JwtHelper.isExpiration(token)) {
			//token过期
			return Result.fail().message("过期，请重新登录后重试");
		}
		//获取用户id和用户类型
		Long userId = JwtHelper.getUserId(token);
		Integer userType = JwtHelper.getUserType(token);
		
		//将密码转换为密文
		oldPwd = MD5.encrypt(oldPwd);
		newPwd = MD5.encrypt(newPwd);
		
		switch (userType) {
			case 1:
				QueryWrapper<Admin> adminQueryWrapper = new QueryWrapper<>();
				adminQueryWrapper.eq("id" , userId);
				adminQueryWrapper.eq("password" , oldPwd);
				Admin admin = adminService.getOne(adminQueryWrapper);
				if(admin != null) {
					admin.setPassword(newPwd);
					adminService.saveOrUpdate(admin);
				} else {
					return Result.fail().message("密码错误！");
				}
				break;
			case 2:
				QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
				studentQueryWrapper.eq("id" , userId);
				studentQueryWrapper.eq("password" , oldPwd);
				Student student = studentService.getOne(studentQueryWrapper);
				if(student != null) {
					student.setPassword(newPwd);
					studentService.saveOrUpdate(student);
				} else {
					return Result.fail().message("密码错误！");
				}
				break;
			case 3:
				QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();
				teacherQueryWrapper.eq("id" , userId);
				teacherQueryWrapper.eq("password" , oldPwd);
				Teacher teacher = teacherService.getOne(teacherQueryWrapper);
				if(teacher != null) {
					teacher.setPassword(newPwd);
					teacherService.saveOrUpdate(teacher);
				} else {
					return Result.fail().message("密码错误！");
				}
				break;
		}
		return Result.ok();
	}
	
	
	@ApiOperation("文件上传统一入口")
	@PostMapping("/headerImgUpload")
	public Result headerImgUpload(@ApiParam("头像文件") @RequestPart("multipartFile") MultipartFile multipartFile){
		
		//将上传的文件修改其名称
		String uuid = UUID.randomUUID().toString().replace("-", "");
		String originalFilename = multipartFile.getOriginalFilename();
		int i = originalFilename.lastIndexOf(".");
		
		String newFilename = uuid.concat(originalFilename.substring(i));
		
		//保存文件
		String portraitPath = "D:/workkspace/ideaProject/SpringBoot/myzhyx/myzhyx/target/classes/public/upload/".concat(newFilename);
		
		try {
			multipartFile.transferTo(new File(portraitPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//响应图片路径
		String path = "upload/".concat(newFilename);
		return Result.ok(path);
	}
	
	/**
	 * 登录成功 跳转首页
	 * @param token
	 * @return
	 */
	
	@GetMapping("/getInfo")
	public Result getInfoByToken(@RequestHeader("token") String token) {
		boolean expiration = JwtHelper.isExpiration(token);
		if (expiration) {
			return Result.build(null , ResultCodeEnum.TOKEN_ERROR);
		}
		
		//从token中解析出用户ID 和 用户 类型
		Long userId = JwtHelper.getUserId(token);
		Integer userType = JwtHelper.getUserType(token);
		
		Map<String, Object> map = new LinkedHashMap<>();
		//判断用户类型
		switch (userType) {
			case 1:
				Admin admin = adminService.getAdminById(userId);
				map.put("userType" , 1);
				map.put("user" , admin);
				break;
			case 2:
				Student student = studentService.getStudentById(userId);
				map.put("userType" , 1);
				map.put("user" , student);
				break;
			case 3:
				Teacher teacher = teacherService.getTeacherById(userId);
				map.put("userType" , 1);
				map.put("user" , teacher);
				break;
		}
		
		return Result.ok(map);
	
	}
	
	/**
	 * 验证码校验
	 * @param loginForm
	 * @param request
	 * @return
	 */
	
	@PostMapping("/login")
	public Result login(@RequestBody LoginForm loginForm , HttpServletRequest request){
		//验证码校验
		String sessionVerifCode = (String) request.getSession().getAttribute("verifCode");
		String loginVerifiCode = loginForm.getVerifiCode();
		if ("".equals(sessionVerifCode) || null == sessionVerifCode){
			return Result.fail().message("验证码失效，请刷新后重试！");
		}
		if (!sessionVerifCode.equalsIgnoreCase(loginVerifiCode)){
			return Result.fail().message("验证码有误，请仔细输入!");
		}
		//从session域中移出验证码
		request.getSession().removeAttribute("verifCode");
		
		
		Map<String , Object> map = new LinkedHashMap<>();
		switch (loginForm.getUserType()){
			//用户的类型和用户id转换成密文，以token的名称向客户端反馈
			case 1:
				try {
					Admin admin = adminService.login(loginForm);
					
					if (null != admin) {
						//用户的类型和用户id转换成密文，以token的名称向客户端反馈
						map.put("token" , JwtHelper.createToken(admin.getId().longValue(), 1));
					} else {
						throw new RuntimeException("用户名或密码错误！");
					}
					return Result.ok(map);
				}catch (Exception e) {
					e.printStackTrace();
					return Result.fail().message(e.getMessage());
				}
			
			case 2:
				try {
					Student student = studentService.login(loginForm);
				
					if (null != student) {
						//用户的类型和用户id转换成密文，以token的名称向客户端反馈
						map.put("token" , JwtHelper.createToken(student.getId().longValue(), 2));
					} else {
						throw new RuntimeException("用户名或密码错误！");
					}
					return Result.ok(map);
				}catch (Exception e) {
					e.printStackTrace();
					return Result.fail().message(e.getMessage());
				}
				
			case 3:
				try {
					Teacher teacher = teacherService.login(loginForm);
				
					if (null != teacher) {
						//用户的类型和用户id转换成密文，以token的名称向客户端反馈
						map.put("token" , JwtHelper.createToken(teacher.getId().longValue(), 3));
					} else {
						throw new RuntimeException("用户名或密码错误！");
					}
					return Result.ok(map);
				}catch (Exception e) {
					e.printStackTrace();
					return Result.fail().message(e.getMessage());
				}
			default:
			
		}
		return Result.fail().message("查无此用户！");
	}
	
	/**
	 * 取得验证码
	 * @param request
	 * @param response
	 */

	@GetMapping("/getVerifiCodeImage")
	public void getVerifiCodeImage(HttpServletRequest request , HttpServletResponse response){
		//获取图片
		BufferedImage verifiCodeImage = CreateVerifiCodeImage.getVerifiCodeImage();
		//获取图片上的验证码
		String verifCode = new String(CreateVerifiCodeImage.getVerifiCode());
		//将验证码放入session域,为下一次验证做准备
		request.getSession().setAttribute("verifCode" , verifCode);
		//将验证码响应给浏览器
		try {
			ImageIO.write(verifiCodeImage, "jpg", response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
