package lua.xgwd.me.core.bean;

import lombok.Data;

/**
 * @author gbl.huang
 * @date 2025/03/10 11:33
 **/
@Data
public class LuaScriptLoadEntity {
    private String id;
    private String value;
    private boolean isCache;

}