package com.leileikang.batchqueryorinsertnativeproject.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * 查询时间实体
 *
 * @Auther: kangleilei
 * @Date: 2020/1/21
 */
@Getter
@Setter
public class QueryTime {

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    public QueryTime() {
    }

    public QueryTime(String startTime, String endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

}
