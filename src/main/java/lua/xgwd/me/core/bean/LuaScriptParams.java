package lua.xgwd.me.core.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

/**
 * @author gbl.huang
 * @date 2025/03/10 12:00
 **/
@Data
@AllArgsConstructor
public class LuaScriptParams {
    private String[] keys;
    private Objects[] args;
}