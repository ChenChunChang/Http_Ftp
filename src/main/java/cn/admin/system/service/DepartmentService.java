package cn.admin.system.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.h2.util.New;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.util.myCode;

@Service
//事务处理
@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
public class DepartmentService {

	@Autowired
	SqlSessionFactory sqlSessionFactory;
	@Autowired
	SqlSession sqlSession;
	private static List<Map> depTreeData;
	private static Object depTreeDataLock=new Object();
	
	public List<Map> getDepTreeData(){
		synchronized(depTreeDataLock){
			if (depTreeData==null) {
				List<Map> deps=selectdepts();
				List<Map> users=sqlSession.selectList("userDao.selectAllUserInfo");
				
				depTreeData=new ArrayList<Map>();
				for(Map d:deps){
					HashMap<String, Object> newMap=new HashMap<String, Object>();
					newMap.put("id", "d"+d.get("departmentId"));
					newMap.put("pId", "d"+d.get("parentId"));
					newMap.put("name", d.get("departmentName"));
					newMap.put("nocheck", true);
					depTreeData.add(newMap);
				}	
				
				for(Map u:users){
					HashMap<String, Object> newMap=new HashMap<String, Object>();
					newMap.put("id", u.get("userId"));
					newMap.put("pId", "d"+u.get("departmentId"));
					newMap.put("name", u.get("realname")==null?u.get("departmentName"):u.get("realname"));
					depTreeData.add(newMap);
				}
			}
			return depTreeData;
		}
	}
	
	public void clearDepTreeData(){
		synchronized(depTreeDataLock){
			depTreeData=null;
		}
	}
	
	public String selectDepartmentNameById(int depId){
		return sqlSession.selectOne("deptDao.selectDeptNameById",depId);
	}
	
	public List selectdepts(){
		return sqlSession.selectList("deptDao.selectdept");
	}
	
	public List selectDeptsWithUserCount(){
		return sqlSession.selectList("deptDao.selectdeptwithusercount");
	}
	
	//增加部门
	public int addDept(Map map){
		return sqlSession.insert("deptDao.addDept",map);
	}
	//根据部门Id查询部门信息
	public Map selDepartmentById(String departmentId){
		return sqlSession.selectOne("deptDao.selDepartmentById",departmentId);
	}
	//修改部门信息
	public int updateDept(Map map){
		int r = sqlSession.update("deptDao.updateDept",map);
		if (r>0) {
			clearDepTreeData();
		}
		return r;
	}
	//删除部门
	public Map deleteDept(int departmentId){
		Map<String, Object> map=new HashMap<String, Object>();
		//查询该部门下是否有子部门
		List cm = sqlSession.selectList("deptDao.findzb",departmentId+"");
		if(cm!=null&&cm.size()!=0){
			map.put("success", true);
			map.put("msg","该部门还有子部门，不能删除");
			return map;
		}
		//查询该部门下是否有人员
		cm=sqlSession.selectList("deptDao.selUserByDeId",departmentId+"");
		if(cm!=null&&cm.size()!=0){
			map.put("success", true);
			map.put("msg","该部门还有人员，不能删除");
			return map;
		}
		int row=sqlSession.delete("deptDao.delDept",departmentId);
		if(row>0){
			clearDepTreeData();
			map.put("success", true);
			map.put("msg","删除成功!");
		}else{
			map.put("success", false);
			map.put("msg","删除失败！");
		}
		return map;
	}
	public List selFU() {
		return sqlSession.selectList("deptDao.selFU");
	}

	public List selZI() {
		return sqlSession.selectList("deptDao.selZI");
	}
}

