package cn.admin.system.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
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
import cn.admin.system.service.RoleService;
import cn.util.Page;

/**
 * 用户角色类
 * 
 * 
 */
@Controller
@RequestMapping("/system/role")
public class RoleController {
	@Autowired
	SqlSessionFactory sqlSessionFactory;
	@Autowired
	SqlSession sqlSession;
	@Autowired
	RoleService roleService;
	@Autowired
	HttpSession session;
	Page page = null;

	// 显示所有，搜索角色 信息列表
	@RequestMapping(value = "/list")
	@Authorize(setting="角色-角色管理")
	public ModelAndView list(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> searchInfo = new HashMap<String, Object>();
		String identify=request.getParameter("identify");
		if(Integer.parseInt(identify)==1){
			searchInfo.put("identify", 1);
		}
		if(Integer.parseInt(identify)==0){
			searchInfo.put("identify",0);
		}
		map.put("identify",identify);
		return new ModelAndView("/system/role/rolelist", map);
	}
	
	
	@RequestMapping(value="/list/dataTable")
	@ResponseBody
	public Object listDataTable(HttpServletRequest request){
		Map<String, Object> searchInfo = new HashMap<String, Object>();
		//dataTable
		int draw = Integer.parseInt(request.getParameter("draw"));
		int start = Integer.parseInt(request.getParameter("start"));
		int length = Integer.parseInt(request.getParameter("length"));
		searchInfo.put("start", start);
		searchInfo.put("pageSize", length);
		//基本信息
		String identify=request.getParameter("identify");
		if(Integer.parseInt(identify)==1){
			searchInfo.put("identify", 1);
		}
		if(Integer.parseInt(identify)==0){
			searchInfo.put("identify",0);
		}
		//得到总条数
		long totalCount=roleService.getTotalCount(searchInfo);
		//list
		List<Map> list = roleService.selectRoleByQuery(searchInfo);
		List<Map> roleList = roleService.selectAllRole();
		for(Map m : list){
			if(m.get("parentId")!=null){
				for(Map mm : roleList){
					if((m.get("parentId")+"").equals(mm.get("roleid")+"")){
						m.put("parentName",mm.get("roleName"));
						break;
					}
				}
			}
		}
		Map result=new HashMap();
		//dataTable基本信息
		result.put("start", start);
		result.put("draw", draw);
		result.put("recordsTotal",totalCount);
		result.put("recordsFiltered", totalCount);
		//返回的数据data
		result.put("data", list);
		
		return result;
	}

	// 修改、添加、 角色权限 的判断（业务逻辑处理方法）
	@RequestMapping(value = "/roleInfo")
	@Authorize(setting = "角色-修改角色,角色-添加角色,角色-角色权限", type = AuthorizeType.ONE)
	public ModelAndView rolelimit(HttpServletRequest request) throws JSONException {
		Map<String, Object> map = new HashMap<String, Object>();
		String _op = request.getParameter("_op");
		String roleId = request.getParameter("roleId");
		String identify = request.getParameter("identify");
		List roleList = roleService.selectAllRole();
		map.put("identify", identify);
		if ("add".equals(_op)) {// 角色增加 不需要传值
			map.put("_op", _op);
			map.put("roleList", roleList);
			return new ModelAndView("/system/role/roleInfo", map);
		} else if ("update".equals(_op)) {// 角色修改 需要传值
			map = roleService.selectRoleById(roleId);// 根据roleid查询一条记录
			map.put("_op", _op);
			map.put("roleList", roleList);
			return new ModelAndView("/system/role/roleInfo", map);
		} else if ("limits".equals(_op)) {// 角色权限
			List li=new ArrayList();
			map.put("roleId",request.getParameter("roleId"));
			//根据roleId查询此角色的权限
			List<Map> list=roleService.selAuthorityID(request.getParameter("roleId"));
			for(Map mm:list){
				Map m=new HashMap();
				String AuthorityID=mm.get("AuthorityID")+"";
				AuthorityID=AuthorityID.substring(AuthorityID.lastIndexOf("-")+1);
				m.put("AuthorityID", AuthorityID);
				li.add(m);
			}
			map.put("li",li);
			//settings为权限配置列表
			Map<String, List<String>> settings=AuthorizeSettingLoader.get();
			map.put("settings", settings);
			return new ModelAndView("/system/role/adminroleset",map);
			
		// TODO
		}
		return null;
	}
	@RequestMapping(value="/saveRoleAuthority")
	@ResponseBody
	@Authorize(setting="角色-角色权限")
	public Map saveRoleAuthority(HttpServletRequest request){
		List list=new ArrayList();
		Map<String, Object> map = new HashMap<String, Object>();
		Map m = new HashMap();
		String roleId=request.getParameter("roleId");
		String spCodesTemp = request.getParameter("spCodesTemp");
		String[] oldArray = spCodesTemp.split(",");
		for(String str:oldArray){
			list.add(str);
		}
		m.put("AuthorityID", list);
		m.put("roleid", roleId);
		int row = roleService.addra(m);
		if(row>0){
			map.put("msg", "设置成功");
		}else{
			map.put("msg", "设置失败");
		}
		return map;
	}

	/**
	 * 增加
	 * 
	 * @return
	 */
	@RequestMapping(value = "/roleAdd")
	@ResponseBody
	@Authorize(setting="角色-添加角色")
	public Map<String, Object> roleAdd(HttpServletRequest request,@RequestParam Map map) {
		int row = roleService.add(map);
		if (row > 0) {
			map.put("success", true);
			map.put("msg", "添加成功");
		} else {
			map.put("success", false);
			map.put("msg", "添加失败");
		}
		return map;
	}

	/**
	 * 修改
	 * 
	 * @return
	 */
	@RequestMapping(value = "/roleUpdate")
	@ResponseBody
	@Authorize(setting="角色-修改角色")
	public Map<String, Object> roleUpdate(HttpServletRequest request,@RequestParam Map map) {
		int row = roleService.update(map);
		if (row > 0) {
			map.put("success", true);
			map.put("msg", "修改成功");
		} else {
			map.put("success", false);
			map.put("msg", "修改失败");
		}
		return map;
	}

	/**
	 * 刪除
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/roleDelete")
	@ResponseBody
	@Authorize(setting="角色-删除角色")
	public Map<String, Object> operation(HttpServletRequest request) {
		Map<String, Object> msg = new HashMap<String, Object>();
		int row = 0;
		String roleid = request.getParameter("roleId");
		//查看权限人员表是否有关联
		int r = sqlSession.selectOne("roleDao.selectur",roleid);
		if (r > 0) {
			msg.put("message", "该角色下尚有人员，不可删除！！！");
			return msg;
		}
		row = roleService.delRole(roleid);
		if (row > 0) {
			msg.put("message", "删除成功");
		} else {
			msg.put("message", "删除失败");
		}
		return msg;
	}
	


}