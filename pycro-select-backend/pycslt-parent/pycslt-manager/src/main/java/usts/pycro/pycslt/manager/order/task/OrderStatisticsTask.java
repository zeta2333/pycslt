package usts.pycro.pycslt.manager.order.task;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import usts.pycro.pycslt.manager.order.service.OrderInfoService;
import usts.pycro.pycslt.manager.order.service.OrderStatisticsService;
import usts.pycro.pycslt.model.entity.order.OrderStatistics;

import java.util.Date;

/**
 * @author Pycro
 * @version 1.0
 * 2023-11-04 16:30
 */
@Component
@Slf4j
public class OrderStatisticsTask {

    @Autowired
    private OrderInfoService orderInfoService;
    @Autowired
    private OrderStatisticsService orderStatisticsService;
    // 测试springTask
    /*@Scheduled(cron = "0/5 * * * * ? ")
    public void testHello() {
        log.info(new Date().toInstant().toString());
    }*/

    @Scheduled(cron = "0 0 2 * * ?")
    // @Scheduled(cron = "0/10 * * * * ?") // TODO 测试用
    public void orderTotalAmountStatistics() {
        log.info(new Date().toInstant().toString());
        // 1 获取前一天的日期
        String targetDate = DateUtil.offsetDay(new Date(), -1).toString("yyyy-MM-dd");
        // 2 统计前一天的交易金额
        OrderStatistics orderStatistics = orderInfoService.selectStatisticsByDate(targetDate);
        // 3 统计之后的数据添加到统计表中
        if (orderStatistics != null) {
            orderStatisticsService.save(orderStatistics);
        }
    }
}
