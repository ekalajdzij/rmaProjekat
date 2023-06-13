package ba.etf.rma23.projekat

import com.google.gson.annotations.SerializedName

data class ReviewBody(
    @SerializedName("review") val review : String,
    @SerializedName("rating") val rating: Int
)
