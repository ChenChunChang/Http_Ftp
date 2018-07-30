package cn.admin.system.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.IntToDoubleFunction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.poi.util.StringUtil;
import org.aspectj.weaver.ast.Var;
import org.bouncycastle.jce.provider.JDKDSASigner.stdDSA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import com.mysql.jdbc.Blob;

import cn.admin.core.Authorize;
import cn.admin.system.service.DepartmentService;
import cn.admin.system.service.UserService;
import cn.util.Page;
import cn.util.Tools;

@Controller
@RequestMapping("/system/user")
public class UserController {
	@Autowired
	UserService userService;
	@Autowired
	HttpSession session;
	@Autowired
	DepartmentService departmentService;
	Page page;
	

	// 查询后台管理用户(包括模糊查询)
	@RequestMapping(value = "/adminlist")
	@Authorize(setting = "用户-管理员管理")
	public ModelAndView adminList(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("role", userService.selRoleByIdentify2());
		return new ModelAndView("/system/user/adminlist", map);
	}
	
	@RequestMapping(value="/adminlistData")
	@ResponseBody
	public Map adminlistData(HttpServletRequest request) {
		Map map = new HashMap();
		Map maps = new HashMap();
		String name = request.getParameter("name");
		String zsName = request.getParameter("zsName");
		String roleName = request.getParameter("roleName");
		int draw = Integer.parseInt(request.getParameter("draw"));
		int start = Integer.parseInt(request.getParameter("start"));
		int length = Integer.parseInt(request.getParameter("length"));
		maps.put("userName", name);
		maps.put("realName", zsName);
		maps.put("roleName", roleName);
		long totalCount = userService.getTotalCount2(maps);// 得到总条数
		maps.put("start", start);
		maps.put("length", length);
		List list = userService.selectAdminUser(maps);
		
		//保留两位小数
		map.put("data", list);
		map.put("start", start);
		map.put("draw", draw);
		map.put("recordsTotal",totalCount);
		map.put("recordsFiltered", totalCount);
		return map;
	} 
	
	

	@RequestMapping(value = "/adds")
	@Authorize(setting = "用户-管理员管理添加")
	public ModelAndView addUrls() {
		Map map = new HashMap();
		map.put("role", userService.selRoleByIdentify2());
		List<Map> list = departmentService.selectdepts();
		JSONArray arr=JSONArray.fromObject(list);
		
		map.put("deptlist", arr.toString());
		
		List<Map> treedata = departmentService.getDepTreeData();
		
		JSONArray treearr=JSONArray.fromObject(treedata);
		
		map.put("deptuserlist", treearr.toString());
		
		return new ModelAndView("/system/user/addAdminUser", map);
	}

	@RequestMapping("/addAdmin")
	@ResponseBody
	@Authorize(setting = "用户-管理员管理添加")
	public Map addAdmin(@RequestParam Map<String, Object> param, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		List list = new ArrayList();
		String userPwd1 = param.get("userPwd") + "";
		if (!StringUtils.isEmpty(userPwd1)) {
			String userPwd = Tools.md5(userPwd1);
			param.put("userPwd", userPwd);
		}
		String[] roleid = request.getParameterValues("roleId");
		for (String str : roleid) {
			list.add(str);
		}
		String birthDate=param.get("birthDate")+"";
		if(StringUtils.isEmpty(birthDate)){
			param.put("birthDate", null);
		}
		param.put("roleId", list);
		int row = userService.addAdminUserInfo(param);
		int ro = userService.addUserRole(param);
		if (row > 0 && ro > 0) {
			map.put("success", true);
			map.put("msg", "添加成功！");
		} else {
			map.put("success", true);
			map.put("msg", "添加失败！");
		}
		return map;
	}

