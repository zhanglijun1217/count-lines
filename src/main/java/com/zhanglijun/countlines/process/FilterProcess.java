package com.zhanglijun.countlines.process;

/**
 * 处理责任链的接口和处理方法
 * @author 夸克
 * @date 2018/11/4 23:22
 */
public interface FilterProcess {

    /**
     * 处理文本
     * @param msg
     * @return
     */
    String process(String msg);
}
