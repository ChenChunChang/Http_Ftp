package cn.admin.system.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.eclipse.jdt.internal.compiler.ast.ArrayAllocationExpression;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.admin.core.Authorize;
import cn.admin.core.AuthorizeSettingLoader;
import cn.admin.core.Authorize.AuthorizeType;
import cn.admin.system.service.DepartmentService;
import cn.admin.system.service.RoleService;
import cn.util.Page;
import net.sf.json.JSON;
import net.sf.json.JSONArray;

/**
 *
 * 
 * 
 */
@Controller
@RequestMapping("/system/department")
public class DepartmentController {
	@Autowired
	DepartmentService departmentService;
	@Autowired
	HttpSession session;
	
	
	@RequestMapping("treeDept")
	@ResponseBody
	@Authorize(setting="公司架构-查看公司架构信息")
	public Map allUser(HttpServletRequest request) {
		//搜索所有部门
		Map<String, Object> map = new HashMap<String, Object>();
		List list = departmentService.selectdepts();
		map.put("dlist", list);
		return map;
	}
	
	@RequestMapping("allDept")
	@Authorize(setting="公司架构-查看公司架构信息")
	public ModelAndView  a() {
		Map m = new HashMap();
		return new ModelAndView("system/deptment/list",m);
	}
	
	//显示添加部门信息的弹框
	@RequestMapping(value="/addDepartment")
	@Authorize(setting="公司架构-添加公司架构信息")
	public ModelAndView addDepartment(){
		Map map=new HashMap();
		List<Map> list = departmentService.selectdepts();
		JSONArray arr=JSONArray.fromObject(list);
		
		map.put("deptlist", arr.toString());
		
		return new ModelAndView("/system/deptment/addDepartment",map);
	}
	//保存部门信息
	@RequestMapping(value="/saveDepartment")
	@ResponseBody
	@Authorize(setting="公司架构-添加公司架构信息")
	public Map saveDepartment(@RequestParam Map param){
		Map map=new HashMap();
		int row=departmentService.addDept(param);
		if(row>0){
			map.put("success", true);
			map.put("msg","添加成功");
		}else{
			map.put("success", false);
			map.put("msg","添加失败");
		}
		return map;
	}
	//显示修改部门信息的弹框
	@RequestMapping(value="/eidtDepartment")
	@Authorize(setting="公司架构-修改公司架构信息")
	public ModelAndView eidtDepartment(HttpServletRequest request){
		Map map=new HashMap();
		String departmentId=request.getParameter("departmentId");
		List list = departmentService.selectdepts();
		JSONArray arr=JSONArray.fromObject(list);
		
		map.put("deptlist", arr.toString());
		map.put("department", departmentService.selDepartmentById(departmentId));
		return new ModelAndView("/system/deptment/editDepartment",map);
	}
	//修改部门信息
	@RequestMapping(value="/upDepartment")
	@ResponseBody
	@Authorize(setting="公司架构-修改公司架构信息")
	public Map upDepartment(@RequestParam Map param){
		Map map=new HashMap();
		int row=departmentService.updateDept(param);
		if(row>0){
			map.put("success", true);
			map.put("msg","修改成功");
		}else{
			map.put("success", false);
			map.put("msg","修改失败");
		}
		return map;
	}
	//删除部门
	@RequestMapping(value="deleteDept")
	@ResponseBody
	@Authorize(setting="公司架构-删除公司架构信息")
	public Map delPosition(@RequestParam("deptId") int deptId){
  		Map<String, Object> maps=departmentService.deleteDept(deptId);
		return maps;
	}
}