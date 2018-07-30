package cn.admin.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TraceLogService {
	@Autowired
	SqlSession sqlSession;
	
	public boolean insertLog(int modelId,String className,int userId,String logType,String logBody){
		Map<String, Object> ma=new HashMap<String, Object>();
		ma.put("modelId", modelId);
		ma.put("className", className);
		ma.put("userId", userId);
		ma.put("logType", logType);
		ma.put("logBody", logBody);
		return sqlSession.insert("traceLog.addLog",ma)>0;
	}
	
	public List selectAllWithOneClass(Map map){
		return sqlSession.selectList("traceLog.selectAllWithOneClass",map);
	}
	
	public List selectAllWithManyClass(Map map){
		return sqlSession.selectList("traceLog.selectAllWithManyClass",map);
	}
	
	public long selectCountWithOneClass(Map map){
		return sqlSession.selectOne("traceLog.selectCountWithOneClass",map);
	}
	public long selectCountWithManyClass(Map map){
		return sqlSession.selectOne("traceLog.selectCountWithManyClass",map);
	}
}
