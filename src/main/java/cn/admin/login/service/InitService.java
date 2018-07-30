package cn.admin.login.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InitService {

	@Autowired
	SqlSessionFactory sqlSessionFactory;
	@Autowired
	SqlSession sqlSession;
	@Autowired
	HttpSession session;
	
	public List selectAllAuthority(){
		return sqlSession.selectList("initPageDao.selectAllMenu");
	}
	
	public List initPage(List m){
		return sqlSession.selectList("initPageDao.selectModule",m);
	}
	//点击顶部导航
	public List selDaoHang(List parentModuleID){
		return sqlSession.selectList("initPageDao.selectDaoHang",parentModuleID);
	}
	//根据点击的类型查询孙子类
	public List selectSZ(String AuthorityType){
		return sqlSession.selectList("initPageDao.selectSZ",AuthorityType);
	}
	//查询子节点
	public List selChild(String userId){
		return sqlSession.selectList("initPageDao.selectChild",userId);
	}
	public Map selUrl(String parentModuleID) {
		//根据权限查询 要显示的一级列表
		String userId =  (String) session.getAttribute("userId");
		Map m = new HashMap();
		m.put("parentModuleID", parentModuleID);
		Map m1 = new HashMap();
		m1.put("userId",userId );
		m1.put("parentModuleID", parentModuleID);
		List<Map> AuthorityIdLi = sqlSession.selectList("initPageDao.selone",m1);
		if(AuthorityIdLi!=null&&AuthorityIdLi.size()!=0){
			m1.put("parentModuleID", AuthorityIdLi.get(0).get("AuthorityID"));
			m.put("indexNoa", AuthorityIdLi.get(0).get("indexNo"));
		}
		List<Map> AuthorityIdLi2 = sqlSession.selectList("initPageDao.seltwo",m1);
		if(AuthorityIdLi2!=null&&AuthorityIdLi2.size()!=0){
			m.put("indexNob", AuthorityIdLi2.get(0).get("indexNo"));
		}
		Map a = sqlSession.selectOne("initPageDao.selUrl",m);
		return a;
	}
}
