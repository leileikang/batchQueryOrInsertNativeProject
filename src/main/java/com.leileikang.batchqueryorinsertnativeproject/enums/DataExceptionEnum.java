package com.leileikang.batchqueryorinsertnativeproject.enums;

import lombok.Getter;

/**
 * 数据异常枚举
 *
 * @author kangleilei
 * @date 2020/1/21
 */
@Getter
public enum DataExceptionEnum {

    /**
     * 数据异常枚举项
     */
    BEAN_INSTANCE_FAIL(-1, "实例化JavaBean失败"),
    PROP_ANALYSE_FAIL(-2, "类属性分析失败"),
    PROP_SETTER_FAIL(-3, "调用属性的setter方法失败"),
    DATA_SEND_QUEUE_FAIL(-10, "数据推送消息队列失败"),
    PAGE_DATA_INPUT_FAIL(-6, "获取页面参数错误"),
    HISTORY_DATA_FAIL(-7, "历史数据获取失败"),
    STATISTICS_INFO_DATA_FAIL(-7, "本月没有访问数据")
    ;

    /**
     * 数据异常代码
     */
    private Integer code;
    /**
     * 数据异常信息描述
     */
    private String msg;

    DataExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
