package edu.nju.ocr;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class FormulaOCRClient {

    private static final String URL = "https://api.mathpix.com/v3/latex";
    private static final String APPID = "1274395169_qq_com_4a03d3";
    private static final String APPKEY = "cc5b8986526f6368d47a";
    private static BASE64Encoder encoder = new BASE64Encoder();

    public static void main(String[] args) throws IOException {
        MathPixResult res = img2Latex("D:\\idea_workspace\\E-learning\\target\\media\\image2.jpeg");
        System.out.println(res);
    }

    public static MathPixResult img2Latex(String img) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(URL);
        httpPost.addHeader("app_id", APPID);
        httpPost.addHeader("app_key", APPKEY);
        httpPost.addHeader("Content-type", "application/json");

        try {
            Mathpix mathpix = new Mathpix("data:image/jpeg;base64,..." + getImageBinary(img));
            StringEntity se = new StringEntity(mathpix.toString(), "UTF-8");
            httpPost.setEntity(se);

            HttpResponse response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                //返回json格式
                String res = EntityUtils.toString(response.getEntity());
                return JSON.parseObject(res, MathPixResult.class);
            }
        } finally {
            if (httpClient != null){
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    static String getImageBinary(String img) {
        File f = new File(img);
        try {
            BufferedImage bi = ImageIO.read(f);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bi, "jpeg", baos);
            byte[] bytes = baos.toByteArray();
            return encoder.encodeBuffer(bytes).trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
