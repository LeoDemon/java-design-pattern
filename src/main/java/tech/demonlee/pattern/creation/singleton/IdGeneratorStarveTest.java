package tech.demonlee.pattern.creation.singleton;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tech.demonlee.pattern.creation.singleton.generate.IdGeneratorStarve;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;

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

    @Test
    void should_create_serializable_id_generator() {
        String dataName = "id-generator.data";
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dataName));
             ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dataName))) {
            oos.writeObject(IdGeneratorStarve.getInstance());
            IdGeneratorStarve idGeneratorStarve = (IdGeneratorStarve) ois.readObject();
            // Assertions.assertNotEquals(idGeneratorStarve, IdGeneratorStarve.getInstance());
            // Assertions.assertNotEquals(idGeneratorStarve.getId(), IdGeneratorStarve.getInstance().getId());
            log.info("new: {}, old: {}", idGeneratorStarve.hashCode(), IdGeneratorStarve.getInstance().hashCode());
            log.info("new.id: {}, old.id: {}", idGeneratorStarve.getId(), IdGeneratorStarve.getInstance().getId());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                Files.deleteIfExists(Path.of(dataName));
            } catch (IOException e) {
                log.error("delete file failed: ", e);
            }
        }
    }
}
