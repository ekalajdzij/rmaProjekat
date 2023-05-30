package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.Game
import ba.etf.rma23.projekat.data.repositories.IGDBApiConfig.retrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.http.*

object GamesRepository {

    suspend fun getGamesByName(name: String): List<Game> = withContext(Dispatchers.IO) {
        try {
            val response = retrofit.getGamesByName(name)
            return@withContext response.body()!!
        } catch (e: Exception) {
            print(e.message)
            return@withContext emptyList()
        }

    }
    suspend fun getGameById(id: Int): List<Game> = withContext(Dispatchers.IO) {
        try {
            val stringId = id.toString()
            val string =
                "fields id, name, platforms.name, first_release_date, rating, age_ratings.rating, age_ratings.category, cover.url, involved_companies," +
                        "involved_companies.developer, involved_companies.publisher, involved_companies.company.name, genres, genres.name, summary; where id = $stringId;"
            val response = retrofit.getGameById(string.toRequestBody())
            print(response.body())
            return@withContext response.body()!!
        } catch (e: Exception) {
            print(e.message)
            return@withContext emptyList()
        }

    }

    suspend fun getGamesSafe(name: String): List<Game> = withContext(Dispatchers.IO) {
        try {
            val age = AccountGamesRepository.age
            if (age in 4..99) {
                val ageString = age.toString()
                val query =
                    "fields id, name, platforms.name, first_release_date, rating, age_ratings.rating, age_ratings.category, cover.url, involved_companies, " +
                            "involved_companies.developer, involved_companies.publisher, involved_companies.company.name, " +
                            "genres, genres.name, summary; where name = \"$name\"; where age_ratings.rating <= $ageString;"
                val response = retrofit.getGamesSafe(query.toRequestBody())
                print(response.body())
                return@withContext response.body()!!
            }
            return@withContext emptyList()
        } catch (e: Exception) {
            print(e.message)
            return@withContext emptyList()
        }
    }

    fun sortGames():List<Game> {
        return emptyList()
    }


}