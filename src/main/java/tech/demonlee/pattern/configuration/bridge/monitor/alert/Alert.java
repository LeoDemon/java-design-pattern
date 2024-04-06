package tech.demonlee.pattern.configuration.bridge.monitor.alert;

import org.junit.jupiter.api.Assertions;
import tech.demonlee.pattern.configuration.bridge.monitor.model.ApiStatistics;
import tech.demonlee.pattern.configuration.bridge.monitor.model.ApiStatistics;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author Demon.Lee
 * @date 2020-08-30 20:15
 * @desc <p>
 * Task Decomposition
 * 1. add 'registerHandler' method for registering {@link AlertHandler}
 * 2. add 'handlers' fields
 * 3. add 'check' method
 */
public class Alert {

    private final Set<AlertHandler> handlers = new CopyOnWriteArraySet<>();

    public void register(AlertHandler handler) {
        Assertions.assertNotNull(handler, "handler is required");
        handlers.add(handler);
    }

    public void registers(Set<AlertHandler> handlers) {
        Assertions.assertNotNull(handlers, "handlers is required");
        this.handlers.addAll(handlers);
    }

    public void check(ApiStatistics apiStatistics) {
        handlers.forEach(handler -> handler.handle(apiStatistics));
    }
}
