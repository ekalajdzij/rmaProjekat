package ba.etf.rma23.projekat

import ba.etf.rma23.projekat.data.repositories.GameReview
import ba.etf.rma23.projekat.data.repositories.GameReviewsRepository.getHash
import retrofit2.Response
import retrofit2.http.*

interface ReviewApi {

    @Headers(
        "Content-Type: application/json"
    )
    @GET("game/{gid}/gamereviews")
    suspend fun getReviewsForGame(
        @Path("gid") gid: Int
    ): Response<List<GameReview>>


    @Headers(
        "Content-Type: application/json"
    )
    @POST("/account/{aid}/game/{gid}/gamereview")
    suspend fun addReviewForGame(
        @Body requestBody: ReviewBody,
        @Path("gid") gid: Int,
        @Path("aid") aid: String = getHash()
    ): ReviewBody
}