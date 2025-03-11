
local key1 = KEYS[1]
local key2 = KEYS[2]
local value1 = ARGV[1]
local value2 = ARGV[2]

-- 在 Redis 中设置键值对
redis.call('SET', key1, value1)
redis.call('SET', key2, value2)

-- 无返回
return 0