package com.leileikang.batchqueryorinsertnativeproject.service.impl;

import com.leileikang.batchqueryorinsertnativeproject.entity.BatchStatisticsInfoEntity;
import com.leileikang.batchqueryorinsertnativeproject.entity.QBatchStatisticsHistoryEntity;
import com.leileikang.batchqueryorinsertnativeproject.entity.QBatchStatisticsInfoEntity;
import com.leileikang.batchqueryorinsertnativeproject.enums.DataExceptionEnum;
import com.leileikang.batchqueryorinsertnativeproject.exception.DataException;
import com.leileikang.batchqueryorinsertnativeproject.repository.BatchStatisticsHistoryRepository;
import com.leileikang.batchqueryorinsertnativeproject.service.BatchStatisticsMonthDelService;
import com.leileikang.batchqueryorinsertnativeproject.utils.HStringUtils;
import com.leileikang.batchqueryorinsertnativeproject.utils.UUIDUtil;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * 访问量定期删除任务业务处理
 *
 * @author kangleilei
 * @date 2020/1/21
 */
@Slf4j
@Service
public class BatchStatisticsMonthDelServiceImpl implements BatchStatisticsMonthDelService {

    // 测试环境
    private String url = "jdbc:dm://127.0.0.1:5236/CEISEFFECTIVENESS?zeroDateTimeBehavior=convertToNull&useUnicode=true&characterEncoding=utf-8";
    // 生产环境
//    private String url = "jdbc:dm://127.0.0.1:5236/CEISEFFECTIVENESS?zeroDateTimeBehavior=convertToNull&useUnicode=true&characterEncoding=utf-8";
    private String user = "admin";
    private String password = "admin@123";

    //实体管理者
    private EntityManager entityManager;

    //JPA查询工厂
    private JPAQueryFactory queryFactory;

    private BatchStatisticsHistoryRepository statisticsHistoryRepository;

    @PostConstruct
    public void initFactory() {
        queryFactory = new JPAQueryFactory(entityManager);
    }

