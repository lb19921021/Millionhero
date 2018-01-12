/*import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.LoadLibs;*/

import java.awt.*;
import java.io.File;

/**
 * Created by 618 on 2018/1/8.
 * @author lingfengsan
 */
public class TessOCR {
/*    String getOCR(File imageFile){
        ITesseract instance = new Tesseract();
//        File tessDataFolder = LoadLibs.extractTessResources("tessdata");
        instance.setLanguage("chi_sim");
        //Set the tessdata path
//        instance.setDatapath(tessDataFolder.getAbsolutePath());
        instance.setDatapath("D:\\adb");
        try {
            Rectangle rectangle = new Rectangle(100, 300, 900, 900);
            return instance.doOCR(imageFile,rectangle)
                    .replace(" ",".").replace(",","");
        } catch (TesseractException e) {
            System.err.println("提取文字失败："+e.getMessage());
        }
        return null;
    }

    public static void main(String[] args) {
        Long beginOfDectect=System.currentTimeMillis();
        File imageFile=new File("D:\\adb\\20180111095747.png");
        TessOCR tessOCR=new TessOCR();
        System.out.println(tessOCR.getOCR(imageFile));
        System.out.println("识别时间："+(System.currentTimeMillis()-beginOfDectect));
    }*/
}
