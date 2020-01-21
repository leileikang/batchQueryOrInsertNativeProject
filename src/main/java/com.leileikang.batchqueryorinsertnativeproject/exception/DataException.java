package com.leileikang.batchqueryorinsertnativeproject.exception;

import com.leileikang.batchqueryorinsertnativeproject.enums.DataExceptionEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * 数据异常封装
 *
 * @author kangleilei
 * @date 2020/1/21
 */
@Getter
@Setter
public class DataException extends RuntimeException {

    private static final long serialVersionUID = -5911038119938998640L;

    private Integer code;

    public DataException(DataExceptionEnum dataExceptionEnum) {
        super(dataExceptionEnum.getMsg());
        this.code = dataExceptionEnum.getCode();
    }

    public DataException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }

}
