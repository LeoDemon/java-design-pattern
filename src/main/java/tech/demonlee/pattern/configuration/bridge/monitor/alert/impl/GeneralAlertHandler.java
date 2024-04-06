package tech.demonlee.pattern.configuration.bridge.monitor.alert.impl;

import lombok.extern.slf4j.Slf4j;
import tech.demonlee.pattern.configuration.bridge.monitor.alert.AlertHandler;
import tech.demonlee.pattern.configuration.bridge.monitor.alert.NotificationService;
import tech.demonlee.pattern.configuration.bridge.monitor.model.ApiStatistics;
import tech.demonlee.pattern.configuration.bridge.monitor.rule.ApiAlertRuleService;
import tech.demonlee.pattern.configuration.bridge.monitor.alert.AlertHandler;
import tech.demonlee.pattern.configuration.bridge.monitor.alert.NotificationService;
import tech.demonlee.pattern.configuration.bridge.monitor.model.ApiStatistics;
import tech.demonlee.pattern.configuration.bridge.monitor.rule.ApiAlertRuleService;

/**
 * @author Demon.Lee
 * @date 2020-08-30 20:03
 */
@Slf4j
public abstract class GeneralAlertHandler implements AlertHandler {

    private NotificationService notificationService;
    private ApiAlertRuleService apiAlertRuleService;

    public GeneralAlertHandler(NotificationService notificationService, ApiAlertRuleService apiAlertRuleService) {
        this.notificationService = notificationService;
        this.apiAlertRuleService = apiAlertRuleService;
    }

    public NotificationService getNotificationService() {
        return notificationService;
    }

    public ApiAlertRuleService getApiAlertRuleService() {
        return apiAlertRuleService;
    }

    @Override
    public void handle(ApiStatistics apiStatistics) {
        if (matchThreshold(apiStatistics)) {
            String message = getSendMessage(apiStatistics);
            notificationService.notify(message);
        }
    }

    public abstract boolean matchThreshold(ApiStatistics apiStatistics);

    public abstract String getSendMessage(ApiStatistics apiStatistics);
}
