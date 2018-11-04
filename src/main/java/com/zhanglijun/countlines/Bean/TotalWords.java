package com.zhanglijun.countlines.Bean;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 统计总字数
 * @author 夸克
 * @date 2018/11/5 00:06
 */
@Component
public class TotalWords {
    private AtomicLong sum = new AtomicLong(0);

    /**
     * 累加
     * @param count
     */
    public void sum(int count) {
        // 累加
        sum.addAndGet(count);
    }

    /**
     * 获取sum值
     * @return
     */
    public long total() {
        return sum.get();
    }
}
