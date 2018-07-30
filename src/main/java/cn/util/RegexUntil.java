package cn.util;

/**
 * 正则格式验证
 * @author LuBin
 * @date 2016年7月23日 上午11:07:09
 */
public class RegexUntil
{
	/**
	 * 验证用户名的格式
	 * @Description: TODO
	 */
  public static boolean isValidUsername(String username)
  {
    if (username==null) {
      return false;
    }
    return username.matches("^[A-Za-z0-9_]{3,10}$");
  }
  
  /**
   * 验证身份证号
   */
  public static boolean isIdentityCard(String card){
	  if(card==null){
		  return false;
	  }
	  if(card.matches("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$") || card.matches("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$")){
		  return true;
	  }else{
		  return false;
	  }
  }
  /** 
   * 验证密码的格式
   */
  public static boolean isValidPassword(String password)
  {
    if (password == null) {
      return false;
    }

    return password.matches("^([^\\s'‘’]{6,20})$");
  }
  /**
   * 验证手机的格式
   * @Description: TODO
   */
  public static boolean isMobileNum(String mobileNum)
  {
    if (mobileNum == null) {
      return false;
    }

    return mobileNum.matches("^((13[0-9])|(14[4,7])|(15[^4,\\D])|(17[6-8])|(18[0-9]))(\\d{8})$");
  }
  /**
   * 验证email的格式
   * @Description: TODO
   */
  public static boolean isEmail(String email)
  {
    if (email == null) {
      return false;
    }

    return email.matches("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$");
  }
  
  public static boolean isQQEmail(String email)
  {
    if (email == null) {
      return false;
    }
    int index = email.indexOf("@") + 1;

    if (-1 == index) {
      return false;
    }
    String qq = null;
    try
    {
      qq = email.substring(index, index + 2);
    } catch (Exception e) {
      return false;
    }

    return (qq.equals("qq")) || (qq.equals("QQ"));
  }
  /**
   * 验证是否是数字
   * @Description: TODO
   */
  public static boolean isNumber(String number)
  {
    if (number == null) {
      return false;
    }

    return number.matches("^[+-]?(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d)+)?$");
  }
  /**
   * 验证是否是整数
   * @Description: TODO
   */
  public static boolean isInt(String number)
  {
    if (number == null) {
      return false;
    }

    return number.matches("^[+-]?(([1-9]{1}\\d*)|([0]{1}))$");
  }
  /**
   * 验证是否是正数
   * @Description: TODO
   */
  public static boolean isPositiveInt(String number)
  {
    if (number == null) {
      return false;
    }

    return number.matches("^[+-]?(([1-9]{1}\\d*)|([0]{1}))$");
  }
  /**
   * 验证是否是时间
   * @Description: TODO
   */
  public static boolean isDate(String date)
  {
    if (date == null) {
      return false;
    }
    return date.matches("^([1-2]\\d{3})[\\/|\\-](0?[1-9]|10|11|12)[\\/|\\-]([1-2]?[0-9]|0[1-9]|30|31)$");
  }
  /**
   * 自定义正则表达式验证
   * @Description: TODO
   */
  public static boolean contains(String str, String regex)
  {
    if ((str == null) || (regex == null)) {
      return false;
    }
    return str.matches(regex);
  }
  /**
   * 判断是否为空
   * @Description: TODO
   */
  public static boolean isNull(String str)
  {
	  if((str == null) || (str=="")) {
	      return false;
	   }else{
		   return true;
	   }
  }
  
}
