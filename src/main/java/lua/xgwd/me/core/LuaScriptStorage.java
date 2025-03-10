package lua.xgwd.me.core;

import lua.xgwd.me.core.bean.LuaScriptEntity;

import java.util.Collection;

/**
 * @author gbl.huang
 * @date 2025/03/10 11:25
 * Lua脚本仓储接口
 */
public interface LuaScriptStorage {
    /**
     * 插入lua脚本
     * @param luaScriptEntity lua脚本
     */
    void insert(LuaScriptEntity luaScriptEntity);

    /**
     * 修改脚本
     * @param id id
     * @param luaScriptEntity lua脚本
     */
    void updateById(String id, LuaScriptEntity luaScriptEntity);

    /**
     * 删除Lua脚本
     * @param id Id
     */
    void removeById(String id);


    /**
     * 根据Id查询Lua脚本
     * @param id Id
     * @return lua脚本
     */
    LuaScriptEntity findById(String id);

    /**
     * 获取全部lua脚本
     * @return 返回
     */
    Collection<LuaScriptEntity> getAll();
}
