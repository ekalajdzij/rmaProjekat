package ba.etf.rma23.projekat

import com.google.gson.annotations.SerializedName

data class RequestBody (
@SerializedName("igdb_id") val Gid: Int,
@SerializedName("name")     val Gtitle: String
) {
    fun toGame(): Game {
        return Game(this.Gid, this.Gtitle, "", "", 0.0, "", "", "", "", "", "", emptyList())
    }
}
