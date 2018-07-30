package cn.admin.test.httpUpload;

import java.io.File;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;


@Controller
public class HttpClientUtils {
		
	/**
	 * 最大线程池
	 */
	public static final int THREAD_POOL_SIZE = 5;
 
	public interface HttpClientDownLoadProgress {
		public void onProgress(int progress);
	}
 
	private static HttpClientUtils httpClientDownload;
 
	private ExecutorService downloadExcutorService;
 
	private HttpClientUtils() {
		downloadExcutorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
	}
 
	public static HttpClientUtils getInstance() {
		if (httpClientDownload == null) {
			httpClientDownload = new HttpClientUtils();
		}
		return httpClientDownload;
	}

	
	/**
	 * 上传文件
	 * 
	 * @param serverUrl 服务器地址
	 *            
	 * @param localFilePath 本地文件路径
	 * @param serverFieldName
	 * @param params 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String uploadFileImpl(String serverUrl, String localFilePath,String serverFieldName, Map<String, String> params,String size)throws Exception {
		
		String respStr = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpPost httppost = new HttpPost(serverUrl);
			FileBody binFileBody = new FileBody(new File(localFilePath));
 
			MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
					
			// add the file params
			multipartEntityBuilder.addPart(serverFieldName, binFileBody);
			// 设置上传的其他参数
			setUploadParams(multipartEntityBuilder, params);
 
			HttpEntity reqEntity = multipartEntityBuilder.build();
			httppost.setEntity(reqEntity);
			
			//判断文件是否超出配置大小
			File file = new File(localFilePath);
			double fileSizeMB = getPrintSize(file.length());//文件大小GB
			//系统配置文件大小
			int peizhiSize = Integer.parseInt(size.substring(0, size.lastIndexOf("Mb")));
			
			if(peizhiSize>=fileSizeMB) {
				CloseableHttpResponse response = httpclient.execute(httppost);
				try {
					//System.out.println(response.getStatusLine());
					HttpEntity resEntity = response.getEntity();
					respStr = getRespString(resEntity);
					EntityUtils.consume(resEntity);
					
					String status = response.getStatusLine().toString();
					if(status.indexOf("200")>0) {//该文件上传成功后进行删除
						//删除文件
						deleteFile(localFilePath);
					}
					
					
				} finally {
					response.close();
				}
			}
			
		} finally {
			httpclient.close();
		}
		//System.out.println("resp=" + respStr);
		return respStr;
	}
 
	/**
	 * 设置上传文件时所附带的其他参数
	 * 
	 * @param multipartEntityBuilder
	 * @param params
	 */
	private void setUploadParams(MultipartEntityBuilder multipartEntityBuilder,Map<String, String> params) {
		if (params != null && params.size() > 0) {
			Set<String> keys = params.keySet();
			for (String key : keys) {
				multipartEntityBuilder.addPart(key, new StringBody(params.get(key),ContentType.TEXT_PLAIN));
			}
		}
	}
 
	/**
	 * 将返回结果转化为String
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	private String getRespString(HttpEntity entity) throws Exception {
		if (entity == null) {
			return null;
		}
		InputStream is = entity.getContent();
		StringBuffer strBuf = new StringBuffer();
		byte[] buffer = new byte[4096];
		int r = 0;
		while ((r = is.read(buffer)) > 0) {
			strBuf.append(new String(buffer, 0, r, "UTF-8"));
		}
		return strBuf.toString();
	}
	
	/**
     * 删除单个文件
     * @param   sPath    被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }
    
	public static double getPrintSize(long size) {
		 //获取到的size为：1705230
        int GB = 1024 * 1024 * 1024;//定义GB的计算常量
        int MB = 1024 * 1024;//定义MB的计算常量
        int KB = 1024;//定义KB的计算常量
        DecimalFormat df = new DecimalFormat("0.00");//格式化小数
        String resultSize = "";
        double result = 0;
        if (size / GB >= 1) {
            //如果当前Byte的值大于等于1GB
            resultSize = df.format(size / (float) GB);// + "GB   ";
            
            resultSize = df.format(Double.valueOf(resultSize) * (float)1024); //MB
        } else if (size / MB >= 1) {
            //如果当前Byte的值大于等于1MB
            resultSize = df.format(size / (float) MB);// + "MB   ";
        } else if (size / KB >= 1) {
            //如果当前Byte的值大于等于1KB
            resultSize = df.format(size / (float) KB);// + "KB   ";
            
            resultSize = df.format(Double.valueOf(resultSize) / (float) 1024); //MB
        } 
//        else {
//            resultSize = size+"";// + "B   ";
//        }
        
        result = Double.valueOf(resultSize);
        
        return result;
	}

	
}
