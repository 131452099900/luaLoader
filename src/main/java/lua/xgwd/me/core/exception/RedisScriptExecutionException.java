package lua.xgwd.me.core.exception;

/**
 * @author gbl.huang
 * @date 2025/03/10 11:49
 **/
public class RedisScriptExecutionException extends RuntimeException{
    public RedisScriptExecutionException(String s) {
        super(s);
    }

    public RedisScriptExecutionException(String s, Exception e) {
        super(s, e);
    }
}