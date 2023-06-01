package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.EsrbRating
import ba.etf.rma23.projekat.Game
import ba.etf.rma23.projekat.GameOn
import ba.etf.rma23.projekat.RequestBody
import ba.etf.rma23.projekat.data.repositories.AccountApiConfig.acRetrofit
import kotlinx.coroutines.*

object AccountGamesRepository {
    var age : Int = 0
    var userHash: String = "3a7c549a-2c44-4b7d-9377-2ffce1b9fa54"


    fun setHash(acHash: String):Boolean {
        userHash = acHash
        return true
    }

    fun getHash():String {
        return this.userHash
    }

    suspend fun getSavedGames():List<Game> = withContext(Dispatchers.IO) {
        try {
            val response = acRetrofit.getSavedGames()
            return@withContext response.body()!!
        } catch(e: Exception) {
            print(e.message)
            return@withContext emptyList()
        }
    }
    suspend fun saveGame(game: Game): Game = withContext(Dispatchers.IO) {
        try {
            var gameCopy = game
            val requestBody = RequestBody(game.id, game.title)
            val response = acRetrofit.saveGame(GameOn(requestBody))
            runBlocking {
                val gameResponse = CoroutineScope(Dispatchers.IO).async {
                    GamesRepository.getGameById(response.Gid)
                }
                val response = gameResponse.await()
                if (response.isNotEmpty()) {
                    gameCopy = response[0]
                }
            }
            return@withContext gameCopy
        } catch(e: Exception) {
            print(e.message)
            return@withContext Game(0, "", "", "", 0.0, "", "", "", "", "", "", emptyList())
        }
    }

    suspend fun removeGame(id: Int): Boolean = withContext(Dispatchers.IO) {
        try {
            val requestBody = acRetrofit.removeGame(id)
            return@withContext requestBody.body()!!
        } catch(e: Exception) {
            print(e.message)
            return@withContext true
        }
    }

    suspend fun getGamesContainingString(query: String):List<Game> {
        val foundList = mutableListOf<Game>()
        try {
            runBlocking {
                val favoriteGamesResponse = CoroutineScope(Dispatchers.IO).async {
                    getSavedGames()
                }
                val favoriteList = favoriteGamesResponse.await()
                if (favoriteList.isNotEmpty()) {
                    for(i in favoriteList.indices) {
                        val game = favoriteList[i]
                        if (game.title == query) foundList.add(game)
                    }
                }
            }
            return foundList

        }catch(e: java.lang.Exception) {
            print(e.message)
            return emptyList()
        }
    }

    suspend fun removeNonSafe(): Boolean {
        val age = age
        var favoriteList = emptyList<Game>()
        var game = Game(0,"","","",0.0,"","","",
            "", "", "", emptyList())
        var esrb = ""
        var esrbValue: EsrbRating
        var value = 0
        var ratingString: String

        try {
            runBlocking {
                favoriteList = getSavedGames()
            }
            if (favoriteList.isNotEmpty()) {
                for (i in favoriteList.indices) {
                    game = favoriteList[i]
                    if (game.esrbRating != "No ESRB rating") {
                        esrb = game.esrbRating!!
                        esrbValue = getEsrbRatingByString(esrb)!!
                        ratingString = esrbValue.name
                        value = realAge(ratingString)
                        if (value != -1) {
                            if (value > age) {
                                runBlocking {
                                    removeGame(game.id)
                                }
                            }
                        }
                    }
                }
            }
            return true
        } catch (e: Exception) {
            print(e.message)
            return true
        }
    }


    fun setAge(age: Int): Boolean {
        AccountGamesRepository.age = age
        if (AccountGamesRepository.age in 4..99) return true
        return false
    }

    fun getEsrbRatingByString(esrb: String): EsrbRating? {
        return EsrbRating.values().find { it.name == esrb }
    }

    fun realAge(value: String): Int {
        if (value == "Three") return 3
        if (value == "Seven") return 7
        if (value == "Twelve") return 12
        if (value == "Sixteen") return 16
        if (value == "Eighteen") return 18
        if (value == "RP") return 18
        if (value == "EC") return 3
        if (value == "E") return 0
        if (value == "E10") return 10
        if (value == "T") return 13
        if (value == "M") return 17
        if (value == "AO") return 18
        return -1
    }

}