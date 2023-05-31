package ba.etf.rma23.projekat


import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository
import ba.etf.unsa.rma23.projekat.R
import ba.etf.rma23.projekat.data.repositories.GamesRepository
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val config: Configuration = baseContext.resources.configuration
        //AccountGamesRepository.setHash("ca0ee672-440b-45b2-8a12-75b80f4fbdd3")
       /* CoroutineScope(Job() + Dispatchers.Main).launch {
            val games = GamesRepository.displayedGameList
            print(games.size)
        }*/

      /* CoroutineScope(Job() + Dispatchers.Main).launch {
            val account = AccountGamesRepository
            account.setAge(18)
            val response = GamesRepository.getGamesSafe("Thief: Deadly Shadows")
            if (response.isNotEmpty()) {
                val game = response[0]
                print(game)
            }
        }
       CoroutineScope(Job() + Dispatchers.Main).launch {
            val rez = AccountGamesRepository.getGamesContainingString("Storybook Workshop")
            val je = rez[0].title
            val jee = rez[0].id
            val cover = rez[0].coverImage
            print(je)
            print(jee)
            print(cover)
        }
        CoroutineScope(Job() + Dispatchers.Main).launch {
            val response2 = AccountGamesRepository.getSavedGames()
            val b = response2.size
            print(b)
            val id1 = response2.get(0).id
            print(id1)
            val ime1 = response2.get(0).title
            print(ime1)
            val pl1 = response2.get(0).platform
            print(pl1)
            val rating1 = response2.get(0).rating
            print(rating1)
            val esrb1 = response2.get(0).esrbRating
            print(esrb1)
            val developer1 = response2.get(0).developer
            print(developer1)
            val pub1 = response2.get(0).publisher
            print(pub1)
        }
        var game = Game(3,"Thief: Deadly Shadows","","",0.0,"","","",
            "", "", "", emptyList())
        CoroutineScope(Job() + Dispatchers.Main).launch {
            AccountGamesRepository.setAge(10)
            AccountGamesRepository.removeNonSafe()

            val response = AccountGamesRepository.saveGame(game)
            print(response)

        }

         */

        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            /*super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_home)*/
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container_right, GameDetailsFragment()).commit()
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container_left, HomeFragment()).commit()
            //supportFragmentManager.beginTransaction().replace(R.id.fragment_container_right, GameDetailsFragment()).commit()
        } else {
            /*super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_home)*/
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            val navController = navHostFragment.navController
            val navView: BottomNavigationView = findViewById(R.id.bottom_nav)
            navView.setupWithNavController(navController)
        }
    }
}