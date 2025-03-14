package lua.xgwd.me.core.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lua.xgwd.me.core.bean.LuaScriptEntity;
import org.springframework.context.ApplicationEvent;

/**
 * @author gbl.huang
 * @date 2025/03/10 17:40
 **/
@Data
public class RegisterEvent extends ApplicationEvent {
    private String id;
    private LuaScriptEntity luaScriptEntity;

    public RegisterEvent(Object source) {
        super(source);
        id = (String) source;
    }

    public RegisterEvent(String id, LuaScriptEntity luaScriptEntity) {
        super(id);
        this.id = id;
        this.luaScriptEntity = luaScriptEntity;
    }
}