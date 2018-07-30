package cn.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;

/**
 * 缓存单例
 * 
 */
public class MapCache {
	private static Map<String, Object> cache;

	private MapCache() {
		if (cache == null) {
			cache = new ConcurrentHashMap<String, Object>();
		}
	}

	private static class MapCacheStatic {
		private static MapCache instance = new MapCache();
	}

	public static MapCache getInstance() {
		return MapCacheStatic.instance;
	}

	/**
	 * 写入缓存值
	 */
	public static void putCache(String key, Object map) {
		cache.put(key, map);
	}

	/**
	 * 获取所有缓存值
	 * */
	public static Map<String, Object> getAllCache() {
		return cache;
	}

	/**
	 * 根据键获取值
	 */
	public static Object getCache(String key) {
		return cache.get(key);
	}

	// 根据键获取值(单点登录)
	public static String getIslogin(String key) {
		return (String) cache.get(key);
	}

	// 写入缓存值
	public static void putUser(String key, String sessionId) {
		cache.put(key, sessionId);
	}

	/**
	 * 清除缓存
	 */
	public static void clearCache() {
		cache.clear();
	}

	/**
	 * 清除特定的缓存
	 */
	public static void removeCache(String key) {
		cache.remove(key);
	}
}

