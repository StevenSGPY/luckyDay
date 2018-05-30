package com.imooc.o2o.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.imooc.o2o.dto.ImageHolder;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class ImgUtil {
	// 获取图片路径
	// （由于方法是通过线程去执行的，我们可以通过线程去逆推到类加载器，再得到资源路径）
	private static String basePath = PathUtil.getImgBasePath();
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	// 定义静态常量的随机对象
	private static final Random r = new Random();
	private static Logger logger = LoggerFactory.getLogger(ImgUtil.class);

	/**
	 * 将CommonsMultipartFile转换成File
	 * 
	 * @param cFile
	 * @return
	 */
	public static File transferCommonsMultipartFileToFile(CommonsMultipartFile cFile) {
		File newFile = new File(cFile.getOriginalFilename());
		try {
			cFile.transferTo(newFile);
		} catch (IllegalStateException | IOException e) {
			logger.error(e.toString());
			e.printStackTrace();
		}
		return newFile;
	}

	/**
	 *处理缩略图，并返回新生成图片的相对值路径
	 * @param thumbnail
	 * @param targetAddr
	 *            目标文件路径
	 * @return CommonsMultipartFile
	 */
	public static String generateThumbnail(ImageHolder thumbnail, String targetAddr) {
		// 因为考虑到图片重名问题，我们就随机生成图片名
		String realFileName = getRandomFileName();
		// 获取用户上传的图片扩展名.jpg,.png之类的
		String extension = getFileExtension(thumbnail.getImageName());
		// 创建文件存储路径
		makeDirPath(targetAddr);
		// ※随机生成的图片名+扩展名就是图片新名字
		String relativeAddr = targetAddr + realFileName + extension;
		logger.debug("current relativeAddr is:" + relativeAddr);
		// 新的文件的路径：就是由根路径ImgBasePath和刚刚生成的相对路径relativeAddr形成的
		File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
		logger.debug("current complete addr is:" + PathUtil.getImgBasePath() + relativeAddr);
		try {
			// 创建缩略图
			Thumbnails.of(thumbnail.getImage()).size(200, 200)
					.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/yes.png")), 0.25f)
					.outputQuality(0.8f).toFile(dest);

		} catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
		}
		// 为什么要返回相对路径？
		// 当项目移到其他系统，我们在数据库中就不用修改路径了
		return relativeAddr;
	}
	
	
	/**
	 * 处理详情图，并返回新生成图片的相对值路径
	 * @param thumbnail
	 * @param targetAddr
	 * @return
	 */
	public static String generateNormalImg(ImageHolder thumbnail, String targetAddr) {
		// 因为考虑到图片重名问题，我们就随机生成图片名
		String realFileName = getRandomFileName();
		// 获取用户上传的图片扩展名.jpg,.png之类的
		String extension = getFileExtension(thumbnail.getImageName());
		// 创建文件存储路径
		makeDirPath(targetAddr);
		// ※随机生成的图片名+扩展名就是图片新名字
		String relativeAddr = targetAddr + realFileName + extension;
		logger.debug("current relativeAddr is:" + relativeAddr);
		// 新的文件的路径：就是由根路径ImgBasePath和刚刚生成的相对路径relativeAddr形成的
		File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
		logger.debug("current complete addr is:" + PathUtil.getImgBasePath() + relativeAddr);
		try {
			// 创建缩略图
			Thumbnails.of(thumbnail.getImage()).size(337, 640)
					.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/yes.png")), 0.25f)
					.outputQuality(0.9f).toFile(dest);

		} catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
		}
		// 为什么要返回相对路径？
		// 当项目移到其他系统，我们在数据库中就不用修改路径了
		return relativeAddr;
	}
	

	/**
	 * 创建目标路径所涉及到的目录，即/images/work/xxx.jpg images work 这两个文件夹都得自动创建
	 * 
	 * @param targetAddr
	 */
	private static void makeDirPath(String targetAddr) {
		String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
		File dirPath = new File(realFileParentPath);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}
	}

	/**
	 * 获取用户上传的图片扩展名.jpg,.png之类的 , 所以我们只要获取jpg或png这种
	 * 
	 * @param thumbnail
	 * @return
	 */
	private static String getFileExtension(String fileName) {
		// 获取原文件名
		// 获取最后一个“.”的下标，然后截取点后面的内容
		return fileName.substring(fileName.lastIndexOf("."));
	}

	/**
	 * 随机生成图片名,当前年月日。。。+五位随机数
	 * 
	 * @return
	 */
	public static String getRandomFileName() {
		// 获取随机的五位数 0-89999之间;>10000, <89999
		int rannum = r.nextInt(89999) + 10000;
		String nowTimeStr = sdf.format(new Date());
		return nowTimeStr + rannum;
	}

	/**
	 * 测试给图片加水印
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		// 设置图片大小，打上水印，并且对图片进行压缩，并且将压缩的图片输出在指定的目录底下
		/**
		 * Thumbnails工具主类 watermark添加水印有三个参数，水印放置位置；水印的绝对路径；透明度
		 * （Positions.BOTTOM_RIGHT位置放在图片右下角；ImageIO.read获取水印的绝对路径； 定义水印的透明度；
		 * outputQuality将压缩的图片输出在指定的目录底下）
		 */
		Thumbnails.of(new File("E:/images/item/pro6.jpg")).size(200, 200)
				.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/yes.png")), 0.25f)
				.outputQuality(0.8f).toFile("E:/images/item/pro7.jpg");

	}

	/**
	 * 观察storePath是文件路径还是目录的路径 ,如果storePath是文件路径则删除该文件;
	 * 如果storePath是目录路径则删除该目录下的所有文件
	 * @param storePath
	 */
	public static void deleteFileOrPath(String storePath) {
		File fileOrPath = new File(PathUtil.getImgBasePath() + storePath);
		if (fileOrPath.exists()) {
			if (fileOrPath.isDirectory()) {
				File files[] = fileOrPath.listFiles();
				for (int i = 0; i < files.length; i++) {
					files[i].delete();
				}
			}
			fileOrPath.delete();
		}
	}
}
