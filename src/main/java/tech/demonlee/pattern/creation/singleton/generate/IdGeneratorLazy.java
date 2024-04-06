package tech.demonlee.pattern.creation.singleton.generate;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Demon.Lee
 * @date 2020-09-05 10:20
 * @desc 懒汉式：每次调用都加锁，效率比较低
 */
public class IdGeneratorLazy {

    private AtomicLong id = new AtomicLong();

    private static IdGeneratorLazy instance;

    private IdGeneratorLazy() {
    }

    public static synchronized IdGeneratorLazy getInstance() {
        if (null == instance) {
            instance = new IdGeneratorLazy();
        }
        return instance;
    }

    public long generateId() {
        return id.getAndIncrement();
    }
}
