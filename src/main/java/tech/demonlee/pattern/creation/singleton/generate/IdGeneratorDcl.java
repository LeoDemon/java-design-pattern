package tech.demonlee.pattern.creation.singleton.generate;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Demon.Lee
 * @date 2020-09-05 10:39
 * @desc 双重检测
 */
public class IdGeneratorDcl {

    private AtomicLong id = new AtomicLong();

    private static volatile IdGeneratorDcl instance;

    private IdGeneratorDcl() {
    }

    public static IdGeneratorDcl getInstance() {
        IdGeneratorDcl tmpInstance = instance;
        if (Objects.isNull(tmpInstance)) {
            synchronized (IdGeneratorDcl.class) {
                tmpInstance = instance;
                if (Objects.isNull(tmpInstance)) {
                    tmpInstance = new IdGeneratorDcl();
                    instance = tmpInstance;
                }
            }
        }
        return tmpInstance;
    }

    public long generateId() {
        return id.getAndIncrement();
    }
}
