package lua.xgwd.me.core.support;

/**
 * @author gbl.huang
 * @date 2025/03/10 16:19
 * 默认存储器 基于ConcurrentHashMap进行存储
 **/
import lua.xgwd.me.core.LuaScriptStorage;
import lua.xgwd.me.core.bean.LuaScriptEntity;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认的Lua脚本存储实现，基于ConcurrentHashMap实现线程安全的存储管理
 */
@Component
public class DefaultLuaScriptStorage implements LuaScriptStorage {


    private final ConcurrentHashMap<String, LuaScriptEntity> storage = new ConcurrentHashMap<>();

    @Override
    public void insert(LuaScriptEntity luaScriptEntity) {
        assert luaScriptEntity != null && luaScriptEntity.getId() != null;
        storage.put(luaScriptEntity.getId(), luaScriptEntity);
    }

    @Override
    public void updateById(String id, LuaScriptEntity luaScriptEntity) {
        storage.put(id, luaScriptEntity);
    }

    @Override
    public void removeById(String id) {
        storage.remove(id);
    }

    @Override
    public LuaScriptEntity findById(String id) {
        return storage.get(id);
    }

    @Override
    public Collection<LuaScriptEntity> getAll() {
        return storage.values();
    }


}
