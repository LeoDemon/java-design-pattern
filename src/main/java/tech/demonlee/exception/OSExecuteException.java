package tech.demonlee.exception;

/**
 * @author Demon.Lee
 * @date 2020-09-11 00:19
 */
public class OSExecuteException extends RuntimeException {
    public OSExecuteException(String why) {
        super(why);
    }
}