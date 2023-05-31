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








/*@GET("games")
    suspend fun getGameById(
        @Query("id") id: Long,
        @Query("fields") fields: String = "id, name, platforms.name, first_release_date, age_ratings.rating, age_ratings.category, cover.url, involved_companies, involved_companies.developer, involved_companies.publisher, involved_companies.company.name, genres.name, summary"
    ): Response<List<Game>>*/



    /*@Headers(
        "Client-ID: ${BuildConfig.clientID}",
        "Authorization: ˘${BuildConfig.authorizationKey}",
        "Content-Type: application/json"
    )
    @GET("age_ratings/{id}")
    suspend fun getAgeRatingsById(
        @Query("search") id: Long,
        @Query("fields") fields: String = "id, category, rating"
    ): Response<List<AgeRating>>

    @Headers(
        "Client-ID: ${BuildConfig.clientID}",
        "Authorization: ˘${BuildConfig.authorizationKey}",
        "Content-Type: application/json"
    )
    @GET("involved_companies/{id}")
    suspend fun getInvolvedCompaniesById(
        @Query("search") id: Long,
        @Query("fields") fields: String = "id, publisher, developer"
    ): Response<List<InvolvedCompanies>>

    @Headers(
        "Client-ID: ${BuildConfig.clientID}",
        "Authorization: ˘${BuildConfig.authorizationKey}",
        "Content-Type: application/json"
    )
    @GET("age_ratings/{id}")
    suspend fun getCompaniesById(
        @Query("search") id: Long,
        @Query("fields") fields: String = "name"
    ): Response<List<Companies>>
*/

}