package ba.etf.rma23.projekat.data.repositories

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlin.random.Random

@Entity
data class GameReview (
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "rating") @SerializedName("rating") val rating : Int?,
    @ColumnInfo(name = "review") @SerializedName("review") val review: String?,
    @ColumnInfo(name = "igdb_id") var igdb_id:Int,
    @ColumnInfo(name = "online") var online: Boolean,
    @SerializedName("student") val userName: String,
    @SerializedName("timestamp") val timestamp: String
    //@ColumnInfo(name = "savedGameId") @SerializedName("GameId") val savedGameId: Int
) {
    constructor(
        rating: Int?,
        review: String?,
        igdb_id: Int,
        online: Boolean,
        username: String,
        timestamp: String
    ) : this(0, rating, review, igdb_id, online, username, timestamp)
}