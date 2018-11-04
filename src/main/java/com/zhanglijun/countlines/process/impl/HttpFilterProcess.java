package com.zhanglijun.countlines.process.impl;

import com.zhanglijun.countlines.process.FilterProcess;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 超链处理实现
 * @author 夸克
 * @date 2018/11/4 23:29
 */
@Service
public class HttpFilterProcess implements FilterProcess {
    @Override
    public String process(String msg) {
        if (StringUtils.isEmpty(msg))
            return "";
        msg = msg.replaceAll("^((https|http|ftp|rtsp|mms)?:\\/\\/)[^\\s]+", "");
        return msg;

    }
}
