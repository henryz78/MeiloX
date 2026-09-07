package com.ljyh.mei.utils

import com.ljyh.mei.AppContext
fun <T> getRandomFromList(list: List<T>): T {
    return list.random()
}

fun getDeviceId(): String {
    val assets=AppContext.instance.assets
    // 读 url
    val inputStream = assets.open("devices.txt")
    val content = inputStream.bufferedReader().use { it.readLines() }
    return getRandomFromList(content)
}
