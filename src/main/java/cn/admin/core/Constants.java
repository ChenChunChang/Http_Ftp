package cn.admin.core;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import cn.admin.core.Authorize.AuthorizeType;

public class Constants {
	public final static LinkedHashMap<String, Integer> DictionayMap =new LinkedHashMap<String,Integer>(){
		{
			put("配件类型", 23);
			put("任务类型", 8);
			put("任务优先级", 9);
			put("客户信息类型", 2);
		}
	};
	public final static LinkedHashMap<Integer, String> ContractStatusMap=new LinkedHashMap<Integer,String>(){
		{
			put(1, "签订中");
			put(2, "执行中");
			put(3, "修订中");
			put(4, "已完成");
		}
	};

	public final static LinkedHashMap<Integer, String> ClientStatusMap=new LinkedHashMap<Integer,String>(){
		{
			put(3, "潜在客户");
			put(1, "正式客户");
			put(2, "失效客户");

		}
	};
	
	public final static LinkedHashMap<Integer, String> BillStatusMap=new LinkedHashMap<Integer,String>(){
		{
			put(0, "开票中");
			put(1, "已开票");
			put(2, "已作废");
		}
	};
	
	public final static LinkedHashMap<Integer, String> WorkOrderStatusMap=new LinkedHashMap<Integer,String>(){
		{
			put(0, "待执行");
			put(1, "执行中");
			put(2, "已暂停");
			put(3, "待提交");
			put(4, "审核中");
			put(5, "已通过");
			put(6, "已退回");
			put(7, "已结算");
			put(8, "已失效");
		}
	};
	
	public final static LinkedHashMap<Integer, String> repairStatusMap = new LinkedHashMap<Integer,String>(){
		{
			put(0,"待入库");
			put(1,"待检验/已入库");
			put(2,"合格");
			put(3,"待报废");
			put(4,"待维修");
			put(5,"维修中");
			put(6,"已报废");
		}
	};
	
	public final static LinkedHashMap<Integer, String> pjoriginMap = new LinkedHashMap<Integer,String>(){
		{
			put(0,"PC端");
			put(1,"APP端");
		}
	};
	
	public final static LinkedHashMap<Integer, String> documntStatusMap = new LinkedHashMap<Integer,String>(){
		{
			put(1,"待审核");
			put(2,"已通过");
			put(3,"已退回");
			put(4,"已终止");
		}
	};
}
