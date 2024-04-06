package tech.demonlee.pattern.configuration.bridge;

import tech.demonlee.pattern.configuration.bridge.monitor.alert.Alert;
import tech.demonlee.pattern.configuration.bridge.monitor.alert.AlertHandler;
import tech.demonlee.pattern.configuration.bridge.monitor.alert.NotificationService;
import tech.demonlee.pattern.configuration.bridge.monitor.alert.impl.*;
import tech.demonlee.pattern.configuration.bridge.monitor.rule.ApiAlertRuleService;
import tech.demonlee.pattern.configuration.bridge.notify.MessageSender;
import tech.demonlee.pattern.configuration.bridge.notify.impl.MailMessageSender;
import tech.demonlee.pattern.configuration.bridge.notify.impl.SmsMessageSender;
import tech.demonlee.pattern.configuration.bridge.notify.impl.TelephoneMessageSender;
import tech.demonlee.pattern.configuration.bridge.notify.impl.WechatMessageSender;
import tech.demonlee.pattern.configuration.bridge.monitor.rule.ApiAlertRuleService;
import tech.demonlee.pattern.configuration.bridge.notify.MessageSender;
import tech.demonlee.pattern.configuration.bridge.notify.impl.MailMessageSender;
import tech.demonlee.pattern.configuration.bridge.notify.impl.SmsMessageSender;
import tech.demonlee.pattern.configuration.bridge.notify.impl.TelephoneMessageSender;
import tech.demonlee.pattern.configuration.bridge.notify.impl.WechatMessageSender;

import java.util.Arrays;
import java.util.Set;

/**
 * @author Demon.Lee
 * @date 2020-08-29 17:22
 * @desc <p>
 * 1. Task Decomposition
 * 1.1 Initialize MessageSender(TelephoneSender, WechatSender, MailSender, SmsSender)
 * 1.2 Initialize NotificationService(SevereNotificationService, UrgencyNotificationService,
 * GeneralNotificationService, InessentialNotificationService)
 * 1.3 Register MessageSender to NotificationService
 * 1.4 Initialize ApiStatistics
 * 1.5 Initialize AlertRuleService
 * 1.6 Initialize AlertHandler(TpsAlertHandler, ErrorAlertHandler, TimeoutAlertHandler)
 * 1.7 Initialize Alert
 * 1.8 Register AlertHandler to Alert
 */
public class BridgeApplicationContext {

    // 1.1 create a private constructor
    // 1.2 create an static-inner class
    // 1.3 create public 'getInstance()' method
    // 1.4 create 'getAlert' method
    // 1.5 Initialize components

    private final Alert alert;

    private BridgeApplicationContext() {
        MessageSender telephoneSender = new TelephoneMessageSender(Arrays.asList("18800001111", "18800001112"));
        MessageSender wechatSender = new WechatMessageSender(Arrays.asList("wx12300019901", "wx12300019902"));
        MessageSender mailSender = new MailMessageSender(Arrays.asList("jwkljh@163.com", "jwkljh@gmail.com"));
        MessageSender smsSender = new SmsMessageSender(Arrays.asList("18800001111", "18800001112"));
        NotificationService severeNotificationService = new SevereNotificationServiceImpl();
        NotificationService urgencyNotificationService = new UrgencyNotificationServiceImpl();
        NotificationService generalNotificationService = new GeneralNotificationServiceImpl();
        NotificationService inessentialNotificationService = new InessentialNotificationServiceImpl();

        severeNotificationService.registerMessageSenders(Set.of(telephoneSender, mailSender, smsSender, wechatSender));
        urgencyNotificationService.registerMessageSenders(Set.of(mailSender, smsSender, wechatSender));
        generalNotificationService.registerMessageSenders(Set.of(mailSender, wechatSender));
        inessentialNotificationService.registerMessageSenders(Set.of(mailSender));

        ApiAlertRuleService apiAlertRuleService = new ApiAlertRuleService();

        AlertHandler errorAlertHandler = new ErrorAlertHandler(severeNotificationService, apiAlertRuleService);
        AlertHandler tpsAlertHandler = new TpsAlertHandler(generalNotificationService, apiAlertRuleService);
        AlertHandler timeoutAlertHandler = new TimeoutAlertHandler(urgencyNotificationService, apiAlertRuleService);

        this.alert = new Alert();
        this.alert.registers(Set.of(errorAlertHandler, tpsAlertHandler, timeoutAlertHandler));
    }

    public static BridgeApplicationContext getInstance() {
        return ApplicationContextSupport.CONTEXT;
    }

    public Alert getAlert() {
        return alert;
    }

    private static class ApplicationContextSupport {
        private static final BridgeApplicationContext CONTEXT = new BridgeApplicationContext();
    }
}
