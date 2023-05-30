package ba.etf.rma23.projekat.repositories

import ba.etf.rma23.projekat.EsrbRating
import ba.etf.rma23.projekat.Game
import ba.etf.rma23.projekat.GameOn
import ba.etf.rma23.projekat.RequestBody
import ba.etf.rma23.projekat.repositories.AccountApiConfig.acRetrofit
import kotlinx.coroutines.*

object AccountGamesRepository {
    var age : Int = 0
    var userHash: String = "3a7c549a-2c44-4b7d-9377-2ffce1b9fa54"

    fun postaviHash(acHash: String):Boolean {
        this.userHash = acHash
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
                    AccountGamesRepository.getSavedGames()
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
        val age = AccountGamesRepository.age
        var favoriteList = emptyList<Game>()
        var game = Game(0,"","","",0.0,"","","",
            "", "", "", emptyList())
        var esrb = ""
        var esrbValue: EsrbRating
        var value = 0

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
                        value = esrbValue.value
                        if (value > age) {
                            runBlocking {
                                removeGame(game.id)
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
        this.age = age
        if (this.age in 4..99) return true
        return false
    }

    fun getEsrbRatingByString(esrb: String): EsrbRating? {
        return EsrbRating.values().find { it.name == esrb }
    }


}