
package cn.admin.login.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.admin.system.service.RoleService;
import cn.util.Tools;



@Controller
@EnableAutoConfiguration
public class LoginController {
	@Autowired
	SqlSession sqlSession;
	@Autowired
	RoleService roleService;
	@Autowired
	HttpSession session;
	
	@RequestMapping(value={"","/login"})
	public ModelAndView goLogin(@RequestParam Map map){
		if(session.getAttribute("loginUser")!=null && !StringUtils.isEmpty(session.getAttribute("loginUser").toString())){
			return new ModelAndView("redirect:/admin/index");
		}
		return new ModelAndView("/admin/login/login",map);
	}
	
	/**
	 * @Description: 登陆
	 */
	@RequestMapping(value = "/login/userLogin", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	Map<String, Object> getUserInfo(@RequestParam Map<String, Object> login) {
		Map<String, Object> map = new HashMap<String, Object>();
		String name = (String) login.get("userName");
		String pwd = (String) login.get("userPwd");
		if (StringUtils.isEmpty(name) || StringUtils.isEmpty(pwd)) {
			map.put("isLogin", false);
			map.put("message", "用户名或密码不得为空！");
			return map;

		} else {
			String md5pwd = Tools.md5(pwd);
			login.put("userPwd", md5pwd);
			Map m = sqlSession.selectOne("initPageDao.login", login);
			if (m != null) {
				session.setAttribute("userId", m.get("userId")+"");
				if(name.length()>10){
					name=name.substring(0,3)+"***";
				}
				session.setAttribute("loginUser",name);
				Object realname=m.get("realname");
				if (realname==null) {
					session.setAttribute("loginUserRealName", name);
				}else{
					session.setAttribute("loginUserRealName", realname.toString());
				}
				
				session.setAttribute("userType", m.get("userType")+"");
				//查询该用户拥有的权限放到session中
				List Alist=roleService.selAuthorityByUserId(m.get("userId")+"");
				//查询该用户的roleId
				List<String> roleId = roleService.selectRoleIdByUserId(m.get("userId")+"");
				session.setAttribute("AuthorityID", Alist);
				session.setAttribute("roleId", roleId);
				map.put("isLogin", true);
				return map;
			} else {
				map.put("isLogin", false);
				map.put("message", "用户名或密码错误！");
				return map;
			}
		}
	}

	/**
	 * 退出登录
	 * */
	@RequestMapping("/login/loginOut")
	public ModelAndView loginOut() {
		session.invalidate();
		return new ModelAndView("redirect:/");
	}
	
}
