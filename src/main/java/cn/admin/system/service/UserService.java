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
@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
public class UserService {
	
	@Autowired
	SqlSessionFactory sqlSessionFactory;
	@Autowired
	SqlSession sqlSession;
	
	public List selectUser(Map<String, Object>  selUser) {
		List list=sqlSession.selectList("userDao.selectUser",selUser);
		return list;
	}
	
	public List findUsers(String username) {
		Map m= new HashMap();
		m.put("username", username);
		List list=sqlSession.selectList("userDao.findUsers",m);
		return list;
	}
	
	public List selectUserByUserType(int userType){
		return sqlSession.selectList("userDao.selectUserInfoByUserType",userType);
	}
	public List selectAllUserInfo(){
		return sqlSession.selectList("userDao.selectAllUserInfo");
	}
	
	public List selectUsersByDeparentment(int depId){
		return sqlSession.selectList("userDao.selbydeparentment",depId);
	}
	
	public String selectRealnameByUserId(String userId){
		return sqlSession.selectOne("userDao.selectRealnameByUserId", userId);
	}
	public String selectUserNameByUserId(String userId){
		return sqlSession.selectOne("userDao.selectUserNameByUserId", userId);
	}
	public void addUser(Map<String, Object>  userMap) {
		sqlSession.insert("userDao.addUserInfo",userMap);
	}
	
	public int addUserRole(Map<String, Object>  userMap){
		return sqlSession.insert("userDao.addUserRole",userMap);
	}
	
	public int addAdminUserInfo(Map<String, Object>  userMap) {
		return sqlSession.insert("userDao.addAdminUserInfo",userMap);
	}

	public Object selectUserInfoById(int userId) {
		return sqlSession.selectOne("userDao.selectUserInfoById",userId);
	
	}

	public int deleteUser(int userId) {
		 return sqlSession.delete("userDao.deleteUser",userId);
	}

	public int updateUser(Map<String, Object> map) {
		return sqlSession.update("userDao.updateUser",map);
	}

	public int addUserRoles(Map map) {
		int userId=  Integer.valueOf(map.get("userId")+"");
		Map m= new HashMap();
		m.put("userId", userId);
		int count = sqlSession.selectOne("userDao.select",m);
		if(count>0){
			sqlSession.delete("userDao.del",m);
		}
		int row = sqlSession.insert("userDao.addUserRole",map);
		return row;
	}
	
	
	public void delUser(List delList) {
		sqlSession.delete("userDao.delUser",delList);
		
	}

	public long getTotalCount(Map<String, Object> selUser) {
		return sqlSession.selectOne("userDao.countUser",selUser);
	}
	/****************************************************以下是后台管理员方法********************************************************/
	public List selectAdminUser(Map<String, Object>  selUser) {
		List list=sqlSession.selectList("userDao.selectAdminUser",selUser);
		return list;
	}
	
	public long getTotalCount2(Map<String, Object> selUser) {
		return sqlSession.selectOne("userDao.countAdminUser",selUser);
	}
	// 查询后台角色
	public List selRoleByIdentify2(){
		return sqlSession.selectList("userDao.selRoleByIdentify2");
	}
	
	/****************************************************以上是后台管理员方法*********************************************************/
	public long updatePwd(Map map) {
		return sqlSession.update("userDao.reAdminPassword",map);
	}

	public List selectAllSubUsers(int parentId){
		return sqlSession.selectList("userDao.selectAllSubUsers",parentId);
	}
	
	public int selectParentForUser(int userId){
		return sqlSession.selectOne("userDao.selectParentId",userId);
	}


	public Long selectUserListCount(Map map) {
		return sqlSession.selectOne("userDao.selectUserListCount",map);
	}

	public List<Map> selectUserList(Map map) {
		return sqlSession.selectList("userDao.selectUserList",map);
	}
}
