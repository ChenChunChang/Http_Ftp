package cn.admin.system.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
//事务处理
@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
public class RoleService {

	@Autowired
	SqlSessionFactory sqlSessionFactory;
	@Autowired
	SqlSession sqlSession;
	//查询所有角色
	public List selectRoleByQuery(Map<String, Object> searchInfo){
		List list=sqlSession.selectList("roleDao.selectRoleInfo",searchInfo);
		return list;
	}
	//根据roleid查询角色
		public Map selectRoleById(String roleid){
			Map m =  sqlSession.selectOne("roleDao.selectRoleById",roleid);
			return m;
		}
	//获取角色表行数
	public long getTotalCount(Map<String, Object> searchInfo){
		return sqlSession.selectOne("roleDao.countRole",searchInfo);
	}
	//增加角色
	public int add(Map<String,Object> map){
		int row = sqlSession.insert("roleDao.addRoleInfo", map);
		return row;
	}
	//修改角色
	public int update(Map<String,Object> map){
		int row = sqlSession.update("roleDao.update", map);
		return row;
	}
	//根据用户查询角色ID
	public List<String> selectRoleIdByUserId(String userId){
		List<String> roleId = sqlSession.selectList("roleDao.selectRoleIdByUserId", userId);
		return roleId;
	}
	//删除角色
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public int delRole(String roleid){
		int ret = sqlSession.delete("roleDao.delRole",roleid);
		//同时删除userrole 和 roleauthority 表中的与role相关的记录
		//sqlSession.delete("roleDao.delur",roleid);
		sqlSession.delete("roleDao.delar",roleid);
		return ret;
	}
	
	public List selRoleByIdentify2(){
		return sqlSession.selectList("roleDao.selRoleByIdentify2");
	}
	public Integer addra(Map map) {
		int roleid=  Integer.valueOf((String) map.get("roleid"));
		Map m= new HashMap();
		m.put("roleid", roleid);
		int count = sqlSession.selectOne("roleDao.select",m);
		if(count>0){
			sqlSession.delete("roleDao.del",m);
		}
		int row = sqlSession.insert("roleDao.add",map);
		return row;
	}
	//根据roleId查询此角色的权限
	public List selAuthorityID(String roleid){
		return sqlSession.selectList("roleDao.selAuthorityID",roleid);
	}
	//查询该用户拥有的权限放到session中 
	public List selAuthorityByUserId(String userId){
		return sqlSession.selectList("roleDao.selAuthorityByUserId",userId);
	}
	//查询所有角色
	public List selectAllRole(){
		return sqlSession.selectList("roleDao.selectAllRole");
	}
	
	
	
}
