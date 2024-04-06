package tech.demonlee.pattern.configuration.bridge.monitor.model;

/**
 * @author Demon.Lee
 * @date 2020-08-30 17:46
 */
public class ApiStatistics {

    private final String apiName;
    private final int errorNum;
    private final int requestNum;
    private final int timeoutNum;
    private final int durationSeconds;

    public ApiStatistics(String apiName, int errorNum, int requestNum, int timeoutNum, int durationSeconds) {
        this.apiName = apiName;
        this.errorNum = errorNum;
        this.requestNum = requestNum;
        this.timeoutNum = timeoutNum;
        this.durationSeconds = durationSeconds;
    }

    public String getApiName() {
        return apiName;
    }

    public int getErrorNum() {
        return errorNum;
    }

    public int getRequestNum() {
        return requestNum;
    }

    public int getTimeoutNum() {
        return timeoutNum;
    }

    public int getDurationSeconds() {
        return durationSeconds;
    }
}
