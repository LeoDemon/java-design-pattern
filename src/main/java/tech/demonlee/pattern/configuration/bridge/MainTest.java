package tech.demonlee.pattern.configuration.bridge;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import tech.demonlee.pattern.configuration.bridge.monitor.model.ApiStatistics;
import tech.demonlee.pattern.configuration.bridge.monitor.model.ApiStatistics;

/**
 * @author Demon.Lee
 * @date 2020-08-22 15:55
 * @desc 桥接模式（Bridge Design Pattern）
 * <p>
 * 1. definition
 * Decouple an abstraction from its implementation so that the two can vary independently.
 * （将抽象和实现解耦，让它们可以独立变化。）
 * <p>
 * <p>
 * 2. best practice
 * JDBC vs com.mysql.jdbc.Driver/oracle.jdbc.driver.OracleDriver
 * JDBC 本身相当于 'abstraction'，而具体的 Driver（比如：oracle.jdbc.driver.OracleDriver）就相当于 'implementation'。
 * 注意：1）这里的 'abstraction' 和 'implementation' 并非 抽象类/接口 或 接口的实现类，而是指一套类库或标准。2）二者独立开发，
 * 并通过对象之间的组合关系进行调用
 * <p>
 * <p>
 * 3. practice: 根据不同的告警类型和渠道进行消息推送的系统
 * 3.1 业务描述
 * 1）告警规则：api调用错误次数、api的tps超过最大值、api的超时次数等引起的告警
 * 2）根据某个api的统计信息，如果有超过告警的阈值（比如api调用的错误次数，api的tps，api的超时次数等），则给相关人员发送告警信息
 * 3) 严重程度：严重、紧急、普通、无关紧要
 * 4）告警渠道包括：电话语音、邮件、微信、短信等
 * 5）一个api的统计信息，可以同时向多个渠道发送告警，比如：api调用超时超过10条则电话语音告警，超过5条则微信告警，大于或等于1条的话则发送邮件告警
 * <p>
 * 3.2 涉众
 * 后台管理：Timer
 * <p>
 * 3.3 功能分析
 * 1）发送渠道：每种发送渠道的具体实现
 * 2）api统计信息：暂时只考虑：api调用的错误次数，api的tps，api的超时次数
 * 3）告警规则：根据api统计信息，判断该api是否触发了告警，哪种类型的告警
 * 4）告警发送：根据告警规则，对应类型的告警选择哪种渠道进行发送
 * <p>
 * 3.4 模型设计
 * 3.4.1 Glossary
 * 1) Alert (告警，校验入口)
 * 2) MessageSender (发送渠道)
 * 3) ApiStatistics (api统计信息)
 * 4) AlertHandler (某种类型的告警处理操作)
 * 5) AlertRuleService (告警规则)
 * 6) AlertLevel (严重程度)
 * 7) NotificationService (消息通知，根据严重程度进一步细化)
 * 8) BridgeApplicationContext (应用上下文，用来组装所有类)
 * <p>
 * 3.4.2 Ubiquitous Language
 * 1) 告警（Alert）有多种处理方式（AlertHandler）
 * 2) 每一种AlertHandler即对应一个告警规则：api错误次数达到阈值、api的tps达到阈值、api的超时次数达到阈值
 * 3) 某种AlertHandler调用哪种Notification（严重、紧急、普通或无关紧要），也是不固定的，可以根据配置随意组合（第1版暂时固定，第2版再支持）
 * 4) 每一个api发送的人可能不同，也就说需要根据api动态获取对应的负责人联系信息（第1版暂时固定，第2版再支持）
 * 4) 某种Notification（即某种严重程度的消息提醒）与告警发送的渠道不固定，可以根据配置随意组合，也可以发送多个渠道
 * <p>
 * 误区：告警（Alert）通过校验api统计信息（ApiStatistics）中的信息，调用对应的AlertHandler进行处理 ---注意：不是这么干的！！！
 * 因为一个apiStatistics可能满足每个类型的告警规则，所以是遍历Alert.alertHandlers，循环处理apiStatistics，逐个判断是否满足。
 * <p>
 * 3.4.3 Object Model
 * 通过类图展示，这里就不用文字描述。
 */
@Slf4j
public class MainTest {

    @Test
    public void testApiMonitor() {
        ApiStatistics apiStatistics = new ApiStatistics("createUser", 100, 300, 100, 30);
        BridgeApplicationContext.getInstance().getAlert().check(apiStatistics);
    }
}
