package cn.admin.test.duandianHttp2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
public class saveFileInfo1 {
	
	//@Scheduled(cron = "*/5 * * * * ?")
	public void test() {
		try {
			// 1. 得到访问地址的URL
			URL url = new URL("http://192.168.168.101:1106/admin/receiveHttp/uploadPic");
			// 2. 得到网络访问对象java.net.HttpURLConnection
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			/* 3. 设置请求参数（过期时间，输入、输出流、访问方式），以流的形式进行连接 */
			// 设置是否向HttpURLConnection输出
			connection.setDoOutput(false);
			// 设置是否从httpUrlConnection读入
			connection.setDoInput(true);
			// 设置请求方式
			connection.setRequestMethod("GET");
			// 设置是否使用缓存
			connection.setUseCaches(true);
			// 设置此 HttpURLConnection 实例是否应该自动执行 HTTP 重定向
			connection.setInstanceFollowRedirects(true);
			// 设置超时时间
			connection.setConnectTimeout(3000);
			// 连接
			connection.connect();
			// 4. 得到响应状态码的返回值 responseCode
			int code = connection.getResponseCode();
			// 5. 如果返回值正常，数据在网络中是以流的形式得到服务端返回的数据
			String msg = "";
			if (code == 200) { // 正常响应
				// 从流中读取响应信息
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line = null;

				while ((line = reader.readLine()) != null) { // 循环从流中读取
					msg += line + "\n";
				}
				reader.close(); // 关闭流
			}
			// 6. 断开连接，释放资源
			connection.disconnect();

			// 显示响应结果
			System.out.println(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
