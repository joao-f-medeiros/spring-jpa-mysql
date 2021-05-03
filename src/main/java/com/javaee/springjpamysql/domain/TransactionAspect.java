package com.javaee.springjpamysql.domain;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.UUID;

@Slf4j
@Aspect
@Component
public class TransactionAspect extends TransactionSynchronizationAdapter {

    private static final Integer STATUS_ROLLED_BACK = 1;

    @Before("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void registerSynchronization() {
        TransactionSynchronizationManager.registerSynchronization(this);
        final String transId = UUID.randomUUID().toString();
        TransactionSynchronizationManager.setCurrentTransactionName(transId);
    }

    @Override
    public void afterCompletion(int status) {
        String uuid = TransactionSynchronizationManager.getCurrentTransactionName();
        if(status == STATUS_ROLLED_BACK) {
            log.info("Rolling Back for transaction: {}", uuid);
            log.info("Calling RabbitMQ to rollback audit for transaction: {}", uuid);
        }
    }
}
