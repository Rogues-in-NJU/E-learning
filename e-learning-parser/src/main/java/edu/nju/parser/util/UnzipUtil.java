package edu.nju.parser.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Slf4j
public class UnzipUtil {

    private static final int BUFFER_SIZE = 2048;

    public static void unzip(String srcFilePath, String destDirPath) throws IOException {
        log.debug("Start unzip file [{}]", srcFilePath);
        File srcFile = new File(srcFilePath);
        if (!srcFile.exists()) {
            throw new FileNotFoundException("目标文件不存在!");
        }
        File destDir = new File(destDirPath);
        if (!destDir.exists()) {
            destDir.mkdir();
        }
        ZipFile zipFile = null;
        zipFile = new ZipFile(srcFile);
        Enumeration<?> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) entries.nextElement();
            // 如果是文件夹，就创建个文件夹
            if (entry.isDirectory()) {
                String dirPath = destDirPath + File.pathSeparator + entry.getName();
                File dir = new File(dirPath);
                dir.mkdirs();
            } else {
                // 如果是文件，就先创建一个文件，然后用io流把内容copy过去
                File targetFile = new File(destDirPath + File.pathSeparator + entry.getName());
                // 保证这个文件的父文件夹必须要存在
                if(!targetFile.getParentFile().exists()){
                    targetFile.getParentFile().mkdirs();
                }
                targetFile.createNewFile();
                // 将压缩文件内容写入到这个文件中
                InputStream is = zipFile.getInputStream(entry);
                FileOutputStream fos = new FileOutputStream(targetFile);
                int len;
                byte[] buf = new byte[BUFFER_SIZE];
                while ((len = is.read(buf)) != -1) {
                    fos.write(buf, 0, len);
                }
                // 关流顺序，先打开的后关闭
                fos.close();
                is.close();
            }
        }
    }

}
