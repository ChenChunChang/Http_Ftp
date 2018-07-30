package cn.admin.core;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import cn.admin.core.Authorize.AuthorizeType;
@Service
public class AuthorizeVerifier {
	public static boolean has(HttpSession session,Authorize authorize){
		return has(session, authorize.setting(),authorize.type());
	}
	
	public static boolean has(HttpSession session,String setting,AuthorizeType type){
		//AuthorityID=AuthorityID.substring(AuthorityID.lastIndexOf("-")+1);
		//setting=setting.substring(setting.indexOf(",")+1);
		List<String> aList=(List<String>) session.getAttribute("AuthorityID");
		String[] arr=setting.split(","); 
		int index = 0;
		for (int i = 0; i < arr.length; i++) {
			String sett=arr[i];
			if(type==type.ONE){
				for(String mm:aList){
					
					if(mm.equals(sett)){
						return true;
					}else{
						continue;
					}
				}
			}else{
				for(String mm:aList){
					
					if(mm.equals(sett)){
							index++;
							if(index==arr.length){
								return true;
							}
					}else{
						continue;
					}
				}
			}
			
		}
		
		return false;
	}
}
