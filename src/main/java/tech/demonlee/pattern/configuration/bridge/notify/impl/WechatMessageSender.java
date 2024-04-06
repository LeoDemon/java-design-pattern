package tech.demonlee.pattern.configuration.bridge.notify.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import tech.demonlee.pattern.configuration.bridge.notify.MessageSender;

import java.util.List;

/**
 * @author Demon.Lee
 * @date 2020-08-30 11:59
 */
@Slf4j
public class WechatMessageSender implements MessageSender {

    private final List<String> wechatIds;

    public WechatMessageSender(List<String> wechatIds) {
        this.wechatIds = wechatIds;
    }

    @Override
    public void send(String message) {
        if (CollectionUtils.isEmpty(wechatIds) || StringUtils.isBlank(message)) {
            log.error("wechatIds or message is empty, cannot send...");
            return;
        }
        log.info("begin to send wechat message: {} to the contacts: {}", message, wechatIds);
    }
}
