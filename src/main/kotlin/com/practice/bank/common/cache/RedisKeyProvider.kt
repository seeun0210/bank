package com.practice.bank.common.cache

object RedisKeyProvider{
    private const val BANK_MUTEX_KEY = "BANK_MUTEX_KEY"
    private const val HISTORY_CACHE_KEY = "HISTORY_CACHE_KEY"

    fun bankMutexKey(ulid:String, accountUlid:String,):String{
        return "$BANK_MUTEX_KEY:$ulid:$accountUlid"
    }

    fun historyCacheKey(ulid:String, accountUlid:String):String{
        return "$HISTORY_CACHE_KEY:$ulid:$accountUlid"
    }

}