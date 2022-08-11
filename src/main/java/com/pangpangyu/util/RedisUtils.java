package com.pangpangyu.util;

import com.pangpangyu.po.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class RedisUtils {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * keys pattern  查看所有符合规则的key
     * @param pattern
     * @return
     */
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    //String  字符串
    /**
     * set key value
     * @param key
     * @param value
     */
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * get key
     * @param key
     * @return
     */
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * exists key
     * @param key
     * @return
     */
    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * incr key
     * value + 1
     * @param key
     */
    public void incr(String key) {
        redisTemplate.opsForValue().increment(key);
    }

    /**
     * incr key l
     * value + l
     * @param key
     * @param l 步长
     */
    public void incr(String key, Long l) {
        redisTemplate.opsForValue().increment(key, l);
    }

    /**
     * decr key
     * value - 1
     * @param key
     */
    public void decr(String key) {
        redisTemplate.opsForValue().decrement(key);
    }

    /**
     * decr key l
     * value - l
     * @param key
     * @param l
     */
    public void decr(String key, Long l) {
        redisTemplate.opsForValue().decrement(key, l);
    }

    /**
     * set if not exist
     * @param key
     * @param value
     */
    public void setNX(String key, Object value) {
        redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    /**
     * del key
     * @param key
     */
    public void del(String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    //List  列表

    /**
     * lpush list value
     * 将值插入列表的头部即左边
     * @param list
     * @param value
     */
    public void lPush(String list, Object value) {
        redisTemplate.opsForList().leftPush(list, value);
    }

    /**
     * rpush list value
     * 将值插入列表的尾部即右边
     * @param list
     * @param value
     */
    public void rPush(String list, Object value) {
        redisTemplate.opsForList().rightPush(list, value);
    }

    /**
     * lrange list l1 l2
     * 当l1为0 l2为-1 即为获取列表所有元素
     * 通过区间获取具体的值
     * @param list
     * @param l1
     * @param l2
     * @return
     */
    public List<Object> lRange(String list, Long l1, Long l2) {
        return redisTemplate.opsForList().range(list, l1, l2);
    }

    /**
     * lpop list
     * 移除list的第一个元素（最左边）
     * @param list
     */
    public void lPop(String list) {
        if (redisTemplate.opsForList().size(list) > 0) {
            redisTemplate.opsForList().leftPop(list);
        }
    }

    /**
     * rpop list
     * 移除list的最后一个元素（最右边）
     * @param list
     */
    public void rPop(String list) {
        if (redisTemplate.opsForList().size(list) > 0) {
            redisTemplate.opsForList().rightPop(list);
        }
    }

    //Set  集合 不能重复

    /**
     * sadd set value
     * @param set
     * @param value
     */
    public void sAdd(String set, Object value) {
        redisTemplate.opsForSet().add(set, value);
    }

    /**
     * smembers set
     * @param set
     * @return
     */
    public Set<Object> sMembers(String set) {
        return redisTemplate.opsForSet().members(set);
    }

    /**
     * sdiff set1 set2
     * 差集 以set1为基准
     * @param set1
     * @param set2
     * @return
     */
    public Set<Object> sDiff(String set1, String set2) {
        return redisTemplate.opsForSet().difference(set1, set2);
    }

    /**
     *
     * sinter set1 set2
     * 交集
     * @param set1
     * @param set2
     * @return
     */
    public Set<Object> sInter(String set1, String set2) {
        return redisTemplate.opsForSet().intersect(set1, set2);
    }

    /**
     * sunion set1 set2
     * 并集
     * @param set1
     * @param set2
     * @return
     */
    public Set<Object> sUnion(String set1, String set2) {
        return redisTemplate.opsForSet().union(set1, set2);
    }

    //Hash  哈希 key-map <key, <key, value>>

    /**
     * hset key hashkey value
     * @param key
     * @param hashKey
     * @param value
     */
    public void hSet(String key, String hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    /**
     * hget key hashkey
     * 获取一个字段的值
     * @param key
     * @param hashKey
     */
    public Object hGet(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * hgeall key
     * 获取所有的数据  返回Map<hKey, hValue>
     * @param key
     * @return
     */
    public Map<Object, Object> hGetAll(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 删除指定的key 对应的value也被删除
     * @param key
     */
    public void hDel(String key) {
        redisTemplate.opsForHash().delete(key);
    }

    public void hDel(String key, String heky) {
        redisTemplate.opsForHash().delete(key, heky);
    }

    //Zset  有序集合

    /**
     * zadd key score value
     * 添加一个值 score
     * @param key
     * @param value
     * @param score
     */
    public void zAdd(String key, Object value, Double score) {
        redisTemplate.opsForZSet().add(key, value, score);
    }

    /**
     * zincrby key delta value
     * 给value增加delta
     * @param key
     * @param value
     */
    public void zIncrby(String key, Object value, double delta) {
        redisTemplate.opsForZSet().incrementScore(key, value, delta);
    }

    /**
     * zrevrangebyscore key min max withscores
     * 返回在[min, max]这个区间中的所有值 从小到大
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Set<ZSetOperations.TypedTuple<Object>> zRevRangeWithScores(String key, double min, double max) {
        return redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key, min, max);
    }

    /**
     * zrevrangebyscore key min max withscores limit offset count
     * 相当于先按照score从大到小排序， 然后limit offset count  是相当于分页 每页count个 返回第offset-1页
     * @param key
     * @param min
     * @param max
     * @param offset
     * @param count
     * @return
     */
    public Set<ZSetOperations.TypedTuple<Object>> zRevRangeWithScores(String key, double min, double max, long offset, long count) {
        return redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key, min, max, offset, count);
    }

    /**
     * zrem key value
     * 移除指定key value
     * @param key
     * @param value
     */
    public void remove(String key, Object value) {
        redisTemplate.opsForZSet().remove(key, value);
    }


    //分页缓存  zset + hash

    /***
     * set 分页缓存
     *   如果是blog的分页缓存 则key 就是 blog
     *   zset key score value  （(double)blogId作为 score，value是blogId）
     *   hset key hkey value  （hkey是blogId，value是blog的实体对象）
     * @param key
     * @param hkey
     * @param score
     * @param value
     */
    public void setPage(String key, String hkey, double score, Object value) {
        this.zAdd(key + ":page", hkey, score);
        this.hSet(key, hkey, value);
    }

    /**
     * zrev
     * get 分页信息
     * @param key
     * @param pageNum
     * @param pageSize
     */
    public List<Object> getPage(String key, int pageNum, int pageSize) {
        Set<ZSetOperations.TypedTuple<Object>> typedTuples = this.zRevRangeWithScores(key + ":page", Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, (pageNum - 1) * pageSize, pageSize);
        List<Object> list = new ArrayList<>();
        for (ZSetOperations.TypedTuple<Object> typedTuple : typedTuples) {
            String hkey = String.valueOf(typedTuple.getValue());
            Object o = hGet(key, hkey);
            list.add(o);
        }
        return list;
    }

    public Long getPageSize(String key) {
        if (key.equals("blog")) {   //因为blog里面存的是全部的博客  而前端只展示已经发布的博客 所以需要将未发布的排除
            List<Object> values = redisTemplate.opsForHash().values(key);
            Long size = 0L;
            for (Object obj : values) {
                Blog blog = (Blog) obj;
                if (blog.isPublished()) {
                    size++;
                }
            }
            return size;
        }
        return redisTemplate.opsForZSet().zCard(key + ":page");
    }

    /**
     * del 分页中某条数据
     * @param key
     * @param hkey
     */
    public void delPage(String key, String hkey) {
        this.remove(key + ":page", hkey);
        this.hDel(key, hkey);
    }

}
