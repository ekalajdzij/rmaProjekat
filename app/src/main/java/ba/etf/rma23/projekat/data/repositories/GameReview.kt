package ba.etf.rma23.projekat.data.repositories

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class GameReview (
    @PrimaryKey @SerializedName("id") var id: Int,
    @ColumnInfo(name = "rating") @SerializedName("rating") val rating : Int?,
    @ColumnInfo(name = "review") @SerializedName("review") val review: String?,
    @ColumnInfo(name = "igdb_id") var igdb_id: Int,
    @ColumnInfo(name = "online") var online: Boolean,
    //@ColumnInfo(name = "timestamp") @SerializedName("timestamp") val timestamp: Long,
    //@ColumnInfo(name = "userName") @SerializedName("student") val userName: String,
    //@ColumnInfo(name = "savedGameId") @SerializedName("GameId") val savedGameId: Int
)