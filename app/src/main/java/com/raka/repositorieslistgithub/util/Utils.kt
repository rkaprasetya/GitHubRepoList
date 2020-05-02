package com.raka.repositorieslistgithub.util

import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import java.security.MessageDigest

object Utils {
    fun hashString(type: String, input: String): String {
        val HEX_CHARS = "0123456789abcdef"
        val bytes = MessageDigest
            .getInstance(type)
            .digest(input.toByteArray())
        val result = StringBuilder(bytes.size * 2)

        bytes.forEach {
            val i = it.toInt()
            result.append(HEX_CHARS[i shr 4 and 0x0f])
            result.append(HEX_CHARS[i and 0x0f])
        }

        return result.toString()
    }

    suspend fun isInternetAvailable(): Boolean {
        try {
            val timeoutMs = 1500
            val sock = Socket()
            val sockaddr = InetSocketAddress("8.8.8.8", 53)
            sock.connect(sockaddr, timeoutMs)
            sock.close()
            return true
        } catch (e: IOException) {
            return false
        }
    }
}