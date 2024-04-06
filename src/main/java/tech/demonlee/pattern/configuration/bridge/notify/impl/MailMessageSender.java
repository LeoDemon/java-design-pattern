package tech.demonlee.pattern.configuration.bridge.notify.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import tech.demonlee.pattern.configuration.bridge.notify.MessageSender;
import tech.demonlee.pattern.configuration.bridge.notify.MessageSender;

import java.util.List;

/**
 * @author Demon.Lee
 * @date 2020-08-30 11:59
 */
@Slf4j
public class MailMessageSender implements MessageSender {

    private final List<String> emailAddresses;

    public MailMessageSender(List<String> emailAddresses) {
        this.emailAddresses = emailAddresses;
    }

    @Override
    public void send(String message) {
        if (CollectionUtils.isEmpty(emailAddresses) || StringUtils.isBlank(message)) {
            log.error("emailAddresses or message is empty, cannot send...");
            return;
        }
        log.info("begin to send mail message: {} to the contacts: {}", message, emailAddresses);
    }
}
