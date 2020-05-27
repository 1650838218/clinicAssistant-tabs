package com.littledoctor.clinicassistant.module.schedule;

import com.littledoctor.clinicassistant.module.purchase.stock.dao.StockDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @业务信息: 定时任务 检查过期品目（药品），并将状态置为过期（3） 执行时间：每天1:00，7:00,8:00
 * @Filename: CheckExpire.java
 * 2020-01-02   周俊林
 */
@Component
public class CheckExpire {

    @Autowired
    private StockDao stockDao;

    private Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 检查过期品目
     * 每天的1点，7点，8点，12点触发执行
     */
    @Scheduled(cron = "0 0 1,7,8,12 * * ?")
    public void checkExpire() {
        // TODO 此处后面可增加通知，通知用户做了哪些定时任务，执行结果是什么
        try {
            int rowNumber = stockDao.updateStateForExpire();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
