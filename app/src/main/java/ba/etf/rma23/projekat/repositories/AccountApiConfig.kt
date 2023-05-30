package ba.etf.rma23.projekat.repositories

import ba.etf.rma23.projekat.*
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AccountApiConfig {
    var baseURL: String = "https://rma23ws.onrender.com"

    val gson = GsonBuilder().registerTypeAdapter(Game::class.java, AccountGameDeserializer()).create()

    val acRetrofit : AccountApi = Retrofit.Builder().
    baseUrl(baseURL).
    addConverterFactory(GsonConverterFactory.create(gson)).
    build().create(AccountApi::class.java)
}