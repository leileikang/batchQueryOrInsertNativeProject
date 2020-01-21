package com.leileikang.batchqueryorinsertnativeproject.task;

import com.leileikang.batchqueryorinsertnativeproject.domain.QueryTime;
import com.leileikang.batchqueryorinsertnativeproject.service.BatchStatisticsMonthDelService;
import com.leileikang.batchqueryorinsertnativeproject.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 访问量定期删除任务 删除上一个月的数据并保存到历史表中
 *
 * @author kangleilei
 * @date 2020/1/21
 */
@Component
@Slf4j
@Async
public class BatchStatisticsMonthDelTask {

    private BatchStatisticsMonthDelService statisticsMonthDelService;

    @Autowired
    public BatchStatisticsMonthDelTask(BatchStatisticsMonthDelService statisticsMonthDelService) {
        this.statisticsMonthDelService = statisticsMonthDelService;
    }


    /**
     * 访问量定期删除任务
     * 每月1号执行
     */
//    @Scheduled(cron = "0 */1 * * * ?")
    @Scheduled(cron = "0 20 1 1 * ?")
    public void scheduled() {
        log.info("EffectivenessStatisticsMonthDelTask start");
        try {
            QueryTime time = DateUtil.getAgoMonth(1);
            statisticsMonthDelService.deleteStatisticsData(time.getStartTime(), time.getEndTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("EffectivenessStatisticsMonthDelTask end");
    }


}
