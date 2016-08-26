package com.yijiajiao.finance.controller;

import ch.qos.logback.classic.Logger;
import com.yijiajiao.finance.alipay.util.AlipayNotify;
import com.yijiajiao.finance.bean.BatchPayment;
import com.yijiajiao.finance.service.IBatchPayService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Controller
public class BatchPay extends HttpServlet {

	private static final long serialVersionUID = 1L;
	@Autowired
	private IBatchPayService batchPayService;
	//public static Logger log = Logger.getLogger(BatchPay.class);
	private static Logger log = (Logger) LoggerFactory.getLogger(BatchPay.class);
	
	public void init(ServletConfig config) throws ServletException {
		   SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
		         config.getServletContext());
		}

	public BatchPay() {
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	@SuppressWarnings("rawtypes")
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("^^batchPayService======="+batchPayService);
		int length = request.getContentLength();
		Map<String,String> params = new HashMap<String,String>();
		if (length > 0) {
			InputStream is = request.getInputStream();
			byte[] content = new byte[length];
			int pad = 0;
			while (pad < length) {
				pad += is.read(content, pad, length);
			}
			String parameters=new String(content, "utf-8");
			System.out.println("paramters=="+parameters);
			String[] strings = parameters.split("&");
			for(int i=0;i<strings.length;i++){
				String param = strings[i];
				String[] strings2 = param.split("=");
				String key = strings2[0];
				String value= URLDecoder.decode(strings2[1], "UTF-8");
				System.out.println(key+"=="+value);
				params.put(key, value);
			}
			Set<String> keySet = params.keySet();
			for(String s: keySet){
				String value = params.get(s);
				System.out.println("key="+s+"------value="+value);
			}
		}
		System.out.println("params.size>>>>>>>>>>>" + params.keySet().size());
		PrintWriter out = response.getWriter();
		BatchPayment bap = new BatchPayment();
		bap.setNotify_time(params.get("notify_time"));
		bap.setNotify_type(params.get("notify_type"));
		bap.setNotify_id(params.get("notify_id"));
		bap.setSign_type(params.get("sign_type"));
		bap.setSign(params.get("sign"));
		bap.setBatch_no(params.get("batch_no"));
		bap.setPay_user_id(params.get("pay_user_id"));
		bap.setPay_user_name(params.get("pay_user_name"));
		bap.setPay_account_no(params.get("pay_account_no"));
		bap.setSuccess_details(params.get("success_details"));
		bap.setFail_details(params.get("fail_details"));

		System.out.println("数据库存储的支付宝信息对象内容:batchPayment===="+bap);
		System.out.println(" 验证结果verify(params)-----------"
				+ AlipayNotify.verify(params));
		if (AlipayNotify.verify(params)) {// 验证成功
			BatchPayment batchPayment = batchPayService.getBatchPaymentByBatch_no(bap.getBatch_no());
			if(batchPayment != null && batchPayment.getIs_dispose()==1){
				log.info("该批次转账已处理!!!! batch_no="+bap.getBatch_no());
			}else if(bap.getFail_details() == null &&  "".equals(bap.getFail_details())){
				bap.setIs_dispose(1);
				batchPayService.modBatchPayment(bap);
				batchPayService.modBatchPayDetailAndMoneyTiemer(bap.getBatch_no());
				System.out.println("batchPayment保存成功!");
			}
			 out.println("success"); // 请不要修改或删除
			// 程序执行完后必须打印输出“success”（不包含引号）。
			// 如果商户反馈给支付宝的字符不是success这7个字符，支付宝服务器会不断重发通知，直到超过24小时22分钟。
		}else{
			out.println("fail");
		}

	}
}
