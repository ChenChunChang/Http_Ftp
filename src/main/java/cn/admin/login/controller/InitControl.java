package cn.admin.login.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import cn.admin.login.service.InitService;
import cn.util.TreeNode;
import com.alibaba.druid.util.StringUtils;



@Controller

public class InitControl {
	
	@Autowired
	InitService initService;
	@Autowired
	HttpSession session;
	
	private boolean hasAuthorize(String chk,List<String> auth){
		if (StringUtils.isEmpty(chk)) {
			return true;
		}
		for(String s:auth){
			if (s.equals(chk)) {
				return true;
			}
		}
		
		return false;
	}
	
	private TreeNode buildNode(Map m,List<Map> all,List<String> auth){
		boolean isLeaf=(boolean)m.get("isInTree");
		if (isLeaf) {
			if(m.get("AuthorityDescription")!=null && !hasAuthorize(m.get("AuthorityDescription").toString(),auth)){
				return null;
			}
		}
		TreeNode node=new TreeNode();
		node.setTreeName(m.get("AuthorityType").toString());
		node.setId(m.get("AuthorityID").toString());
		
		if (!isLeaf) {
			List<TreeNode> childs=buildNode(m.get("AuthorityID").toString(), all,auth);
			if (childs==null||childs.size()==0) {
				return null;
			}else{
				node.setChList(childs);
			}
		}else{
			if (m.get("icoUrl")!=null) {
				node.setIcon(m.get("icoUrl").toString());
			}
			if (m.get("moduleUrl")!=null) {
				node.setUrl(m.get("moduleUrl").toString());
			}
			
		}

		return node;
	}
	
	private List<TreeNode> buildNode(String parentId,List<Map> all,List<String> auth){
		List<TreeNode> nodes=new ArrayList<TreeNode>();
		if (StringUtils.isEmpty(parentId)) {
			for(Map m:all){
				if(m.get("parentModuleID")== null || m.get("parentModuleID").toString().equals("0")){
					TreeNode node=buildNode(m,all,auth);
					if (node!=null) {
						nodes.add(node);
					}
					
				}
			}
		}else{
			for(Map m:all){
				if(m.get("parentModuleID")!= null && m.get("parentModuleID").toString().equals(parentId)){
					TreeNode node=buildNode(m,all,auth);
					if (node!=null) {
						nodes.add(node);
					}
				}
			}
		}
		
		return nodes;
	}
	
	
	@RequestMapping(value="/admin/index")
	public ModelAndView index(String moduleName){
		Map<String, Object> map=new HashMap<String, Object>();
		Object menu=session.getAttribute("menu");
		
		List<TreeNode> nodes=null;
		
		if (menu==null) {
			//获取用户权限列表
			List<String> allAuthorityForUser=(List<String>)session.getAttribute("AuthorityID");
			List allAuthority=initService.selectAllAuthority();
			nodes = buildNode("",allAuthority,allAuthorityForUser);
			session.setAttribute("menu", nodes);
		}
		else{
			nodes=(List<TreeNode>)session.getAttribute("menu");
		}
		
		map.put("treenodes", nodes);
		if (!StringUtils.isEmpty(moduleName)) {
			for(TreeNode node:nodes){
				if (node.getTreeName().equals(moduleName)) {
					map.put("erList", node.getChList());
					map.put("url", node.getChList().get(0).getUrl());
				}
			}
		}else{
			map.put("erList", nodes.get(0).getChList());
			map.put("url", "/shcrm/customer/index");
		}
	
		map.put("moduleName",moduleName);
		map.put("username", session.getAttribute("loginUserRealName"));
		return new ModelAndView("/admin/login/index",map);
		
	}
}

