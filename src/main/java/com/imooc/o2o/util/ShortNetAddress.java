package com.imooc.o2o.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ShortNetAddress {
	private static Logger log = LoggerFactory.getLogger(ShortNetAddress.class);

	public static int TIMEOUT = 30 * 1000;
	public static String ENCODING = "UTF-8";

	/**
	 * JSON get value by key
	 * 
	 * @param replyText
	 * @param key
	 * @return
	 */
	private static String getValueByKey_JSON(String replyText, String key) {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node;
		String tinyUrl = null;
		try {
			//把调用返回的消息串转换成json对象
			node = mapper.readTree(replyText);
			tinyUrl = node.get(key).asText();
		} catch (JsonProcessingException e) {
			log.error("getValueByKey_JSON error:" + e.toString());
			e.printStackTrace();
		} catch (IOException e) {
			log.error("getValueByKey_JSON error:" + e.toString());
		}

		return tinyUrl;
	}

	/**
	 * 通过HttpConnection 获取返回的字符串
	 * 
	 * @param connection
	 * @return
	 * @throws IOException
	 */
	private static String getResponseStr(HttpURLConnection connection)
			throws IOException {
		//从连接中获取http状态码
		StringBuffer result = new StringBuffer();
		int responseCode = connection.getResponseCode();

		if (responseCode == HttpURLConnection.HTTP_OK) {
			//如果返回的状态码是OK的，那么取出连接的输入流
			InputStream in = connection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					in, ENCODING));
			String inputLine = "";
			while ((inputLine = reader.readLine()) != null) {
				result.append(inputLine);
			}
		}
		return String.valueOf(result);
	}

	/**
	 * 根据传入的url，通过访问百度短视频的接口，将其转换成短的URL
	 * @param originURL
	 * @return
	 */
	public static String getShortURL(String originURL) {
		String tinyUrl = null;
		try {
			//指定百度短视频的接口
			URL url = new URL("http://dwz.cn/create.php");
			//建立连接
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			//设置连接的参数
			//使用连接进行输出
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setConnectTimeout(TIMEOUT);
			connection.setRequestMethod("POST");
			String postData = URLEncoder.encode(originURL.toString(), "utf-8");
			connection.getOutputStream().write(("url=" + postData).getBytes());
			connection.connect();
			String responseStr = getResponseStr(connection);
			log.info("response string: " + responseStr);
			tinyUrl = getValueByKey_JSON(responseStr, "tinyurl");
			log.info("tinyurl: " + tinyUrl);
			connection.disconnect();
		} catch (IOException e) {
			log.error("getshortURL error:" + e.toString());
		}
		return tinyUrl;

	}

	/**
	 * ‘ 百度短链接接口 无法处理不知名网站，会安全识别报错
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		getShortURL("https://mp.weixin.qq.com/debug/cgi-bin/sandbox?t=sandbox/login");
	}
}
