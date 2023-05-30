package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.Api
import ba.etf.rma23.projekat.Game
import ba.etf.rma23.projekat.GameDeserializer
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object IGDBApiConfig {
    var baseURL: String = "https://api.igdb.com/v4/"

    val gson = GsonBuilder().registerTypeAdapter(Game::class.java, GameDeserializer()).create()

    val retrofit : Api = Retrofit.Builder().
    baseUrl(baseURL).
    addConverterFactory(GsonConverterFactory.create(gson)).
    build().create(Api::class.java)

    fun postaviBaseUrl(url : String):Unit { baseURL = url}
}