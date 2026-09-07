package com.ljyh.mei.utils.netease

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.ljyh.mei.constants.AndroidIdKey
import com.ljyh.mei.utils.dataStore
import com.ljyh.mei.utils.encrypt.encryptId
import com.ljyh.mei.utils.encrypt.generateRandomMac
import com.ljyh.mei.utils.get
import korlibs.encoding.Base64
import kotlin.text.buildString
import kotlin.text.isEmpty

object NeteaseUtils {
    private val HEX_CHARS = "0123456789abcdef"

    // 对应 Node: CryptoJS.lib.WordArray.random(32).toString()
    // 生成 ntes_nuid, ntes_nnid 等
    fun getRandomHex(length: Int = 16) = buildString(length) {
        repeat(16) {
            append(HEX_CHARS.random())
        }
    }
    fun getAndroidId(): String = Base64.encode(
        "null\t${generateRandomMac()}\t${getRandomHex()}\t${getRandomHex()}".toByteArray(),
        url = true
    )

    fun getResourceLink(id: String, extension: String = "jpg"): String {
        val encrypted = encryptId(id)

        val seed = id.last().digitToInt()
        val p = when {
            seed < 2.5 -> 1
            seed < 5 -> 2
            seed < 7.5 -> 3
            else -> 4
        }

        return "http://p$p.music.126.net/$encrypted/$id.$extension"
    }

    suspend fun getAndroidId(context: Context): String {
        val androidId = context.dataStore[AndroidIdKey] ?: ""
        if (androidId.isEmpty()) {
            val androidId = Base64.encode(
                "null\t${generateRandomMac()}\t${getRandomHex()}\t${getRandomHex()}".toByteArray(),
                url = true
            )
            context.dataStore.edit {
                it[AndroidIdKey] = androidId
            }
            return androidId
        } else {
            return androidId
        }
    }

    fun getWNMCID(): String {
        val characters = "abcdefghijklmnopqrstuvwxyz"
        val randomString = (1..6).map { characters.random() }.joinToString("")
        return "$randomString.${System.currentTimeMillis()}.01.0"
    }



}
