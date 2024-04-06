package tech.demonlee.pattern.creation.singleton.generate;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Demon.Lee
 * @date 2020-09-05 11:04
 * @desc 枚举方式
 * 下面的方式是饿汉模式的实现方式
 */
public enum IdGeneratorEnum {

    /**
     * 实例
     */
    INSTANCE;

    private AtomicLong id = new AtomicLong();

    private IdGeneratorEnum() {
    }

    public long generateId() {
        return id.getAndIncrement();
    }

    public IdGeneratorEnum getInstance() {
        return INSTANCE;
    }
}
