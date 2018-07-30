package cn.admin.test.duandianHttp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;  
import java.io.RandomAccessFile;  
import javax.servlet.ServletException;  
import javax.servlet.ServletOutputStream;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  

@Controller
@RequestMapping(value="/admin/ddHttp")
public class LoadServlet {
		
	private static final long serialVersionUID = 237208504975097723L;  
	  
	@RequestMapping(value="/runddHttp")
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {     
         this.doGet(request, response);  
    } 
	
	//服务端
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {     
            
        int id = Integer.parseInt(request.getParameter("id"));  
        System.out.println(request.getContextPath());  
        //String path = request.getServletContext().getRealPath("/load");
        String path = "E:\\yingxiang";
        String filename = path+"\\repository.zip";  
            
        RandomAccessFile raFile = new RandomAccessFile(filename, "r");  
          
        String range = request.getHeader("RANGE");  
        int start=0, end=0;  
        if(null!=range && range.startsWith("bytes=")){  
            String[] values =range.split("=")[1].split("-");  
            start = Integer.parseInt(values[0]);  
            end = Integer.parseInt(values[1]);  
        }  
        int requestSize=0;  
        if(end!=0 && end > start){  
            requestSize = end - start + 1;  
            response.addHeader("content-length", ""+(requestSize));  
        } else {  
            requestSize = Integer.MAX_VALUE;  
        }  
          
        byte[] buffer = new byte[4096];           
        response.setContentType("application/x-download");        
        filename = new String(filename.getBytes("UTF-8"), "ISO8859-1");  
        response.addHeader("Content-Disposition", "attachment;filename="+filename);  
        ServletOutputStream os = response.getOutputStream();  
        int needSize = requestSize;  
        raFile.seek(start);  
        while(needSize > 0){  
            int len = raFile.read(buffer);  
            if(needSize < buffer.length){  
                os.write(buffer,0,needSize);  
            } else {  
                os.write(buffer,0,len);  
                if(len < buffer.length){  
                    break;  
                }  
            }  
            needSize -= buffer.length;  
        }  
              
        raFile.close();  
        os.close();  
  
    }  
  
}
