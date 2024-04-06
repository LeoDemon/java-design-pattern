package tech.demonlee.pattern.configuration.bridge.monitor.alert;

import tech.demonlee.pattern.configuration.bridge.monitor.model.ApiStatistics;
import tech.demonlee.pattern.configuration.bridge.monitor.model.ApiStatistics;

/**
 * @author Demon.Lee
 * @date 2020-08-30 17:44
 */
public interface AlertHandler {

    /**
     * check api statistics with the rule-config
     *
     * @param apiStatistics api statistics info
     */
    void handle(ApiStatistics apiStatistics);
}
