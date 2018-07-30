package cn.util;

public class myCode { 
	String code;
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public myCode() {
		number();
	}
	public myCode(int a) {
		number1(a);
	}
	//若不传参
	public void number(){
		//随机生成6位数字
		String[] num = {"0","1","2","3","5","6","8","q",
				"w","e","r","t","y","u","i","o","p","a","s","d","f",
				"g","h","j","k","l","z","x","c","v","b","n","m"};
		String b="";
		for(int i=0;i<6;i++){
			int d = (int)(Math.random()*33);
			String a = num[d];
			b+=a;
		}
		code=b;
	}
	//若传参
		public void number1(int n){
		//随机生成6位数字
		String[] num = {"0","1","2","3","5","6","8","q",
				"w","e","r","t","y","u","i","a","s","d","f",
				"g","h","j","k","l","z","x","c","v","b","n","m"};
		String b="";
		for(int i=0;i<n;i++){
			int d = (int)(Math.random()*33);
			String a = num[d];
			b+=a;
		}
		code=b;
	}

}
