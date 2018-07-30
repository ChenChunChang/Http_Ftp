package cn.util;

import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;


public class NumberUtil {
	  public static Pattern patternInt = Pattern.compile("(^[+]?([1-9][0-9]*))");
	  public static Pattern patternDouble = Pattern.compile("(^[+]?([0-9]\\d*\\.?\\d{0,2}))");
	  /**
	   * 是否是数字
	   * @Description: TODO
	   */
	  public static boolean isNumeric(String str)
	  {
	    if (str==null) {
	      return false;
	    }
	    int i = str.length();
	    do { if (!Character.isDigit(str.charAt(i)))
	        return false;
	      i--; } while (i >= 0);

	    return true;
	  }

	  /**
	   * 是否是正整数
	   */
	  public static boolean isNumericInt(String str)
	  {
	    if (str == null) {
	      return false;
	    }
	    if(str.equals("0")){
	    	return true;
	    }
	    return patternInt.matcher(str).matches();
	  }

	  /**
	   * 是否是double
	   */
	  public static boolean isNumericDouble(String str)
	  {
	    if (str == null) {
	      return false;
	    }
	    return (patternDouble.matcher(str).matches()) || (isNumericInt(str));
	  }
	  /**
	   * 是否是boolean
	   * @Description: TODO
	   */
	  public static boolean isBoolean(String str) {
	    if (str == null) {
	      return false;
	    }

	    return (str.equals("true")) || (str.equals("false"));
	  }
	  /**
	   * 是否是日期
	   */
	  public static boolean isDate(String str) {
	    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	    try
	    {
	      format.parse(str);
	    } catch (ParseException e) {
	      return false;
	    }

	    return true;
	  }
	  public static boolean isDateTime(String str) {
		    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    try
		    {
		      format.parse(str);
		    } catch (ParseException e) {
		      return false;
		    }

		    return true;
		  }
	

	  public static String getBillNo()
	  {
	    Date currentTime = new Date();
	    SimpleDateFormat formatter2 = new SimpleDateFormat("yyyyMMddHHmmss");
	    Random random = new Random();
	    String billno = formatter2.format(currentTime) + random.nextInt(9) + random.nextInt(9) + random.nextInt(9) + random.nextInt(9) + random.nextInt(9) + random.nextInt(9);
	    return billno;
	  }

	  public static String getBillNo(String uid)
	  {
	    Date currentTime = new Date();
	    String date = new SimpleDateFormat("yyyyMMddHHmmss").format(currentTime);
	    long noNum=Long.parseLong(date)*1000;
	    Random random = new Random();
	    String billno = noNum+""+uid+random.nextInt(9) + random.nextInt(9);

	    return billno;
	  }

	  public static long getLongVal(String value)
	  {
	    return Long.parseLong(value);
	  }

	  /**
		 * 将msg采用MD5算法处理,返回一个String结果
		 * @param msg 明文
		 * @return 密文
		 */
		public static String md5(String msg){
			try{
				MessageDigest md = 
					MessageDigest.getInstance("MD5");
				//原始信息input
				byte[] input = msg.getBytes();
				//加密信息output
				byte[] output = md.digest(input);//加密处理
				//采用Base64将加密内容output转成String字符串
				String s = Base64.encodeBase64String(output);
				return s;
			}catch(Exception ex){
				System.out.println("md5加密失败");
				return null;
			}
		}
}