	// 后台管理员侧滑跳转
	@RequestMapping(value = "/adminUpdate")
	@Authorize(setting = "用户-管理员管理修改")
	public ModelAndView adminUpdate(@RequestParam("userId") int userId) {
		// 通过ID查询单个会员
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("role", userService.selRoleByIdentify2());
		Object user=userService.selectUserInfoById(userId);
		if (user==null) {
			throw new NullPointerException();
		}
		Map userMap=(Map)user;
		int depId=(int)userMap.get("departmentId");
		map.put("departmentName", departmentService.selectDepartmentNameById(depId));
		Object leaderId=userMap.get("leaderId");
		if (leaderId!=null) {
			map.put("parentUserName", userService.selectUserNameByUserId(leaderId.toString()));
		}
		map.put("list",userMap );
		List<Map> list = departmentService.selectdepts();
		JSONArray arr=JSONArray.fromObject(list);
		
		map.put("deptlist", arr.toString());
		
		List<Map> treedata = departmentService.getDepTreeData();
		
		JSONArray treearr=JSONArray.fromObject(treedata);
		
		map.put("deptuserlist", treearr.toString());
		
		return new ModelAndView("/system/user/editAdminUser", map);
	}

	@RequestMapping("/editAdminUser")
	@ResponseBody
	@Authorize(setting = "用户-管理员管理修改")
	public Map editAdminUser(@RequestParam Map<String, Object> param, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		List list = new ArrayList();
		String userPwd1 = param.get("userPwd") + "";
		if (!StringUtils.isEmpty(userPwd1)) {
			String userPwd = Tools.md5(userPwd1);
			param.put("userPwd", userPwd);
		}
		String[] roleid = request.getParameterValues("roleId");
		for (String str : roleid) {
			list.add(str);
		}
		String birthDate=param.get("birthDate")+"";
		if(StringUtils.isEmpty(birthDate)){
			param.put("birthDate", null);
		}
		param.put("roleId", list);
		int row = userService.updateUser(param);
		int ro = userService.addUserRoles(param);
		if (row > 0 && ro > 0) {
			map.put("success", true);
			map.put("msg", "修改成功！");
		} else {
			map.put("success", false);
			map.put("msg", "修改失败！");
		}
		return map;
	}

	// 删除后台用户
	@ResponseBody
	@RequestMapping(value = "/delAdminUser")
	@Authorize(setting = "用户-管理员管理删除")
	public Map delAdminUser(@RequestParam("userId") int userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		int row = userService.deleteUser(userId);
		if (row > 0) {
			map.put("success",true);
			map.put("msg", "删除成功！");
		} else {
			map.put("success",false);
			map.put("msg", "删除失败！");
		}
		return map;
	}

	// 批量删除会员
	@RequestMapping(value = "/deletes")
	public String del(HttpServletRequest request) {
		List delList = new ArrayList();
		String items = request.getParameter("delitems");
		String[] strs = items.split(",");
		for (String str : strs) {
			delList.add(str);
		}
		userService.delUser(delList);
		return "redirect:/admin/user/list";
	}

	// 批量删除管理员
	@RequestMapping(value = "/deletesAdmin")
	public String deletesAdmin(HttpServletRequest request) {
		List delList = new ArrayList();
		String items = request.getParameter("delitems");
		String[] strs = items.split(",");
		for (String str : strs) {
			delList.add(str);
		}
		userService.delUser(delList);
		return "redirect:/admin/user/adminlist";
	}

	// 退出后台登录
	@RequestMapping(value = "/loginOut")
	public void loginOut(HttpServletResponse res) {
		session.invalidate();
		try {// 重定向到首页请求
			res.sendRedirect("/admin/login");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/findusers")
	public Object findUsers(HttpServletRequest request){
		return userService.findUsers(request.getParameter("term"));
	}


	@RequestMapping(value = "/userList",method = RequestMethod.POST)
	@ResponseBody
	public Map userList(@RequestParam Map map){
		//dataTable
		int draw = Integer.parseInt(map.get("draw").toString());
		int start = Integer.parseInt(map.get("start").toString());
		int length = Integer.parseInt(map.get("length").toString());
		map.put("start", start);
		map.put("pageSize", length);

		Long totalCount = userService.selectUserListCount(map);
		List<Map> userList = userService.selectUserList(map);

		map.put("draw", draw);
		map.put("recordsTotal",totalCount);
		map.put("recordsFiltered", totalCount);
		map.put("data",userList);

		return map;
	}
}