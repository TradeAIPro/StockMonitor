package com.artinsoft.StockMonitor.data.repository

import com.artinsoft.StockMonitor.domain.entity.Balance
import com.artinsoft.StockMonitor.domain.repository.BingxOrder
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.*
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestTemplate
import java.net.URLEncoder
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.sql.Timestamp
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec


@Repository
class BingxOrderImpl(
    @Value("\${bingx.apiKey}")
    private val apiKey: String = "",
    @Value("\${bingx.secretKey}")
    private val secretKey: String = "",
    @Value("\${bingx.url}")
    private val url: String = "",
) : BingxOrder {

    private fun generateHmac256(message: String): String {
        return try {
            val bytes = hmac("HmacSHA256", secretKey.toByteArray(), message.toByteArray())

            // base64 encode
            val codec: Base64.Encoder = Base64.getEncoder()
            val b64Str: String = codec.encodeToString(bytes)

            // url encode
            val signature = URLEncoder.encode(b64Str, "UTF-8")
            signature

        } catch (e: Exception) {
            println("generateHmac256 exception: $e")
            ""
        }
    }

    @Throws(NoSuchAlgorithmException::class, InvalidKeyException::class)
    private fun hmac(algorithm: String, key: ByteArray, message: ByteArray): ByteArray {
        val mac = Mac.getInstance(algorithm)
        mac.init(SecretKeySpec(key, algorithm))
        return mac.doFinal(message)
    }

    private fun getRequestUrl(path: String, parameters: TreeMap<String, String>): String {
        var urlStr = "$url$path?"

        var first = true
        for (e in parameters.entries) {
            if (!first) {
                urlStr += "&"
            }
            first = false
            urlStr += e.key + "=" + e.value
        }

        return urlStr
    }

    private fun post(requestUrl: String): Balance? {
        val restTemplate: RestTemplate = RestTemplateBuilder().build()
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val entity = HttpEntity<String>("parameters", headers)
        val result =  restTemplate.exchange(requestUrl, HttpMethod.POST, entity, Balance::class.java)
        return result.body
    }

    private fun postStr(requestUrl: String): String? {
        val restTemplate: RestTemplate = RestTemplateBuilder().build()
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val entity = HttpEntity<String>("parameters", headers)
        val result =  restTemplate.exchange(requestUrl, HttpMethod.POST, entity, String::class.java)
        return result.body
    }

    private fun getMessageToDigest(method: String, path: String, parameters: TreeMap<String, String>): String {
        var first = true
        var valueToDigest = "$method$path"
        for (e in parameters.entries) {
            if (!first) {
                valueToDigest += "&"
            }
            first = false
            valueToDigest += e.key + "=" + e.value
        }
        return valueToDigest
    }

    override fun getBalance(symbol: String): Balance? {
        val method = "POST"
        val path = "/api/v1/user/getBalance"
        val currency = "USDT"
        val timestamp = "" + Timestamp(System.currentTimeMillis()).time

        val parameters = TreeMap<String, String>()
        parameters["timestamp"] = timestamp
        parameters["apiKey"] = apiKey
        parameters["currency"] = currency

        val valueToDigest = getMessageToDigest(method, path, parameters)
        val messageDigest = generateHmac256(valueToDigest)
        parameters["sign"] = messageDigest
        val requestUrl = getRequestUrl(path, parameters)

        return post(requestUrl)
    }

    override fun getPositions(symbol: String) {
        val method = "POST"
        val path = "/api/v1/user/getPositions"
        val timestamp = "" + Timestamp(System.currentTimeMillis()).time
        val parameters = TreeMap<String, String>()
        parameters["timestamp"] = timestamp
        parameters["apiKey"] = apiKey
        parameters["symbol"] = symbol
        val valueToDigest = getMessageToDigest(method, path, parameters)
        val messageDigest = generateHmac256(valueToDigest)
        parameters["sign"] = messageDigest
        val requestUrl = getRequestUrl(path, parameters)
        postStr(requestUrl)
    }

    override fun closePositions() {
        val method = "POST"
        val path = "/api/v1/user/oneClickCloseAllPositions"
        val currency = "USDT"
        val timestamp = "" + Timestamp(System.currentTimeMillis()).time

        val parameters = TreeMap<String, String>()
        parameters["timestamp"] = timestamp
        parameters["apiKey"] = apiKey
        parameters["currency"] = currency

        val valueToDigest = getMessageToDigest(method, path, parameters)
        val messageDigest = generateHmac256(valueToDigest)
        parameters["sign"] = messageDigest
        val requestUrl = getRequestUrl(path, parameters)
        print( postStr(requestUrl))
    }
}