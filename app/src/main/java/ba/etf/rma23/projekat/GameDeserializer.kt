package ba.etf.rma23.projekat

import android.annotation.SuppressLint
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*

class GameDeserializer :JsonDeserializer<Game>{
    @SuppressLint("SimpleDateFormat")
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Game {
        val jsonObject = json?.asJsonObject ?: JsonObject()
        val id = jsonObject.get("id")?.asInt ?: 0
        val title = jsonObject.get("name")?.asString ?: "No title"
        var platform = ""
        val platformArray = jsonObject.getAsJsonArray("platforms")
        if ((platformArray != null) && (platformArray.size() > 0)) {
            for(i in 0 until platformArray.size()) {
                val string = platformArray[i].asJsonObject.get("name").asString
                if (i < platformArray.size()-1) {
                    platform += string + "/"
                    continue
                }
                if (platform == "")
                    platform = string
                else platform += string
            }
        }
        val releaseDate = jsonObject.get("first_release_date")?.asLong ?: 0
        val date = Date(releaseDate*1000)
        val dateFormat = SimpleDateFormat("dd.MM.yyyy")
        val dateS = dateFormat.format(date)
        var rating = jsonObject.get("rating")?.asDouble ?: 0.0
        var esrb = "No ESRB rating"
        val ratingArray = jsonObject.getAsJsonArray("age_ratings")
        if(ratingArray != null && ratingArray.size()>0) {
            for(i in 0 until ratingArray.size()) {
                val objectArray = ratingArray[i].asJsonObject.get("category")?.asInt ?:0
                if(objectArray == 1) {
                    val esrbJson = ratingArray[i].asJsonObject.get("rating")?.asInt ?:0
                    esrb = getGameRatingString(esrbJson)
                }
            }
        }
        val coverImage = jsonObject.getAsJsonObject("cover")?.get("url")?.asString ?: "No cover url"
        val companyArray = jsonObject.getAsJsonArray("involved_companies")
        var developer = "Unknown developer"
        var publisher = "Unknown publisher"
        if(companyArray != null && companyArray.size() > 0) {
            for (i in 0 until companyArray.size()) {
                val objectC = companyArray[i].asJsonObject
                if(objectC.get("developer").asBoolean) {
                    developer = objectC.getAsJsonObject("company")?.get("name")?.asString ?: "Unknown developer"
                }
                if(objectC.get("publisher").asBoolean) {
                    publisher = objectC.getAsJsonObject("company")?.get("name")?.asString ?: "Unknown publisher"
                }
            }
        }
        var genre = "Unknown genre"
        val genreArray = jsonObject.getAsJsonArray("genres")
        if ((genreArray != null) && (genreArray.size() > 0)) {
            for(i in 0 until genreArray.size()) {
                if( i == 0 ) genre=""
                val string = genreArray[i].asJsonObject.get("name").asString
                if (i < genreArray.size()-1) {
                    genre += string + ","
                    continue
                }
                if (genre == "")
                    genre = string
                else genre += string
            }
        }
        val description = jsonObject.get("summary")?.asString ?: "No description"

        return Game(id,title,platform,dateS,rating,coverImage,esrb,developer,publisher,genre,description,
            emptyList()
        )
    }
    fun getGameRatingString(value: Int): String {
        return EsrbRating.values().find { it.value == value }!!.name
    }
}