package com.leileikang.batchqueryorinsertnativeproject.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * 访问信息实体类
 *
 * @author kangleilei
 * @date 2020/1/21
 */
@ApiModel(value = "BatchStatisticsHistoryEntity", description = "访问信息历史库")
@Table(name = "t_statistics_history")
@QueryEntity
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BatchStatisticsHistoryEntity {

    /**
     * 主键id
     */
    @ApiModelProperty("主键id，新增时不需要填写")
    @Id
    @GeneratedValue(generator = "guuidGenerator")
    @GenericGenerator(name = "guuidGenerator", strategy = "com.ceis.core.base.persistence.GUUIDGenerator")
    @Column(name = "id", nullable = false, length = 36)
    private String id;

    /**
     * 文章id
     */
    @Column(name = "article_id", length = 36)
    private String articleId;

    /**
     * 文章标题
     */
    @ApiModelProperty("页面名称（文章标题）")
    @Column(name = "article_title", length = 7500)
    private String articleTitle;

    /**
     * 受访页面URL
     */
    @ApiModelProperty("受访页面URL")
    @Column(name = "article_url", length = 7500)
    private String articleUrl;

    /**
     * IP
     */
    @ApiModelProperty("IP")
    @Column(name = "visit_ip", length = 36)
    private String visitIp;

    /**
     * 设备类型
     */
    @ApiModelProperty("设备类型")
    @Column(name = "visit_device_type", length = 36)
    private String visitDeviceType;

    /**
     * 设备名称
     */
    @ApiModelProperty("设备名称")
    @Column(name = "visit_device_name", length = 36)
    private String visitDeviceName;

    /**
     * 浏览器
     */
    @ApiModelProperty("浏览器")
    @Column(name = "visit_browser", length = 36)
    private String visitBrowser;

    /**
     * 地域信息
     */
    @ApiModelProperty("地域信息")
    @Column(name = "city_info", length = 7500)
    private String cityInfo;

    /**
     * 操作系统
     */
    @ApiModelProperty("操作系统")
    @Column(name = "operation_system", length = 36)
    private String operationSystem;

    /**
     * 屏幕分辨率
     */
    @ApiModelProperty("屏幕分辨率")
    @Column(name = "resolution_ratio", length = 36)
    private String resolutionRatio;

    /**
     * 登录账号
     */
    @ApiModelProperty("登录账号")
    @Column(name = "user_id", length = 36)
    private String userId;

    /**
     * 登录账号
     */
    @ApiModelProperty("登录账号")
    @Column(name = "user_name", length = 36)
    private String userName;

    /**
     * 访问类型（新访客/老访客）
     */
    @ApiModelProperty("访问类型")
    @Column(name = "visit_type", length = 36)
    private String visitType;

    /**
     * 访问时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    @Temporal(TemporalType.TIMESTAMP)
    @ApiModelProperty("访问时间")
    @Column(name = "visit_time", length = 36)
    private Date visitTime;

    /**
     * 访客标识码
     */
    @ApiModelProperty("访客标识码")
    @Column(name = "visitor_code", length = 36)
    private String visitorCode;

    /**
     * 上一次访问时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    @Temporal(TemporalType.TIMESTAMP)
    @ApiModelProperty("上一次访问时间")
    @Column(name = "last_visitor_time", length = 36)
    private Date lastVisitorTime;

    /**
     * sessionId唯一标识
     */
    @ApiModelProperty("sessionId唯一标识")
    @Column(name = "session_id", length = 36)
    private String sessionId;

    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    @Column(name = "creator_id", length = 36)
    private String creatorId;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time", updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date createTime;

    /**
     * 最后操作人
     */
    @ApiModelProperty("最后操作人")
    @Column(name = "last_operator_id", length = 36)
    private String lastOperatorId;

    /**
     * 最后操作时间
     */
    @ApiModelProperty("最后操作时间")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 访问量统计
     */
    @Transient
    private String pageView;

    @Transient
    private String visitDate;

}
