
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 加载配置文件工具
 * 
 * @author xiaolin
 * @date 2013-7-4 上午10:29:49
 * @version V1.0
 */
public class LoadProperties {

	private static Properties systemProps;
	private static final String JRE = "jre";

	/**
	 * 初始化类的时候装载属性文件，只装载一次
	 * 首先从WEB-INF/classes/conf/hibernate/hibernate.properties 文件中找
	 * 找不到再从java.home路径下找 hibernate.properties
	 */
	private static void loadProperties() {
		InputStream in = null;
		if (systemProps == null) {
			systemProps = new Properties();
			try {
				in = LoadProperties.class
						.getResourceAsStream("account.properties");
			} catch (Exception ex) {
				in = null;
			}
			if (in != null) {
				try {
					systemProps.load(in);
				} catch (IOException ex) {
					ex.printStackTrace();
				} finally {
					try {
						if (in != null) {
							in.close();
							in = null;
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} else {
				String realPath = LoadProperties.class.getClassLoader()
						.getResource("").getFile();
				java.io.File file = new java.io.File(realPath);
				realPath = file.getAbsolutePath();
				try {
					realPath = java.net.URLDecoder.decode(realPath, "utf-8");
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					// String strJavaHome = System.getProperty("java.home");
					// if (strJavaHome.indexOf(JRE)>=0){
					// strJavaHome = strJavaHome.replace(JRE, "");
					// }
					// strJavaHome = strJavaHome + File.separator;
					in = new BufferedInputStream(new FileInputStream(realPath
							+ File.separator + "account.properties"));
					systemProps.load(in);
				} catch (IOException ex) {
					ex.printStackTrace();
				} finally {
					try {
						if (in != null) {
							in.close();
							in = null;
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		}
	}

	static {
		// 先读取配置文件
		loadProperties();
	}

	/**
	 * 获取字符串系统属性值
	 * 
	 * @param key
	 * @return String
	 */
	public static String getStrProperty(String key) {
		String temp = "";
		try {
			loadProperties();
			temp = systemProps.getProperty(key);
		} catch (Exception e) {
			e.printStackTrace();
			temp = "";
		}
		return temp;
	}

	/**
	 * 获取系统属性定义
	 * 
	 * @return Properties
	 */
	public static Properties getSystemProperties() {
		loadProperties();
		return systemProps;
	}

	/**
	 * 获取系统属性，如果key不存在，直接返回""
	 * 
	 * @param key
	 * @return String
	 */
	public static String getProperty(String key) {
		String tmp = "";
		try {
			loadProperties();
			tmp = systemProps.getProperty(key);
		} catch (Exception e) {
			tmp = "";
		} catch (Throwable e) {
			tmp = "";
		}
		return tmp;
	}
}
