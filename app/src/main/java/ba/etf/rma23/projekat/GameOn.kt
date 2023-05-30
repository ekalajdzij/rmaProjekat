package ba.etf.rma23.projekat

import com.google.gson.annotations.SerializedName

data class GameOn(
    @SerializedName("game") val game: RequestBody
)
