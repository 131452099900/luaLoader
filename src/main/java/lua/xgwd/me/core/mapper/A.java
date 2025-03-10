package lua.xgwd.me.core.mapper;

import lua.xgwd.me.core.annotation.LuaMapper;
import lua.xgwd.me.core.annotation.LuaScript;

@LuaMapper
public interface A {

    @LuaScript("test.lua")
    Long test();
}
