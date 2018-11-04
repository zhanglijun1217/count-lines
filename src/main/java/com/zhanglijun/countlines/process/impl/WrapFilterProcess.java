package com.zhanglijun.countlines.process.impl;

import com.zhanglijun.countlines.process.FilterProcess;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 处理空格和换行
 * @author 夸克
 * @date 2018/11/4 23:27
 */
@Service
public class WrapFilterProcess implements FilterProcess {

    @Override
    public String process(String msg) {
        if (StringUtils.isEmpty(msg)) {
            return "";
        }
        msg = msg.replaceAll("\\s*", "");
        return msg;
    }
}
