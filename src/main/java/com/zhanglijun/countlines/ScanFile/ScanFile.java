package com.zhanglijun.countlines.ScanFile;

import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 夸克
 * @date 2018/11/5 00:30
 */
@Component
public class ScanFile {

    private List<String> allFile = new ArrayList<>();

    /**
     * 根据path 递归读取可读取的md文件路径
     * @param path
     * @return
     */
    public List<String> getAllFile(String path) {
        File f = new File(path);
        List<String> allFile = new ArrayList<>();
        File[] files = f.listFiles();
        if (null != files && files.length > 0) {
            for (File file : files) {
                if (file.isDirectory()) {
                    String directoryPath = file.getPath();
                    getAllFile(directoryPath);
                } else {
                    String filePath = file.getPath();
                    if (!filePath.endsWith(".md")) {
                        continue;
                    }

                    allFile.add(filePath);
                }
            }
        }
        return allFile;
    }

}
