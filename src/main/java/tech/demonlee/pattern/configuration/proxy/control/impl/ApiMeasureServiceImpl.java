package tech.demonlee.pattern.configuration.proxy.control.impl;

import lombok.extern.slf4j.Slf4j;
import tech.demonlee.pattern.configuration.proxy.control.ApiMeasureService;
import tech.demonlee.pattern.configuration.proxy.control.ApiMeasureService;

/**
 * @author Demon.Lee
 * @date 2020-08-07 23:50
 */
@Slf4j
public class ApiMeasureServiceImpl implements ApiMeasureService {

    @Override
    public void collectApiTimeConsume(String apiName, long elapsedTime) {
        log.info("invoke {} cost {} ms", apiName, elapsedTime);
    }
}
