package com.zhanglijun.countlines.process;

import com.zhanglijun.countlines.Bean.TotalWords;
import com.zhanglijun.countlines.process.impl.HttpFilterProcess;
import com.zhanglijun.countlines.process.impl.WrapFilterProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 在初始化的时候要将这些处理都加入责任链中，同时提供一个API给客户端去调用执行即可
 * @author 夸克
 * @date 2018/11/4 23:43
 */
@Service
public class FilterProcessManager {

    @Autowired
    private TotalWords totalWords;

    @Resource
    private WrapFilterProcess filterProcess;

    @Resource
    private HttpFilterProcess httpFilterProcess;

    /**
     * 责任链容器
     */
    private List<FilterProcess> filterProcessList = new ArrayList<>();

    @PostConstruct
    public void start() {
        this.addProcess(filterProcess)
                .addProcess(httpFilterProcess);
    }

    /**
     * 责任链中添加process
     * @param filterProcess
     * @return
     */
    public FilterProcessManager addProcess(FilterProcess filterProcess) {
        filterProcessList.add(filterProcess);
        return this;
    }

    /**
     * 责任链中的处理逻辑
     * @param msg
     */
    public void process(String msg) {
        if (StringUtils.isEmpty(msg)) {
            return;
        }
        for (FilterProcess process : filterProcessList) {
            msg = process.process(msg);
        }

        // msg进行统计字数
        totalWords.sum(msg.toCharArray().length);
    }
}
