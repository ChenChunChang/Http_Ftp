package cn.admin.test.ftpUploadduandian;

import java.io.File;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
public class runFtp {
	
	//@Scheduled(cron = "*/5 * * * * ?")
	public void testrun() {
		
		String ftpUrl = "192.168.168.101";
		String userName = "administrator";
		String pwd = "000000";
		String port = "21";
		
		String path = "E:/yingxiang";
		String fileName = "";
		File file = new File(path);
		File[] tempList = file.listFiles();
		for (int i = 0; i < tempList.length; i++) {
			File filedata = tempList[i];
			fileName = filedata.getName();
			String fileData = path+"/"+fileName;
			
			//if(i%2==0){//i为偶数
				ContinueFTP2 runner2 = new ContinueFTP2(ftpUrl, userName, pwd, port, fileData,fileName);
				Thread thread2 = new Thread(runner2);
				//thread2.start();
				thread2.run();
//			}else{//i为基数
//				ContinueFTP2 runner1 = new ContinueFTP2(ftpUrl, userName, pwd, port, fileData,fileName);
//				Thread thread1 = new Thread(runner1);
//				// thread1.start();
//				thread1.run();
//			}
		}
	}
	
	
	
}
