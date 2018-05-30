package com.imooc.o2o.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 地址的处理
 * 应执行环境的变化提供不同的根路径
 * @author zp
 *
 */
@Configuration
public class PathUtil {
	//获取系统文件分隔符
	private static String separator = System.getProperty("file.separator");
	
	private static String winPath;
	
	private static String linuxPath;
	
	private static String shopPath;
	
	@Value("${win.base.path}")
	public  void setWinPath(String winPath) {
		PathUtil.winPath = winPath;
	}
	@Value("${linux.base.path}")
	public  void setLinuxPath(String linuxPath) {
		PathUtil.linuxPath = linuxPath;
	}
	@Value("${shop.relevant.path}")
	public  void setShopPath(String shopPath) {
		PathUtil.shopPath = shopPath;
	}
	/**
	 * 根据不同系统环境，返回项目图片根路径
	 * 不需要将类的实例new出来，直接根据类名下的方法执行
	 * @return
	 */
	public static String getImgBasePath() {
		//获取系统名称
		String os = System.getProperty("os.name");
		String basePath="";
		//判断系统，如果是Windows10系统，就给Windows的根目录路径
		//startsWith从。。。开始  win开头的系统就走这里
		if (os.toLowerCase().startsWith("win")) {
			basePath = winPath;
		//如果是虚拟机Linux就给以下
		}else {
			basePath=linuxPath;
		}
		//将“/”替换成系统分隔符
		basePath = basePath.replace("\\", separator);
		return basePath;
	}
	/**
	 * 根据业务需求返回项目图片子路径
	 * @param shopId
	 * @return
	 */
	public static String getShopImagePath(long shopId) {
		String imagePath = shopPath+shopId+"/";
		return imagePath.replace("\\", separator); 
	}
}
