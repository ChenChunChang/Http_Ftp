package cn.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

/**
 * 
 * @author 卢斌
 * @DATE 2017年1月7日上午10:44:59 
 * @TODO TODO 解析properties 文件
 */
public class ConfigPro {
	private static Logger logger=Logger.getLogger(ConfigPro.class);
	private static PropertiesConfiguration pc;
	public static synchronized boolean updateConfig(Map<String,Object> map,String path){
		try {
			pc = new PropertiesConfiguration(path);
			for(String key:map.keySet()){
				Object obj=map.get(key);
				if(obj!=null){
					String value=obj.toString();
					pc.setProperty(key, value);
				}
			}
			pc.save();
			return true;
		} catch (ConfigurationException e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			System.out.println("找不到配置文件");
			return false;
		}		
	} 

	/**
	 * 
	 * @author 卢斌
	 * @DATE 2017年1月6日下午4:29:22 
	 * @TODO 	查询短信配置文件 
	 * @param prefix
	 * @return 返回配置文件
	 */
	public static synchronized  Map<String,Object> getConfig(String path){
		Map<String,Object> map=new HashMap<String, Object>();
		try {
			pc=new PropertiesConfiguration();
			pc.setDelimiterParsingDisabled(true);
			pc.setFileName(path);
			pc.load();
			Iterator<String> it=pc.getKeys();
			while(it.hasNext()){
				String key=it.next();
				String value=pc.getString(key);
				map.put(key, value);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}
	public static synchronized Map<String,Object> getConfig(String prefix,String path){
		Map<String,Object> map=new HashMap<String, Object>();
		try {
			pc=new PropertiesConfiguration();
			pc.setDelimiterParsingDisabled(true);
			pc.setFileName(path);
			pc.load();
			Iterator<String> it=pc.getKeys(prefix);
			while(it.hasNext()){
				String key=it.next();
				String value=pc.getString(key);
				map.put(key.substring(prefix.length()+1), value);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}
}
