package edu.nju.parser.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Slf4j
public class FileUtil {

    /**
     * 获取给定路径下的所有文件名称，包装成一个列表返回
     *
     * @param targetPath 目标文件/文件夹路径
     * @return 所有文件名称列表
     * */
    public static List<String> getAllFileName(String targetPath) {
        File target = new File(targetPath);
        if (!target.exists()) {
            return new LinkedList<>();
        }
        if (target.isFile()) {
            return Collections.singletonList(formatFileName(target.getName()));
        } else {
            List<String> results = new LinkedList<>();
            results.add(formatFileName(target.getName()));
            if (Objects.nonNull(target.listFiles())) {
                for (File f: Objects.requireNonNull(target.listFiles())) {
                    try {
                        results.addAll(getAllFileName(f.getCanonicalPath()));
                    } catch (IOException e) {
                        log.error(e.getMessage(), e);
                    }
                }
            }
            return results;
        }
    }

    public static String formatFileName(String fileName) {
        if (fileName.contains(".")) {
            return fileName.substring(0, fileName.indexOf("."));
        } else {
            return fileName;
        }
    }

}