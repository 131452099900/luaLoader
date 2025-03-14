package lua.xgwd.me.core.listener;

import lua.xgwd.me.core.LuaScriptRegistry;
import lua.xgwd.me.core.event.RegisterEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


/**
 * @author gbl.huang
 * @date 2025/03/10 17:38
 **/
public class RegisterListener implements ApplicationListener<RegisterEvent> {

    private LuaScriptRegistry luaScriptRegistry;
    @Autowired
    public RegisterListener(LuaScriptRegistry registry){
        luaScriptRegistry = registry;
    }

    @Override
    public void onApplicationEvent(RegisterEvent registerEvent) {
        luaScriptRegistry.register(registerEvent.getLuaScriptEntity());
    }
}