package ba.etf.rma23.projekat

import ba.etf.unsa.rma23.projekat.BuildConfig
import retrofit2.Response
import retrofit2.http.*

interface Api {

    @Headers(
        "Client-ID: ${BuildConfig.clientID}",
        "Authorization: ${BuildConfig.authorizationKey}",
        "Content-Type: application/json"
    )
    @GET("games")
    suspend fun getGamesByName(
        @Query("search") name: String,
        @Query("fields") fields: String = "id, name, platforms.name, first_release_date, rating, age_ratings.rating, age_ratings.category, cover.url, involved_companies, involved_companies.developer, involved_companies.publisher, involved_companies.company.name, genres, genres.name, summary"
    ): Response<List<Game>>

    @Headers(
        "Client-ID: ${BuildConfig.clientID}",
        "Authorization: ${BuildConfig.authorizationKey}",
        "Content-Type: application/json"
    )
    @GET("games")
    suspend fun getGames(
        @Query("fields") fields: String = "id, name, platforms.name, first_release_date, rating, age_ratings.rating, age_ratings.category, cover.url, involved_companies, involved_companies.developer, involved_companies.publisher, involved_companies.company.name, genres, genres.name, summary"
    ): Response<List<Game>>

    @Headers(
        "Client-ID: ${BuildConfig.clientID}",
        "Authorization: ${BuildConfig.authorizationKey}",
        "Content-Type: application/json"
    )
    @POST("games")
    suspend fun getGameById(
        @Body body: okhttp3.RequestBody
    ): Response<List<Game>>

    @Headers(
        "Client-ID: ${BuildConfig.clientID}",
        "Authorization: ${BuildConfig.authorizationKey}",
        "Content-Type: application/json"
    )
    @POST("games")
    suspend fun getGamesSafe(
        @Body body: okhttp3.RequestBody
    ): Response<List<Game>>
}