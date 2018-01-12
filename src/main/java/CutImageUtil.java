import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

/**
 * 剪裁图片(从网络上获取图片) 21 * @description
 * 
 * @time 2013-2-24下午02:07:00 24 * @version
 */
public class CutImageUtil {

	private static final String DESTIMAGEPATH = LoadProperties.getProperty("DESTIMAGEPATH");
	private static final String BASE_IMAGE_URL = "http://pimages3.tianjimedia.com/resources/product/";
	/** 剪切点X坐标 */
	private static int x = 0;
	/** 剪切点Y坐标 */
	private static int y = 300;
	/** 剪切点宽度 */
	private static int width = 1080;
	/** 剪切点高度 */
	private static int height = 300;

	public static File cutImage(File imageFile) {
		String fileName = "";
		String fileNameAndPath = "";
		FileInputStream fis = null;
		ImageInputStream iis = null;
		File endfile = null;
		try {
			/** 读取图片 */
			Iterator<ImageReader> it = ImageIO
					.getImageReadersByFormatName("png");
			ImageReader reader = it.next();
			/** 获取图片流 */
			fis = new FileInputStream(imageFile);
			iis = ImageIO.createImageInputStream(fis);
			reader.setInput(iis, true);

			ImageReadParam param = reader.getDefaultReadParam();
			Rectangle rect = new Rectangle(x, y, width, height);
			param.setSourceRegion(rect);
			BufferedImage bi = reader.read(0, param);
			fileName = GlobalMethodUtil.createFileName("png");
			fileNameAndPath = GlobalMethodUtil
					.createDir("/home/tmp/qicheInfo/resources/product/")
					+ fileName;
			endfile = new File(DESTIMAGEPATH + "temp.png");
			ImageIO.write(bi, "png", endfile);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
				if (iis != null) {
					iis.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return endfile;
	}

	/**
	 * 
	 * @Description: 剪切本地图片
	 * @param imagePath
	 *            String
	 */
	public static String cutLocalImage(String imagePath) {
		String fileName = "";
		String fileNameAndPath = "";
		FileInputStream fis = null;
		ImageInputStream iis = null;
		try {
			/** 读取图片 */
			Iterator<ImageReader> it = ImageIO
					.getImageReadersByFormatName("jpg");
			ImageReader reader = it.next();
			/** 获取图片流 */
			fis = new FileInputStream(imagePath);
			iis = ImageIO.createImageInputStream(fis);
			reader.setInput(iis, true);

			ImageReadParam param = reader.getDefaultReadParam();
			Rectangle rect = new Rectangle(x, y, width, height);
			param.setSourceRegion(rect);
			BufferedImage bi = reader.read(0, param);

			fileName = GlobalMethodUtil.createFileName("jpg");
			fileNameAndPath = GlobalMethodUtil
					.createDir("/home/tmp/qicheInfo/resources/product/")
					+ fileName;
			ImageIO.write(
					bi,
					"jpg",
					new File(DESTIMAGEPATH + new Date().getTime() + "." + "jpg"));
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
				if (iis != null) {
					iis.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return BASE_IMAGE_URL
				+ fileNameAndPath
						.substring(fileNameAndPath.indexOf("product/") + 8);
	}

	private static class GlobalMethodUtil {

		/**
		 * 返回文件名(file)
		 * 
		 * @Description:
		 * @return
		 */
		public static String createFileName(String suffix) {
			/** 结合目录和文件名 生成唯一标识符 */
			Calendar c = Calendar.getInstance();
			/** 利用小时分钟毫秒和源文件的文件名生成新文件名+文件后缀 */
			String name = c.get(Calendar.HOUR) + "" + c.get(Calendar.MINUTE)
					+ "" + c.get(Calendar.SECOND) + c.get(Calendar.MILLISECOND)
					+ randStr() + suffix;
			return name;
		}

		/**
		 * 利用26个字母生成随机字母组合
		 * 
		 * @Description:
		 * @return
		 */
		public static String randStr() {
			String[] rands = { "a", "b", "c", "d", "e", "f", "g", "h", "i",
					"j", "k", "l", "m", "n", "k", "o", "p", "q", "r", "s", "t",
					"u", "v", "w", "x", "y", "z" };
			String randstr = "";
			/** 生成8个随机字母组合 */
			for (int i = 0; i < 8; i++) {
				randstr += rands[new Random().nextInt(26)];
			}
			return randstr;
		}

		/**
		 * 创建文件目录
		 * 
		 * @Description:
		 * @return 文件目录路径
		 */
		public static String createDir(String path) {
			Calendar c = Calendar.getInstance();
			String dir = path;
			dir += c.get(Calendar.YEAR)
					+ ""
					+ ((c.get(Calendar.MONTH) + 1) < 10 ? "0"
							+ (c.get(Calendar.MONTH) + 1) : (c
							.get(Calendar.MONTH) + 1))
					+ ""
					+ (c.get(Calendar.DATE) < 10 ? "0" + c.get(Calendar.DATE)
							: c.get(Calendar.DATE)) + "/";
			File tempDir = new File(dir);
			/** 判断文件路径是否存在 */
			if (!tempDir.exists()) {
				/** 不存在就创建文件目录 */
				tempDir.mkdirs();
			}
			return dir;
		}

		/**
		 * 
		 * @Description: 获取分页的总页数
		 * @param count
		 *            数据总量
		 * @param size
		 *            每页分页数量
		 * @return int
		 */
		public static int getPageSize(int count, int size) {
			return count % size != 0 ? (count / size + 1) : (count / size);
		}

	}
}
