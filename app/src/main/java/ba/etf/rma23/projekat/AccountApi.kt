package ba.etf.rma23.projekat

import ba.etf.rma23.projekat.repositories.AccountGamesRepository.getHash
import retrofit2.Response
import retrofit2.http.*

interface AccountApi {

    @Headers(
        "Content-Type: application/json"
    )
    @GET("account/{aid}/games")
    suspend fun getSavedGames(
        @Path("aid") aid: String = getHash()
    ): Response<List<Game>>

    @Headers(
        "Content-Type: application/json"
    )
    @POST("account/{aid}/game")
    suspend fun saveGame(
        @Body requestBody: GameOn,
        @Path("aid") aid: String = getHash()
    ): RequestBody

    @Headers(
        "Content-Type: application/json"
    )
    @DELETE("account/{aid}/game/{gid}")
    suspend fun removeGame(
        @Path("gid") gid: Int,
        @Path("aid") aid: String = getHash()
    ): Response<Boolean>



}