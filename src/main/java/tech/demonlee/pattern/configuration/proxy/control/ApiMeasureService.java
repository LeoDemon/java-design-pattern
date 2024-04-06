package tech.demonlee.pattern.configuration.proxy.control;

/**
 * @author Demon.Lee
 * @date 2020-08-07 23:45
 */
public interface ApiMeasureService {

    /**
     * 记录每次调用的api耗时
     *
     * @param apiName
     * @param elapsedTime ms
     */
    void collectApiTimeConsume(String apiName, long elapsedTime);
}
