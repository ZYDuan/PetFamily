package redisTest;

import com.zyd.petChat.redis.RedisPool;
import org.junit.Test;

/**
 * @program: petfamily
 * @author: zyd
 * @description:
 * @create: 2019-05-02 19:36
 */
public class redis {
    @Test
    public void redisTest(){

        System.out.println(RedisPool.getJedis().hget("online", "zyd:zzr"));
    }
}
