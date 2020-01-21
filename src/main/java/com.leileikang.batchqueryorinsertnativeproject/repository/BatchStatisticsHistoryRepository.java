package com.leileikang.batchqueryorinsertnativeproject.repository;

import com.leileikang.batchqueryorinsertnativeproject.entity.BatchStatisticsHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;


/**
 * Jpa实现数据库交互
 *
 * @author kangleilei
 * @date 2020/1/21
 */
public interface BatchStatisticsHistoryRepository extends JpaRepository<BatchStatisticsHistoryEntity,
        String>, QuerydslPredicateExecutor<BatchStatisticsHistoryEntity> {
}
