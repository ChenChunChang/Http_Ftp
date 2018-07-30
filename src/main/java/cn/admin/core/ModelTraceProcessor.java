package cn.admin.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.constraints.Null;

import org.h2.util.StringUtils;
import org.hibernate.validator.internal.xml.ParameterType;
import org.springframework.beans.BeanUtils;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.annotation.ModelFactory;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import cn.util.SpringContextUtils;

import org.springframework.web.bind.support.WebRequestDataBinder;

@Component
public class ModelTraceProcessor implements HandlerMethodArgumentResolver {
	
	private Object createAttribute(MethodParameter methodParam, NativeWebRequest request){
		
		Class<?> parameterType=methodParam.getParameterType();
		ModelTrace trace=parameterType.getAnnotation(ModelTrace.class);
		Class<?> serviceType=trace.serviceType();
		String keyName=trace.key();
		String key=request.getParameter(keyName);
		if (serviceType != null && serviceType.isAssignableFrom(TraceService.class) && key!=null || StringUtils.isNumber(key)) {
			int id=Integer.parseInt(key);
			TraceService tService = (TraceService)SpringContextUtils.getContext().getBean(serviceType);
			if (tService!=null) {
				return tService.get(id);
			}
		}
		return BeanUtils.instantiateClass(parameterType);
	}
	
	protected void bindRequestParameters(WebDataBinder binder, NativeWebRequest request) {
		ServletRequest servletRequest = request.getNativeRequest(ServletRequest.class);
		ServletRequestDataBinder servletBinder = (ServletRequestDataBinder) binder;
		servletBinder.bind(servletRequest);
	}
	
	protected void validateIfApplicable(WebDataBinder binder, MethodParameter methodParam) {
		Annotation[] annotations = methodParam.getParameterAnnotations();
		for (Annotation ann : annotations) {
			Validated validatedAnn = AnnotationUtils.getAnnotation(ann, Validated.class);
			if (validatedAnn != null || ann.annotationType().getSimpleName().startsWith("Valid")) {
				Object hints = (validatedAnn != null ? validatedAnn.value() : AnnotationUtils.getValue(ann));
				Object[] validationHints = (hints instanceof Object[] ? (Object[]) hints : new Object[] {hints});
				binder.validate(validationHints);
				break;
			}
		}
	}
	
	protected boolean isBindExceptionRequired(WebDataBinder binder, MethodParameter methodParam) {
		int i = methodParam.getParameterIndex();
		Class<?>[] paramTypes = methodParam.getMethod().getParameterTypes();
		boolean hasBindingResult = (paramTypes.length > (i + 1) && Errors.class.isAssignableFrom(paramTypes[i + 1]));
		return !hasBindingResult;
	}
	
	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {
		
		
		String name = ModelFactory.getNameForParameter(parameter);
		Class<?> parameterType=parameter.getParameterType();

		Object attribute = (mavContainer.containsAttribute(name) ? mavContainer.getModel().get(name) :
			createAttribute(parameter,webRequest));
		
		Map<String, Object> oldValueMap=new HashMap<String,Object>();
		List<Field> fields=new ArrayList<Field>();
		
		for(Field field : parameterType.getDeclaredFields()){
			if (field.isAnnotationPresent(PropertyTrace.class)) {
				fields.add(field);
				field.setAccessible(true);
				oldValueMap.put(field.getName(), field.get(attribute));
			}
		}
		ModelTrace mTrace=parameterType.getAnnotation(ModelTrace.class);
		WebDataBinder binder = binderFactory.createBinder(webRequest, attribute, name);
		if (binder.getTarget() != null) {
			bindRequestParameters(binder, webRequest);
			validateIfApplicable(binder, parameter);
			if (binder.getBindingResult().hasErrors() && isBindExceptionRequired(binder, parameter)) {
				throw new BindException(binder.getBindingResult());
			}
		}

		Map<String, Object> bindingResultModel = binder.getBindingResult().getModel();
		mavContainer.removeAttributes(bindingResultModel);
		mavContainer.addAllAttributes(bindingResultModel);

		Object bindResult = binder.convertIfNecessary(binder.getTarget(), parameter.getParameterType(), parameter);
		
		//比较新的值
		String log="";
		for(Field field : fields){
			Object vObject=oldValueMap.get(field.getName());
			Object newValue=field.get(bindResult);
			if ((vObject==null && newValue!=null) || (vObject!=null && newValue==null) || (vObject!=null && !vObject.equals(newValue))) {
				PropertyTrace trace = field.getAnnotation(PropertyTrace.class);
				//判断是否是日期类型，日期类型额外处理
				if(vObject instanceof Date && newValue instanceof Date){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					log+=trace.name()+"由"+(vObject!=null?sdf.format(vObject):"空值")+"更改为"+(newValue!=null?sdf.format(newValue):"空值")+";";
				}else{
					log+=trace.name()+"由"+(vObject!=null?vObject.toString():"空值")+"更改为"+(newValue!=null?newValue.toString():"空值")+";";
				}
			}
		}
		
		if (!StringUtils.isNullOrEmpty(log)) {
			Object userId= webRequest.getAttribute("userId", RequestAttributes.SCOPE_SESSION);
			if (userId!=null) {
				//写库
				try {
					//获取当前修改对象的主键值
					String keyName= mTrace.key();
					String key= webRequest.getParameter(keyName);
					
					TraceLogService logService=SpringContextUtils.getContext().getBean(TraceLogService.class);
					int userIdInt=Integer.parseInt(userId.toString());
					if(!logService.insertLog(Integer.parseInt(key),parameterType.getName(), userIdInt,"更新", log)){
						throw new Exception("add "+mTrace.name()+"'s log fail");
					}
						
				} catch (Exception e) {
					throw new Exception("add "+mTrace.name()+"'s log fail");
				}
				
			}
		}
		
		return bindResult;
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(TraceParameter.class) && parameter.getParameterType().isAnnotationPresent(ModelTrace.class);
	}

	
	
}
