import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import sun.misc.BASE64Encoder;

import com.aliyun.api.gateway.demo.util.HttpUtils;

public class hanwang {

	private static final String ip = LoadProperties.getProperty("ip");
	private static final String APPCODE = LoadProperties.getProperty("appcode");

	public static void main(String[] args) {
		Long beginOfDectect = System.currentTimeMillis();
		File imageFile = new File("D:\\adb\\20180111095747.png");
		hanwang h = new hanwang();
		System.out.println(h.getOCR(imageFile));
		System.out.println("识别时间："
				+ (System.currentTimeMillis() - beginOfDectect));

	}

	public String getOCR(File imageFile) {
		// 裁剪图片
		imageFile = CutImageUtil.cutImage(imageFile);

		InputStream inputStream = null;
		byte[] data = null;
		try {
			inputStream = new FileInputStream(imageFile);
			data = new byte[inputStream.available()];
			inputStream.read(data);
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 加密
		BASE64Encoder encoder = new BASE64Encoder();
		String base64 = encoder.encode(data);

		String host = "http://text.aliapi.hanvon.com";
		String path = "/rt/ws/v1/ocr/text/recg";
		String method = "POST";
		String appcode = APPCODE;
		Map<String, String> headers = new HashMap<String, String>();
		// 最后在header中的格式(中间是英文空格)为Authorization:APPCODE
		// 83359fd73fe94948385f570e3c139105
		headers.put("Authorization", "APPCODE " + appcode);
		// 根据API的要求，定义相对应的Content-Type
		headers.put("Content-Type", "application/json; charset=UTF-8");
		headers.put("Content-Type", "application/octet-stream");
		Map<String, String> querys = new HashMap<String, String>();
		querys.put("code", "74e51a88-41ec-413e-b162-bd031fe0407e");

		Map<String, String> p = new HashMap<String, String>();
		p.put("uid", ip);
		p.put("lang", "chns");
		p.put("color", "color");
		p.put("image", base64);
		String bodys = GsonUntil.tojson(p);

		// String bodys =
		// "{\"uid\":\"116.205.13.22\",\"lang\":\"chns\",\"color\":\"gray\",\"image\":\""
		// + base64 + "\"}";
		String s = "";
		try {
			HttpResponse response = HttpUtils.doPost(host, path, method,
					headers, querys, bodys);
			// 获取response的body
			s = EntityUtils.toString(response.getEntity());
			Map<String, Object> d = GsonUntil.toMap(s);
			s = d.get("textResult").toString();
			// s=s.replaceAll("\r", "").replaceAll("\n", "").replaceAll(" ",
			// "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}
}
