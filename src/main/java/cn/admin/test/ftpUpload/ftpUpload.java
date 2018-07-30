package cn.admin.test.ftpUpload;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;


@Controller
public class ftpUpload {

	//@Scheduled(cron = "*/5 * * * * ?")
	public void timingUpload() throws IOException {
		String path = "E:/yingxiang";
		File file = new File(path);
		File[] tempList = file.listFiles();
		for (int i = 0; i < tempList.length; i++) {
			File filedata = tempList[i];
			ftpupload(path + "/" + filedata.getName(),filedata.getName());
		}
	}
	
	
	// 文件的跨服务器上传   filedata：要上传的本地文件的绝对路径    localFileName：要上传的文件的名称
	public static void ftpupload(String filedata,String localFileName) {
		
		String ftpHost = "192.168.168.101";
		int ftpPort = 21;// 服务器端口
		String ftpUserName = "administrator";
		String ftpPassword = "000000";
		String ftpPath = "E:/ftpUpload";
		getFTPClient(ftpHost, ftpPassword, ftpUserName, ftpPort);
		
		// 创建客户端对象
		FTPClient ftpClient = new FTPClient();
		ftpClient.enterLocalPassiveMode(); // 这一句话一定要记得加上
		InputStream local = null;
		try {
			// 连接ftp服务器
			ftpClient.connect(ftpHost, ftpPort);
			// 登录
			ftpClient.login(ftpUserName, ftpPassword);
			// 检查上传路径是否存在 如果不存在返回false
			boolean flag = ftpClient.changeWorkingDirectory(ftpPath);
			if (!flag) {
				// 创建上传的路径 该方法只能创建一级目录，在这里如果/home/ftpuser存在则可创建image
				ftpClient.makeDirectory(ftpPath);
			}
			
			// 指定上传路径
			ftpClient.changeWorkingDirectory(ftpPath);
			// 指定上传文件的类型 二进制文件
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			
			// 读取本地文件
			File file = new File(filedata);
			local = new FileInputStream(file);
			
			//判断该文件在文件中心是否存在
//			FTPFile[] files = ftpClient.listFiles(ftpPath+"/"+localFileName);//该路径为该文件在影像中心的绝对路径
//			if (files.length == 1) {// 软件中心包含该文件
//				long remoteSize = files[0].getSize();// 软件中心的文件大小
//				long localSize = file.length();// 打算要上传的文件大小
//				if (remoteSize == localSize) { // 软件中心有这个文件，并且和打算要上传的文件大小一样，则说要上传的文件已存在
//					System.out.println("要上传的文件已存在");
//					ftpClient.disconnect();
//					return;
//				} else if (remoteSize > localSize) {// 软件中心的文件比要上传的大，可能新上传的文件被修改了，然后再次上传的
//					System.out.println("软件中心的软件比即将上传的要大，无须上传或重新命名要上传的文件名");
//					ftpClient.disconnect();
//					return;
//				}
//				// 软件中心存的文件比要上传的文件小，则尝试移动文件内读取指针,实现断点续传 **************
//				if (local.skip(remoteSize) == remoteSize) {
//					ftpClient.setRestartOffset(remoteSize);
//					boolean i = ftpClient.storeFile(new String(localFileName.getBytes("UTF-8"), "iso-8859-1"), local);
//					if (i) {
//						System.out.println("文件断点续传成功");
//						ftpClient.disconnect();
//						return;
//					}
//				}
//			} else { // 软件中心不包含要上传的文件，或者续传不成功，则上传全新的文件即可
//				boolean i = ftpClient.storeFile(new String(localFileName.getBytes("UTF-8"), "iso-8859-1"), local);
//				System.out.println("文件上传" + i);
//			}
			
			
			// 第一个参数是文件名
			ftpClient.storeFile(file.getName(), local);
			
		} catch (SocketException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 关闭文件流
				local.close();
				// 退出
				ftpClient.logout();
				// 断开连接
				ftpClient.disconnect();
				
				boolean aa =  deleteFile(filedata);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取FTPClient对象
	 * 
	 * @param ftpHost
	 *            FTP主机服务器
	 * @param ftpPassword
	 *            FTP 登录密码
	 * @param ftpUserName
	 *            FTP登录用户名
	 * @param ftpPort
	 *            FTP端口 默认为21
	 * @return
	 */
	private static Logger logger = Logger.getLogger(ftpUpload.class);

	public static FTPClient getFTPClient(String ftpHost, String ftpPassword, String ftpUserName, int ftpPort) {

		FTPClient ftpClient = null;
		try {
			ftpClient = new FTPClient();
			ftpClient.connect(ftpHost, ftpPort);// 连接FTP服务器
			ftpClient.login(ftpUserName, ftpPassword);// 登陆FTP服务器
			if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
				logger.info("未连接到FTP，用户名或密码错误 ！");
				ftpClient.disconnect();
			} else {
				logger.info("FTP连接成功。");
			}
		} catch (SocketException e) {
			e.printStackTrace();
			logger.info("请检查FTP的IP地址 ！ ");
		} catch (IOException e) {
			e.printStackTrace();
			logger.info("请核对FTP的端口 ！ ");
		}
		return ftpClient;
	}
	
	/**
     * 删除单个文件
     * @param   sPath    被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }
	
}
