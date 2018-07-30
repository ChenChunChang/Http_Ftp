package cn.admin.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.h2.util.New;
import org.springframework.context.annotation.Configuration;

import cn.admin.core.Authorize.AuthorizeType;
@Configuration
public class AuthorizeTag extends TagSupport {

	private static final long serialVersionUID = 1L;
	private String setting;
	private AuthorizeType type;
	@Override
	public int doStartTag() throws JspException {
		HttpSession session = this.pageContext.getSession();
		return AuthorizeVerifier.has(session,setting,type)? EVAL_BODY_INCLUDE : SKIP_BODY; 
	}
	public String getSetting() {
		return setting;
	}
	public void setSetting(String setting) {
		this.setting = setting;
	}
	public AuthorizeType getType() {
		return type;
	}
	public void setType(AuthorizeType type) {
		this.type = type;
	}
}
