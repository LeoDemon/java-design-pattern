package tech.demonlee.pattern.creation.singleton.generate;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Demon.Lee
 * @date 2020-09-05 10:56
 * @desc 静态内部类
 */
public class IdGeneratorStaticInner {

    private AtomicLong id = new AtomicLong();

    private IdGeneratorStaticInner() {
    }

    public static IdGeneratorStaticInner getInstance() {
        return IdGeneratorHolder.instance;
    }

    public long generateId() {
        return id.getAndIncrement();
    }

    private static class IdGeneratorHolder {
        private static final IdGeneratorStaticInner instance = new IdGeneratorStaticInner();
    }
}
