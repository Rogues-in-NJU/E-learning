package edu.nju.parser.util;

import de.innosystec.unrar.Archive;
import de.innosystec.unrar.NativeStorage;
import de.innosystec.unrar.exception.RarException;
import de.innosystec.unrar.rarfile.FileHeader;
//import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

//@Slf4j
public class UnCompressUtil {

    private static final int BUFFER_SIZE = 2048;

    public static void unzip(String srcFilePath, String destDirPath) throws IOException {
        unzip(srcFilePath, destDirPath, "UTF-8");
    }

    /**
     * 解压缩zip文件
     *
     * @param srcFilePath 源zip文件
     * @param destDirPath 目标文件夹
     */
    public static void unzip(String srcFilePath, String destDirPath, String charset) throws IOException {
//        log.debug("Start unzip file [{}]", srcFilePath);
        System.out.println("Start unzip file " + srcFilePath);
        File srcFile = new File(srcFilePath);
        if (!srcFile.exists()) {
            throw new FileNotFoundException("目标文件不存在!");
        }
        File destDir = new File(destDirPath);
        if (!destDir.exists()) {
            destDir.mkdir();
        }

        InputStream is = null;
        FileOutputStream fos = null;
        ZipFile zipFile = null;

        try {
            zipFile = new ZipFile(srcFile, Charset.forName(charset));
            Enumeration<?> entries = zipFile.entries();

            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();

                if (entry.isDirectory()) {
                    // 如果是文件夹，就创建个文件夹
                    String dirPath = destDirPath + File.separator + entry.getName();
                    File dir = new File(dirPath);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                } else {
                    // 如果是文件，就先创建一个文件，然后用io流把内容copy过去
                    File targetFile = new File(destDirPath + File.separator + entry.getName());
                    // 保证这个文件的父文件夹必须要存在
                    if (!targetFile.getParentFile().exists()) {
                        targetFile.getParentFile().mkdirs();
                    }
                    targetFile.createNewFile();
                    // 将压缩文件内容写入到这个文件中
                    is = zipFile.getInputStream(entry);
                    fos = new FileOutputStream(targetFile);
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
        } finally {
            if (Objects.nonNull(fos)) {
                fos.close();
            }
            if (Objects.nonNull(is)) {
                is.close();
            }
            if (Objects.nonNull(zipFile)) {
                zipFile.close();
            }
        }
    }

    public static void unrar(String srcFilePath, String destDirPath) throws IOException, RarException {
        unrar(srcFilePath, destDirPath, "UTF-8");
    }

    public static void unrar(String srcFilePath, String destDirPath, String charset) throws IOException, RarException {
        Archive archive = null;
        FileOutputStream fos = null;

        File srcFile = new File(srcFilePath);
        if (!srcFile.exists()) {
            throw new FileNotFoundException("目标文件不存在!");
        }

        File destDir = new File(destDirPath);
        if (!destDir.exists()) {
            destDir.mkdir();
        }

        try {
            archive = new Archive(new NativeStorage(srcFile));
            FileHeader fh = archive.nextFileHeader();
            while (Objects.nonNull(fh)) {
                // 通过这种方式获取文件名称，传入对应编码避免文件乱码
                byte[] fileNameBytes = fh.getFileNameByteArray();
                int length = 0;
                while (length < fileNameBytes.length
                        && fileNameBytes[length] != 0) {
                    length++;
                }
                byte[] name = new byte[length];
                System.arraycopy(fileNameBytes, 0, name, 0, name.length);
                if (fh.isDirectory()) {
                    String dirPath = destDirPath + File.separator + new String(name, charset);
                    File dir = new File(dirPath);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                } else {
                    // 1 根据不同的操作系统拿到相应的 destDirName 和 destFileName
                    String compressFileName = new String(name, charset);
                    String destFileName = "";
                    String destDirName = "";
                    // 非windows系统
                    if ("/".equals(File.separator)) {
                        destFileName = destDirPath + File.separator + compressFileName.replaceAll("\\\\", "/");
                        destDirName = destFileName.substring(0, destFileName.lastIndexOf("/"));
                        //windows系统
                    } else {
                        destFileName = destDirPath + File.separator + compressFileName.replaceAll("/", "\\\\");
                        destDirName = destFileName.substring(0, destFileName.lastIndexOf("\\"));
                    }
                    // 2 创建文件夹
                    File dir = new File(destDirName);
                    if (!dir.exists() || !dir.isDirectory()) {
                        dir.mkdirs();
                    }
                    System.out.println(destFileName);
                    // 3 解压缩文件
                    fos = new FileOutputStream(new File(destFileName));
                    archive.extractFile(fh, fos);
                    fos.close();
                }
                fh = archive.nextFileHeader();
            }
        } finally {
            if (Objects.nonNull(fos)) {
                fos.close();
            }
            if (Objects.nonNull(archive)) {
                archive.close();
            }
        }
    }

}
