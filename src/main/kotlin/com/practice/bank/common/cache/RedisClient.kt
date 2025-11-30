package com.practice.bank.common.cache

import com.practice.bank.common.exception.CustomException
import com.practice.bank.common.exception.ErrorCode
import org.redisson.api.RedissonClient
import org.springframework.data.redis.core.RedisTemplate
import java.util.concurrent.TimeUnit

class RedisClient (
    private val template: RedisTemplate<String, String>
    private val redisson: RedissonClient
)
{
    fun get(key:String):String?{
        return template.opsForValue().get(key)
    }

    fun <T> get(key:String,kSerializer:(Any)->T):T?{
        val value = get(key)

        value?.let{return kSerializer(it)} ?: return null
    }

    fun setIfNotExist(key:String, value:String):Boolean{
        return template.opsForValue().setIfAbsent(key,value) == true
    }

    fun <T> invokeWithMutex(key:String, function:()->T?){
        val lock = redisson.getLock(key)

        try{
            lock.lock(15, TimeUnit.SECONDS)
            function.invoke()
        }catch(e: Exception){
            throw CustomException(ErrorCode.FAILED_TO_MUTEX_INVOKE, key)
        }finally {
            lock.unlock()
        }
    }
}