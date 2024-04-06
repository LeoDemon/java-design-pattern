package tech.demonlee.util;

import com.relops.snowflake.Snowflake;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Demon.Lee
 * @date 2020-08-16 18:10
 */
public class IdUtil {

    private static final ConcurrentHashMap<Integer, Snowflake> NODE_SNOWFLAKE_MAP = new ConcurrentHashMap<>(8);

    public static long generate(Integer nodeId) {
        Snowflake snowflake = getSnowflake(nodeId);
        return snowflake.next();
    }

    private static Snowflake getSnowflake(Integer nodeId) {
        Snowflake snowflake;
        if (NODE_SNOWFLAKE_MAP.containsKey(nodeId)) {
            snowflake = NODE_SNOWFLAKE_MAP.get(nodeId);
        } else {
            synchronized (IdUtil.class) {
                snowflake = new Snowflake(nodeId);
                NODE_SNOWFLAKE_MAP.put(nodeId, snowflake);
            }
        }
        return snowflake;
    }
}
