package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.*
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GameReviewApiConfig{
    var baseURL: String = "https://rma23ws.onrender.com"

    val gson = GsonBuilder().registerTypeAdapter(GameReview::class.java, ApiReviewDeserializer()).create()

    val revRetrofit : ReviewApi = Retrofit.Builder().
    baseUrl(baseURL).
    addConverterFactory(GsonConverterFactory.create(gson)).
    build().create(ReviewApi::class.java)
}
