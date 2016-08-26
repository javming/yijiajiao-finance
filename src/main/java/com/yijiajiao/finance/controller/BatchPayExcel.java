package com.yijiajiao.finance.controller;

import com.sun.jersey.api.client.ClientResponse;
import com.yijiajiao.finance.bean.BatchPayDetail;
import com.yijiajiao.finance.util.Config;
import com.yijiajiao.finance.util.DateUtil;
import com.yijiajiao.finance.util.HttpClient;
import com.yijiajiao.finance.util.JsonUtil;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
@Controller
public class BatchPayExcel extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html;charset=UTF-8");   
	    PrintWriter out = response.getWriter();
	    DiskFileItemFactory factory = new DiskFileItemFactory();
	    ServletFileUpload upload = new ServletFileUpload(factory);
	    File file = null;
	    File tempFile = null;
	    String itemStr = null;
	    String uploadfilename = "";
	    try {
	    	List  items = upload.parseRequest(request);
	    	Iterator itr = items.iterator();
	    	String root = request.getSession().getServletContext().getRealPath("");
	    	System.out.println("root===="+root);
	    	String videoSavePath = root.substring(0,root.lastIndexOf("\\"));
	    	System.out.println("video================"+videoSavePath);
	    	videoSavePath=videoSavePath.substring(0,videoSavePath.lastIndexOf("\\"))+"/uploadfile/alipayexcel"+"/"+ DateUtil.getNowTime("yyyyMMdd");
	    	System.out.println("videoSavePath==="+videoSavePath);
		    while (itr.hasNext()) {
		    	FileItem item = (FileItem) itr.next();
		    	//item.isFormField()用来判断当前对象是否是file表单域的数据  如果返回值是true说明不是 就是普通表单域  
		        if(item.isFormField()){  
		        	System.out.println( "普通表单域" +item.getFieldName());  
		        	System.out.println(item.getString("utf-8"));
		        }else{
		        	if (item.getName() != null && !item.getName().equals("")) {
					    // item.getName()返回上传文件在客户端的完整路径名称
					    System.out.println("上传文件的名称:" + item.getName());
					    uploadfilename=DateUtil.getNowTime("yyyyMMddhhmmss")+item.getName();
					    itemStr = item.getContentType();
					    tempFile = new File(videoSavePath);
					    if(!tempFile.exists()){
					    	System.out.println("路径不存在，创建===》");
					    	boolean mkdir = tempFile.mkdirs();
					    	if(mkdir){
					    		System.out.println("目录创建成功！！");
					    	}else{
					    		System.out.println("目录创建失败！！");
					    	}
					    }
					    // 上传文件的保存路径
					    file = new File(videoSavePath,uploadfilename);
					    try {
					    	item.write(file);
					    } catch (Exception e) {
					    	e.printStackTrace();
					    }
		        	}
		        }
		    }
		    //读取excel,获得批量支付账号信息
		    List<BatchPayDetail> payDetails = readExcel(videoSavePath+"/"+uploadfilename);
		    //回调签名接口
		    String listToJson = JsonUtil.listToJson(payDetails);
		    //去掉多余的键值(id和payString)
		    String params = jsonTrimparam(listToJson);
		    String url = Config.getString("batchpay.server")+Config.getString("batchpay.url");
		    System.out.println("url=="+url);
		    ClientResponse response2 = HttpClient.resource(url, params, "POST");
		    String httpresp = response2.getEntity(String.class);
		    System.out.println("httpRest=="+httpresp);
		    out.write("<html><body><h1>文件数据信息：</h1><table><tr><td>流水号</td><td>收款账号</td><td>收款账号名</td><td>收款金额</td><td>收款原因</td></tr>");
		    for(BatchPayDetail bpd : payDetails){
		    	System.out.println(bpd.getPayString());
		    	out.write("<tr><td>"+bpd.getRunning_no()+"</td><td>"+bpd.getProceeds_account()+"</td><td>"+bpd.getProceeds_name()
		    			+"</td><td>"+bpd.getProceeds_fee()+"</td><td>"+bpd.getRemark()+"</td></tr>");
		    }
		    out.write("<tr>"+httpresp+"</tr></table></body></html>");
		    
		    System.out.println("params=="+params);
	    } catch (FileUploadException e) {
	    	e.printStackTrace();
	    }catch (Exception e){
	    	e.printStackTrace();
	    	out.println(e.getLocalizedMessage());
	    }
	}
	
	/**
	 *@description 读取excel文件，并封装到List<BatchPayDetail>
	 *@date 2016-2-22
	 *@return List<BatchPayDetail>
	 *@param file
	 */
	public List<BatchPayDetail> readExcel(String file) throws Exception{
		List<BatchPayDetail> payDetails = new ArrayList<BatchPayDetail>();
	    Workbook readwb = null;
		//构建workbook对象，只读workbook对象
		//直接从本地文件创建workbook
		InputStream instream = new FileInputStream(file);
		readwb = Workbook.getWorkbook(instream);
		
		//sheet的下标是从0开始的
		//获取第一张Sheet表
		Sheet readsheet = readwb.getSheet(0);
		//获取sheet表中所包含的总列数和总行数
		int rsColums = readsheet.getColumns();
		int rsRows = readsheet.getRows();
		System.out.println("hang=="+rsRows+"--lie=="+rsColums);
		//获取指定单元格的对象引用
		for(int i =1;i<rsRows;i++){
			BatchPayDetail payDetail = new BatchPayDetail();
			for(int j=0;j<rsColums;j++){
				Cell cell = readsheet.getCell(j,i);
				String value = cell.getContents();
				if(value==null || "".equals(value.trim())){
					throw new RuntimeException("数据不完整：表中第"+(i+1)+"行第"+(j+1)+"列的值为空，请确认！");
				}
				System.out.print(j+"列"+value+"----");
				switch (j) {
				case 0:
					payDetail.setRunning_no(value);
					break;
				case 1:
					payDetail.setProceeds_account(value);
					break;
				case 2:
					payDetail.setProceeds_name(value);
					break;
				case 3:
					try {
						payDetail.setProceeds_fee(BigDecimal.valueOf(Double.parseDouble(value)));
					} catch (Exception e) {
						e.printStackTrace();
						throw new RuntimeException("数据有误：表中第"+(i+1)+"行第"+(j+1)+"列的值应该为数字类型！");
					}
					break;
				case 4:
					payDetail.setRemark(value);
					break;
				default:
					break;
				}
			}
			payDetails.add(payDetail);
			System.out.println();
		}
	    readwb.close();
	    return payDetails;
		}
	//去掉json串中的id和payString 
	public String jsonTrimparam(String json){
		JSONArray jarry = JSONArray.fromObject(json);
		JSONArray jarry2 = new JSONArray();
		for(int i=0;i<jarry.size();i++){
			JSONObject jobject = (JSONObject) jarry.get(i);
			JSONObject jobject2 = new JSONObject();
			Iterator keys = jobject.keys();
			while(keys.hasNext()){
				String key= keys.next().toString();
				String value= jobject.get(key).toString();
				if("payString".equals(key.trim())){
					continue;
				}else if("id".equals(key.trim())){
					continue;
				}else if("".equals(value.trim())){
					continue;
				}else if(value == null){
					continue;
				}else{
					jobject2.put(key, value);
				}
			}
			jarry2.add(jobject2);
		}
		return jarry2.toString();
	}

}
