package cn;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/error")
public class GlobalErrorController  {
	@RequestMapping(value="notallow")
	public String notAllow(){
		return "/admin/error/notallow";
	}
	
	@RequestMapping(value="/{code}")
	public String index(@PathVariable String code){
		return "/admin/error/exception";
	}
}
