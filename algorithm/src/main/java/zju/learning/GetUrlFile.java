package zju.learning;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ZhXiQi
 * @Title:
 * @date 2020/3/5 17:26
 */
public class GetUrlFile {

    public static Pattern HASH_NAME = Pattern.compile("[a-zA-Z0-9]{64}\\.[a-z]+");
    public static void main(String[] args) throws IOException {
        String filePath = "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3866293453,1576785989&fm=26&gp=0.jpg";
        URL url = new URL(filePath);
        URLConnection urlConnection = url.openConnection();
        BufferedInputStream bis = new BufferedInputStream(urlConnection.getInputStream());

        String hashName = getHashName(filePath);
        System.out.println(hashName);

        String outPath = "/Users/ZhXiQi/Desktop/tmp/img.jpg";
        File file = new File(outPath);
        FileOutputStream fos = new FileOutputStream(file);
        byte[] buffer = new byte[8192];
        int bytesRead = 0;
        while ((bytesRead = bis.read(buffer,0,8192)) != -1){
            fos.write(buffer,0,bytesRead);
        }
        fos.close();
        bis.close();
    }

    private static String getHashName(String url) {
        Matcher matcher = HASH_NAME.matcher(url);
        return matcher.find() ? matcher.group() : null;
    }
}
