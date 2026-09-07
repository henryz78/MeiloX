package com.ljyh.mei.utils.cache

import com.ljyh.mei.utils.DateUtils

object CacheFile {
    fun isNewDay(lastRequestTime: Long): Boolean {
        return DateUtils.isWithinSameDayWindow(lastRequestTime)
    }
}
