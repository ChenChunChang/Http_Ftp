package cn.admin.test.httpUpload;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
public class runHttp {
		
	//配置文件中限制的文件的大小
	@Value("${multipart.maxFileSize}")
	private String size;
	
	//@Scheduled(cron = "*/5 * * * * ?")
	public void timingUpload() throws Exception {
		
		// 上传文件 POST 同步方法
		try {
			Map<String,String> uploadParams = new LinkedHashMap<String, String>();
			
			//String serverUrl = "http://192.168.168.101:1106/admin/receiveHttp/uploadPic";//服务器地址
			String serverUrl = "http://39.107.239.105:9092/api/personal/uploadPic";
			String path = "E:/yingxiang";
			File file = new File(path);
			File[] tempList = file.listFiles();
			for (int i = 0; i < tempList.length; i++) {
				File filedata = tempList[i];
				String fileName = filedata.getName();
				
				uploadParams.put("userImageContentType", "dcm");
				uploadParams.put("userImageFileName", fileName);
				
				String localFilePath = path+"/"+fileName; //本地文件路径
				
				HttpClientUtils.getInstance().uploadFileImpl(serverUrl, localFilePath,"dcmImage", uploadParams,size);
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
 
	}
	
}	

