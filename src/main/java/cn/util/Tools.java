package cn.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.jasper.tagplugins.jstl.core.Out;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.h2.util.New;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.admin.core.Authorize;

import com.alibaba.druid.util.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
@Service
public class Tools {
	/**
	 * json转Map
	 * @author dhr
	 * @param json
	 * @return
	 */
	public static Map<String, Object> JsonToMap(String json) {
		Map<String, Object> map = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			map = objectMapper.readValue(json, HashMap.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * json转List
	 * @author dhr
	 * @param json
	 * @return
	 */
	public static List JsonTolist(String json) {
		List list =null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			 list = new ObjectMapper().readValue(json, ArrayList.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	/** 
	 * 将msg采用MD5算法处理,返回一个String结果
	 * @param msg 明文
	 * @return 密文
	 */
	public static String md5(String msg){
		try{
			MessageDigest md = 
				MessageDigest.getInstance("MD5");
			//原始信息input
			byte[] input = msg.getBytes();
			//加密信息output
			byte[] output = md.digest(input);//加密处理
			//采用Base64将加密内容output转成String字符串
			String s = Base64.encodeBase64String(output);
			return s;
		}catch(Exception ex){
			System.out.println("md5加密失败");
			return null;
		}
	}
	
	/**
	 * 字符串按逗号分隔为List
	 * @author dhr
	 * @param photoString
	 * @return
	 */
	public static List handleString(String photoString) {
		List list = new ArrayList();
		String[] strs = photoString.split(",");
		for(String str:strs){
			list.add(str);
		}
		return list;
	}
	
	/**
	 * 上传单张图片
	 * @author dhr
	 * @param files 文件
	 * @param path 上传地址
	 * @return
	 */
	public static String saveUploadFile(MultipartFile files,String path){
		//上传图片
		String fileSavePath="";
		//上传的图片保存路径
		String filePath=path;
		File pathFile = new File(filePath);// 建文件夹
		if (!pathFile.exists()) {
			pathFile.mkdirs();
		}
		MultipartFile upFile = files;
		if(files.isEmpty()){
			fileSavePath="";
		}else{
			String newFileName =UUID.randomUUID().toString()+Tools.getFileExtension(upFile.getOriginalFilename());
			String newFilePath = filePath + newFileName;// 新路径
			File newFile = new File(newFilePath);
			try {
				upFile.transferTo(newFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
			fileSavePath=Tools.getFileVirtualPath(newFilePath);//保存到数据库的路径
		}
		return fileSavePath;
	}
	
	/**
	 * 上传多张图片
	 * @author dhr
	 * @param filess 多个文件数组
	 * @param path 上传地址
	 * @return
	 */
	public static String addImages(MultipartFile[] filess,String path){
		//图片的个数
		//System.out.println("files:"+filess.length);
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(
				"yyyyMMddHHmmss");
		//存到数据库的图片集合名称
		String SiteLogos = "";
		for(MultipartFile files : filess){
			//上传图片
			String SiteLogo="";
			//上传的图片保存路径
			String filePath=path;
			//建文件夹
			File pathFile = new File(filePath);
			//如果这个文件夹存在!pathFile.exists()会返回false
			if (!pathFile.exists()) {
				//建立目录文件夹
				pathFile.mkdirs();
			}
			MultipartFile upFile = files;
			//判断要上传的文件是否为空，如果为空则返回true
			if(files.isEmpty()){
				SiteLogo="";
			}else{
				//获取图片名称
				String newFileName = upFile.getOriginalFilename();
				// 图片保存路径
				String newFilePath = filePath + newFileName;
				File newFile = new File(newFilePath);
				//如果这个图片名字存在！newFile.exists()会返回true
				if (newFile.exists()) {
					//获取新的图片名
					newFileName = sdf.format(new Date()) + "_"
							+ upFile.getOriginalFilename();
					newFilePath = filePath + newFileName;
					newFile = new File(newFilePath);
				}
				try {
					upFile.transferTo(newFile);
				} catch (Exception e) {
					e.printStackTrace();
				}
				//保存到数据库的路径
				SiteLogo=path+newFileName+",";
				//将所有图片名称按照逗号拼接
				SiteLogos=SiteLogos+SiteLogo;
			}
		}
		return SiteLogos;
	}
	
	
	
	/**
	 * 删除图片
	 * @param preFilePath 配置文件路径
	 * @param url 文件路径
	 */
	public static void delImg(String url){
		try {
			String dir=System.getProperty("user.dir");
			if(!StringUtils.isEmpty(url)){
				String path=dir+url.replace("/", "\\");
				File file=new File(path);
				if(file.exists()){
					file.delete();
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	public static String getUploadDir(){
		SimpleDateFormat sdfFolderName = new SimpleDateFormat("yyyyMMdd");  
        String newFolderName = sdfFolderName.format(new Date()); 
        String userDir=System.getProperty("user.dir");
        String path = "E:\\httpUpload\\";
        //String path = userDir + "\\upload\\" + newFolderName + "\\";
        File file=new File(path);
        if (!file.exists()) {
			file.mkdirs();
		}
        return path;
	}
	
	public  static String getFileVirtualPath(String path) {
		return path.replace(System.getProperty("user.dir"),"").replaceAll("\\\\", "/");
	}
	
	public static String getFileExtension(String file){
		if (com.mysql.jdbc.StringUtils.isNullOrEmpty(file) || file.lastIndexOf(".")==-1) {
			return "";
		}
		return file.substring(file.lastIndexOf("."));
	}
	
	public static String getTextFromWord(String path){
		File file=new File(path);
		String text="";
		try {
			InputStream is = new FileInputStream(file);
			WordExtractor ex = new WordExtractor(is);
			text= ex.getText();
		} catch (Exception e) {
			OPCPackage opcPackage;
			try {
				opcPackage = POIXMLDocument.openPackage(path);
				POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
				text = extractor.getText();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
		}
		
		return text;
	}
				
}
