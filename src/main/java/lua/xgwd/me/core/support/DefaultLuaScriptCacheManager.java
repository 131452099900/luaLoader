package lua.xgwd.me.core.support;

import lombok.Data;
import lua.xgwd.me.core.LuaScriptCacheManager;
import lua.xgwd.me.core.LuaScriptStorage;
import lua.xgwd.me.core.bean.LuaScriptEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author gbl.huang
 * @date 2025/03/10 16:31
 * 默认使用Lua进行实现
 **/
@Data
@Component
public class DefaultLuaScriptCacheManager implements LuaScriptCacheManager {

//    @Autowired
    private LuaScriptStorage luaScriptStorage;

    @Autowired
    public void setLuaScriptStorage(LuaScriptStorage luaScriptStorage) {
       this.luaScriptStorage = luaScriptStorage;
        lruCache.storage = luaScriptStorage;
    }
    private final int capacity;

    LRUCache lruCache;

    public DefaultLuaScriptCacheManager() {
        capacity = 256;
        lruCache = new LRUCache(capacity, luaScriptStorage);
    }

    public DefaultLuaScriptCacheManager(LuaScriptStorage storage, int capacity) {
        this.capacity = capacity;
        this.luaScriptStorage = storage;
        lruCache = new LRUCache(capacity, storage);
    }


    @Override
    public void addScript(LuaScriptEntity luaScriptEntity) {
        luaScriptStorage.insert(luaScriptEntity);
        lruCache.put(luaScriptEntity.getId());
    }

    @Override
    public void removeScript(String id) {
        luaScriptStorage.removeById(id);
        lruCache.remove(id);
    }

    @Override
    public LuaScriptEntity getById(String id) {
        String key = lruCache.get(id);
        if (key == null) {
            return null;
        }
        return luaScriptStorage.findById(id);
    }


    @Override
    public List<LuaScriptEntity> getAll() {
        return new ArrayList<>(luaScriptStorage.getAll());
    }
}

class LRUCache {
    private final Map<String, Node> map;
    private final int capacity;
    private final Node head, tail;
    private final ReentrantLock lock;
    LuaScriptStorage storage;
    public LRUCache(int capacity, LuaScriptStorage storage) {
        map = new HashMap<String, Node>();
        this.capacity = capacity;
        head = new Node("0");
        tail = new Node("0");
        head.prev = tail;
        tail.next = head;
        lock = new ReentrantLock();
        this.storage = storage;
    }
    public String get(String key) {
        lock.lock();
        try {
            if (map.containsKey(key)) {
                Node node = map.get(key);
                removeNode(key);
                addNode(key);
                return node.key;
            } else {
                return key;
            }
        } finally {
            lock.unlock();
        }
    }


    public void remove(String key) {
        lock.lock();
        try {
            if (map.containsKey(key)) {
                removeNode(key);
            }
            storage.removeById(key);
        } finally {
            lock.unlock();
        }
    }

    public void put(String key) {
        lock.lock();
        try {
            if (map.containsKey(key)) {
                removeNode(key);
            }
            addNode(key);
        } finally {
            lock.unlock();
        }
    }

    // Remove the Node from DLL
    private void removeNode(String key) {
        Node node = map.get(key);
        node.prev.next = node.next;
        node.next.prev = node.prev;

        node.prev = node.next = null;

        map.remove(node.key);
    }

    // Add the Node at the head of DLL
    private void addNode(String key) {
        Node node = new Node(key);

        node.prev = head.prev;
        head.prev = node;
        node.prev.next = node;
        node.next = head;

        map.put(key, node);
        if (map.size() > capacity) {
            removeNode(tail.next.key);
        }
    }

    private static class Node {
        final String key;
        Node next;
        Node prev;

        public Node(String key) {
            this.key = key;
        }
    }
}

