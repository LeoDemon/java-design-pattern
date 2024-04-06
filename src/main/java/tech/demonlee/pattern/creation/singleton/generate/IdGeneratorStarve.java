package tech.demonlee.pattern.creation.singleton.generate;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Demon.Lee
 * @date 2020-09-05 09:58
 * @desc 饿汉式：实现简单，对资源耗用不大的情况，建议优先使用
 */
public class IdGeneratorStarve {

    private AtomicLong id = new AtomicLong();
    private static boolean instanceCreated = false;
    private static final IdGeneratorStarve instance = new IdGeneratorStarve();

    private IdGeneratorStarve() {
        if (instanceCreated) {
            throw new IllegalStateException("cannot create new IDGeneratorStarve instance for singleton");
        }
        instanceCreated = true;
    }

    public static IdGeneratorStarve getInstance() {
        return instance;
    }

    public long generateId() {
        return id.getAndIncrement();
    }
}
