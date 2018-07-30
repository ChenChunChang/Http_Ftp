package cn.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;

public class ExcelUntil {
	
	/**
	 * 读取excel 公共方法
	 * @param is 文件流
	 * @param fileType 文件后缀
	 * @return 
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	public static List<String> readExcel(InputStream is,String fileType){
		Workbook wb=null;
		List<String> re=new ArrayList<String>();
		try{
			if(fileType.equals("xls")){
				wb=new HSSFWorkbook(is);
			}else if (fileType.equals("xlsx")) {  
	            wb = new XSSFWorkbook(is);  
	        } else {  
	        	return null;
	        }  
			//循环每一页 并处理当前循环页
			for(int numSheet=0;numSheet<wb.getNumberOfSheets();numSheet++){
				Sheet sheet=wb.getSheetAt(numSheet);//这里表示当前页的数据
				if(sheet==null)
					continue; //如果当前页面没有数据则跳出循环 继续下一页
				//获取当前页的行数
				int rows=sheet.getLastRowNum();
				for(int rowNum=0;rowNum<=rows;rowNum++){
					List resList=new ArrayList();
					Row row=sheet.getRow(rowNum); //取到当前行
					if(row!=null){
						int cellNum= row.getPhysicalNumberOfCells();
						for(int j=0;j<cellNum;j++){
							String cellVa=getValue(row.getCell(j));
							if(!StringUtils.isEmpty(cellVa)){
								re.add(cellVa);
							}
						}
					}
				}
			}
		}catch(Exception ex){
			
		}
		return re;
	}
	/**
	 * 生成树
	 * @param list
	 * @return
	 */
	public static List<TreeNode> createTree(InputStream is,String fileType){
		List<String> list= readExcel(is,fileType);
		if(list==null){
			return null;
		}
		List<TreeNode> rootList=new ArrayList<TreeNode>();
		for(int i=0;i<list.size();i++){
			String[] str=list.get(i).split("-");
			if(str!=null && str.length>0){
				createChilde(rootList,str,0);
			}
		}
		return rootList;
	}
	
	/**
	 * 迭代生成子节点集合
	 * @param root 子节点结合
	 * @param arr 子节点数据 数组
	 * @param index 数组索引
	 */
	public static void createChilde(List<TreeNode> root,String[] arr,int index){
		if(root==null){
			root=new ArrayList<TreeNode>();
		}
		TreeNode tn=new TreeNode(arr[index]);
		boolean finded=false; 
		for (TreeNode item : root) {
			if(item.getTreeName().equals(arr[index]))
			{
				tn=item;
				finded=true;
				break;
			}
		}
		if(!finded){
			root.add(tn);
			tn.setChList(new ArrayList<TreeNode>());
		}
		if(index<arr.length-1){
			index++;
			createChilde(tn.getChList(),arr,index);
		}	
	}
	
		/**
		 * 获取值
		 * @param hssfRow
		 * @return
		 */
		public static String getValue(Cell hssfRow) {
	        if (hssfRow.getCellType() == hssfRow.CELL_TYPE_BOOLEAN) {
	        	String boo=String.valueOf(hssfRow.getBooleanCellValue());
	        	if(StringUtils.isEmpty(boo))
	        		return null ;
	        	else 
	        		return boo;
	        } else if (hssfRow.getCellType() == hssfRow.CELL_TYPE_NUMERIC) {
	        	String num=String.valueOf((int)hssfRow.getNumericCellValue());
	        	if(StringUtils.isEmpty(num))
	        		  return null;
	        	else  return num;
	         } else{
	        	 String str=hssfRow.getStringCellValue();
	        	 if(StringUtils.isEmpty(str))
	       		  return "";
	        	 else return str;
	        }
		}
}