    @Autowired
    public BatchStatisticsMonthDelServiceImpl(EntityManager entityManager, BatchStatisticsHistoryRepository statisticsHistoryRepository) {
        this.entityManager = entityManager;
        this.statisticsHistoryRepository = statisticsHistoryRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteStatisticsData(String startTime, String endTime) {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        QBatchStatisticsInfoEntity infoEntity = QBatchStatisticsInfoEntity.batchStatisticsInfoEntity;
        QBatchStatisticsHistoryEntity historyEntity = QBatchStatisticsHistoryEntity.batchStatisticsHistoryEntity;
        // 安条件求info表总数
        int totalCount = (int) queryFactory.selectFrom(infoEntity).where(infoEntity.visitTime.between
                (HStringUtils.minStringDate(startTime), HStringUtils.maxStringDate(endTime))).fetchCount();
        if (totalCount > 0) {
            short pageSize = 500;
            int pageNum = totalCount / pageSize;
            int surplus = totalCount % pageSize;
            if (surplus > 0) {
                pageNum = pageNum + 1;
            }
            for (int i = 0; i < pageNum; i++) {
                int pageBegin = i * pageSize;
                log.info("i:" + i + "---->pageNum:"+ pageNum + "----->totalCount:"+totalCount);
                List<BatchStatisticsInfoEntity> infoList = new ArrayList<>();
                // 按条件查询原生
                try {
                    Class.forName("dm.jdbc.driver.DmDriver");
                    connection = DriverManager.getConnection(url, user, password);
                    String selectSql = "select * from T_STATISTICS_INFO t JOIN (select id from T_STATISTICS_INFO t1 " +
                                  "where t1.visit_time between ? and ? limit ? , 500) t2 ON t2.id = t.id";
                    Long selectStartTimes = System.currentTimeMillis();
                    statement = connection.prepareStatement(selectSql);
                    statement.setTimestamp(1, new Timestamp(HStringUtils.minStringDate(startTime).getTime()));
                    statement.setTimestamp(2, new Timestamp(HStringUtils.maxStringDate(endTime).getTime()));
                    statement.setInt(3, pageBegin);
                    resultSet = statement.executeQuery();
                    BatchStatisticsInfoEntity entity = new BatchStatisticsInfoEntity();
                    while (resultSet.next()) {
                        entity.setId(resultSet.getString("id"));
                        entity.setArticleTitle(resultSet.getString("article_title"));
                        entity.setArticleUrl(resultSet.getString("article_url"));
                        entity.setVisitIp(resultSet.getString("visit_ip"));
                        entity.setVisitDeviceType(resultSet.getString("visit_device_type"));
                        entity.setVisitDeviceName(resultSet.getString("visit_device_name"));
                        entity.setVisitBrowser(resultSet.getString("visit_browser"));
                        entity.setCityInfo(resultSet.getString("city_info"));
                        entity.setOperationSystem(resultSet.getString("operation_system"));
                        entity.setResolutionRatio(resultSet.getString("resolution_ratio"));
                        entity.setUserId(resultSet.getString("user_id"));
                        entity.setUserName(resultSet.getString("user_name"));
                        entity.setVisitType(resultSet.getString("visit_type"));
                        entity.setVisitTime(resultSet.getTimestamp("visit_time"));
                        entity.setVisitorCode(resultSet.getString("visitor_code"));
                        entity.setLastVisitorTime(resultSet.getTimestamp("last_visitor_time"));
                        entity.setSessionId(resultSet.getString("session_id"));
                        entity.setCreatorId(resultSet.getString("creator_id"));
                        entity.setCreateTime(resultSet.getTimestamp("create_time"));
                        entity.setLastOperatorId(resultSet.getString("last_operator_id"));
                        entity.setUpdateTime(resultSet.getTimestamp("update_time"));
                        infoList.add(entity);
                    }
                    Long selectEndTimes = System.currentTimeMillis();
                    log.info("查询用时：" + (selectEndTimes - selectStartTimes) / 1000 + " s");
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                } finally {
                    if (resultSet != null) {
                        try {
                            resultSet.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    if (statement != null) {
                        try {
                            statement.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                            throw new RuntimeException(e);
                        }
                    }
                    if (connection != null) {
                        try {
                            connection.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                            throw new RuntimeException(e);
                        }
                    }
                }

                // 插入数据库原生
                try {
                    Class.forName("dm.jdbc.driver.DmDriver");
                    connection = DriverManager.getConnection(url, user, password);
                    String insertSql = "insert into \"T_STATISTICS_HISTORY\"(\"ARTICLE_ID\", \"ARTICLE_TITLE\", \"ARTICLE_URL\", \"CREATE_TIME\", \"CREATOR_ID\", " +
                            " \"LAST_OPERATOR_ID\", \"LAST_VISITOR_TIME\", \"OPERATION_SYSTEM\", \"RESOLUTION_RATIO\", \"SESSION_ID\", \"UPDATE_TIME\", \"USER_ID\", \"USER_NAME\", " +
                            " \"VISIT_BROWSER\", \"VISIT_DEVICE_NAME\", \"VISIT_DEVICE_TYPE\", \"VISIT_IP\", \"VISIT_TIME\", \"VISIT_TYPE\", \"VISITOR_CODE\", \"CITY_INFO\", \"ID\") " +
                            " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                    statement = connection.prepareStatement(insertSql);
                    Long insertStartTimes = System.currentTimeMillis();
                    connection.setAutoCommit(false);
                    for (BatchStatisticsInfoEntity data : infoList) {
                        statement.setString(1, data.getId());
                        statement.setString(2, data.getArticleTitle());
                        statement.setString(3, data.getArticleUrl());
                        statement.setTimestamp(4, new Timestamp(data.getCreateTime().getTime()));
                        statement.setString(5, data.getCreatorId());
                        statement.setString(6, data.getLastOperatorId());
                        if (data.getLastVisitorTime() != null) {
                            statement.setTimestamp(7, new Timestamp(data.getLastVisitorTime().getTime()));
                        } else {
                            statement.setDate(7, null);
                        }
                        statement.setString(8, data.getOperationSystem());
                        statement.setString(9, data.getResolutionRatio());
                        statement.setString(10, data.getSessionId());
                        statement.setTimestamp(11, new Timestamp(data.getUpdateTime().getTime()));
                        statement.setString(12, data.getUserId());
                        statement.setString(13, data.getUserName());
                        statement.setString(14, data.getVisitBrowser());
                        statement.setString(15, data.getVisitDeviceName());
                        statement.setString(16, data.getVisitDeviceType());
                        statement.setString(17, data.getVisitIp());
                        statement.setTimestamp(18, new Timestamp(data.getVisitTime().getTime()));
                        statement.setString(19, data.getVisitType());
                        statement.setString(20, data.getVisitorCode());
                        statement.setString(21, data.getCityInfo());
                        statement.setString(22, UUIDUtil.getUUID());
                        statement.addBatch();
                    }
                    statement.executeBatch();
                    connection.commit();
                    Long insertEndTimes = System.currentTimeMillis();
                    log.info("插入用时：" + (insertEndTimes - insertStartTimes) / 1000 + " s");
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                } finally {
                    if (statement != null) {
                        try {
                            statement.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                            throw new RuntimeException(e);
                        }
                    }
                    if (connection != null) {
                        try {
                            connection.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
             // 查询历史表数据
            int historyCount = (int) queryFactory.selectFrom(historyEntity).where(historyEntity
                    .visitTime.between(HStringUtils.minStringDate(startTime), HStringUtils.maxStringDate(endTime))).fetchCount();
            if (historyCount == totalCount) {
                queryFactory.delete(infoEntity).where(infoEntity
                        .visitTime.between(HStringUtils.minStringDate(startTime), HStringUtils.maxStringDate(endTime))).execute();
                log.info("数据删除成功");
            } else {
                throw new DataException(DataExceptionEnum.HISTORY_DATA_FAIL);
            }

        } else {
            throw new DataException(DataExceptionEnum.STATISTICS_INFO_DATA_FAIL);
        }

    }

}
