package com.zhanglijun.countlines;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication(scanBasePackages = "com.zhanglijun.countlines.*")
public class CountLinesApplication {

	public static void main(String[] args) {
		SpringApplication.run(CountLinesApplication.class, args);
	}

	/**
	 * java8 读取文件的方法
	 */
	private List<String> readFile(List<String> allFile) throws IOException {
		List<String> list = new ArrayList<>();
		for (String path : allFile) {
			Stream<String> stringStream = Files.lines(Paths.get(path), StandardCharsets.UTF_8);
			// TODO 接下来就是对读出的文件每行字符串进行筛选
			List<String> collect = stringStream.collect(Collectors.toList());
			list.addAll(collect);
		}
		return list;

	}

}
