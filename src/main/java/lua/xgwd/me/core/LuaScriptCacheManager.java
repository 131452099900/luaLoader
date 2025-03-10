package lua.xgwd.me.core;

import lua.xgwd.me.core.bean.LuaScriptEntity;

import java.util.List;

/**
 * @author gbl.huang
 * @date 2025/03/10 11:25
 * 缓存管理器接口
 */
public interface LuaScriptCacheManager {
    /**
     * 添加脚本到管理器中
     * @param luaScriptEntity
     */
    void addScript(LuaScriptEntity luaScriptEntity);

    /**
     * 根据ID删除脚本
     * @param id 脚本ID
     */
    void removeScript(String id);

    LuaScriptEntity getById(String id);

    List<LuaScriptEntity> getAll();
}
