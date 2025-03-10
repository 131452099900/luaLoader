package lua.xgwd.me.core.support;

import lombok.Data;
import lua.xgwd.me.core.LuaScriptCacheManager;
import lua.xgwd.me.core.LuaScriptStorage;
import lua.xgwd.me.core.bean.LuaScriptEntity;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author gbl.huang
 * @date 2025/03/10 16:31
 * 默认使用Lua进行实现
 **/
@Data
public class DefaultLuaScriptCacheManager implements LuaScriptCacheManager {

    private LuaScriptStorage storage;

    private final int capacity;

    LRUCache lruCache;


    public static void main(String[] args) {
        DefaultLuaScriptCacheManager cacheManager =
                new DefaultLuaScriptCacheManager(new DefaultLuaScriptStorage(), 2);
        cacheManager.addScript(new LuaScriptEntity("1", "value1"));
        cacheManager.addScript(new LuaScriptEntity("2", "value2"));
        cacheManager.addScript(new LuaScriptEntity("3", "value3"));
        System.out.println(cacheManager.getById("1"));
    }

    public DefaultLuaScriptCacheManager(LuaScriptStorage storage, int capacity) {
        this.capacity = capacity;
        this.storage = storage;
        lruCache = new LRUCache(capacity, storage);
    }


    @Override
    public void addScript(LuaScriptEntity luaScriptEntity) {
        storage.insert(luaScriptEntity);
        lruCache.put(luaScriptEntity.getId());
    }

    @Override
    public void removeScript(String id) {
        storage.removeById(id);
        lruCache.remove(id);
    }

    @Override
    public LuaScriptEntity getById(String id) {
        String key = lruCache.get(id);
        if (key == null) {
            return null;
        }
        return storage.findById(id);
    }


    @Override
    public List<LuaScriptEntity> getAll() {
        return new ArrayList<>(storage.getAll());
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
        storage.removeById(key);
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

