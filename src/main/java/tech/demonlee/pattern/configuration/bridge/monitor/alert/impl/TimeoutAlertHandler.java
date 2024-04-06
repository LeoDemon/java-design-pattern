package tech.demonlee.pattern.configuration.bridge.monitor.alert.impl;

import lombok.extern.slf4j.Slf4j;
import tech.demonlee.pattern.configuration.bridge.monitor.alert.NotificationService;
import tech.demonlee.pattern.configuration.bridge.monitor.model.ApiStatistics;
import tech.demonlee.pattern.configuration.bridge.monitor.rule.ApiAlertRuleService;
import tech.demonlee.pattern.configuration.bridge.monitor.alert.NotificationService;
import tech.demonlee.pattern.configuration.bridge.monitor.model.ApiStatistics;
import tech.demonlee.pattern.configuration.bridge.monitor.rule.ApiAlertRuleService;

/**
 * @author Demon.Lee
 * @date 2020-08-30 17:49
 */
@Slf4j
public class TimeoutAlertHandler extends GeneralAlertHandler {

    public TimeoutAlertHandler(NotificationService notificationService, ApiAlertRuleService apiAlertRuleService) {
        super(notificationService, apiAlertRuleService);
    }

    @Override
    public boolean matchThreshold(ApiStatistics apiStatistics) {
        return this.getApiAlertRuleService().matchTimeoutThreshold(apiStatistics.getApiName(),
                apiStatistics.getErrorNum());
    }

    @Override
    public String getSendMessage(ApiStatistics apiStatistics) {
        return apiStatistics.getApiName() + " occurs timeout error, count: " + apiStatistics.getErrorNum();
    }
}
