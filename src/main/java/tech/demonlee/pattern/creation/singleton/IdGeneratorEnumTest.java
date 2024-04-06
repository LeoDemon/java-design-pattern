package tech.demonlee.pattern.creation.singleton;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tech.demonlee.pattern.creation.singleton.generate.IdGeneratorEnum;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author Demon.Lee
 * @date 2024-04-06 14:59
 */
@Slf4j
public class IdGeneratorEnumTest {

    @Test
    void should_failed_create_more_id_generator() {
        Assertions.assertThrows(NoSuchMethodException.class, IdGeneratorEnum.class::getDeclaredConstructor);
    }

    @Test
    void should_create_serializable_id_generator() {
        String dataName = "id-generator-enum.data";
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dataName));
             ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dataName))) {
            oos.writeObject(IdGeneratorEnum.INSTANCE);
            IdGeneratorEnum idGeneratorEnum = (IdGeneratorEnum) ois.readObject();
            Assertions.assertEquals(idGeneratorEnum, IdGeneratorEnum.INSTANCE);
            Assertions.assertEquals(idGeneratorEnum.getId(), IdGeneratorEnum.INSTANCE.getId());
            log.info("new: {}, old: {}", idGeneratorEnum.hashCode(), IdGeneratorEnum.INSTANCE.hashCode());
            log.info("new.id: {}, old.id: {}", idGeneratorEnum.getId(), IdGeneratorEnum.INSTANCE.getId());
            log.info("id: {}, id: {}", idGeneratorEnum.generateId(), IdGeneratorEnum.INSTANCE.generateId());
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
