package tech.demonlee.pattern.creation.singleton;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import tech.demonlee.pattern.creation.singleton.generate.*;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

/**
 * @author Demon.Lee
 * @date 2020-09-05 09:04
 * @desc <p>
 * 1.What (Definition)
 * 在给定的系统范围（线程、进程、集群）内，一个类只能有一个对象（实例）。
 * <p>
 * 2.Why (Usage Scenario)
 * 1) 解决资源访问冲突问题：多个client访问同一个资源，存在并发问题。
 * 比如日志记录Logger，多个client端向同一个文件中写日志，
 * 如果每个client都创建一个logger实例，那么就会存在并发覆盖的问题（A-Client写的日志被B-Client给覆盖了）。
 * 2) 表示全局唯一类：比如配置文件的信息
 * <p>
 * 3.How
 * 1) 饿汉式
 * 2) 懒汉式
 * 3) 双重检测式
 * 4) 静态内部类式
 * 5) 枚举式
 * <p>
 * 4.Attention (Advantage/Disadvantage)
 * 1) 反序列化
 * 2) 双重检测中的指令重排
 * <p>
 * 5.example
 * 全局唯一id生成器
 */
@Slf4j
public class MainTest {

    /**
     * 饿汉式单例
     */
    @Test
    public void testStarve() {
        IntStream.rangeClosed(1, 3).forEach(__ -> {
            new Thread(() -> {
                IntStream.rangeClosed(1, 5).forEach(___ -> {
                    log.info("starve, id: {}", IdGeneratorStarve.getInstance().generateId());
                });
            }).start();
        });
    }

    /**
     * 懒汉式
     */
    @Test
    public void testLazy() {
        Executor executor = new ThreadPoolExecutor(3, 5, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(8));
        IntStream.rangeClosed(1, 3).forEach(__ -> {
            executor.execute(() -> {
                IntStream.rangeClosed(1, 5).forEach(___ -> {
                    log.info("lazy, id: {}", IdGeneratorLazy.getInstance().generateId());
                });
            });
        });
    }

    /**
     * 双重检测
     */
    @Test
    public void testDcl() {
        Executor executor = new ThreadPoolExecutor(3, 5, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(8));
        IntStream.rangeClosed(1, 3).forEach(__ -> {
            executor.execute(() -> {
                IntStream.rangeClosed(1, 5).forEach(___ -> {
                    log.info("dcl, id: {}", IdGeneratorDcl.getInstance().generateId());
                });
            });
        });
    }

    /**
     * 静态内部类 【推荐方式】
     * PS：通过spring的ThreadPoolTaskExecutor进行线程池创建，并指定对应的线程名字前缀
     */
    @Test
    public void testStaticInner() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(5);
        executor.setKeepAliveSeconds(60);
        executor.setQueueCapacity(8);
        executor.setThreadNamePrefix("Singleton-StaticInner-Pool-");
        executor.initialize();

        // Executor executor = new ThreadPoolExecutor(3, 5, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(8));
        IntStream.rangeClosed(1, 3).forEach(__ -> {
            executor.execute(() -> {
                IntStream.rangeClosed(1, 5).forEach(k -> {
                    // if (k % 2 == 0) {
                    //     throw new RuntimeException("create a runtime exception...");
                    // }
                    log.info("static inner, id: {}", IdGeneratorStaticInner.getInstance().generateId());
                });
            });
        });
    }

    /**
     * 枚举类
     * PS: 通过ThreadFactory指定线程池的名字等
     */
    @Test
    public void testEnum() {
        Executor executor = new ThreadPoolExecutor(3, 5, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(8),
                new ThreadFactory() {

                    private AtomicLong count = new AtomicLong();

                    @Override
                    public Thread newThread(Runnable r) {
                        Thread thread = new Thread(r);
                        thread.setName("Singleton-Enum-Pool-" + count.getAndIncrement());
                        return thread;
                    }
                });

        IntStream.rangeClosed(1, 3).forEach(__ -> {
            executor.execute(() -> {
                IntStream.rangeClosed(1, 5).forEach(k -> {
                    // 线程处理任务时异常，会导致对应的线程被终止，故需要捕获所有异常进行处理
                    try {
                        if (k % 2 == 0) {
                            throw new RuntimeException("create a runtime exception...");
                        }
                        log.info("enum, id: {}", IdGeneratorEnum.INSTANCE.generateId());
                    } catch (RuntimeException exception) {
                        log.error("runtime exception: {}", exception.getMessage());
                    } catch (Throwable throwable) {
                        log.error("throwable exception: {}", throwable.getMessage());
                    }
                });
            });
        });
    }
}
