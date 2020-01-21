package com.leileikang.batchqueryorinsertnativeproject.service;

/**
 * 访问量定期删除任务实现
 *
 * @author kangleilei
 * @date 2020/1/21
 */
public interface BatchStatisticsMonthDelService {

    /**
     * 删除上个月访问数据
     * @param startTime 开始时间
     * @param endTime 结束时间
     */
    void deleteStatisticsData(String startTime, String endTime);

}
