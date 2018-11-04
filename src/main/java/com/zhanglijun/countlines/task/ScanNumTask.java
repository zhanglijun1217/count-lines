package com.zhanglijun.countlines.task;

import com.zhanglijun.countlines.process.FilterProcessManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author 夸克
 * @date 2018/11/5 00:14
 */
public class ScanNumTask implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(ScanNumTask.class);
    private String path;
    private FilterProcessManager filterProcessManager;

    public ScanNumTask(String path, FilterProcessManager filterProcessManager) {
        this.path = path;
        this.filterProcessManager = filterProcessManager;
    }

    /**
     * 利用java8读取文本
     */
    @Override
    public void run() {
        Stream<String> stringStream = null;
        try {
            stringStream = Files.lines(Paths.get(path), StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("IOException", e);
        }

        if (null == stringStream) {
            return;
        }
        try {
            TimeUnit.MILLISECONDS.sleep(100);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 读取文件
        List<String> collect = stringStream.collect(Collectors.toList());
        // 责任链处理文本
        collect.forEach(e -> filterProcessManager.process(e));
    }
}
