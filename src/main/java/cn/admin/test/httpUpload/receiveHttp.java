package cn.admin.test.httpUpload;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import cn.util.Tools;

@Controller
@RequestMapping(value="/admin/receiveHttp")
public class receiveHttp {

		/**
		 * 影像文件上传
		 * @param files
		 * @return
		 */
		@RequestMapping(value="/uploadPic")
		public Map<String, Object> uploadImg(@RequestParam("dcmImage") MultipartFile files) {
			Map<String, Object> result = new HashMap<String, Object>();
			
			// 文件上传
			String imgUrl = "";
			// 上传的文件保存路径
			String filePath = Tools.getUploadDir();
			
			String newFileName = "";
			// 获取路径 upload/Advertisement/yyyyMMdd/
			//String pathSub = filePath.substring(filePath.indexOf("upload")).replace("\\", "/");
			if (files.isEmpty()) {
				imgUrl = "";
			} else {
				newFileName = files.getOriginalFilename();// 获取文件名称
				String newFilePath = filePath + newFileName;// 新路径
				File newFile = new File(newFilePath);
				if (newFile.exists()) {
					//String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
					newFileName = files.getOriginalFilename();
					newFilePath = filePath + newFileName;
					newFile = new File(newFilePath);
				}
				try {
					files.transferTo(newFile);
				} catch (Exception e) {
					result.put("msg", "上传失败!"+e.getMessage());
				};
				//imgUrl = "/" + pathSub + newFileName;// 保存到数据库的路径
			}
			if(StringUtils.isEmpty(newFileName)){
				result.put("result", 0); 
				result.put("msg", newFileName+":上传失败!");
			}else{
				result.put("result", 1); 
				result.put("msg", newFileName+":上传成功!");
			}
			System.out.println(result);
			return result;
		}  
		
}
