package lua.xgwd.me.core.support;

import lua.xgwd.me.core.LuaScriptLoader;
import lua.xgwd.me.core.annotation.LuaScript;
import lua.xgwd.me.core.bean.LuaScriptEntity;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.*;

/**
 * @author gbl.huang
 * @date 2025/03/10 17:44
 **/
public class DefaultLuaScriptLoader implements LuaScriptLoader {

    @Override
    public LuaScriptEntity loadScript(String scriptId) {
        // TODO 修改路径
        String luaFilePath = "lua/" + scriptId;
        Resource resource = new ClassPathResource(luaFilePath);
        LuaScriptEntity luaScriptEntity = new LuaScriptEntity(scriptId);
        try (InputStream inputStream = resource.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            StringBuilder content = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            luaScriptEntity.setValue(content.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return luaScriptEntity;
    }


}