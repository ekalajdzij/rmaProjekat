package ba.etf.rma23.projekat

import ba.etf.rma23.projekat.data.repositories.GameReview
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type

class ApiReviewDeserializer : JsonDeserializer<GameReview> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): GameReview {
        val jsonObject = json?.asJsonObject ?: JsonObject()
        val id = jsonObject.getAsJsonArray("gamereviews")?.get(0)?.asJsonObject?.get("id")?.asInt ?: 0
        val rating = jsonObject.getAsJsonArray("gamereviews")?.get(0)?.asJsonObject?.get("rating")?.asInt ?: 0
        val review = jsonObject.getAsJsonArray("gamereviews")?.get(0)?.asJsonObject?.get("review")?.asString ?: "No review"

        /*val impressions = mutableListOf<UserImpression>()
        val userReview = UserReview(userName,timestamp,review)
        val userRating = UserRating(userName,timestamp, rating.toDouble())

        impressions.add(userReview)
        impressions.add(userRating)*/

        return GameReview(id,rating,review,-1,false)
    }
}