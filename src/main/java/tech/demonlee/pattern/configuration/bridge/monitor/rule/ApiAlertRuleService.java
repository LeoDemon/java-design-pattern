package tech.demonlee.pattern.configuration.bridge.monitor.rule;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Demon.Lee
 * @date 2020-08-30 17:52
 */
@Slf4j
public class ApiAlertRuleService {

    public boolean matchTpsThreshold(String apiName, int tpsNum) {
        return Math.random() * 100 >= 30;
    }

    public boolean matchErrorThreshold(String apiName, int errNum) {
        return Math.random() * 100 >= 40;
    }

    public boolean matchTimeoutThreshold(String apiName, int timeoutNum) {
        return Math.random() * 100 >= 50;
    }

}
