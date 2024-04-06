package tech.demonlee.pattern.configuration.bridge.monitor.alert.impl;

import lombok.extern.slf4j.Slf4j;
import tech.demonlee.pattern.configuration.bridge.monitor.alert.NotificationService;
import tech.demonlee.pattern.configuration.bridge.notify.MessageSender;
import tech.demonlee.pattern.configuration.bridge.monitor.alert.NotificationService;
import tech.demonlee.pattern.configuration.bridge.notify.MessageSender;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author Demon.Lee
 * @date 2020-08-30 17:06
 */
@Slf4j
public class GeneralNotificationServiceImpl extends NotificationService {

    private final Set<MessageSender> messageSenders = new CopyOnWriteArraySet<>();

    @Override
    public Set<MessageSender> getMessageSenders() {
        return messageSenders;
    }
}
