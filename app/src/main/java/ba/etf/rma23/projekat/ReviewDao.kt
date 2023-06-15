package ba.etf.rma23.projekat

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ba.etf.rma23.projekat.data.repositories.GameReview

@Dao
interface ReviewDao {

    @Query("SELECT * FROM gamereview")
    suspend fun getOfflineReviews(): Array<GameReview>


    @Query("UPDATE gamereview SET online = 1 WHERE igdb_id = :id")
    suspend fun updateReviewToTrue(id: Int)


    @Insert
    suspend fun addOfflineReview(vararg review: GameReview)

}