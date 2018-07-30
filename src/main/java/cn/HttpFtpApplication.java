package cn;


import java.util.List;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import cn.admin.core.AuthorizeInterceptor;
import cn.admin.core.ModelTraceProcessor;
import cn.admin.core.UploadServlet;
import cn.admin.core.UserSecurityInterceptor;


@Configuration
@EnableAutoConfiguration
@SpringBootApplication
@EnableScheduling // 定时任务
public class HttpFtpApplication extends WebMvcConfigurerAdapter {
	public static void main(String args[]) {
		SpringApplication.run(HttpFtpApplication.class);
	}
	
	@Bean
	@Profile({"product"})
	public EmbeddedServletContainerCustomizer containerCustomizer() {    
	    return new EmbeddedServletContainerCustomizer(){        
	        @Override        
	         public void customize(ConfigurableEmbeddedServletContainer container) {            
	            container.addErrorPages(new ErrorPage(HttpStatus.BAD_REQUEST, "/error/400"));            
	            container.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/500"));            
	            container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/error/404"));        
	        }    
	    };
	}
	
	@Bean
    public ServletRegistrationBean servletRegistrationBean() {
		ServletRegistrationBean bean = new ServletRegistrationBean(new UploadServlet(), "/uploader");
		bean.setMultipartConfig(new MultipartConfigElement(""));
		return bean;
    }
	
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		super.addArgumentResolvers(argumentResolvers);
		argumentResolvers.add(new ModelTraceProcessor());
	}

	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String root=System.getProperty("user.dir");
		registry.addResourceHandler("/upload/**").addResourceLocations("file:"+root+"\\upload\\");
		super.addResourceHandlers(registry);
	}
	/**
	 * 配置安全拦截器
	 * 
	 * @param registry
	 */
//	public void addInterceptors(InterceptorRegistry registry) {
//		
//		// 后台拦劫器
//		registry.addInterceptor(new UserSecurityInterceptor()).excludePathPatterns("/login/**").excludePathPatterns("/v1/**").excludePathPatterns("/api/**").excludePathPatterns("/error/**");
//
//		registry.addInterceptor(new AuthorizeInterceptor()).excludePathPatterns("/").excludePathPatterns("/login/**").excludePathPatterns("/v1/**").excludePathPatterns("/api/**").excludePathPatterns("/error/**");
//	}
}
