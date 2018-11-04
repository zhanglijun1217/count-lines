package com.zhanglijun.countlines;

import com.zhanglijun.countlines.Bean.TotalWords;
import com.zhanglijun.countlines.ScanFile.ScanFile;
import com.zhanglijun.countlines.process.FilterProcessManager;
import com.zhanglijun.countlines.task.ScanNumTask;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.annotation.Resources;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CountLinesApplicationTests {

	@Test
	public void contextLoads() {
	}


	@Resource
	private ScanFile scanFile;
	@Resource
	private FilterProcessManager filterProcessManager;
	@Resource
	private TotalWords totalWords;
	@Test
	public void testCountLines() throws Exception{

		long start = System.currentTimeMillis();

		List<String> allFile = scanFile.getAllFile("/Users/zhanglijun/hexo/blog/source/_posts");
		System.out.println("allFile size = " + allFile.size());

		ExecutorService executorService = Executors.newCachedThreadPool();
		for (String s : allFile) {
			executorService.execute(new ScanNumTask(s, filterProcessManager));
		}


		executorService.shutdown();
		// 这里要保证线程都执行完 再去打印结果 这里是通过awaitTermination方法来实现线程执行完
		while (!executorService.awaitTermination(100, TimeUnit.MILLISECONDS)) {
			System.out.println("还有线程在运行...");
		}

		long total = totalWords.total();
		long end = System.currentTimeMillis();

		System.out.println("total sum = " + total + " cost = " + (end - start));
	}

	/**
	 * 测试同步执行
	 */
	@Test
	public void testSyncCountLine() {

		long start = System.currentTimeMillis();

		List<String> allFile = scanFile.getAllFile("/Users/zhanglijun/hexo/blog/source/_posts");
		System.out.println("allFile size = " + allFile.size());

		allFile.forEach(e -> read(e));

		long total = totalWords.total();
		long end = System.currentTimeMillis();

		System.out.println("total sum = " + total + " cost = " + (end - start));
	}

	public void read(String path) {

		Stream<String> stringStream = null;
		try {
			stringStream = Files.lines(Paths.get(path), StandardCharsets.UTF_8);
		} catch (Exception e) {
			System.out.println("IOException\n" + e);
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
