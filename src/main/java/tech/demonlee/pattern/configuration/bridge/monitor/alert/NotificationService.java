package tech.demonlee.pattern.configuration.bridge.monitor.alert;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Assertions;
import tech.demonlee.pattern.configuration.bridge.notify.MessageSender;
import tech.demonlee.pattern.configuration.bridge.notify.MessageSender;

import java.util.Set;

/**
 * @author Demon.Lee
 * @date 2020-08-30 17:00
 */
@Slf4j
public abstract class NotificationService {

    /**
     * initialize message senders in the sub-class
     *
     * @return List<MessageSender>
     */
    public abstract Set<MessageSender> getMessageSenders();

    /**
     * register new messageSender
     *
     * @param messageSender message sender
     */
    public void registerMessageSender(MessageSender messageSender) {
        Assertions.assertNotNull(messageSender, "register messageSender is null");
        Set<MessageSender> messageSenders = this.getMessageSenders();
        Assertions.assertNotNull(messageSenders, "getMessageSenders is null");
        messageSenders.add(messageSender);
    }

    /**
     * register new messageSenders
     *
     * @param messageSenders message senders
     */
    public void registerMessageSenders(Set<MessageSender> messageSenders) {
        Assertions.assertNotNull(messageSenders, "register messageSenders is empty");
        Set<MessageSender> currentMessageSenders = this.getMessageSenders();
        Assertions.assertNotNull(currentMessageSenders, "getMessageSenders is empty");
        currentMessageSenders.addAll(messageSenders);
    }

    /**
     * send the message to the contacts
     *
     * @param message inform message
     */
    public void notify(String message) {
        Set<MessageSender> messageSenders = this.getMessageSenders();
        if (CollectionUtils.isEmpty(messageSenders)) {
            log.error("senders is empty, cannot send...");
            return;
        }
        this.getMessageSenders().forEach(sender -> sender.send(message));
    }

}
