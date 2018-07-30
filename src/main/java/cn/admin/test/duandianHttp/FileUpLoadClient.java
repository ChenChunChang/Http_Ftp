package cn.admin.test.duandianHttp;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.io.FileNotFoundException;  
import java.io.IOException;  
import java.io.InputStream;  
import java.io.RandomAccessFile;  
import java.net.HttpURLConnection;  
import java.net.MalformedURLException;  
import java.net.URL; 

@Controller
public class FileUpLoadClient{
	
	//@Scheduled(cron = "*/5 * * * * ?")
	public void testrun() throws IOException {  
         
	     load(0,1000);  
	     load(1001,0);  
	      
	} 
	
	//客户端
	public static void load(int start, int end) throws MalformedURLException, FileNotFoundException{  
        
        String endpoint = "http://192.168.168.101:1106/admin/ddHttp/runddHttp?id=5";  
              
        URL url = new URL(endpoint);  
        try {  
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
            conn.setRequestProperty("Content-Type","text/plain; charset=UTF-8");   
            conn.setRequestProperty("RANGE","bytes="+start+"-"+end); //header中增加range属性           
            conn.connect();  
            System.out.println(conn.getResponseCode());  
            System.out.println(conn.getContentLength());  
            System.out.println(conn.getContentType());  
            InputStream ins = (InputStream)conn.getContent();     
            String fileName=conn.getHeaderField("Content-Disposition");  
            fileName = new String(fileName.getBytes("ISO8859-1"), "UTF-8");  
            fileName=fileName.substring(fileName.lastIndexOf("\\")+1);  
            System.out.println(fileName);  
            RandomAccessFile raFile = new RandomAccessFile("E:\\httpUpload\\"+fileName, "rw");  
            raFile.seek(start);           
            byte[] buffer = new byte[4096];  
            int len = -1;  
            while((len = ins.read(buffer))!=-1){  
                raFile.write(buffer,0,len);  
            }  
            raFile.close();  
            conn.disconnect();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
  
}
