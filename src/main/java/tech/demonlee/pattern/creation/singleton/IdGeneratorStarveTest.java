package tech.demonlee.pattern.creation.singleton;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tech.demonlee.pattern.creation.singleton.generate.IdGeneratorStarve;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Demon.Lee
 * @date 2024-04-06 11:11
 */
@Slf4j
public class IdGeneratorStarveTest {

    @Test
    void should_create_more_id_generator() throws NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException {
        Constructor constructor = IdGeneratorStarve.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        Assertions.assertThrows(InvocationTargetException.class, constructor::newInstance);
        // IdGeneratorStarve idGeneratorStarve = (IdGeneratorStarve) constructor.newInstance();
        // Assertions.assertNotEquals(IdGeneratorStarve.getInstance(), idGeneratorStarve);
    }
}
