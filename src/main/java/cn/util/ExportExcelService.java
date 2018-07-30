package cn.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


@Service
public class ExportExcelService {
	public HSSFWorkbook exprot(List list,String[] excelHeader,String[] mapKey){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//创建工作簿
		HSSFWorkbook wb=new HSSFWorkbook();
		//创建工作表格
		  HSSFFont fontStyle = wb.createFont();
	        fontStyle.setFontName("微软雅黑");
		HSSFSheet sheet=wb.createSheet("sheet");
		//创建行
		HSSFRow row=sheet.createRow((int)0);
		//创建单元格 并将表头设置为居中
		HSSFCellStyle style=wb.createCellStyle();
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		HSSFFont font = wb.createFont();    
		font.setFontName("仿宋_GB2312");    
		font.setFontHeightInPoints((short) 12); 
		font.setColor(HSSFFont.COLOR_RED);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//表格居中
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直
		style.setFont(font);
		//创建单元格 并将表头设置为居中
		HSSFCellStyle style2=wb.createCellStyle();
		style2.setBorderBottom(BorderStyle.THIN);
		style2.setBorderLeft(BorderStyle.THIN);
		style2.setBorderRight(BorderStyle.THIN);
		style2.setBorderTop(BorderStyle.THIN);
		style2.setBottomBorderColor(HSSFColor.BLACK.index);
		HSSFFont font2 = wb.createFont();    
		font2.setFontName("仿宋_GB2312");    
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);//表格居中
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直
		//创建标题
		for(int i=0;i<excelHeader.length;i++){
			//标题的列
			HSSFCell cell=row.createCell(i);
			//标题列的写入
			cell.setCellValue(excelHeader[i]);
			//样式
			cell.setCellStyle(style);
			//每列长度自适应
			sheet.autoSizeColumn(i);
		}
		//创建内容 先判断有多少行 然后每一行添加每一列的数据	
		int listLeg=list.size();
		int kegLeg=mapKey.length;
		for(int i=0;i<listLeg;i++){
			row=sheet.createRow(i+1);
			Map map=(Map)list.get(i);
			for(int j=0;j<kegLeg;j++){
				//每列长度自适应
				sheet.autoSizeColumn(j);
				//写入样式
				HSSFCell cell=row.createCell(j);
				cell.setCellStyle(style2);
				String key=mapKey[j];
				if(key.equals("userName")){
					if(StringUtils.isEmpty(map.get(key))){
						cell.setCellValue("");
					}else{
						cell.setCellValue(map.get(key)+"");
					}
				}else if(key.equals("applystatus")){
					switch(map.get(key)+""){
					case "0":
						cell.setCellValue("已提交");
						break;
					case "1":
						cell.setCellValue("审核通过");
						break;
					}
				}else if(key.equals("status")){
					switch(map.get(key)+""){
					case "0":
						cell.setCellValue("失败");
						break;
					case "1":
						cell.setCellValue("成功");
						break;
					}
				}else if(key.equals("operationType")){
					switch(map.get(key)+""){
					case "0":
						cell.setCellValue("押金充值");
						break;
					case "1":
						cell.setCellValue("余额充值");
						break;
					case "3":
						cell.setCellValue("消费");
						break;
					case "2":
						cell.setCellValue("押金提现");
						break;
					}
				}else if(key.equals("cellNum1")||key.equals("shelfNum1")){
					if(StringUtils.isEmpty(map.get(key))){
						cell.setCellValue("未还");
					}else{
						cell.setCellValue(map.get(key)+"");
					}
				}else if(key.equals("state")){
					switch(map.get(key)+""){
					case "0":
						cell.setCellValue("已还");
						break;
					case "1":
						cell.setCellValue("未还");
						break;
					}
				}else if(key.equals("shengyu")){
					cell.setCellValue(Integer.valueOf(map.get("zong")+"")-Integer.valueOf(map.get("jiechu")+""));
				}else if(key.equals("lendDate")||key.equals("shouldDate")||key.equals("returnDate")||key.equals("operationTime")){
					if(map.get(key)!=null){
						
						try {
							Date offT = sdf.parse(map.get(key)+"");
							cell.setCellValue(sdf.format(offT));
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}else{
						cell.setCellValue("未还");
					}
				}else{
					cell.setCellValue(map.get(key)+"");
				}
			}
		}
		return wb;
	}
	//预约记录导出
	public HSSFWorkbook yuyue(List list,String[] excelHeader,String[] mapKey){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//创建工作簿
		HSSFWorkbook wb=new HSSFWorkbook();
		//创建工作表格
		  HSSFFont fontStyle = wb.createFont();
	        fontStyle.setFontName("微软雅黑");
		HSSFSheet sheet=wb.createSheet("sheet");
		//创建行
		HSSFRow row=sheet.createRow((int)0);
		//创建单元格 并将表头设置为居中
		HSSFCellStyle style=wb.createCellStyle();
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		HSSFFont font = wb.createFont();    
		font.setFontName("仿宋_GB2312");    
		font.setFontHeightInPoints((short) 12); 
		font.setColor(HSSFFont.COLOR_RED);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//表格居中
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直
		style.setFont(font);
		//创建单元格 并将表头设置为居中
		HSSFCellStyle style2=wb.createCellStyle();
		style2.setBorderBottom(BorderStyle.THIN);
		style2.setBorderLeft(BorderStyle.THIN);
		style2.setBorderRight(BorderStyle.THIN);
		style2.setBorderTop(BorderStyle.THIN);
		style2.setBottomBorderColor(HSSFColor.BLACK.index);
		HSSFFont font2 = wb.createFont();    
		font2.setFontName("仿宋_GB2312");    
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);//表格居中
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直
		//创建标题
		for(int i=0;i<excelHeader.length;i++){
			//标题的列
			HSSFCell cell=row.createCell(i);
			//标题列的写入
			cell.setCellValue(excelHeader[i]);
			//样式
			cell.setCellStyle(style);
			//每列长度自适应
			sheet.autoSizeColumn(i);
		}
		//创建内容 先判断有多少行 然后每一行添加每一列的数据	
		int listLeg=list.size();
		int kegLeg=mapKey.length;
		for(int i=0;i<listLeg;i++){
			row=sheet.createRow(i+1);
			Map map=(Map)list.get(i);
			for(int j=0;j<kegLeg;j++){
				//每列长度自适应
				sheet.autoSizeColumn(j);
				//写入样式
				HSSFCell cell=row.createCell(j);
				cell.setCellStyle(style2);
				String key=mapKey[j];
				if(key.equals("status")){
					switch(map.get(key)+""){
					case "0":
						cell.setCellValue("未取书");
						break;
					case "1":
						cell.setCellValue("已取书");
						break;
					case "2":
						cell.setCellValue("已失效");
						break;
					}
				}else{
					cell.setCellValue(map.get(key)+"");
				}
			}
		}
		return wb;
	}
}
