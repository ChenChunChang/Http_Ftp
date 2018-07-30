package cn.util;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;
//import com.xuyw.wx.config.Config;


@Service
public class BaiDuUtill {
	
	/**
	 * @author dhr
	 * @param lat 纬度
	 * @param lng 精度
	 * @return 地址
	 */
	public static String getCity(String lat, String lng) {
	    JSONObject obj = getLocationInfo(lat, lng).getJSONObject("result")
	        .getJSONObject("addressComponent");
	    String address = "";
	    if(("北京市").equals(obj.getString("province")) || ("上海市").equals(obj.getString("province")) || ("天津市").equals(obj.getString("province")) || ("重庆市").equals(obj.getString("province"))){
	    	address = obj.getString("city")+obj.getString("district");
	    }else{
	    	address = obj.getString("province")+obj.getString("city")+obj.getString("district");
	    }
	    return address;
	}
	
	public static JSONObject getLocationInfo(String lat, String lng) {
	    String url = "http://api.map.baidu.com/geocoder/v2/?location=" + lat + ","
	        + lng + "&output=json&ak=SiwEzEZVDFEcIzfFjTgwoy5ogIz9Oj5B&pois=0";
	    JSONObject obj = JSONObject.fromObject(HttpUtill.getRequest(url));
	    return obj;
	}
	
	
	
	
	
	  static double DEF_PI = 3.14159265359; // PI  
	  static double DEF_2PI= 6.28318530712; // 2*PI  
	  static double DEF_PI180= 0.01745329252; // PI/180.0  
	  static double DEF_R =6370693.5; // radius of earth  
	  
	  /**
	   * 百度地图-勾股定理计算量坐标间的距离（适用于比较近的距离）
	   * @author dhr
	   * @param lon1 坐标1纬度
	   * @param lat1 坐标1经度
	   * @param lon2 坐标2纬度
	   * @param lat2 坐标2经度
	   * @return 米
	   */
	  public double GetShortDistance(double lon1, double lat1, double lon2, double lat2){
	     double ew1, ns1, ew2, ns2;  
	     double dx, dy, dew;  
	     double distance;  
	     // 角度转换为弧度  
	     ew1 = lon1 * DEF_PI180;  
	     ns1 = lat1 * DEF_PI180;  
	     ew2 = lon2 * DEF_PI180;  
	     ns2 = lat2 * DEF_PI180;  
	     // 经度差  
	     dew = ew1 - ew2;  
	     // 若跨东经和西经180 度，进行调整  
	     if (dew > DEF_PI)  
	     dew = DEF_2PI - dew;  
	    else if (dew < -DEF_PI)  
	     dew = DEF_2PI + dew;  
	     dx = DEF_R * Math.cos(ns1) * dew; // 东西方向长度(在纬度圈上的投影长度)  
	     dy = DEF_R * (ns1 - ns2); // 南北方向长度(在经度圈上的投影长度)  
	     // 勾股定理求斜边长  
	     distance = Math.sqrt(dx * dx + dy * dy);  
	     return distance;  
	 }  
	   /**
	    * 百度地图-标准的球面大圆劣弧长度计算量坐标间的距离（适用于比较远的距离）
	    * @author dhr
	    * @param lon1 坐标1纬度
	    * @param lat1 坐标1经度
	    * @param lon2 坐标2纬度
	    * @param lat2 坐标2经度
	    * @return 米
	    */
	   public double GetLongDistance(double lon1, double lat1, double lon2, double lat2){ 
	   
	     double ew1, ns1, ew2, ns2;  
	     double distance;  
	     // 角度转换为弧度  
	     ew1 = lon1 * DEF_PI180;  
	     ns1 = lat1 * DEF_PI180;  
	     ew2 = lon2 * DEF_PI180;  
	     ns2 = lat2 * DEF_PI180;  
	     // 求大圆劣弧与球心所夹的角(弧度)  
	     distance = Math.sin(ns1) * Math.sin(ns2) + Math.cos(ns1) * Math.cos(ns2) * Math.cos(ew1 - ew2);  
	     // 调整到[-1..1]范围内，避免溢出  
	     if (distance > 1.0)  
	          distance = 1.0;  
	     else if (distance < -1.0)  
	           distance = -1.0;  
	     // 求大圆劣弧长度  
	     distance = DEF_R * Math.acos(distance);  
	    return distance;  
	}  
	   
	
}
