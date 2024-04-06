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
public class TpsAlertHandler extends GeneralAlertHandler {

    public TpsAlertHandler(NotificationService notificationService, ApiAlertRuleService apiAlertRuleService) {
        super(notificationService, apiAlertRuleService);
    }

    @Override
    public boolean matchThreshold(ApiStatistics apiStatistics) {
        return this.getApiAlertRuleService().matchTpsThreshold(apiStatistics.getApiName(), apiStatistics.getErrorNum());
    }

    @Override
    public String getSendMessage(ApiStatistics apiStatistics) {
        return apiStatistics.getApiName() + " tps is overloaded, count: " + apiStatistics.getErrorNum();
    }
}
