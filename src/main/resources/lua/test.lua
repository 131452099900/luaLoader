--库存key
local key1 = KEYS[1]
--事务回查key
local key2 = KEYS[2]
--扣减数量
local num = ARGV[1]
--操作redis 获取sku库存数stock
local stock = tonumber(redis.call('GET', key1))
local result = 0
--如果库存数大于等于减去的数量 则执行redis的decrby命令
--在lua中，除了nil和false，其他的值都为true，所以可用作判空
if(stock and tonumber(stock) >= tonumber(num)) then
  redis.call('DECRBY', key1, num)
  result = 1
end
--用于事务回查
redis.call('SET', key2, result)
return result
