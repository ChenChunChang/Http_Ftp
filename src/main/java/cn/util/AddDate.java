package cn.util;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

public class AddDate {
	  static int gudingdate=30;//查询45天
	  static int sum;//
	  static int num;
	  
	  
//	  public static void main(String[] args) throws ParseException {
//		  ArrayList list = new ArrayList();
//		  list = getdays();
//		  for(int i=list.size()-1;i>=0;i--) {
//			  System.out.println(list.get(i));// 输出系统当前时间45天的日期 格式是 格式： 20130921
//		  }
//		 
//	}
	  
	  
	  
	  
	  
	  
		public static ArrayList getdays() {
			 Calendar cal=Calendar.getInstance();//使用日历类
			 int year=cal.get(Calendar.YEAR);//得到年
			 int month=cal.get(Calendar.MONTH)+1;//得到月，因为从0开始的，所以要加1
			  int day=cal.get(Calendar.DAY_OF_MONTH);//得到天
			  int hour=cal.get(Calendar.HOUR);//得到小时
			  int minute=cal.get(Calendar.MINUTE);//得到分钟
			  int second=cal.get(Calendar.SECOND);//得到秒
//			  System.out.println("结果："+year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second);
			  
			  ArrayList list = new ArrayList();
			  list.add(month+"."+day);
					   sum= gudingdate-day;
					  //正常查 当期日期到 当月1日的数据  如果当月是 9月5日
					   
					   ///输出 4 - 1号的数据
					  for(int abc = day-1;abc>0;abc--) {
						  if(month<10) {
						 
						if(abc<10) {
							String sql =month+"."+abc;	
							list.add(sql);
						
							num=num+1;
						}
						else {
							String sql =month+"."+abc;
							list.add(sql);
							num=num+1;
						}
						  }
						  else {
								if(abc<10) {
									String sql =month+"."+abc;	
									list.add(sql);
									num=num+1;
								}
								else {
									String sql =month+"."+abc;
									list.add(sql);
									num=num+1;
								}
						  }
					  }
					  /****************************************************/
					  
					  
					  if(month==11){
						  int tianshu =sum -31;//减去当月时间
						  if(tianshu>0) {
							
							  for(int abc = 31;abc>0;abc--) {					  
									if(abc<10) {
										String sql =(month-1)+"."+abc;	
									
										list.add(sql);
										num=num+1;
									}  
									else {
										String sql =(month-1)+"."+abc;	
										list.add(sql);
										num=num+1;
									}
							  	}
							
						 for(int abc = 30 ;abc>=30-tianshu;abc--) {					  
									if(abc<10) {
										String sql =(month-2)+"."+abc;	
										list.add(sql);
										num=num+1;
									}  
									else {
										String sql =(month-2)+"."+abc;	
										list.add(sql);
										num=num+1;
									}
						  }
					  }		
						  
						//// 45减去当前日期剩余的时间小于 1个月
						  
						  else {
							  for(int abc = 31;abc>31-sum;abc--) {					  
									if(abc<10) {
										String sql =(month-1)+"."+abc;	
										list.add(sql);
										num=num+1;
									}  
									else {
										String sql =(month-1)+"."+abc;	
										list.add(sql);
										num=num+1;
									}

						  }						  						 
						  }
					  }
					  
					  //如果是小月                                如果选项的查询日期比较小      45 减去 查询日期 剩余时间大于1个月 的天数
					  if(month==2 || month==4 ||month==6 ||month==9) {
						  
						  
						  
						  
						  //如果当月9月份  上个月8月有31天   45 - 5 =  40  7月份 从 31号开始输出数据
					 
						  if(month==9) {//上个月
							  int aiaiai =sum -31;//减去当月的时间
							  if(aiaiai>0) {
								  //输出8月数据
								  for(int abc = 31;abc>0;abc--) {					  
										if(abc<10) {
											String sql =(month-1)+"."+abc;	
											list.add(sql);
											num=num+1;
										}  
										else {
											String sql =(month-1)+"."+abc;	
											list.add(sql);
											num=num+1;
										}

							  }
								  //输出7月份数据
							 for(int abc = 31 ;abc>=31-aiaiai;abc--) {					  
										if(abc<10) {
											String sql =(month-2)+"."+abc;	
											list.add(sql);
											num=num+1;
										}  
										else {
											String sql =(month-2)+"."+abc;	
											list.add(sql);
											num=num+1;
										}
							  }
						  }		
							  
							  //// 45减去当前日期剩余的时间小于 1个月
							  else {						
									 for(int abc = 31 ;abc>=31-sum;abc--) {					  
											if(abc<10) {
												String sql =(month-1)+"."+abc;	
												list.add(sql);
												num=num+1;
											}  
											else {
												String sql =(month-1)+"."+abc;	
												list.add(sql);
												num=num+1;
											}
								  }
							  }
					  }
						  else if (month!=4 && month!=2 && month!=9){//如果不是8月
							  int tianshu =sum -31;//减去当月时间
							  if(tianshu>0) {
								
								  for(int abc = 31;abc>0;abc--) {					  
										if(abc<10) {
											String sql =(month-1)+"."+abc;	
											list.add(sql);
											num=num+1;
										}  
										else {
											String sql =(month-1)+"."+abc;	
											list.add(sql);
											num=num+1;
										}
								  	}
								
							 for(int abc = 30 ;abc>=30-tianshu;abc--) {					  
										if(abc<10) {
											String sql =(month-2)+"."+abc;	
											list.add(sql);
											num=num+1;
										}  
										else {
											String sql =(month-2)+"."+abc;	
											list.add(sql);
											num=num+1;
										}
							  }
						  }		
							  
							//// 45减去当前日期剩余的时间小于 1个月
							  
							  else {
								  for(int abc = 31;abc>31-sum;abc--) {					  
										if(abc<10) {
											String sql =(month-1)+"."+abc;	
											list.add(sql);
											num=num+1;
										}  
										else {
											String sql =(month-1)+"."+abc;	
											list.add(sql);
											num=num+1;
										}

							  }
								  
								  
							  }
						  }  
					 
					  else if (month==4) { // 如果4月
						  int tianshu =sum -31;// 剩余天数
						  if(tianshu>0) {
							  //输出3月数据
							  for(int abc = 31;abc>0;abc--) {					  
									if(abc<10) {
										String sql =(month-1)+"."+abc;	
										list.add(sql);
										num=num+1;
									}  
									else {
										String sql =(month-1)+"."+abc;	
										list.add(sql);
										num=num+1;
									}

						  }
							  //输入2月数据
							  if((year%4==0&&year%1!=0)||(year%400==0))  //输出2月份数据   // 闰年
							  {
									 for(int abc = 29 ;abc>=29-tianshu;abc--) {					  
											if(abc<10) {
												String sql =(month-2)+"."+abc;	
												list.add(sql);
												num=num+1;
											}  
											else {
												String sql =(month-2)+"."+abc;	
												list.add(sql);
												num=num+1;
											}
								  }  
							  }
							  else {
								  
								//输出2月份数据   // 平年
								  for(int abc = 28 ;abc>28-tianshu;abc--) {					  
										if(abc<10) {
											String sql =(month-2)+"."+abc;	
											list.add(sql);
											num=num+1;
										}  
										else {
											String sql =(month-2)+"."+abc;	
											list.add(sql);
											num=num+1;
										}
							  }
							  }	  
							
				
					  }	
						//// 45减去当前日期剩余的时间小于 1个月
							  else {
								  for(int abc = 31;abc>31-sum;abc--) {					  
										if(abc<10) {
											String sql =(month-1)+"."+abc;	
											list.add(sql);
											num=num+1;
										}  
										else {
											String sql =(month-1)+"."+abc;	
											list.add(sql);
											num=num+1;
										}

							  }
								  
								  
							  }
						  }
						  
						  
						  
						  
					  
					  else if (month==2) {
						//输出2月份数据   // 闰年
						  if((year%4==0&&year%1!=0)||(year%400==0))  
						  {
						  int tianshu =sum -31;// 剩余天数
						  if(tianshu>0) {
						 
						  
								 for(int abc = 31 ;abc>0;abc--) {					  
										if(abc<10) {
											String sql =(month-1)+"."+abc;	
											list.add(sql);
											num=num+1;
										}  
										else {
											String sql =(month-1)+"."+abc;	
											list.add(sql);
											num=num+1;
										}
							  }  
								 for(int abc = 31 ;abc>=31-tianshu;abc--) {					  
										if(abc<10) {
									
											String sql =(month+10)+"."+abc;	
											list.add(sql);
											num=num+1;
										}  
										else {
										
											String sql =(month+10)+"."+abc;	
											list.add(sql);
											num=num+1;
										}
							  }
								 
						  }
						  
						  else {
							  for(int abc = 31;abc>=31-sum;abc--) {					  
									if(abc<10) {
										String sql =(month-1)+"."+abc;	
										list.add(sql);
										num=num+1;
									}  
									else {
										String sql =(month-1)+"."+abc;	
										list.add(sql);
										num=num+1;
									}

						  }
						  }
						  
						  
						  }
						  
						  
						//输出2月份数据   // 平年年
						  else {
							  int tianshu =sum -31;// 剩余天数
							//输出2月份数据   // 平年
							  if(tianshu>0) {								 
								  
									 for(int abc = 31 ;abc>0;abc--) {					  
											if(abc<10) {
												String sql =(month-1)+"."+abc;	
												list.add(sql);
												num=num+1;
											}  
											else {
												String sql =(month-1)+"."+abc;	
												list.add(sql);
												num=num+1;
											}
								  }  
									 for(int abc = 31 ;abc>=31-tianshu;abc--) {					  
											if(abc<10) {
												String sql =(month-2)+"."+abc;	
												list.add(sql);
												num=num+1;
											}  
											else {
												String sql =(month-2)+"."+abc;	
												list.add(sql);
												num=num+1;
											}
								  }
									 
							  }
							  else {
								  for(int abc = 31;abc>31-sum;abc--) {					  
										if(abc<10) {
											String sql =(month-1)+"."+abc;	
											list.add(sql);
											num=num+1;
										}  
										else {
											String sql =(month-1)+"."+abc;	
											list.add(sql);
											num=num+1;
										}

							  }
							  }
						  }	
						  
					  }					  
					  }
					  /****************************************************/
					 
					  
					  if(month==1 || month==3 ||month==5 ||month==7 ||month==8||month==10|| month==12 ) {
						  //如果当月9月份  上个月8月有31天   45 - 5 =  40  7月份 从 31号开始输出数据
							
						  if (month==12) {

							  int tianshu =sum -31;//减去当月的时间
							  if(tianshu>0) {
								  //输出7月数据
							
								  for(int abc = 30;abc>0;abc--) {					  
										if(abc<10) {
											String sql =(month-1)+"."+abc;	
											list.add(sql);
											num=num+1;
										}  
										else {
											String sql =(month-1)+"."+abc;	
											list.add(sql);
											num=num+1;
										}

							  }
								  //输出6月份数据
							 for(int abc = 31 ;abc>30-tianshu;abc--) {					  
										if(abc<10) {
											String sql =(month-1)+"."+abc;	
											list.add(sql);
											num=num+1;
										}  
										else {
											String sql =(month-1)+"."+abc;	
											list.add(sql);
											num=num+1;
										}
							  }
						  }
							  
							  else {
								  for(int abc = 30;abc>0;abc--) {					  
										if(abc<10) {
											String sql =(month-1)+"."+abc;	
											list.add(sql);
											num=num+1;
										}  
										else {
											String sql =(month-1)+"."+abc;	
											list.add(sql);
											num=num+1;
										}
							  }
							  }
						  	}
						  if(month==10) {
							  int tianshu =sum -31;//减去当月的时间
							  if(tianshu>0) {
								  //输出7月数据
							
								  for(int abc = 31;abc>0;abc--) {					  
										if(abc<10) {
											String sql =(month-1)+"."+abc;	
											list.add(sql);
											num=num+1;
										}  
										else {
											String sql =(month-1)+"."+abc;	
											list.add(sql);
											num=num+1;
										}

							  }
								  //输出6月份数据
							 for(int abc = 31 ;abc>=31-tianshu;abc--) {					  
										if(abc<10) {
											String sql =(month-2)+"."+abc;	
											list.add(sql);
											num=num+1;
										}  
										else {
											String sql =(month-2)+"."+abc;	
											list.add(sql);
											num=num+1;
										}
							  }
						  }
							  
							  else {
								  for(int abc = 30;abc>0;abc--) {					  
										if(abc<10) {
											String sql =(month-1)+"."+abc;	
											list.add(sql);
											num=num+1;
										}  
										else {
											String sql =(month-1)+"."+abc;	
											list.add(sql);
											num=num+1;
										}
							  }
							  }
							  
						  }
						  if(month==1) {
							  int tianshu =sum -31;//减去当月的时间
							  if(tianshu>0) {
								  //输出7月数据
							

								  for(int abc = 31;abc>0;abc--) {					  
										if(abc<10) {
											String sql =(month+11)+"."+abc;	
											list.add(sql);
											num=num+1;
										}  
										else {
											String sql =(month+11)+"."+abc;	
											list.add(sql);
											num=num+1;
										}

							  }
								  //输出6月份数据
							 for(int abc = 30 ;abc>30-tianshu;abc--) {					  
										if(abc<10) {
											String sql =(month+10)+"."+abc;	
											list.add(sql);
											num=num+1;
										}  
										else {
											String sql =(month+10)+"."+abc;	
											list.add(sql);
											num=num+1;
										}
							  }
						  }
							 }
						  if(month==8) {//上个月
					
							  int tianshu =sum -31;//减去当月的时间
							  if(tianshu>0) {
								  //输出7月数据
							

								  for(int abc = 31;abc>0;abc--) {					  
										if(abc<10) {
											String sql =(month-1)+"."+abc;	
											list.add(sql);
											num=num+1;
										}  
										else {
											String sql =(month-1)+"."+abc;	
											list.add(sql);
											num=num+1;
										}

							  }
								  //输出6月份数据
							 for(int abc = 30 ;abc>=30-tianshu;abc--) {					  
										if(abc<10) {
											String sql =(month-2)+"."+abc;	
											list.add(sql);
											num=num+1;
										}  
										else {
											String sql =(month-2)+"."+abc;	
											list.add(sql);
											num=num+1;
										}
							  }
						  }			
							  
							  else {
								  //// 45减去当前日期剩余的时间小于 1个月
									 for(int abc = 31 ;abc>=31-sum;abc--) {					  
											if(abc<10) {
												String sql =(month-1)+"."+abc;	
												list.add(sql);
												num=num+1;
											}  
											else {
												String sql =(month-1)+"."+abc;	
												list.add(sql);
												num=num+1;
											}
								  }
								  
								  
							  }
							  
					  }
						  else if (month!=3 && month!=8 && month!=1 && month!=2 && month!=4 && month!=12 && month!=11 && month!=10){//如果不是8月
							  int tianshu =sum -30;//减去当月时间
							  if(tianshu>0) {
								  //输出7月数据
								  for(int abc = 30;abc>0;abc--) {					  
										if(abc<10) {
											
											String sql =(month-1)+"."+abc;	
											list.add(sql);
											num=num+1;
										}  
										else {
											String sql =(month-1)+"."+abc;	
											list.add(sql);
											num=num+1;
										}

							  }
								  //输出6月份数据
							 for(int abc = 31 ;abc>=31-tianshu;abc--) {					  
										if(abc<10) {
											String sql =(month-2)+"."+abc;	
											list.add(sql);
											num=num+1;
										}  
										else {
											String sql =(month-2)+"."+abc;	
											list.add(sql);
											num=num+1;
										}
							  }
						  }	
							  
							  
							  else {
								  //// 45减去当前日期剩余的时间小于 1个月  
									 for(int abc = 30 ;abc>30-sum;abc--) {					  
											if(abc<10) {
												String sql =(month-1)+"."+abc;		
												list.add(sql);
												num=num+1;
											}  
											else {
												String sql =(month-1)+"."+abc;	
												list.add(sql);
												num=num+1;
											}
								  }
							  }
						  }  
					 
					  else if (month==3) { // 如果4月
						  if((year%4==0&&year%1!=0)||(year%400==0))  //输出2月份数据   // 闰年
						  {
						  int tianshu =sum -29;// 剩余天数
						  //如果剩余天数大于上一个月的天数
						  if(tianshu>0) {
							  //输入2月数据
						
									 for(int abc = 29 ;abc>0;abc--) {					  
											if(abc<10) {
												String sql =(month-1)+"."+abc;	
												list.add(sql);
												num=num+1;
											}  
											else {
												String sql =(month-1)+"."+abc;	
												list.add(sql);
												num=num+1;
											}
								  }  
									 for(int abc = 31 ;abc>=31-tianshu;abc--) {					  
											if(abc<10) {
												String sql =(month-2)+"."+abc;	
												list.add(sql);
												num=num+1;
											}  
											else {
												String sql =(month-2)+"."+abc;	
												list.add(sql);
												num=num+1;
											}
								  } 
									 
							  }
						  
						  // 剩余时间小于下个月的时间
						  else {
								 for(int abc = 29 ;abc>29-tianshu;abc--) {					  
										if(abc<10) {
											String sql =(month-1)+"."+abc;	
											list.add(sql);
											num=num+1;
										}  
										else {
											String sql =(month-1)+"."+abc;	
											list.add(sql);
											num=num+1;
										}
							  } 
						  }
						  }
						  
						  
							  else {
									//输出2月份数据   // 平年
								  int tianshu =sum -28;// 剩余天数
								  //如果剩余天数大于上一个月的天数
								  if(tianshu>0) {							  						
								  for(int abc = 28 ;abc>0;abc--) {					  
										if(abc<10) {
											String sql =(month-1)+"."+abc;	
											list.add(sql);
											num=num+1;
										}  
										else {
											String sql =(month-1)+"."+abc;	
											list.add(sql);
											num=num+1;
										}
							  }
								  for(int abc = 31 ;abc>=31-tianshu;abc--) {					  
										if(abc<10) {
											String sql =(month-2)+"."+abc;	
											list.add(sql);
											num=num+1;
										}  
										else {
											String sql =(month-2)+"."+abc;	
											list.add(sql);
											num=num+1;
										}
							  }
							  }	
								/// 剩余天数小于 上一个月的天数  
								  else {
										 for(int abc = 28 ;abc>28-sum;abc--) {					  
												if(abc<10) {
													String sql =(month-1)+"."+abc;	
													list.add(sql);
													num=num+1;
												}  
												else {
													String sql =(month-1)+"."+abc;	
													list.add(sql);
													num=num+1;
												}
									  }
								  }

							  }					 					 
					  }
					  }	
//					  System.out.println(num+"++++++++++++++++++++");
//					  System.out.println(list.get(0));
					  return list;
		}
		
	}