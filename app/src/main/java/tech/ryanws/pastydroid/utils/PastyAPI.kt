package tech.ryanws.pastydroid.utils

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

object PastyAPI {
    private const val BASE_URL = "pasty.ryanws.tech"
    private val client = OkHttpClient()

    fun createPaste(content: String): Boolean {
        val url = "$BASE_URL/pastes"
        val json = JSONObject().put("content", content).toString()
        val body = json.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        val request = Request.Builder().url(url).post(body).build()
        try {
            val response = client.newCall(request).execute()
            return response.isSuccessful
        } catch (e: IOException) {
            e.printStackTrace()
            return false
        }
    }

    fun listPastes(page: Int = 1, perPage: Int = 20): List<Paste> {
        val url = "$BASE_URL/pastes?page=$page&per_page=$perPage"
        val request = Request.Builder().url(url).get().build()
        try {
            val response = client.newCall(request).execute()
            val json = response.body?.string()?.let { JSONObject(it) }
            val pastes = mutableListOf<Paste>()
            json?.getJSONArray("pastes")?.let { array ->
                for (i in 0 until array.length()) {
                    val paste = array.getJSONObject(i)
                    pastes.add(Paste(paste.getInt("id"), paste.getString("content")))
                }
            }
            return pastes
        } catch (e: IOException) {
            e.printStackTrace()
            return emptyList()
        }
    }

    fun getPaste(id: Int): Paste? {
        val url = "$BASE_URL/pastes/$id"
        val request = Request.Builder().url(url).get().build()
        try {
            val response = client.newCall(request).execute()
            val json = response.body?.string()?.let { JSONObject(it) }
            return json?.let { Paste(it.getInt("id"), it.getString("content")) }
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
    }
}