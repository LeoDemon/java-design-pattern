package tech.demonlee.pattern.configuration.bridge.notify;

/**
 * @author Demon.Lee
 * @date 2020-08-30 11:55
 */
public interface MessageSender {

    /**
     * send message to contacts
     *
     * @param message inform message
     */
    void send(String message);
}
