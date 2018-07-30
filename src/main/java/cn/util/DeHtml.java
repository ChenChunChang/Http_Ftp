package cn.util;

import java.util.regex.Matcher; 
import java.util.regex.Pattern;

public class DeHtml {
	    public static String delHTMLTag(String htmlStr){ 
	        String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式 
	        String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式 
	        String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式 
	         
	        Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE); 
	        Matcher m_script=p_script.matcher(htmlStr); 
	        htmlStr=m_script.replaceAll(""); //过滤script标签 
	         
	        Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE); 
	        Matcher m_style=p_style.matcher(htmlStr); 
	        htmlStr=m_style.replaceAll(""); //过滤style标签 
	         
	        Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE); 
	        Matcher m_html=p_html.matcher(htmlStr); 
	        htmlStr=m_html.replaceAll(""); //过滤html标签 

	        return htmlStr.trim(); //返回文本字符串 
	    } 
	    /**
	     * 根据两个位置的经纬度，来计算两地的距离（单位为KM）
	     * 参数为String类型
	     * @param lat1 用户经度
	     * @param lng1 用户纬度
	     * @param lat2 商家经度
	     * @param lng2 商家纬度
	     * @return
	     */
	    //根据经纬度计算距离
	    private static double EARTH_RADIUS = 6378.137;
	    private static double rad(double d)
	    {
	       return d * Math.PI / 180.0;
	    }
	    
	    public static String getDistance(String lat1Str, String lng1Str, String lat2Str, String lng2Str) {
	        Double lat1 = Double.parseDouble(lat1Str);
	        Double lng1 = Double.parseDouble(lng1Str);
	        Double lat2 = Double.parseDouble(lat2Str);
	        Double lng2 = Double.parseDouble(lng2Str);
	         
	        double radLat1 = rad(lat1);
	        double radLat2 = rad(lat2);
	        double difference = radLat1 - radLat2;
	        double mdifference = rad(lng1) - rad(lng2);
	        double distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(difference / 2), 2)
	                + Math.cos(radLat1) * Math.cos(radLat2)
	                * Math.pow(Math.sin(mdifference / 2), 2)));
	        distance = distance * EARTH_RADIUS;
	        distance = Math.round(distance * 10000) / 10000;
	        String distanceStr = distance+"";
	        distanceStr = distanceStr.
	            substring(0, distanceStr.indexOf("."));
	         
	        return distanceStr;
	    }
}
