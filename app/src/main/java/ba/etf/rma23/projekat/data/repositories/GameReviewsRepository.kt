package ba.etf.rma23.projekat.data.repositories

import android.content.Context
import ba.etf.rma23.projekat.AppDatabase
import ba.etf.rma23.projekat.Game
import ba.etf.rma23.projekat.ReviewBody
import ba.etf.rma23.projekat.ReviewDao
import ba.etf.rma23.projekat.data.repositories.GameReviewApiConfig.revRetrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext


object GameReviewsRepository {

    var userHash: String = "3a7c549a-2c44-4b7d-9377-2ffce1b9fa54"

    fun setHash(acHash: String):Boolean {
        AccountGamesRepository.userHash = acHash
        return true
    }

    fun getHash():String {
        return this.userHash
    }

    suspend fun getReviewsForGame(id: Int): List<GameReview> = withContext(Dispatchers.IO) {
        try {
            val response = revRetrofit.getReviewsForGame(id)
            for (i in 0 until response.body()!!.size)
                response.body()!![i].igdb_id = id;
            return@withContext response.body()!!
        }
        catch(e: Exception) {
            print(e.message)
            return@withContext emptyList()
        }
    }

    suspend fun addReviewsForGame(id: Int, review: GameReview) : String? = withContext(Dispatchers.IO) {
        try {
            val reviewBody = ReviewBody(review.review!!, review.rating!!)
            val response = revRetrofit.addReviewForGame((reviewBody), id)
            print(response.review)
            return@withContext "success"
        } catch(e : Exception) {
            print(e.message)
            return@withContext null
        }
    }

    suspend fun sendReview(context: Context, review: GameReview) : Boolean {
        return withContext(Dispatchers.IO) {
            try {
                var inList = false
                val savedGames = AccountGamesRepository.getSavedGames()
                for (element in savedGames) {
                    if (element.id == review.igdb_id) {
                        inList = true
                        break
                    }
                }
                val game: Game
                if (!inList) {
                    runBlocking {
                        game = GamesRepository.getGameById(review.igdb_id)[0]
                    }
                    runBlocking {
                        AccountGamesRepository.saveGame(game)
                    }
                }
                val resposne = revRetrofit.addReviewForGame(ReviewBody(review.review!!, review.rating!!), review.igdb_id)
                return@withContext true
            } catch(e : Exception) {
                val db = AppDatabase.getInstance(context)
                db.reviewDao().addOfflineReview(review)
                return@withContext false
            }
        }
    }

    suspend fun getOfflineReviews(context: Context): List<GameReview> {
        return withContext(Dispatchers.IO) {
            try {
                val db = AppDatabase.getInstance(context)
                val result = db.reviewDao().getOfflineReviews()
                return@withContext result
            } catch(e: Exception) {
                return@withContext emptyList()
            }
        }
    }

    suspend fun updateReviewToTrue(context: Context, id: Int) {
        return withContext(Dispatchers.IO) {
            val db = AppDatabase.getInstance(context)
            val response = db.reviewDao().updateReviewToTrue(id)
        }
    }

    suspend fun sendOfflineReviews(context: Context): Int {
        return withContext(Dispatchers.IO) {
            val db = AppDatabase.getInstance(context)
            val reviews = db.reviewDao().getOfflineReviews()
            var counter = 0
            for (i in reviews.indices) {
                if (!reviews[i].online) {
                    counter++
                    updateReviewToTrue(context, reviews[i].igdb_id)
                    runBlocking {
                        addReviewsForGame(reviews[i].igdb_id, reviews[i])
                    }
                }
            }
            return@withContext counter
        }
    }

    suspend fun addOfflineReviews(context: Context, review: GameReview) :String? {
        return withContext(Dispatchers.IO) {
            try {
                val db = AppDatabase.getInstance(context)
                db.reviewDao().addOfflineReview(review)
                return@withContext "success"
            } catch(e: Exception) {
                return@withContext null
            }
        }
    }


}