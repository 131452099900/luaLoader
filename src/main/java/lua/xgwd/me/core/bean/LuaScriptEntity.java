package lua.xgwd.me.core.bean;

import lombok.Data;

/**
 * @author gbl.huang
 * @date 2025/03/10 11:33
 **/
@Data
public class LuaScriptEntity {
    private String id;
    private String value;
    private boolean isCache = false;
    private long ttl = 0L;

    public LuaScriptEntity(String id) {
        this.id = id;
    }
    public LuaScriptEntity(String id, String value) {
        this.id = id;
        this.value = value;
    }
}